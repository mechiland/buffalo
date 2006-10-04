package net.buffalo.protocal.io;

import java.util.ArrayList;
import java.util.List;

import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.converters.ConverterLookup;

public class DefaultMarshallingContext implements MarshallingContext {
	
	private List objects = new ArrayList();
	private final ConverterLookup converterLookup;
	private final StreamWriter streamWriter;

	public DefaultMarshallingContext(ConverterLookup converterLookup, StreamWriter streamWriter) {
		this.converterLookup = converterLookup;
		this.streamWriter = streamWriter;
	}

	public void convertAnother(Object value) {
		if (value == null) {
			converterLookup.getNullConverter().marshal(value, this, streamWriter);
		} else {
			Converter converter = converterLookup.lookupConverterForType(value.getClass());
			converter.marshal(value, this, streamWriter);
		}
	}

	public List getObjects() {
		return this.objects;
	}

	public void addObjectRef(Object object) {
		this.objects.add(object);
	}

}
