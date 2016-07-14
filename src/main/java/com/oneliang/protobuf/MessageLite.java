package com.oneliang.protobuf;

import java.io.IOException;

public abstract interface MessageLite {

	/**
	 * compute size
	 * 
	 * @return int
	 */
	public abstract int computeSize();

	/**
	 * write fields
	 * 
	 * @param outputWriter
	 * @throws IOException
	 */
	public abstract void writeFields(final OutputWriter outputWriter) throws IOException;

	/**
	 * parse fields
	 * 
	 * @param reader
	 * @return T
	 * @throws IOException
	 */
	public abstract void parseFields(final InputReader reader) throws IOException;
}
