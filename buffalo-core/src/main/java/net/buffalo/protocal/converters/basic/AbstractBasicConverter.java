package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public abstract class AbstractBasicConverter implements Converter {
	
	public void marshal(Object source, MarshallingContext context, StreamWriter streamWriter) {
		streamWriter.startNode(getType());
		streamWriter.setValue(source.toString());
		streamWriter.endNode();
	}

	protected String getType() {
		throw new UnsupportedOperationException();
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		return fromString(reader.getValue());
	}

	public abstract Object fromString(String string);

}
