/*
 * Created on 2005-2-28
 *
 */
package net.buffalo.demo.gbook;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * @author michael
 *
 * 
 */
public class PostDAOMemoryImpl implements PostDAO {
	
	private static List posts ;
	
	public PostDAOMemoryImpl(ServletContext context) {
		Object o = context.getAttribute("posts");
		if (o == null) {
			posts = new ArrayList();
			context.setAttribute("posts", posts);
		} else {
			posts = (List)o;
		}
			
	} 
	
	public void store(Post post) {
		posts.add(post);
	}

	/* 
	 * @see net.buffalo.demo.gbook.PostDAO#load(long)
	 */
	public Post load(long id) {
		Post post = null;
		for (int i = 0; i < posts.size(); i++) {
			Post p = (Post)(posts.get(i));
			if (p.getId().longValue() == id) {
				post = p;
				break;
			}
		}
		return post;
	}

	/* 
	 * @see net.buffalo.demo.gbook.PostDAO#listAll()
	 */
	public Post[] listAll() {

		return (Post[]) (posts.toArray(new Post[0]) );
	}

	/* 
	 * @see net.buffalo.demo.gbook.PostDAO#list(int, int)
	 */
	public Post[] list(int pageSize, int page) {
		
		return (Post[]) (posts.toArray(new Post[0]) );
	}

}
