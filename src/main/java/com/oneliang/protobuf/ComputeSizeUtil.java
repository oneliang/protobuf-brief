package com.oneliang.protobuf;

import java.util.List;

public final class ComputeSizeUtil {

	private ComputeSizeUtil() {
	}

	public static int computeDelimitedIntSize(final int value) {
		return CodedOutputStream.computeRawVarint32Size(value);
	}

	public static int computeIntSize(final int id, final int value) {
		return CodedOutputStream.computeInt32Size(id, value);
	}

	public static int computeStringSize(final int id, final String value) {
		return CodedOutputStream.computeStringSize(id, value);
	}

	public static int computeBooleanSize(final int id, final boolean value) {
		return CodedOutputStream.computeBoolSize(id, value);
	}

	public static int computeDoubleSize(final int id, final double value) {
		return CodedOutputStream.computeDoubleSize(id, value);
	}

	public static int computeFloatSize(final int id, final float value) {
		return CodedOutputStream.computeFloatSize(id, value);
	}

	public static int computeLongSize(final int id, final long value) {
		return CodedOutputStream.computeInt64Size(id, value);
	}

	public static int computeByteStringSize(final int id, final ByteString value) {
		return CodedOutputStream.computeBytesSize(id, value);
	}

	public static int computeMessageSize(final int id, final int value) {
		return CodedOutputStream.computeTagSize(id) + CodedOutputStream.computeRawVarint32Size(value) + value;
	}

	public static int computeListSize(final int id, final DataType dataType, final List<?> list) {
		int listSize = 0;

		if (list != null) {
			switch (dataType) {
			case BYTE_ARRAY:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeByteStringSize(id, (ByteString) list.get(i));
				}
				break;
			case DOUBLE:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeDoubleSize(id, ((Double) list.get(i)).doubleValue());
				}
				break;
			case FLOAT:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeFloatSize(id, ((Float) list.get(i)).floatValue());
				}
				break;
			case INT:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeIntSize(id, ((Integer) list.get(i)).intValue());
				}
				break;
			case LONG:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeLongSize(id, ((Long) list.get(i)).longValue());
				}
				break;
			case STRING:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeStringSize(id, (String) list.get(i));
				}
				break;
			case BOOLEAN:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeBooleanSize(id, ((Boolean) list.get(i)).booleanValue());
				}
				break;
			case OBJECT:
				for (int i = 0; i < list.size(); i++) {
					listSize += computeMessageSize(id, ((MessageLite) list.get(i)).computeSize());
				}
				break;
			default:
				throw new IllegalArgumentException("The data type was not found, the id used was " + dataType);
			}
		}

		return listSize;
	}
}
