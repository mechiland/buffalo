package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class DoubleConverter extends AbstractBasicConverter implements Converter{

	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(double.class) || 
				value.equals(Double.class) ||
				value.equals(float.class) ||
				value.equals(Float.class);
	}
	
	protected String getType() {
		return ProtocalTag.TAG_DOUBLE;
	}

	public Object fromString(String string) {
		return Double.valueOf(string);
	}
	
}
