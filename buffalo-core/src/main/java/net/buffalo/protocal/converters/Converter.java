package net.buffalo.protocal.converters;

import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public interface Converter {
	
	boolean canConvert(Class type);

	void marshal(Object source, MarshallingContext marshallingContext, StreamWriter streamWriter);
	
	Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext);
}