package net.buffalo.protocal.converters.basic;

import java.util.Date;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.util.DateUtil;

public class DateConverter extends AbstractBasicConverter implements Converter {

	public boolean canConvert(Class value) {
		if (value == null)
			return false;
		return value.equals(Date.class);
	}

	public void marshal(Object value, MarshallingContext context, StreamWriter streamWriter) {
		long time = ((Date) value).getTime();
		streamWriter.startNode(ProtocalTag.TAG_DATE);
		streamWriter.setValue(DateUtil.toUTCString(time));
		streamWriter.endNode();
	}

	public Object fromString(String string) {
		return DateUtil.fromUTCString(string);
	}

}
