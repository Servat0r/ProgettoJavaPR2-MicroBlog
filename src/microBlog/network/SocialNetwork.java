package microBlog.network;

import java.util.List;
import java.util.Map;
import java.util.Set;
import microBlog.post.*;

/**
 * Modella una rete sociale in cui sono presenti diversi utenti che possono seguire altri utenti e
 * pubblicare o eliminare post. L'interfaccia fornisce inoltre altre funzionalità di filtraggio e/o
 * analisi dati, ad esempio:
 * - aggiunta e/o rimozione di utenti;
 * - determinazione della sotto-rete sociale individuata da una lista di post;
 * - determinazione degli utenti col maggior numero di followers ("influencers");
 * - determinazione di tutti gli utenti menzionati (taggati) nei post presenti;
 * - determinazione dell'insieme dei post scritti da un utente;
 * - la lista dei post presenti nella rete sociale che includono almeno una parola presente in una
 * specifica lista;
 * - metodi di salvataggio /caricamento dei dati degli utenti (post) per effettuare tali analisi,
 * tutti assolutamente reference-safe, i.e. non violano la barriera di astrazione dei dati interni.
 * 
 * @author Salvatore Correnti
 */

public interface SocialNetwork {
	
	//Post methods
	
	/**
	 * @requires post != null
	 * @param post 
	 * 			Il post da cercare.
	 * @return true se il post è presente nella rete sociale, false altrimenti.
	 * @throws NPE se post == null
	 */
	public boolean containsPost(Post post);
	
	/**
	 * @requires post != null && !this.containsPost(post)
	 * @param post
	 * 			Il post da aggiungere.
	 * @return Aggiunge il post alla rete sociale e ritorna true se l'operazione è riuscita, 
	 * false altrimenti.
	 * @throws NPE se post == null
	 * @throws IllegalArgumentException se this.containsPost(post)
	 */
	public boolean storePost(Post post);
	
	/**
	 * @requires post != null
	 * @param post
	 * 			Il post da rimuovere.
	 * @return Se post è presente, questi viene rimosso e ritorna true. Se non è presente,
	 * non fa nulla e ritorna false.
	 * @throws NPE se post == null
	 */
	public boolean removePost(Post post); //TODO Meglio void o boolean?
	
	
	/**
	 * @requires user != null && this.isRegistered(user)
	 * @param user
	 * 			L'utente di cui si vogliono rimuovere i post.
	 * @effects Tutti i post di user vengono eliminati dalla rete.
	 * @throws NPE se user == null
	 * @throws IllegalArgumentException se !this.isRegistered(user)
	 */
	public void removeAllPosts(String user);
	
	//User methods
	
	/**
	 * @requires user != null
	 * @param user 
	 * 			L'utente che potrebbe essere registrato.
	 * @return true se l'utente è registrato, false altrimenti.
	 * @throws NPE se user == null
	 */
	public boolean isRegistered(String user);
	/**
	 * @requires user != null &&!this.isRegistered(user)
	 * @param user
	 * 			L'utente da registrare.
	 * @effects this.isRegistered(user) => true.
	 * @throws NPE se user == null
	 * @throws IllegalArgumentException se this.isRegistered(user).
	 */
	public void registerUser(String user);
	
	/**
	 * @requires user != null && this.isRegistered(user)
	 * @param user
	 * 			L'utente da rimuovere.
	 * @effects this.isRegistered(user) => false, tutti i suoi followers smetteranno di seguirlo e
	 * tutti i suoi post verranno eliminati.
	 * @throws NPE se user == null
	 * @throws IllegalArgumentException se !this.isRegistered(user).
	 */
	public void removeUser(String user);
	
	/**
	 * @requires
	 * @return Un Set<String> che contiene gli identificativi di tutti gli utenti, vuoto se la
	 * rete non ha utenti (garantisce che modifiche di questo set non influiscono sulla rete
	 * sociale).
	 */
	public Set<String> loadAllUsers();
	
	/**
	 * @requires
	 * @effects Tutti gli utenti sono rimossi dalla rete (e i loro post eliminati).
	 */
	public void removeAllUsers();
	
	//Following methods
	
	/**
	 * @requires following != null && followed != null
	 * @param following
	 * 				L'utente che potrebbe star seguendo followed.
	 * @param followed
	 * 				L'utente che potrebbe essere seguito da following.
	 * @return true se following sta seguendo followed, false altrimenti
	 * (ad esempio se following.equals(followed)).
	 * @throws NPE se following == null
	 * @throws NPE se followed == null
	 */
	public boolean isFollowing(String following, String followed);
	
