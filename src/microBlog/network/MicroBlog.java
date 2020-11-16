package microBlog.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import microBlog.post.*;
import microBlog.user.*;


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
	 * Per ogni String s : this.posts.containsKey(s), this.posts.get(s) è 
	 * l'insieme di tutti i post pubblicati dall'utente s.
	 */
	private Map<String,Set<Post>> posts;
	
	/**
	 * Una Map che rappresenta tutte le connessioni nella rete. 
	 * Per ogni String s: this.network.containsKey(s), this.network.get(s) è 
	 * l'insieme di tutti gli utenti che sono seguiti da s.
	 */
	private Map<String, Set<String>> network;
	
	//TODO Da cambiare in lista ordinata (manualmente) in senso decrescente
	/**
	 * Un insieme ordinato di Pair che contengono il numero di followers per ogni
	 * utente della rete, utilizzato principalmente per il metodo
	 * {@link #influencers()}.
	 */
	private SortedSet<Pair<String>> followersCount;
	
	/**
	 * Costruttore di default, inizializza posts e network a due mappe vuote e 
	 * followersCount a un SOrtedSet vuoto.
	 */
	public MicroBlog() {
		this.posts = new HashMap<String, Set<Post>>();
		this.network = new HashMap<String, Set<String>>();
		//Chiaramente cambiare anche qui
		this.followersCount = new TreeSet<Pair<String>>();
	}
	
	//Post methods
	
	public boolean containsPost(Post post) {
		if (post == null) throw new NullPointerException();
		String author = post.getAuthor().getUsername();
		if (!this.posts.containsKey(author)) return false; //Non ha mai postato
		return this.posts.get(author).contains(post);
	}

	public boolean storePost(Post post) {
		if (post == null) throw new NullPointerException();
		if (this.containsPost(post)) throw new IllegalArgumentException("Post is already stored!");
		if (!this.isRegistered(post.getAuthor().getUsername()))
			throw new PermissionDeniedException(); //L'utente non è nella rete
		/* Grazie a quanto sopra possiamo affermare che this.contains(post) =>
		   this.isRegistered(post.getAuthor().getUsername()) */
		if (!post.getAuthor().hasSentRequest()) throw new PermissionDeniedException();
		if (post.getText() == null) throw new PostException();
		String author = post.getAuthor().getUsername();
		if (!this.posts.containsKey(author)) { //Non ha mai postato
			Set<Post> sp = new HashSet<>();
			this.posts.put(author, sp);
		}
		return this.posts.get(author).add(post);
	}
	
	public boolean removePost(Post post) {
		if (post == null) throw new NullPointerException();
		if (!this.containsPost(post)) throw new PostException();
		if (!post.getAuthor().hasSentRequest()) throw new PermissionDeniedException();
		String author = post.getAuthor().getUsername();
		boolean b = this.posts.get(author).remove(post);
		TextPost tp = (TextPost)post;
		User exLiker;
		User exLiked = post.getAuthor();
		if (b) {
			for (Like l : tp.getLikes()) {
				exLiker = l.getAuthor();
				//Conta solo i likes dei post esistenti, quindi si è "aggiornato" da solo
				if (getLikesCount(exLiker, exLiked) == 0) 
					b = b && unFollowUser(exLiker.getUsername(), exLiked.getUsername());
			}
		}
		return b;
	}
	
	//User methods
	
	public boolean isRegistered(String username) {
		if (username == null) throw new NullPointerException();
		return this.network.containsKey(username);
	}

	public void registerUser(User user) { 
		if (user == null) throw new NullPointerException();
		if (this.isRegistered(user.getUsername())) throw new UserException();
		if (!user.hasSentRequest()) throw new PermissionDeniedException();
		//Inserisce due insiemi vuoti di post e di persone seguite.
		Set<Post> sp = new HashSet<Post>();
		Set<String> t = new HashSet<String>();
		this.posts.put(user.getUsername(), sp);
		this.network.put(user.getUsername(), t);
		System.out.println("Aggiunta a conta followers: " + //FIXME
				this.followersCount.add(new Pair<String>(0, user.getUsername())));
	}
	
	//Following methods

	public boolean isFollowing(String following, String followed) {
		if (following == null) throw new NullPointerException();
		if (followed == null) throw new NullPointerException();
		return this.network.get(following).contains(followed);
	}
	
	/**
	 * @requires follower != null &amp; followed != null &amp; (!follower.equals(followed))
	 * @param follower
	 * 				Il futuro follower.
	 * @param followed
	 * 				Il futuro followed.
	 * @effects this.isFollowing(follower, followed) diventa true.
	 * @return true se follower ha cominciato a seguire followed correttamente,
	 * false altrimenti.
	 * @throws NullPointerException se follower == null
	 * @throws NullPointerException se followed == null
	 * @throws UserException se follower.equals(followed)
	 */
	private boolean followUser(String follower, String followed) {
		if (follower == null || followed == null) throw new NullPointerException();
		if (follower.equals(followed)) throw new UserException();
		boolean b = this.network.get(follower).add(followed);
		if (b) {
			Iterator<Pair<String>> ips = this.followersCount.iterator();
			Pair<String> p = null;
			while (ips.hasNext()) {
				p = ips.next();
				System.out.println("Prossimo utente" + p.toString()); //FIXME
				if (p.getSecond().equals(followed)) {
					ips.remove();
					break;
				}
			}
			if (p != null) {
				p.setFirst(p.getFirst() + 1);
				this.followersCount.add(p);
			}
		}
		return b;
	}

	/**
	 * @requires unfollower != null &amp; unfollowed != null &amp;
	 * this.isRegistered(unfollower) &amp; this.isRegistered(unfollowed)
	 * @param unfollower
	 * 					L'utente che smetterà di seguire.
	 * @param unfollowed
	 * 					L'utente che non sarà più seguito.
	 * @effects this.isFollowing(follower, followed) diventa false.
	 * @return true se unfollowed ha smesso di essere sguito da unfollower,
	 * false altrimenti
	 * @throws NullPointerException se unfollower == null
	 * @throws NullPointerException se unfollowed == null
	 * @throws UserException se !this.isRegistered(unfollower)
	 * @throws UserException se !this.isRegistered(unfollowed)
	 */
	private boolean unFollowUser(String unfollower, String unfollowed) {
		if (unfollower == null) throw new NullPointerException();
		if (unfollowed == null) throw new NullPointerException();
		if (!this.isRegistered(unfollower)) throw new UserException();
		if (!this.isRegistered(unfollower)) throw new UserException();
		boolean b = this.network.get(unfollower).remove(unfollowed);
		if (b) {
			Iterator<Pair<String>> ips = this.followersCount.iterator();
			Pair<String> p = null;
			while (ips.hasNext()) {
				p = ips.next();
				if (p.getSecond().equals(unfollowed)) {
					ips.remove();
					break;
				}
			}
			if (p != null) {
				p.setFirst(p.getFirst() - 1);
				this.followersCount.add(p);
			}
		}
		return b;
	}
	
	//Analysis methods
	
	/**
	 * Private method used for checking (recurrent) exceptions in the following methods.
	 * @param ps
	 * 			La lista da controllare.
	 * @throws NullPointerException se ps == null
	 * @throws PostException se esiste Post p : ps | p == null
	 * @throws PostException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	private void  checkForExceptions(List<Post> ps) {
		if (ps == null) throw new NullPointerException();
		for (Post p : ps) {
			if (p == null) throw new PostException();
			if (!this.isRegistered(p.getAuthor().getUsername())) throw new PostException();
		}
	}
	
	//TODO Va fatta così (cioè per ogni like aggiungi un follower)?
	public Map<String, Set<String>> guessFollowers(List<Post> ps) {
		checkForExceptions(ps); /* Ci dice automaticamente che tutti i post di ps
		sono in questa rete */
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		String s;
		for (Post p : ps) {
			s = p.getAuthor().getUsername();
			map.putIfAbsent(s, new HashSet<String>());
			for (Like l : ((TextPost)p).getLikes()) 
				map.get(l.getPost().getAuthor().getUsername()).add(s);
		}
		return map;
	}
	
	//TODO Non va cambiato nel passaggio da Set a List
	public List<String> influencers() {
		List<String> infl = new ArrayList<>(this.network.keySet().size());
		//Sono inseriti in ordine
		for (Pair<String> p : this.followersCount) infl.add(p.getSecond());
		return infl;
	}
	
	public Set<String> getMentionedUsers() {
		Set<String> s = new HashSet<String>();
		List<Post> l;
		for (String us : this.posts.keySet()) {
			l = this.writtenBy(us);
			s.addAll(this.getMentionedUsers(l));
		}
		return s;
	}

	//TODO Qualcosa non va perché getMentionedUsers() non stampava correttamente i tag
	public Set<String> getMentionedUsers(List<Post> ps) { //Safe
		checkForExceptions(ps);
		Set<String> s = new HashSet<String>();
		Set<Tag> ts = new HashSet<Tag>();
		for (Post p : ps) ts.addAll(((TextPost)p).getTags());
		for (Tag t : ts) s.add(t.getTagText());
		return s;
	}
	
	//TODO Questo sembra che funzioni
	public List<Post> writtenBy(String username) { //Safe
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered(username)) throw new UserException();
		List<Post> sp = new ArrayList<Post>();
		for (Post p : this.posts.get(username)) {
			sp.add(p);
		}
		return sp;
	}
	
	public List<Post> writtenBy(List<Post> ps, String username) { //Safe
		checkForExceptions(ps);
		if (username == null) throw new NullPointerException();
		if (ps == null) throw new NullPointerException();
		if (!this.isRegistered(username)) throw new UserException();
		for (Post p : ps) {
			if (p == null) throw new PostException();
			if (!this.isRegistered(p.getAuthor().getUsername())) 
				throw new PostException();
		}
		List<Post> l = new ArrayList<Post>();
		for (Post p : ps) {
			if (p.getAuthor().getUsername().equals(username)) l.add(p);
		}
		return l;
	}
	
	/**
	 * Metodo privato di supporto a containing(List di String). Verifica se post contiene almeno
	 * una parola in words.
	 * @param post Il post da controllare.
	 * @param words La lista di parole da esaminare.
	 * @return true se almeno una parola in words è contenuta in post.getText(), false altrimenti.
	 * @throws NullPointerException se post == null
	 * @throws NullPointerException se words == null
	 * @throws IllegalArgumentException se esiste String w : words | w == null
	 */
	private boolean postContains(Post post, List<String> words) {
		if (post == null) throw new NullPointerException();
		if (words == null) throw new NullPointerException();
		for (String w : words) if (w == null) throw new IllegalArgumentException();
		for (String w : words) if (post.getText().indexOf(w) != -1) return true;
		return false;
	}
	/**
	 * Metodo privato di supporto a containing(List di String).
	 * @param user L'utente di cui si analizzano i post.
	 * @param words Le parole da analizzare.
	 * @return Una List di Post che contiene esattamente i post scritti da user che contengono
	 * almeno una parola fra quelle fornite in words.
	 * @throws NullPointerException se user == null
	 * @throws NullPointerException se words == null
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
				l.add(p);
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
	
	/**
	 * @requires id &gt; 0 &amp; username != null &amp; this.isRegistered(username)
	 * @param id L'id del post.
	 * @param username Lo username dell'autore del post.
	 * @return Il TextPost scritto da username, se esiste
	 * @throws IllegalArgumentException se id &le; 0
	 * @throws NullPointerException se username == null
	 * @throws UserException se this.isRegistered(username)
	 * @throws PostException se non esiste un post di username con questo id.
	 */
	public TextPost getPost(int id, String username) {
		if (id <= 0) throw new IllegalArgumentException();
		List<Post> ltp = this.writtenBy(username);
		for (Post tp : ltp) {
			if (tp.getId() == id) return (TextPost)tp;
		}
		throw new PostException("Questo id non è associato con nessun post della rete!");
	}

	public boolean addLike(User user, Post post) {
		if (user == null) throw new NullPointerException();
		if (post == null) throw new NullPointerException();
		if (!this.containsPost(post)) throw new PostException(); /* Se la rete corrente
		contiene post, allora necessariamente il suo autore è registrato */
		if (!this.isRegistered(user.getUsername())) throw new UserException();
		if (this.isLikedByUser(post, user.getUsername()))
			throw new PermissionDeniedException();
		if (!user.hasSentRequest()) throw new PermissionDeniedException();
		String liker = user.getUsername();
		String liked = post.getAuthor().getUsername();
		boolean b = this.posts.get(liked).remove(post);
		Like l = new Like(user, post);
		TextPost tp = ((TextPost)post);
		Set<Like> sl = new HashSet<Like>(tp.getLikes());
		sl.add(l);
		tp = tp.copy(sl); //Viene creato un nuovo oggetto (!)
		b = b && this.posts.get(liked).add(tp); /*Se è false allora qualcosa 
		non ha funzionato */
		if (b && !isFollowing(liker, liked)) b = b && followUser(liker, liked);
		return b;
	}
	
	/**
	 * @requires liker != null &amp; liked != null &amp; 
	 * this.isRegistered(liker.getUsername()) &amp; this.isRegistered(liked.getUsername())
	 * @param liker L'utente che potrebbe aver messo dei like
	 * @param liked L'utente che potrebbe aver ricevuto dei like
	 * @return Il numero di likes di liker a liked.
	 * @throws NullPointerException se liker == null
	 * @throws NullPointerException se liked == null
	 * @throws UserException se !this.isRegistered(liker)
	 * @throws UserException se !this.isRegistered(liked)
	 */
	private int getLikesCount(User liker, User liked) {
		if (liker == null) throw new NullPointerException();
		if (liked == null) throw new NullPointerException();
		String likerName =liker.getUsername();
		String likedName = liked.getUsername();
		if (!this.isRegistered(likerName)) throw new UserException();
		if (!this.isRegistered(likedName)) throw new UserException();
		int result = 0;
		for (Post p : this.posts.get(likedName)) {
			TextPost tp = (TextPost)p;
			if (tp.getLikes().contains(new Like(liker, p))) result++;
		}
		return result;
	}

	//TODO Questo (se non si possono rimuovere i likes) sembra che funzioni
	public boolean isLikedByUser(Post post, String username) {
		if (post == null) throw new NullPointerException();
		if (username == null) throw new NullPointerException();
		if (!this.isRegistered(post.getAuthor().getUsername())) throw new UserException();
		if (!this.isRegistered(username)) throw new UserException();
		Set<Like> sl = ((TextPost)post).getLikes();
		for (Like l : sl) if (l.getAuthor().getUsername().equals(username)) return true;
		return false;
	}
}