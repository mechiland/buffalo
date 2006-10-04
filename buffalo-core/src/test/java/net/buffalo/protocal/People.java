package net.buffalo.protocal;

public class People {
	private String name;
	private int age;
	private boolean sex;
	
	private People friend;
	
	public People getFriend() {
		return friend;
	}

	public void setFriend(People friend) {
		this.friend = friend;
	}

	public People() {}
	
	public People(String name, int age, boolean sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	
}
