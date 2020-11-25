package microBlog.user;

import java.util.Date;
import java.util.Set;

import microBlog.network.*;
import microBlog.post.Post;
import microBlog.post.PostException;
import microBlog.post.Tag;
import microBlog.post.TextPost;

/**
 * Implementazione dell'interfaccia User.
 * @RepInvariant username != null &amp; registrationDate != null &amp; net != null &amp; 
 * @AbstractFunction f(username, registrationDate, net, sentRequest) = \lt; nome_utente, Date(), SocialNetwork(), false/true \gt; 
 * @author Salvatore Correnti
 * 
 * @see User
 *
 */
public class UserImpl implements User {
	
	private final String username;
	private final Date registrationDate;
	private SocialNetwork net;
	private boolean sentRequest = false;
	
	/**
	 * @requires username != null &amp; net != null &amp; !net.isRegistered(username) 
	 * @param username Lo username che l'utente adotta
	 * @param net La rete a cui l'utente si registra.
	 * @throws NullPointerException se username == null
	 * @throws NullPointerException se net == null
	 * @throws UserException se net.isRegistered(username)
	 * @throws IllegalArgumentException se username contiene uno spazio
	 * @effects Viene aggiunto a net un nuovo utente il cui nome è username
	 */
	public UserImpl(String username, SocialNetwork net) {
		if (username == null) throw new NullPointerException();
		if (net == null) throw new NullPointerException();
		if (net.isRegistered(username)) throw new UserException();
		if (username.indexOf(' ') != -1) 
			throw new IllegalArgumentException("Uno username non può contenere spazi bianchi!");
		this.username = username;
		this.net = net;
		this.sentRequest = true;
		this.net.registerUser(this);
		this.sentRequest = false;
		this.registrationDate = new Date();
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((net == null) ? 0 : net.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImpl other = (UserImpl) obj;
		if (net == null) {
			if (other.net != null)
				return false;
		} else if (!net.equals(other.net))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	public String getUsername() {
		return this.username;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public boolean hasSentRequest() {
		return this.sentRequest;
	}

	public int writePost(String text) {
		if (text == null) throw new NullPointerException();
		if (!Post.checkTextLength(text)) throw new PostException("Il tuo messaggio"
				+ " è troppo lungo");
		Set<Tag> st = Tag.getTagsFromText(text);
		for (Tag t : st) {
			if (!this.net.isRegistered(t.getTagText())) 
				throw new PostException("Hai taggato un utente inesistente!");
		}
		// tp != null sempre ed è immutable, quindi lo si può ritornare con sicurezza 
		Post p = new TextPost(this, text);
		this.sentRequest = true;
		this.net.storePost(p);
		this.sentRequest = false;
		return p.getId();
	}

	public boolean removePost(int id) {
		//getPost lancia tutte le eccezioni possibili
		Post p = this.net.getPost(id, this.getUsername());
		//Se si prosegue, vuol dire che è tutto ok
		this.sentRequest = true;
		boolean b = this.net.removePost(p);
		this.sentRequest = false;
		return b;
	}
	
	public boolean addLike(int id, String username) {
		if (id <= 0) throw new IllegalArgumentException();
		if (username == null) throw new NullPointerException();
		if (!this.net.isRegistered(username)) throw new UserException();
		if (this.getUsername().equals(username)) throw new PermissionDeniedException();
		Post p = this.net.getPost(id, username);
		this.sentRequest = true;
		boolean b = this.net.addLike(this, p);
		this.sentRequest = false;
		return b;
	}
}