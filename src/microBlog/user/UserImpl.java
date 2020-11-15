package microBlog.user;

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
		net.registerUser(this);
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

	public boolean isRegistered() {
		return (this.net != null);
	}

	public boolean unregister() {
		if (!this.isRegistered()) throw new UserException();
		this.sentRequest = true;
		boolean b = this.net.removeUser(this);
		this.sentRequest = false;
		this.net = null;
		return b;
	}

	public boolean writeTextPost(String text) {
		if (text == null) throw new NullPointerException();
		if (!this.isRegistered()) throw new PermissionDeniedException();
		if (!Post.checkTextLength(text)) throw new PostException("Il tuo messaggio"
				+ " è troppo lungo");
		Set<Tag> st = Tag.getTagsFromText(text);
		for (Tag t : st) {
			if (!this.net.isRegistered(t.getTagText())) 
				throw new PostException("Hai taggato un utente inesistente!");
		}
		TextPost tp = new TextPost(this, text);
		this.sentRequest = true;
		Boolean b = this.net.storePost(tp);
		this.sentRequest = false;
		return b;
	}

	public boolean removePost(int id) {
		if (id <= 0) throw new IllegalArgumentException("Id must be > 0 !");
		this.sentRequest = true;
		TextPost tp = this.getPost(id, username);
		boolean b = this.net.removePost(tp);
		this.sentRequest = false;
		return b;
	}

	/**
	 * @requires id &gt; 0
	 * @param id L'identificativo del post.
	 * @param username Lo username dell'utente di cui si vuole il post.
	 * @return Il TextPost con quell'identificativo.
	 * @throws IllegalArgumentException se id &le; 0
	 * @throws PostException se non esiste un post con quell'identificativo
	 */
	private TextPost getPost(int id, String username) {
		if (id <= 0) throw new IllegalArgumentException();
		List<Post> ltp = this.net.writtenBy(username);
		for (Post tp : ltp) {
			if (tp.getId() == id) return (TextPost)tp;
		}
		throw new PostException("Questo id non è associato con nessun tuo post!");
	}

	public boolean addLike(int id, String username) {
		if (id <= 0) throw new IllegalArgumentException();
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered()) throw new PermissionDeniedException();
		if (!this.net.isRegistered(username)) throw new UserException();	
		TextPost tp = this.getPost(id, username);
		this.sentRequest = true;
		boolean b = this.net.addLike(this, tp);
		this.sentRequest = false;
		return b;
	}

	public boolean removeLike(int id, String username) {
		if (id <= 0) throw new IllegalArgumentException();
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered()) throw new PermissionDeniedException();
		if (!this.net.isRegistered(username)) throw new UserException();	
		TextPost tp = this.getPost(id, username);
		this.sentRequest = true;
		boolean b = this.net.removeLike(this, tp);
		this.sentRequest = false;
		return b;
	}

	public int[] getTextPostIds(String username) {
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered()) throw new PermissionDeniedException();
		if (!this.net.isRegistered(username)) throw new UserException();
		List<Post> ltp = this.net.writtenBy(username);
		int[] ids = new int[ltp.size()];
		for (int i = 0; i < ltp.size(); i++) ids[i] = ltp.get(i).getId();
		return ids;
	}
}