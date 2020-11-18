package microBlog.user;

import java.util.Date;
import java.util.List;

import microBlog.post.*;

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
	 * @requires text != null &amp; Post.checkTextLength(text)
	 * @param text Il testo del post
	 * @return Crea un TextPost con testo text e restituisce un riferimento a questi.
	 * @throws NullPointerException se text == null
	 * @throws PostException se !Post.checkTextLength(text)
	 * @throws PostException se è taggato un utente non presente nella rete
	 */
	public TextPost writeTextPost(String text);
	
	/**
	 * @requires tp != null &amp; this.equals(tp.getAuthor()) &amp; tp è contenuto
	 * nella stessa rete di this
	 * @param tp Il TextPost da eliminare.
	 * @return true se il post è stato rimosso con successo, false altrimenti
	 * @throws NullPointerException se tp == null
	 * @throws PermissionDeniedException se !this.equals(tp.getAuthor())
	 * @throws PostException se tp non è nella stessa rete di this
	 */
	public boolean removeTextPost(TextPost tp);
	
	//TODO In questo modo non si può mettere like a un proprio post
	/**
	 * @requires tp != null &amp; !this.equals(tp.getAuthor()) &amp; tp è nella stessa
	 * rete di this
	 * @param tp Il TextPost a cui mettere il like.
	 * @return Il TextPost aggiornato col nuovo like.
	 * @throws PostException se tp non è nella stessa rete di this
	 * @throws NullPointerException se username == null
	 * @throws PermissionDeniedException se this.equals(tp.getAuthor())
	 */
	public TextPost addLike(TextPost tp);
	
	/**
	 * @requires username != null &amp; this.getUsername() e username devono 
	 * essere utenti della stessa rete.
	 * @return Una lista dei TextPost scritti da username.
	 * @throws NullPointerException se username == null
	 * @throws UserException se this.getUsername() e username non sono utenti 
	 * della stessa rete.
	 */
	public List<TextPost> getTextPost(String username); 
}