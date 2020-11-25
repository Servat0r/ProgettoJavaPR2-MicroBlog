package microBlog.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import microBlog.post.*;
import microBlog.user.User;
import microBlog.user.UserException;

/**
 * Modella una rete sociale come quella in microBlog.network in cui è possibile segnalare
 * e rimuovere contenuti offensivi presenti nei post (i quali verranno eliminati).
 * 
 * @author Salvatore Correnti
 * 
 * @see SocialNetwork
 */

public final class MicroBlogExt extends MicroBlog {

	/**
	 * Lista di stringhe che contiene le parole considerate offensive nella rete.
	 */
	private List<String> forbiddenWords;
	
	/**
	 * Mappa che contiene tutti i post riconosciuti come offensivi.
	 */
	private Map<String, Set<Integer>> offensivePosts;
	
	
	/**
	 * Costruttore a 0 argomenti: in tal caso, this.forbiddenWords è inizializzata
	 * con dei valori di default.
	 */
	public MicroBlogExt() {
		super();
		List<String> sts = new ArrayList<>();
		sts.add("cazzo");
		sts.add("stronzo");
		sts.add("stronza");
		sts.add("muori");
		sts.add("di merda");
		this.forbiddenWords = sts;
		this.offensivePosts = new HashMap<String, Set<Integer>>();
	}
	
	/**
	 * Costruttore a 1 argomento: memorizza come parole proibite la lista passata.
	 * @requires forbiddenWords != null &amp; forall String fw : forbiddenWords,
	 * fw != null
	 * @param forbiddenWords La lista di parole proibite nella rete.
	 * @throws NullPointerException se forbiddenWords == null
	 * @throws IllegalArgumentException se esiste String fw : forbiddenWords t.c.
	 * fw == null
	 */
	public MicroBlogExt(final List<String> forbiddenWords) {
		super(); //Non si può chiamarlo dopo
		if (forbiddenWords == null) throw new NullPointerException();
		for (String fw : forbiddenWords) {
			if (fw == null) throw new IllegalArgumentException();
		}
		this.forbiddenWords = forbiddenWords;
	}
	
	/**
	 * Overriding del metodo definito in MicroBlog.java.
	 * @requires post != null &amp; !this.containsPost(post) &amp; 
	 * post.getText() != null &amp; this.isRegistered(post.getAuthor()) &amp; 
	 * post.getAuthor().hasSentRequest()
	 * @param post Il post da aggiungere alla rete.
	 * @return true se il post è stato aggiunto alla rete, false altrimenti. Stampa a schermo
	 * un messaggio di avvertimento se il post è stato considerato offensivo secondo i criteri
	 * della rete.
	 * @throws NullPointerException se post == null
	 * @throws PostException se this.containsPost(post)
	 * @throws PermissionDeniedException se !this.isRegistered(post.getAuthor())
	 * @throws PermissionDeniedException se !post.getAuthor().hasSentRequest()
	 */
	@Override
	public boolean storePost(Post post) {
		/* post.getText() != null automaticamente perché tale controllo è fatto
		in writePost in User.java
		Tutte le possibili eccezioni sono nel caso lanciate qui */
		boolean b = super.storePost(post);
		String text = post.getText();
		for(String fw : this.forbiddenWords) {
			if (text.indexOf(fw) != -1) {
				System.err.println(post.getAuthor().getUsername() + ": il tuo post contiene"
						+ " contenuti offensivi, pertanto\" ti invitiamo a rimuoverlo dalla"
						+ " rete sociale");
				this.offensivePosts.get(post.getAuthor().getUsername()).add(post.getId());
				break;
			}
		}
		return b;
	}
	
	/**
	 * Overriding del metodo removePost definito in MicroBlog.java.
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
	@Override
	public boolean removePost(Post post) {
		if (post == null) throw new NullPointerException();
		boolean b = super.removePost(post);
		this.offensivePosts.get(post.getAuthor().getUsername()).remove(post);
		return b;
	}
	
	/**
	 * Overriding del metodo registerUser definito in MicroBlog.java.
	 * @requires user != null &amp;!this.isRegistered(user.getName()) 
	 * &amp; user.hasSentRequest() 
	 * @param user
	 * 			L'utente da registrare.
	 * @effects this.isRegistered(user) diventa true.
	 * @throws NullPointerException se user == null
	 * @throws UserException se this.isRegistered(user)
	 * @throws PermissionDeniedException se !user.hasSentRequest()
	 */
	@Override
	public void registerUser(User user) {
		if (user == null) throw new NullPointerException("Utente non valido");
		Set<Integer> op = new HashSet<Integer>();
		this.offensivePosts.putIfAbsent(user.getUsername(), op);
		super.registerUser(user);
	}
	
	/**
	 * @requires id &gt; 0 &amp; username != null &amp; this.isRegistered(username) &amp;
	 * la rete contiene un post con tale id e nome utente
	 * @param id L'id del post.
	 * @param username Lo username dell'autore del post.
	 * @return true se il post con questo id è offensivo, false altrimenti.
	 */
	public boolean isOffensive(int id, String username) {
		Post p = this.getPost(id, username);
		return this.offensivePosts.get(username).contains(p.getId());
	}
}