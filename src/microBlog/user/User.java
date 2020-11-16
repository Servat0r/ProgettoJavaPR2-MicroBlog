package microBlog.user;

import java.util.Date;
import microBlog.network.*;
import microBlog.post.PostException;

/**
 * Modella un utente della rete sociale MicroBlog. Un utente è un oggetto che può:
 * - registrarsi a o cancellarsi da una rete sociale;
 * - pubblicare o rimuovere post;
 * - aggiungere o rimuovere like ai post di un altro utente (e dunque rispettivamente
 *   seguirlo oppure smettere di seguirlo);
 * - sono inclusi altri utility methods (getPosts, getLikes).
 * 
 * Dopo essere stato creato, un utente può registrarsi a UNA SOLA rete sociale, e in
 * tal caso potrà interagire (pubblicare/rimuovere post, aggiungere/rimuovere likes)
 * solo con essa.
 * I metodi register, unregister, writePost, removePost, addLike, removeLike chiamano
 * i corrispondenti metodi della rete sociale dell'utente comunicandogli che è l'utente
 * stesso ad averne fatto richiesta, pertanto è garantito che SOLO l'utente può modificare
 * effettivamente i propri dati (insieme di post, likes e persone seguite) sulla rete.
 * L'utente non comunica con la rete direttamente attraverso i post, ma solo attraverso
 * gli id dei medesimi, attraverso i metodi getTextPostIds e getLikeIds.
 * @author Salvatore Correnti
 *
 */
public interface User {

	/**
	 * @requires
	 * @return Lo username dell'utente.
	 */
	public String getUsername();
	
	/**
	 * @requires
	 * @return La data di registrazione dell'utente
	 */
	public Date getRegistrationDate();
	
	/**
	 * @requires
	 * @return true se l'utente ha mandato una richiesta alla sua rete sociale (se
	 * esistente), false altrimenti
	 */
	public boolean hasSentRequest();
	
	/**
	 * @requires
	 * @return true se l'utente è registrato a una rete sociale, false altrimenti
	 */
	public boolean isRegistered();
	
	//TODO Controllare che i tag siano di utenti effettivamente presenti nella rete
	/**
	 * @requires text != null &amp; this.isRegistered() &amp; 
	 * Post.checkTextLength(text)
	 * @param text Il testo del post
	 * @return true se il post è stato aggiunto con successo alla rete, false
	 * altrimenti
	 * @throws NullPointerException se text == null
	 * @throws PermissionDeniedException se !this.isRegistered()
	 * @throws PostException se !Post.checkTextLength(text)
	 * @throws PostException se è taggato un utente non presente nella rete
	 */
	public boolean writeTextPost(String text);
	
	/**
	 * @requires id &gt; 0
	 * @param id L'id del post da eliminare
	 * @return true se il post è stato rimosso con successo, false altrimenti
	 * @throws IllegalArgumentException se id &le; 0
	 */
	public boolean removePost(int id);
	
	/**
	 * @requires username != null &amp; this.isRegistered() &amp; this.getAuthor()
	 * e username compaiono nella stessa rete.
	 * @param username Lo username dell'utente di cui si vogliono gli id dei post.
	 * @return Un array di interi che contiene l'id di ogni TextPost che l'utente
	 * ha pubblicato sulla sua rete sociale, se registrato a una.
	 * @throws NullPointerException se username == null
	 * @throws PermissionDeniedException se !this.isRegistered()
	 * @throws UserException se username non è registrato alla stessa rete di this
	 */
	public int[] getTextPostIds(String username);
	
	/**
	 * @requires id &gt; 0 &amp; username != null &amp; this.isRegistered() &amp;
	 * this.getAuthor() e username compaiono nella stessa rete.
	 * @param id L'id del post a cui mettere il like
	 * @param username Lo username dell'utente a cui si mette like
	 * @return true se il like è stato aggiunto con successo, false altrimenti
	 * @throws IllegalArgumentException se id &le; 0
	 * @throws NullPointerException se username == null
	 * @throws PermissionDeniedException se !this.isRegistered()
	 * @throws UserException se username non è registrato alla stessa rete di this
	 */
	public boolean addLike(int id, String username);
}