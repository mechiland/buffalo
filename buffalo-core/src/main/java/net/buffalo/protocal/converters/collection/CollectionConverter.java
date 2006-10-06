package net.buffalo.protocal.converters.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.TypeNotFoundException;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;
import net.buffalo.protocal.util.ClassUtil;

public class CollectionConverter extends AbstractListConverter implements Converter {

	public boolean canConvert(Class type) {
		if (type == null)
			return false;
		return Collection.class.isAssignableFrom(type);
	}

	public void marshalObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		Collection collection = (Collection) value;
		writeListHeader(streamWriter, collection.getClass().getName(), collection.size());
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			context.convertAnother(iter.next());
		}
		streamWriter.endNode();
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		reader.moveDown();
		String type = reader.getValue();
		reader.moveUp();
		reader.moveDown();
		int length = Integer.valueOf(reader.getValue()).intValue();
		reader.moveUp();
		
		if (type.startsWith("[")) {
			return dealWithArray(unmarshallingContext, reader, type, length);
		}

		if (type.equals("") || type.equals("java.util.List") || type.equals("java.util.Collection")) { 
			type = "java.util.ArrayList";
		}
		
		Collection collection = (Collection) ClassUtil.newInstanceOfType(type);
		unmarshallingContext.addObject(collection);
		
		while(reader.hasMoreChildren()) {
			collection.add(unmarshallingContext.convertAnother());
		}
		
		return collection;
	}

	private Object dealWithArray(UnmarshallingContext unmarshallingContext, StreamReader reader, 
			String type, int length) {
		Class arrayType = arrayType(type);
		Object retObjects = Array.newInstance(arrayType.getComponentType(),	length);
		unmarshallingContext.addObject(retObjects);
		int index = 0;
		while(reader.hasMoreChildren()) {
			Object obj = unmarshallingContext.convertAnother();
			Array.set(retObjects, index++, obj);
		}
		return retObjects;
	}

	private Class arrayType(String type) {
		int idx = type.lastIndexOf("[") + 1;
		String classType = type.substring(idx);
		StringBuffer className = new StringBuffer();
		className.append(type.substring(0, idx));
		className.append("L");

		classType = classType.equals(ProtocalTag.TAG_STRING) ? "java.lang.String" : 
					classType.equals(ProtocalTag.TAG_INT) ? "java.lang.Integer" : 
					classType.equals(ProtocalTag.TAG_LONG) ? "java.lang.Long" : 
					classType.equals(ProtocalTag.TAG_DOUBLE) ? "java.lang.Double" : 
					classType.equals(ProtocalTag.TAG_DATE) ? "java.util.Date" : classType;

		className.append(classType);
		className.append(";");
		
		try {
			return Class.forName(className.toString());
		} catch (ClassNotFoundException e) {
			throw new TypeNotFoundException(classType, e);
		}
	}
}