	/**
	 * @requires follower != null && followed != null && (!follower.equals(followed))
	 * @param follower
	 * 				Il futuro follower.
	 * @param followed
	 * 				Il futuro followed.
	 * @effects this.isFollowing(follower, followed) => true.
	 * @throws NPE se follower == null
	 * @throws NPE se followed == null
	 * @throws IllegalArgumentException se follower.equals(followed)
	 */
	public void followUser(String follower, String followed);
	
	/**
	 * @requires unfollower != null && unfollowed != null
	 * @param unfollower
	 * 					L'utente che smetterà di seguire.
	 * @param unfollowed
	 * 					L'utente che non sarà più seguito.
	 * @effects this.isFollowing(follower, followed) => false.
	 * @throws NPE se unfollower == null
	 * @throws NPE se unfollowed == null
	 */
	public void unFollowUser(String unfollower, String unfollowed);
	
	/**
	 * @requires user != null && this.isRegistered(user)
	 * @param user
	 * 			L'utente che deve smettere di seguire tutti.
	 * @effects Forall String u : this.isRegistered(u), this.isFollowing(user, u) => false.
	 * @throws NPE se user == null
	 * @throws IllegalArgumentException se !this.isRegistered(user)
	 */
	public void unFollowAll(String user);
	
	/**
	 * @requires user != null && this.isRegistered(user)
	 * @param user
	 * 			L'utente che tutti devono smettere di seguire.
	 * @effects forall String u : this.isRegistered(u), this.isFollowing(u, user) => false.
	 * @throws NPE se user == null
	 * @throws IllegalArgumentException se !this.isRegistered(user)
	 */
	public void getUnFollowedByAll(String user);
	
	//Analysis methods

	/**
	 * @requires ps != null && forall Post p : ps, (p != null && this.isRegistered(p.getAuthor())
	 * @param ps
	 * 		La lista di post da controllare.
	 * @return Una Map<String, Set<String>> che rappresenta la sotto-rete sociale individuata dalla
	 * lista ps.
	 * @throws NPE se ps == null
	 * @throws IllegalArgumentException se esiste Post p : ps | p == null
	 * @throws IllegalArgumentException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public Map<String, Set<String>> guessFollowers(List<Post> ps);
	
	/**
	 * @requires 
	 * @return Un Set<String> che contiene tutti gli utenti registrati menzionati (taggati)
	 * nella rete.
	 */
	public Set<String> getMentionedUsers();
	
	/**
	 * @requires ps != null && forall Post p : ps, (p != null && this.isRegistered(p.getAuthor())
	 * @param ps
	 * 			La lista di post da controllare.
	 * @return Un Set<String> che contiene tutti gli utenti registrati menzionati (taggati)
	 * nella lista di post.
	 * @throws NPE se ps == null
	 * @throws IllegalArgumentException se esiste Post p : ps | p == null
	 * @throws IllegalArgumentException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public Set<String> getMentionedUsers(List<Post> ps);
		
	/**
	 * @requires username != null && this.isRegistered(username)
	 * @param username
	 * 				Lo username dell'utente.
	 * @return Una List<Post> che contiene tutti i post scritti da username.
	 * @throws NPE se username == null
	 * @throws IllegalArgumentException se !this.isRegistered(username)
	 */
	public List<Post> writtenBy(String username);
	
	/**
	 * @requires ps != null && forall Post p : ps, (p != null && this.isRegistered(p.getAuthor())
	 * @param ps
	 * 			La lista di post da controllare.
	 * @param username
	 * 			Lo username dell'utente.
	 * @return Una List<Post> che contiene tutti i post scritti da username in ps (una lista
	 * vuota se non ce n'è nessuno!).
	 * @return null se !this.isRegistered(username).
	 * @throws NPE se ps == null
	 * @throws IllegalArgumentException se esiste Post p : ps | p == null
	 * @throws IllegalArgumentException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public List<Post> writtenBy(List<Post> ps, String username);
	
	/**
	 * @requires words != null && forall String s : words, s != null
	 * @param words
	 * 			Una lista di stringhe che contiene le parole da cercare, senza valori null.
	 * @return Una List<Post> che contiene tutti i post nella rete che contengono almeno una delle
	 * parole specificate in words.
	 * @throws NPE se words == null.
	 * @throws IllegalArgumentException se esiste String s : words | s == null
	 */
	public List<Post> containing(List<String> words);
}