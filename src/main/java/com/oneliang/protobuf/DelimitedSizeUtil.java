package com.oneliang.protobuf;

import java.io.IOException;
import java.io.InputStream;

public final class DelimitedSizeUtil {
	
	private DelimitedSizeUtil() {
	}
	
	public static int readDelimitedSize(final InputStream input) throws IOException {
		return CodedInputStream.readDelimitedSize(input);
	}
}
