package microBlog.post;

import java.util.Date;

import microBlog.user.User;

public interface Publishable {
	/**
	 * @requires 
	 * @return L'id del post.
	 */
	public int getId();
	
	/**
	 * @requires
	 * @return L'autore del post.
	 */
	public User getAuthor();
	
	/**
	 * @requires
	 * @return La data e l'ora di pubblicazione del post.
	 */
	public Date getTimeStamp();
}
