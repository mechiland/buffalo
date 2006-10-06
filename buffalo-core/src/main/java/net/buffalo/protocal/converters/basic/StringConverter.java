package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.Converter;

public class StringConverter extends AbstractBasicConverter implements Converter{

	protected String getType() {
		return ProtocalTag.TAG_STRING;
	}
	
	

	public boolean canConvert(Class value) {
		if (value == null) return false;
		return value.equals(String.class) ||
				value.equals(char.class) ||
				value.equals(Character.class);
	}
	
	public Object fromString(String string) {
		return string;
	}

}
