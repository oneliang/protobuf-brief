package com.oneliang.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class OutputWriter {
	private byte[] dataHolder = null;
	private OutputStream output = null;
	private CodedOutputStream codedOutput = null;

	public OutputWriter(final byte[] dataHolder) {
		this.dataHolder = dataHolder;
		output = null;

		codedOutput = CodedOutputStream.newInstance(dataHolder);
	}

	public OutputWriter(final byte[] dataHolder, final OutputStream output) {
		this.output = output;
		this.dataHolder = dataHolder;

		codedOutput = CodedOutputStream.newInstance(this.dataHolder);
	}

	public void writeBoolean(final int id, final boolean value) throws IOException {
		codedOutput.writeBool(id, value);
	}

	public void writeByteString(final int id, final ByteString value) throws IOException {
		codedOutput.writeBytes(id, value);
	}

	public void writeDouble(final int id, final double value) throws IOException {
		codedOutput.writeDouble(id, value);
	}

	public void writeFloat(final int id, final float value) throws IOException {
		codedOutput.writeFloat(id, value);
	}

	public void writeInt(final int id, final int value) throws IOException {
		codedOutput.writeInt32(id, value);
	}

	public void writeLong(final int id, final long value) throws IOException {
		codedOutput.writeInt64(id, value);
	}

	public void writeString(final int id, final String value) throws IOException {
		codedOutput.writeString(id, value);
	}

	public void writeDelimitedSize(final int value) throws IOException {
		codedOutput.writeDelimitedSize(value);
	}

	public void writeMessage(final int id, final int value) throws IOException {
		codedOutput.writeMessage(id, value);
	}

	/**
	 * Try to avoid this since it involves lots of casts, may impact
	 * performance.
	 * 
	 * @param id
	 * @param dataType
	 * @param list
	 * @throws IOException
	 */
	public void writeList(final int id, final DataType dataType, final List<?> list) throws IOException {
		if (list != null) {
			switch (dataType) {
			case BYTE_ARRAY:
				for (int i = 0; i < list.size(); i++) {
					writeByteString(id, (ByteString) list.get(i));
				}
				break;
			case DOUBLE:
				for (int i = 0; i < list.size(); i++) {
					writeDouble(id, ((Double) list.get(i)).doubleValue());
				}
				break;
			case FLOAT:
				for (int i = 0; i < list.size(); i++) {
					writeFloat(id, ((Float) list.get(i)).floatValue());
				}
				break;
			case INT:
				for (int i = 0; i < list.size(); i++) {
					writeInt(id, ((Integer) list.get(i)).intValue());
				}
				break;
			case LONG:
				for (int i = 0; i < list.size(); i++) {
					writeLong(id, ((Long) list.get(i)).longValue());
				}
				break;
			case STRING:
				for (int i = 0; i < list.size(); i++) {
					writeString(id, (String) list.get(i));
				}
				break;
			case BOOLEAN:
				for (int i = 0; i < list.size(); i++) {
					writeBoolean(id, ((Boolean) list.get(i)).booleanValue());
				}
				break;
			case OBJECT:
				for (int i = 0; i < list.size(); i++) {
					MessageLite customList = (MessageLite) list.get(i);
					writeMessage(id, customList.computeSize());
					customList.writeFields(this);
				}
				break;
			default:
				throw new IllegalArgumentException("The data type was not found, the id used was " + dataType);
			}
		}
	}

	public void writeData() throws IOException {
		if (output != null) {
			output.write(dataHolder);
			output.flush();
		}
	}
}
