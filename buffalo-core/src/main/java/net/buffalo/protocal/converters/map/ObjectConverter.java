package net.buffalo.protocal.converters.map;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.buffalo.protocal.AccessFieldException;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class ObjectConverter extends AbstractMapConverter implements Converter {

	public boolean canConvert(Class type) {
		if (type == null)
			return false;
		return Object.class.isAssignableFrom(type);
	}

	protected void marshalMapObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		Class clazz = value.getClass();
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];

				if (Modifier.isTransient(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers()))
					continue;
				field.setAccessible(true);
				context.convertAnother(field.getName());
				try {
					context.convertAnother(field.get(value));
				} catch (Exception e) {
					throw new AccessFieldException("error accessing property ["+field.getName()
							+"] of class ["+clazz+"]",e);
				}
			}
		}		
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		throw new UnsupportedOperationException("the MapConverter has done it already");
	}

}
