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
public class MicroBlog implements SocialNetwork {

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
		this.getUnFollowedByAll(user); //Tutti smettono di seguirlo
		this.network.remove(user); //Rimosso da chi può seguire ed essere seguito
	}
	
	public Set<String> loadAllUsers() { //Safe
		Set<String> t = this.network.keySet(); //Modificare questo set modifica anche la Map
		Set<String> res = new HashSet<String>();
		for (String s : t) {
			String w = new String(s);
			res.add(w);
		}
		return res; //In questo modo le modifiche non hanno effetti sulla Map
	}
	
	public void removeAllUsers() { //Chiaramente qui non ci sono problemi di modifiche
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

	public void unFollowUser(String unfollower, String unfollowed) {
		if (unfollower == null || unfollowed == null) throw new NullPointerException();		 
		this.network.get(unfollower).remove(unfollowed);
	}
	
	public void unFollowAll(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();		
		for (String u : this.network.keySet()) this.unFollowUser(user, u);
	}
	
	public void getUnFollowedByAll(String user) {
		if (user == null) throw new NullPointerException();
		if (!this.isRegistered(user)) throw new IllegalArgumentException();
		for (String u : this.network.keySet()) this.unFollowUser(u, user);
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
	
	//FIXME Ma in che senso la rete sociale sottostante? Sulla base dei tags?
	public Map<String, Set<String>> guessFollowers(List<Post> ps) {
		checkForExceptions(ps);
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		return null;
	}
	
	public Set<String> getMentionedUsers() { //Safe
		Set<String> s = new HashSet<String>();
		List<Post> l;
		for (String us : this.posts.keySet()) {
			l = this.writtenBy(us);
			s.addAll(this.getMentionedUsers(l));
		}
		return s;
	}

	public Set<String> getMentionedUsers(List<Post> ps) { //Safe
		checkForExceptions(ps);
		Set<String> s = new HashSet<String>();
		Set<Tag> ts = new HashSet<Tag>();
		for (Post p : ps) ts.addAll(p.getTags());
		for (Tag t : ts) s.add(t.getTagText());
		return s;
	}
	
	public List<Post> writtenBy(String username) { //Safe
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered(username)) throw new IllegalArgumentException();
		List<Post> sp = new ArrayList<Post>();
		for (Post p : this.posts.get(username)) {
			sp.add((Post)p.clone());
		}
		return sp;
	}
	
	public List<Post> writtenBy(List<Post> ps, String username) { //Safe
		checkForExceptions(ps);
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered(username)) throw new IllegalArgumentException();
		List<Post> l = new ArrayList<Post>();
		for (Post p : ps) {
			if (p.getAuthor().equals(username)) l.add((Post)p.clone());
		}
		return l;
	}
	
	/**
	 * Metodo privato di supporto a containing(List<String>). Verifica se post contiene almeno
	 * una parola in words.
	 * @param post Il post da controllare.
	 * @param words La lista di parole da esaminare.
	 * @return true se almeno una parola in words è contenuta in post.getText(), false altrimenti.
	 * @throws NPE se post == null
	 * @throws NPE se words == null
	 * @throws IllegalArgumentException se esiste String w : words | w == null
	 */
	private boolean postContains(Post post, List<String> words) {
		if (post == null) throw new NullPointerException();
		if (words == null) throw new NullPointerException();
		for (String w : words) {
			if (w == null) throw new IllegalArgumentException(); //FIXME Sicuro che vada bene così?
			if (post.getText().indexOf(w) != -1) return true;
		}
		return false;
	}
	/**
	 * Metodo privato di supporto a containing(List<String>).
	 * @param user L'utente di cui si analizzano i post.
	 * @param words Le parole da analizzare.
	 * @return Una List<Post> che contiene esattamente i post scritti da user che contengono
	 * almeno una parola fra quelle fornite in words.
	 * @throws NPE se user == null
	 * @throws NPE se words == null
	 * @throws IllegalArgumentException se esiste String w : words | w == null
	 */
	private List<Post> containingInUserPosts(String user, List<String> words){ //Safe
		if (user == null) throw new NullPointerException();
		if (words == null) throw new NullPointerException();
		for (String w : words) {
			if (w == null) throw new IllegalArgumentException();
		}
		List<Post> l = new ArrayList<Post>();
		for(Post p : this.posts.get(user)) {
			if (postContains(p, words)) {
				l.add((Post)p.clone());
			}
		}
		return l;
	}

	public List<Post> containing(List<String> words) { //Safe
		if (words == null) throw new NullPointerException();
		for (String w : words) {
			if (w == null) throw new IllegalArgumentException();
		}
		List<Post> l = new ArrayList<Post>();
		for (String user : this.posts.keySet()) {
			l.addAll(containingInUserPosts(user, words));
		}
		return l;
	}
}