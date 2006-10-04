package net.buffalo.protocal.converters.collection;

import java.util.Date;

import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;
import net.buffalo.protocal.util.PrimitiveTypeUtil;

public class ArrayConverter extends AbstractListConverter implements Converter {

	public boolean canConvert(Class type) {
		if (type == null)
			return false;

		return type.isArray();
	}
	
	public void marshalObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		Object[] array = (Object[]) PrimitiveTypeUtil.toWrapperArrayIfNeeded(value);
		writeListHeader(streamWriter, getArrayType(value.getClass()), array.length);
		for (int i = 0; i < array.length; i++) {
			context.convertAnother(array[i]);
		}
		streamWriter.endNode();
	}
	
	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		throw new UnsupportedOperationException("the CollectionConverter has done it already");
	}

	private String getArrayType(Class clazz) {
		if (clazz.isArray())
			return '[' + getArrayType(clazz.getComponentType());

		String name = clazz.getName();

		if (clazz.equals(String.class))
			return "string";
		else if (clazz.equals(Object.class))
			return "object";
		else if (clazz.equals(Date.class))
			return "date";
		else if (clazz.equals(short.class) || clazz.equals(byte.class) 
				|| clazz.equals(Short.class) || clazz.equals(Byte.class) 
				|| clazz.equals(Integer.class)) 
			return "int"; 
		else if (clazz.equals(Long.class)) 
			return "long";
		else if (clazz.equals(float.class) || clazz.equals(Float.class)
				|| clazz.equals(double.class) || clazz.equals(Double.class)) 
			return "double"; 
		else if (clazz.equals(Boolean.class)) 
			return "boolean";
		
		return name;
	}

}
