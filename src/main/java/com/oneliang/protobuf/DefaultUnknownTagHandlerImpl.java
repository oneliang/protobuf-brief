package com.oneliang.protobuf;

public class DefaultUnknownTagHandlerImpl implements UnknownTagHandler {

	public static DefaultUnknownTagHandlerImpl newInstance() {
		return new DefaultUnknownTagHandlerImpl();
	}

	private DefaultUnknownTagHandlerImpl() {
	}

	public void unknownTag(final String unknownTag) {
	}
}
