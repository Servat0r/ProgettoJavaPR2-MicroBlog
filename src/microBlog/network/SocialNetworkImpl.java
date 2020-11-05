package microBlog.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import microBlog.post.*;

/**
 * Implementazione dell'interfaccia SocialNetwork.
 * 
 * @author Salvatore Correnti
 *
 * @see SocialNetwork 
 */
public class SocialNetworkImpl implements SocialNetwork {

	/**
	 * Una Map che rappresenta tutti i post nella rete. 
	 * Per ogni String s : this.posts.containsKey(s), this.posts.get(s) è l'insieme di tutti i
	 * post pubblicati dall'utente s.
	 */
	private Map<String,Set<Post>> posts;
	
	/**
	 * Una Map che rappresenta tutte le connessioni nella rete. 
	 * Per ogni String s: this.network.containsKey(s), this.network.get(s) è l'insieme di tutti 
	 * gli utenti che sono seguiti da s.
	 */
	private Map<String, Set<String>> network;
	
	//Post methods
	
	public boolean containsPost(Post post) {
		if (post == null) throw new NullPointerException();
		String author = post.getAuthor();
		return this.posts.get(author).contains(post);
	}

	public boolean storePost(Post post) {
		if (post == null) throw new NullPointerException();
		if (this.containsPost(post)) throw new IllegalArgumentException("Post is already stored!");
		String author = post.getAuthor();
		return this.posts.get(author).add(post);
	}
	
	public boolean removePost(Post post) {
		if (post == null) throw new NullPointerException();
		String author = post.getAuthor();
		return this.posts.get(author).remove(post);
	}
	
	public List<Post> loadAllPosts(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();
		List<Post> sp = new ArrayList<Post>();
		for (Post p : this.posts.get(user)) {
			sp.add(new PostImpl(p.getId(), p.getAuthor(), p.getText(), p.getVisibilityScope(),
					p.getTags()));
		}
		return sp;
	}
	
	public void removeAllPosts(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();
		for (Post p : this.posts.get(user)) this.posts.remove(user, p);
	}
	
	//User methods
	
	public boolean isRegistered(String user) {
		if (user == null) throw new NullPointerException();
		return this.network.containsKey(user);
	}

	public void registerUser(String user) {
		if (user == null) throw new NullPointerException();
		if (this.isRegistered(user)) throw new IllegalArgumentException();
		//Inserisce due insiemi vuoti di post e di persone seguite.
		Set<Post> s = new HashSet<Post>();
		Set<String> t = new HashSet<String>();
		this.posts.put(user, s);
		this.network.put(user, t);
	}
	
	public void removeUser(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();
		this.removeAllPosts(user); //Tutti i post rimossi
		this.posts.remove(user); //Rimosso da chi può postare
		this.unFollowAll(user); //Smette di seguire tutti
		this.getUnfollowedByAll(user); //Tutti smettono di seguirlo
		this.network.remove(user); //Rimosso da chi può seguire ed essere seguito
	}
	
	public Set<String> loadAllUsers() {
		return this.network.keySet();
	}
	
	public void removeAllUsers() {
		 this.network.clear();
		 this.posts.clear();
	}
	
	//Following methods

	public boolean isFollowing(String following, String followed) {
		if (following == null || followed == null) throw new NullPointerException();
		return this.network.get(following).contains(followed);
	}
	
	public void followUser(String follower, String followed) {
		if (follower == null || followed == null) throw new NullPointerException();
		if (follower.equals(followed)) throw new IllegalArgumentException();
		this.network.get(follower).add(followed);
	}

	public void unfollowUser(String unfollower, String unfollowed) {
		if (unfollower == null || unfollowed == null) throw new NullPointerException();		 
		this.network.get(unfollower).remove(unfollowed);
	}
	
	public void unFollowAll(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();		
		for (String u : this.network.keySet()) this.unfollowUser(user, u);
	}
	
	public void getUnfollowedByAll(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();
		for (String u : this.network.keySet()) this.unfollowUser(u, user);
	}
	
	//Analysis methods
	
	/**
	 * Private method used for checking (recurrent) exceptions in the following methods.
	 * @param ps
	 * 			La lista da controllare.
	 * @throws NPE se ps == null
	 * @throws IllegalArgumentException se esiste Post p : ps | p == null
	 * @throws IllegalArgumentException se esiste Post p : ps | this.isRegistered(p.getAuthor())
	 */
	private void  checkForExceptions(List<Post> ps) {
		if (ps == null) throw new NullPointerException();
		for (Post p : ps) {
			if (p == null) throw new IllegalArgumentException();
			if (this.isRegistered(p.getAuthor())) throw new IllegalArgumentException();
		}
	}
	
	//TODO Ma in che senso la rete sociale sottostante? Sulla base dei tags?
	public Map<String, Set<String>> guessFollowers(List<Post> ps) {
		checkForExceptions(ps);
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		return null;
	}
	
	public Set<String> getMentionedUsers() {
		Set<String> s = new HashSet<String>();
		List<Post> l;
		for (String us : this.posts.keySet()) {
			l = this.loadAllPosts(us);
			s.addAll(this.getMentionedUsers(l));
		}
		return s;
	}

	public Set<String> getMentionedUsers(List<Post> ps) {
		checkForExceptions(ps);
		Set<String> s = new HashSet<String>();
		Set<Tag> ts = new HashSet<Tag>();
		for (Post p : ps) ts.addAll(p.getTags());
		for (Tag t : ts) s.add(t.getTagText());
		return s;
	}
	
	public List<Post> writtenBy(String username) {
		List<Post> l = new ArrayList<Post>();
		for (Post p : this.posts.get(username)) {
			l.add((Post)p.clone());
		}
		return l;
	}
	
	public List<Post> writtenBy(List<Post> ps, String username) {
		checkForExceptions(ps);
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered(username)) throw new IllegalArgumentException();
		List<Post> l = new ArrayList<Post>();
		for (Post p : ps) {
			if (p.getAuthor() == username) l.add((Post)p.clone());
		}
		return l;
	}

	//TODO Ma ignorando upper/lower case?
	public List<Post> containing(List<String> words) {
		if (words == null) throw new NullPointerException();
		for (String w : words) {
			if (w == null || !this.isRegistered(w)) throw new IllegalArgumentException();
		}
		List<Post> l = new ArrayList<Post>();
		boolean b;
		for (Set<Post> sp : this.posts.values()) {
			for (Post p : sp) {
				b = false;
				for (String w : words) {
					if (p.getText().indexOf(w) != -1) { //Il testo di p contiene w
						l.add((Post)p.clone()); //Aggiungiamo dunque il post corrente
						b = true; //Per proseguire
						break; //Usciamo
					}
				}
				if (b) continue; //Andiamo al prossimo post
			}
		}
		return l;
	}
}