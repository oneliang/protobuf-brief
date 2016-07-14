package com.oneliang.protobuf;

import java.io.IOException;
import java.io.InputStream;

public class DelimitedInputStream extends InputStream {
	private final InputStream input;
	private int limit;

	public DelimitedInputStream(InputStream input, int limit) {
		this.input = input;
		this.limit = limit;
	}

	public int available() throws IOException {
		return Math.min(super.available(), limit);
	}

	public int read() throws IOException {
		if (limit <= 0)
			return -1;
		int result = input.read();
		if (result >= 0)
			--limit;
		return result;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (limit <= 0)
			return -1;
		len = Math.min(len, limit);
		int result = super.read(b, off, len);
		if (result >= 0)
			limit -= result;
		return result;
	}

	public long skip(long n) throws IOException {
		long result = super.skip(Math.min(n, limit));
		if (result >= 0)
			limit -= result;
		return result;
	}
}
