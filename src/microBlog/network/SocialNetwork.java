package microBlog.network;

import java.util.List;
import java.util.Map;
import java.util.Set;
import microBlog.post.*;
import microBlog.user.User;
import microBlog.user.UserException;

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
	
	/** TODO Eventualmente modificarlo per contare anche i likes come post.
	 * @requires post != null
	 * @param post 
	 * 			Il post da cercare.
	 * @return true se il post è presente nella rete sociale, false altrimenti.
	 * @throws NullPointerException se post == null
	 */
	public boolean containsPost(Post post);
	
	/**
	 * @requires post != null &amp; !this.containsPost(post) &amp; 
	 * post.getText() != null &amp; this.isRegistered(post.getAuthor()) &amp; 
	 * post.getAuthor().hasSentRequest()
	 * @param post
	 * 			Il post da aggiungere.
	 * @return Se l'autore di post è registrato e ha mandato una richiesta a questa
	 * rete sociale, aggiunge il post alla medesima e ritorna true se l'operazione è 
	 * riuscita, false altrimenti.
	 * @throws NullPointerException se post == null
	 * @throws PostException se this.containsPost(post)
	 * @throws PermissionDeniedException se !this.isRegistered(post.getAuthor())
	 * @throws PermissionDeniedException se !post.getAuthor().hasSentRequest()
	 */
	public boolean storePost(Post post);
	
	/**
	 * @requires post != null &amp; this.containsPost(post) &amp; 
	 * post.getAuthor().hasSentRequest()
	 * @param post Il post da rimuovere.
	 * @return Se l'autore di post ha mandato una richiesta a questa rete sociale e
	 * post è presente, questi viene rimosso e per ogni altro utente user che dopo la
	 * rimozione non ha più alcun like ai post di post.getAuthor(), questi non viene
	 * più seguito da user; ritorna true se tutte queste operazioni sono riuscite,
	 * false altrimenti. 
	 * @throws NullPointerException se post == null
	 * @throws PostException se !this.containsPost(post)
	 * @throws PermissionDeniedException se !post.getAuthor().hasSentRequest()
	 */
	public boolean removePost(Post post);
	
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
	public Post getPost(int id, String username);
	
	//User methods
	
	/**
	 * @requires username != null
	 * @param username 
	 * 			Lo username dell'utente che potrebbe essere registrato: si è scelto
	 * 			di avere username di tipo stringa anziché User per facilitare la
	 * 			ricerca di un utente di nome username, dato che un tale metodo non
	 * 			ha bisogno di protezioni.
	 * @return true se l'utente è registrato (i.e., esiste un utente user t.c.
	 * user.getName().equals(username)), false altrimenti.
	 * @throws NullPointerException se username == null
	 */
	public boolean isRegistered(String username);
	
	/**
	 * @requires user != null &amp;!this.isRegistered(user.getName()) 
	 * &amp; user.hasSentRequest() 
	 * @param user
	 * 			L'utente da registrare.
	 * @effects this.isRegistered(user) diventa true.
	 * @throws NullPointerException se user == null
	 * @throws UserException se this.isRegistered(user)
	 * @throws PermissionDeniedException se !user.hasSentRequest()
	 */
	public void registerUser(User user);
	
	//Following methods
	
	/**
	 * @requires post != null &amp; username != null &amp; post.getAuthor()
	 * &amp; this.isRegistrated(post.getAuthor().getUsername()) &amp;
	 * this.isRegistrated(username)
	 * username sono registrati alla stessa rete di post
	 * @param post Il post che potrebbe aver il like.
	 * @param username Lo username dell'utente che potrebbe aver messo like.
	 * @return true se username ha messo like a post, false altrimenti
	 * @throws NullPointerException se post == null
	 * @throws NullPointerException se username == null
	 * @throws UserException se !this.isRegistrated(post.getAuthor().getUsername())
	 * @throws UserException se !this.isRegistrated(this.isRegistrated(username))
	 */
	public boolean isLikedByUser(Post post, String username);
	
	/**
	 * @requires user != null &amp; post != null &amp; this.containsPost(post)
	 * &amp; this.isRegistered(user) &amp; this.isRegistered(post.getAuthor()) 
	 * &amp; user.hasSentRequest() &amp; !user.equals(post.getAuthor()) &amp;
	 *   //l'utente non ha già messo like allo stesso post
	 * @param user L'utente che mette like
	 * @param post Il Post a cui sarà messo like da user
	 * @return Il TextPost aggiornato col nuovo like.
	 * @effects se ritorna true, user sarà follower di post.getAuthor()
	 * @throws NullPointerException se user == null
	 * @throws NullPointerException se post == null
	 * @throws UserException se !this.isRegistered(user.getUsername())
	 * @throws PostException se !this.containsPost(post)
	 * @throws PermissionDeniedException se this.isLikedByUser(post, user.getUsername())
	 * @throws PermissionDeniedException se !user.hasSentRequest()
	 */
	public Post addLike(User user, Post post);
	
	/**
	 * @requires username != null &amp; this.isRegistered(username)
	 * @param username Lo username dell'utente.
	 * @return Il numero totale di followers dell'utente username.
	 * @throws NullPointerException se username == null
	 * @throws UserException se !this.isRegistered(username)
	 */
	public int getTotalFollowersCount(String username);
	
	/**
	 * @requires following != null &amp; followed != null
	 * @param following
	 * 				L'utente che potrebbe stare seguendo followed.
	 * @param followed
	 * 				L'utente che potrebbe essere seguito da following.
	 * @return true se following sta seguendo followed, false altrimenti
	 * (ad esempio se following.equals(followed)).
	 * @throws NullPointerException se following == null
	 * @throws NullPointerException se followed == null
	 */
	public boolean isFollowing(String following, String followed);

	
	//Analysis methods

	/**
	 * @requires ps != null &amp; forall Post p : ps, (p != null &amp; this.isRegistered(p.getAuthor())
	 * @param ps
	 * 		La lista di post da controllare.
	 * @return Una Map che rappresenta la sotto-rete sociale individuata dalla lista ps,
	 * i.e.: l'insieme degli autori dei post e per ogni 
	 * coppia di utenti se uno è follower dell'altro.
	 * @throws NullPointerException se ps == null
	 * @throws PostException se esiste Post p : ps | p == null
	 * @throws PostException se !this.containsPost(p)
	 * @throws PostException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public Map<String, Set<String>> guessFollowers(List<Post> ps);
	
	/**
	 * @requires 
	 * @return Un Un Set di stringhe che contiene tutti gli utenti registrati menzionati (taggati)
	 * nella rete.
	 */
	public Set<String> getMentionedUsers();
	
	/**
	 * @requires ps != null &amp; forall Post p : ps, (p != null &amp; this.isRegistered(p.getAuthor())
	 * @param ps
	 * 			La lista di post da controllare.
	 * @return Un Set di stringhe che contiene tutti gli utenti registrati menzionati (taggati)
	 * nella lista di post.
	 * @throws NullPointerException se ps == null
	 * @throws PostException se !this.containsPost(p)
	 * @throws PostException se esiste Post p : ps | p == null
	 * @throws PostException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public Set<String> getMentionedUsers(List<Post> ps);
	
	/**
	 * @requires
	 * @return
	 * 		Una Lista di stringhe che contiene gli x (=quanti?) utenti più influenti della rete (cioè
	 * 		che hanno il numero maggiore di followers), una List di stringhe vuota se la
	 * 		rete non ha utenti registrati.
	 */
	public List<String> influencers();
		
	/**
	 * @requires username != null &amp; this.isRegistered(username)
	 * @param username
	 * 				Lo username dell'utente.
	 * @return Una Lista di Post che contiene tutti i post scritti da username.
	 * @throws NullPointerException se username == null
	 * @throws UserException se !this.isRegistered(username)
	 */
	public List<Post> writtenBy(String username);
	
	/**
	 * @requires ps != null &amp; username != null &amp; 
	 * this.isRegistered(username) &amp; 
	 * forall Post p : ps, (p != null &amp; this.isRegistered(p.getAuthor())
	 * @param ps
	 * 			La lista di post da controllare.
	 * @param username
	 * 			Lo username dell'utente.
	 * @return Una Lista di Post che contiene tutti i post scritti da username in ps (una lista
	 * vuota se non ce n'è nessuno!).
	 * @throws NullPointerException se ps == null
	 * @throws NullPointerException se username == null 
	 * @throws UserException se !this.isRegistered(username).
	 * @throws PostException se !this.containsPost(p)
	 * @throws PostException se esiste Post p : ps | p == null
	 * @throws PostException se esiste Post p : ps | !this.isRegistered(p.getAuthor())
	 */
	public List<Post> writtenBy(List<Post> ps, String username);
	
	/**
	 * @requires words != null &amp; forall String s : words, s != null
	 * @param words Una lista di stringhe che contiene le parole da cercare, 
	 * senza valori null.
	 * @return Una Lista di Post che contiene tutti i post nella rete che contengono almeno una delle
	 * parole specificate in words.
	 * @throws NullPointerException se words == null.
	 * @throws IllegalArgumentException se esiste String s : words | s == null
	 */
	public List<Post> containing(List<String> words);
}