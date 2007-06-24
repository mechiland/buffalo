/*
 * Created on 2005-2-27
 *
 */
package net.buffalo.demo.simple;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author michael
 *
 */
public class SimpleService {
	
	public double divide(double a, double b) {
		System.out.println("Calling Divide...,a="+a+", b="+b);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a/b;
	}

	public Date now() {
		return new Date();
	}
	
	public void fault() throws IOException {
		throw new IOException("Test Exception");
	}
	
	public User randomUser() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return UserUtil.randomUser();
	}
	
	public ComplexUser randomComplexUser() {
		return UserUtil.randomComplexUser();
	}
	
	public Locale[] allLocales() {
        return Locale.getAvailableLocales();
	}
	
	public List provincesNames() {
	    return ProvinceUtil.provinceNames();
	}
	
	public String[]  provinceCities(String name) {
	    return ProvinceUtil.getProvince(name).getCityList();
	}
	
	public Map object(Map arg) {
	    System.out.println(arg);
	    return arg;
	}
	
	public String overloadMethod(int i) {
		String result = "An integer value: " + i;
		System.out.println(result);
		return result;
	}
	
	public String overloadMethod(String value) {
		String result = "An integer value: " + value;
		System.out.println(result);
		return result;
	}
	
	public void timeoutTest() throws Exception {
		// just sleep
		Thread.sleep(6000);
	}
	
	public int testArrayParameter(List l, String xh) {
		return 1;
	}
	
}
