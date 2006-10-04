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
public class ComplexUser {
	private Name name;
	private ComplexUser[] friends;
	
	/**
	 * @param name
	 * @param friends
	 */
	public ComplexUser(Name name, ComplexUser[] friends) {
		super();
		this.name = name;
		this.friends = friends;
	}
	
	/**
	 * @param name
	 * @param friends
	 */
	public ComplexUser(Name name) {
		super();
		this.name = name;
		this.friends = new ComplexUser[0];
	}
	
	/**
	 * @return Returns the friends.
	 */
	public ComplexUser[] getFriends() {
		return friends;
	}
	/**
	 * @param friends The friends to set.
	 */
	public void setFriends(ComplexUser[] friends) {
		this.friends = friends;
	}
	/**
	 * @return Returns the name.
	 */
	public Name getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(Name name) {
		this.name = name;
	}
}
