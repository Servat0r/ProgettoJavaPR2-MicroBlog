package microBlog.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import microBlog.network.*;
import microBlog.post.Like;
import microBlog.post.Post;
import microBlog.post.PostException;
import microBlog.post.Tag;
import microBlog.post.TextPost;

/**
 * Implementazione dell'interfaccia User.
 * 
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
			throw new IllegalArgumentException("Usernames cannot contain white spaces!");
		this.username = username;
		this.net = net;
		this.sentRequest = true;
		this.net.registerUser(this);
		this.sentRequest = false;
		this.registrationDate = new Date();
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

	public Post writePost(String text) {
		if (text == null) throw new NullPointerException();
		if (!Post.checkTextLength(text)) throw new PostException("Il tuo messaggio"
				+ " è troppo lungo");
		Set<Tag> st = Tag.getTagsFromText(text);
		for (Tag t : st) {
			if (!this.net.isRegistered(t.getTagText())) 
				throw new PostException("Hai taggato un utente inesistente!");
		}
		// tp != null sempre ed è immutable, quindi lo si può ritornare con sicurezza 
		TextPost tp = new TextPost(this, text);
		this.sentRequest = true;
		this.net.storePost(tp);
		this.sentRequest = false;
		return tp;
	}

	public boolean removePost(Post p) {
		if (p == null) throw new NullPointerException();
		if (!this.equals(p.getAuthor())) throw new PermissionDeniedException();
		if (!this.net.containsPost(p)) throw new PostException();
		this.sentRequest = true;
		boolean b = this.net.removePost(p);
		this.sentRequest = false;
		return b;
	}
	
	public Post addLike(Post tp) {
		if (tp == null) throw new NullPointerException();
		if (this.equals(tp.getAuthor())) throw new PermissionDeniedException();
		if (!this.net.containsPost(tp)) throw new PostException();	
		this.sentRequest = true;
		tp = this.net.addLike(this, tp);
		this.sentRequest = false;
		return tp;
	}

	//FIXME Riconvertire in TextPost se necessario
	public List<Post> getPost(String username) {
		if (username == null) throw new NullPointerException();
		if (!this.net.isRegistered(username)) throw new UserException();
		List<Post> ltp = new ArrayList<Post>();
		for (Post p: this.net.writtenBy(username)) ltp.add(p);
		return ltp;
	}
}