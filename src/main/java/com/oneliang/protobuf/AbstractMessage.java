package com.oneliang.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractMessage implements MessageLite {

	protected static final UnknownTagHandler UNKNOWN_TAG_HANDLER = DefaultUnknownTagHandlerImpl.newInstance();

	public byte[] toByteArray() throws IOException {
		final byte[] outputData = new byte[computeSize()];
		writeTo(outputData);
		return outputData;
	}

	public void writeTo(final byte[] data) throws IOException {
		final OutputWriter writer = new OutputWriter(data);
		writeFields(writer);
	}

	public void writeTo(final OutputStream outputStream) throws IOException {
		final byte[] outputData = new byte[computeSize()];
		final OutputWriter writer = new OutputWriter(outputData, outputStream);
		writeFields(writer);
	}

	public void writeDelimitedTo(final OutputStream outputStream) throws IOException {
		final int dataSize = computeSize();
		final int delimitedSize = dataSize + ComputeSizeUtil.computeDelimitedIntSize(dataSize);
		final byte[] outputData = new byte[delimitedSize];
		final OutputWriter writer = new OutputWriter(outputData, outputStream);
		writer.writeDelimitedSize(dataSize);
		writeFields(writer);
	}

	public void parseFields(final InputReader reader) throws IOException {
		int nextFieldNumber = reader.getNextFieldNumber();
		while (nextFieldNumber > 0) {
			if (!foundAndParseField(reader, nextFieldNumber)) {
				reader.getPreviousTagDataTypeAndReadContent();
			}
			nextFieldNumber = reader.getNextFieldNumber();
		}
	}

	/**
	 * found and parse field
	 * @param reader
	 * @param fieldNumber
	 * @return boolean,default true,if true found the field,else not found the field
	 * @throws IOException
	 */
	public abstract boolean foundAndParseField(final InputReader reader, final int fieldNumber) throws IOException;

	public void parseFrom(final byte[] data) throws IOException {
		parseFields(new InputReader(data, UNKNOWN_TAG_HANDLER));
	}

	public void parseFrom(final InputStream inputStream) throws IOException {
		parseFields(new InputReader(inputStream, UNKNOWN_TAG_HANDLER));
	}

	public void parseDelimitedFrom(final InputStream inputStream) throws IOException {
		final int limit = DelimitedSizeUtil.readDelimitedSize(inputStream);
		parseFields(new InputReader(new DelimitedInputStream(inputStream, limit), UNKNOWN_TAG_HANDLER));
	}
}
