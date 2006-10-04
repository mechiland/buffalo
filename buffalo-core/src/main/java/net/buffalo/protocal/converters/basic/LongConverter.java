package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.converters.Converter;

public class LongConverter extends AbstractBasicConverter implements Converter {

	protected String getType() {
		return "long";
	}
	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(long.class) || 
				value.equals(Long.class);
	}
	
	public Object fromString(String string) {
		return new Long(string);
	}
	
}
