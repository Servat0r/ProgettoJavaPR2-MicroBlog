package microBlog.post;

import java.util.Date;
import microBlog.user.User;


/**
 * Modella un like che un utente può aggiungere al post di un altro utente.
 * 
 * @author Salvatore Correnti
 * 
 */
public final class Like implements Post {

	//Massimo id usato finora
	private static int currentMaxId = 1;
	
	private final int id;
	private final User author;
	private final Post post;
	private final User receiver;
	private final Date timestamp;
	
	//TODO Aggiungere eccezioni
	/**
	 * Costruttore pubblico di default per Like.
	 * @requires author != null &amp; post != null &amp; this.getAuthor() e
	 * post.getAuthor() devono essere entrambi registrati alla stessa rete sociale
	 * @param author L'autore del Like.
	 * @param post Il post a cui è stato messo Like.
	 */
	public Like(User author, Post post) {
		this.id = Like.currentMaxId++;
		this.author = author;
		this.post = post;
		this.receiver = post.getAuthor();
		this.timestamp = new Date();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Like other = (Like) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * @requires
	 * @return L'id del like
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @requires 
	 * @return L'autore del like
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @return Il post associato al like
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @requires
	 * @return Data e ora di aggiunta del like
	 */
	public Date getTimeStamp() {
		return timestamp;
	}

	/**
	 * @requires
	 * @return L'autore del post che ha ricevuto like.
	 */
	public User getReceiver() {
		return receiver;
	}

	@Override
	public String getText() {
		throw new UnsupportedOperationException();
	}
}