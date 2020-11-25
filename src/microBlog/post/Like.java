package microBlog.post;

import java.util.Date;


import microBlog.network.PermissionDeniedException;
import microBlog.user.User;


/**
 * Modella un like che un utente può aggiungere al post di un altro utente.
 * A differenza di un TextPost, un Like non viene pubblicato come post a se' stante,
 * ma come like di un altro post, e non è possibile aggiungere like ad un Like.
 * @RepInvariant id &gt; 0 &amp; author != null &amp; text != null &amp;
 * 				   per ogni &lt; tag &gt; t.c. tags &lt; tag &gt; != null allora tag!=null
 * @AbstractFunction f(id, author, post, receiver, timestamp) = 
 * 					  &lt;codice_identificativo, User(), TextPost(), User(), Data()&gt; 
 * @author Salvatore Correnti
 * 
 */
public final class Like {

	//Massimo id usato finora
	private static int currentMaxId = 1;
	
	private final int id;
	private final User author;
	private final Post post;
	private final User receiver;
	private final Date timestamp;
	
	/**
	 * Costruttore pubblico di default per Like.
	 * @requires author != null &amp; post != null &amp; this.getAuthor() e
	 * post.getAuthor() devono essere entrambi registrati alla stessa rete sociale
	 * @param author L'autore del Like.
	 * @param post Il post a cui è stato messo Like.
	 * @throws NullPointerException se author == null || post==null
	 * @throws PermissionDeniedException se author non ha mandato la richiesta || 
	 * 		   se post.getAuthor().getUsername()== author.getUsername()
	 */
	public Like(User author, Post post) {
		if (author == null) throw new NullPointerException();
		if (post == null) throw new NullPointerException();
		if (post.getAuthor().getUsername()== author.getUsername()) throw new PermissionDeniedException();
		if (!author.hasSentRequest()) throw new PermissionDeniedException();
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
	
	/**
	 * @requires obj!=null
	 * @param obj a cui comparare this.
	 * @throws NullPointerException se obj==null
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Like other = (Like) obj;
		if (this.id != other.id)
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
}