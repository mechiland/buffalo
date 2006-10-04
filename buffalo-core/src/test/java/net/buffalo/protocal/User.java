package net.buffalo.protocal;

import java.util.List;

public class User {
	private String username;
	private String password;
	private String gendor;
	private List interests;
	private String[] roles;
	
	public User() {};
	
	public User(String gendor, List interests, String password, String[] roles, String username) {
		super();
		this.gendor = gendor;
		this.interests = interests;
		this.password = password;
		this.roles = roles;
		this.username = username;
	}
	public String isGendor() {
		return gendor;
	}
	public void setGendor(String gendor) {
		this.gendor = gendor;
	}
	public List getInterests() {
		return interests;
	}
	public void setInterests(List interests) {
		this.interests = interests;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return super.toString()+"{username:" + username + ", password:" + password + ", gendor:"
				+ gendor + ",interests:" + interests + ",roles:" + roles + "}";
	}
	
}
