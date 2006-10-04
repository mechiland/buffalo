/*
 * Created on 2005-2-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.buffalo.demo.simple;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Name {
	private String firstName;
	private String middleName;
	private String familyName;
	
	
	/**
	 * @param firstName
	 * @param middleName
	 * @param familyName
	 */
	public Name(String firstName, String middleName, String familyName) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.familyName = familyName;
	}
	/**
	 * @return Returns the familyName.
	 */
	public String getFamilyName() {
		return familyName;
	}
	/**
	 * @param familyName The familyName to set.
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
}
