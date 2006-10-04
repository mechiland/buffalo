/*
 * Created on 2005-2-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.buffalo.demo.gbook;

import java.util.Date;

/**
 * @author michael
 *
 */
public class Post {
	private Long id;
	private String author;
	private String email;
	private String content;
	private Date time;
	
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param author
	 * @param email
	 * @param content
	 */
	public Post(String author, String email, String content) {
		this.author = author;
		this.email = email;
		this.content = content;
		this.time = new Date();
	}
	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the time.
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time The time to set.
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}
