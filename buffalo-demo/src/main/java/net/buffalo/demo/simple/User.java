/*
 * Created on 2005-2-27
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
public class User {
	private int id;
	private String name;
	private int age;
	private boolean sex;
	private String memo;
	
	public User() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param age
	 * @param sex
	 * @param memo
	 */
	public User(int id, String name, int age, boolean sex, String memo) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.memo = memo;
	}
	/**
	 * @return Returns the age.
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age The age to set.
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the sex.
	 */
	public boolean isSex() {
		return sex;
	}
	/**
	 * @param sex The sex to set.
	 */
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public String toString() {
	    return super.toString() + "{name:" + name + ", id:" + id
				+ ", sex:" + sex + ", age:" + age + ", memo:" + memo + "}";
	}
}
