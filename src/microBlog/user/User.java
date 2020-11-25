package microBlog.user;

import java.util.Date;
import java.util.List;
import microBlog.network.PermissionDeniedException;

import microBlog.post.*;

/**
 * Tipo di dato astratto rappresentante un utente della rete. 
 */

/**
 * @overview Modella un utente della rete sociale MicroBlog. Un utente e' un oggetto che puo':
 * - registrarsi a una rete sociale;
 * - pubblicare o rimuovere post;
 * - aggiungere like ai post di un altro utente (e dunque rispettivamente seguirlo);
 * - sono inclusi altri utility methods (getMyPosts).
 * 
 * Dopo essere stato creato, un utente puo' registrarsi a UNA SOLA rete sociale, e in
 * tal caso potrà interagire (pubblicare/rimuovere post, aggiungere likes)
 * solo con essa.
 * I metodi register, writePost, removePost, addLike, chiamano
 * i corrispondenti metodi della rete sociale dell'utente comunicandogli che e' l'utente
 * stesso ad averne fatto richiesta, pertanto e' garantito che SOLO l'utente può modificare
 * effettivamente i propri dati (insieme di post, likes e persone seguite) sulla rete.
 * L'utente non comunica con la rete direttamente attraverso i post, ma solo attraverso
 * gli id dei medesimi ottenuti tramite writePost().
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
	 * @requires text != null &amp; Post.checkTextLength(text)
	 * @param text Il testo del post
	 * @modifies viene aggiunto un nuov elemento Post a this.net
	 * @return Crea un TextPost con testo text e restituisce un riferimento id a questi.
	 * @throws NullPointerException se text == null
	 * @throws PostException se !Post.checkTextLength(text)
	 * 		   PostException se è taggato un utente non presente nella rete
	 */
	public int writePost(String text);
	
	
	/**
	 * @requires id &gt; 0 
	 * @param id Il TextPost da eliminare.
	 * @modifies rimuove il post dal SocialNetwork in cui e' pubblicato
	 * @return true se il post è stato rimosso con successo, false altrimenti
	 * @throws NullPointerException se tp == null
	 * @throws PermissionDeniedException se !this.equals(tp.getAuthor())
	 * @throws PostException se tp non è nella stessa rete di this
	 */
	public boolean removePost(int id);
	
	//TODO Changed
	/**
	 * @requires id &gt; 0 e this.getUsername() e username devono 
	 * essere utenti della stessa rete.
	 * @param id Il TextPost a cui mettere il like.
	 * @param username Il nome dell'utente a cui si vuole mettere like.
	 * @return true se e' stato aggiunto con successo false altrimenti.
	 * @throws PostException se tp non è nella stessa rete di this
	 * @throws NullPointerException se username == null
	 * @throws PermissionDeniedException se this.equals(tp.getAuthor())
	 */
	public boolean addLike(int id, String username);
}