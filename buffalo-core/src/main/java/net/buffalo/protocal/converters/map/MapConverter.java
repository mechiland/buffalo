package net.buffalo.protocal.converters.map;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;

import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;
import net.buffalo.protocal.util.ClassUtil;
import net.buffalo.protocal.util.DateUtil;

public class MapConverter extends AbstractMapConverter implements Converter {

	public boolean canConvert(Class type) {
		if (type == null)
			return false;
		return Map.class.isAssignableFrom(type);
	}

	public void marshalMapObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		Map map = (Map) value;
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			Object val = map.get(key);
			context.convertAnother(key);
			context.convertAnother(val);
		}
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		reader.moveDown();
		String type = reader.getValue();
		reader.moveUp();
		
		if (type.equals("java.sql.Date")) {
			return dealWithSqlDate(reader, unmarshallingContext);
		} else if (type.equals("java.math.BigDecimal") || type.equals("java.math.BigInteger")) {
			return dealWithBigNumber(reader, unmarshallingContext, type);
		} 
		
		if (type.equals("") || type.equals("java.util.Map")) type = "java.util.HashMap";
		
		Object obj = ClassUtil.newInstanceOfType(type);
		unmarshallingContext.addObject(obj);
		
		while (reader.hasMoreChildren()) {
			if (obj instanceof Map) {
				((Map) obj).put(unmarshallingContext.convertAnother(),unmarshallingContext.convertAnother());
			} else {
				String fieldName = (String) unmarshallingContext.convertAnother();
				Object value = unmarshallingContext.convertAnother();
				ClassUtil.setFieldValue(obj, fieldName, value);
			}
		}
		
		return obj;
	}

	private Object dealWithBigNumber(StreamReader reader, UnmarshallingContext unmarshallingContext, String type) {
		reader.moveDown();
		reader.moveUp();
		reader.moveDown();
		Object number;
		if (type.equals("java.math.BigDecimal")) {
			number = new BigDecimal(reader.getValue());
		} else {
			number = new BigInteger(reader.getValue());
		}
		unmarshallingContext.addObject(number);
		reader.moveUp();
		return number;
	}

	private Object dealWithSqlDate(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		reader.moveDown();
		reader.moveUp();
		reader.moveDown();
		java.util.Date result = DateUtil.fromUTCString(reader.getValue());
		java.sql.Date sqlDate = new java.sql.Date(result.getTime());
		unmarshallingContext.addObject(sqlDate);
		reader.moveUp();
		return sqlDate;
	}

}
