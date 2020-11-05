package microBlog.post;

import java.util.Set;

public interface Post {

	/**
	 * @requires 
	 * @return L'id del post.
	 */
	public int getId();
	
	/**
	 * @requires
	 * @return L'autore del post.
	 */
	public String getAuthor();
	
	/**
	 * @requires
	 * @return Il testo del post.
	 */
	public String getText();
	
	/**
	 * @requires
	 * @return Il livello di visibilità del post (solo l'autore, followers, pubblico).
	 */
	public Visibility getVisibilityScope();
	
	/**
	 * @requires
	 * @return La data e l'ora di pubblicazione del post.
	 */
	public String getTimeStamp(); //Rivedere meglio
	
	/**
	 * @requires
	 * @return L'insieme dei tag nel post.
	 */
	public Set<Tag> getTags();
}