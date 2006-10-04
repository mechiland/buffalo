/*
 * Created on 2005-2-28
 *
 */
package net.buffalo.demo.gbook;

/**
 * @author michael
 * 
 */
public interface PostDAO {
	public void store(Post post);
	public Post load(long id);
	public Post[] listAll();
	public Post[] list(int pageSize, int page);
}
