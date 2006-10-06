package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class NullConverter implements Converter{

	public boolean canConvert(Class type) {
		return type == null;
	}

	public void marshal(Object object, MarshallingContext context, StreamWriter streamWriter) {
		streamWriter.startNode(ProtocalTag.TAG_NULL);
		streamWriter.endNode();
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		return null;
	}

}
