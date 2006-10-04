/*
 * Created on 2005-2-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.buffalo.demo.simple;

import java.util.Random;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserUtil {
	private static User[] users = {
		new User(1,"Zhang Sanfeng", 90, true, "Founder of Wudang"),
		new User(2,"Linghu Chong", 30, true, "Member of Huashan"),
		new User(3,"Qiao Feng", 36, true, "Leader of Gai Bang"),
		new User(4,"Zhao Linger", 16, false, "Daughter of Nvwa"),
		new User(5,"Su Rongrong", 24, false, "Full of knowledge, girl friend of Chu Liuxiang"),
	};

	private static ComplexUser[] complexUsers = {
		new ComplexUser(new Name("John","M","Smith")),
		new ComplexUser(new Name("Friend","H","Johnson")),
		new ComplexUser(new Name("Michael","J","Jordon")),
	};
	
	static {
		complexUsers[0].setFriends( new ComplexUser[]{complexUsers[1],complexUsers[2]} );
		complexUsers[1].setFriends( new ComplexUser[]{complexUsers[0],complexUsers[2]} );
		complexUsers[2].setFriends( new ComplexUser[]{complexUsers[1],complexUsers[0]} );
	}
	
	public static User randomUser() {
		int r = new Random().nextInt(5);
		return users[r];
	}
	
	public static ComplexUser randomComplexUser() {
		int r = new Random().nextInt(3);
		return complexUsers[r];
		
	}
	
}
