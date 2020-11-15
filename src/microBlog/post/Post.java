package microBlog.post;


import java.util.Date;
import java.util.Set;

import microBlog.user.User;

/**
 * Modella un post, i.e. un'entità che può essere creata da un utente e pubblicata
 * sulla rete sociale, ed è visibile a tutti gli altri utenti.
 * Un Post è caratterizzato da: autore, id, testo (di max 140 caratteri), data e ora 
 * di pubblicazione. Esempi di post sono:
 * - TextPost, il Post standard per pubblicare messaggi di testo;
 * - Like, un post che rappresenta un like che un utente può mettere a un Post di
 *   un altro utente.
 * 
 * @author Salvatore Correnti
 * 
 *
 */

public interface Post {

	/**
	 * @requires text != null
	 * @param text
	 * 			Il testo da cui bisogna rimuovere le segnature dei tag.
	 * @return Una copia di text in cui ha sostituito tutte le occorrenze di 
	 * "@[string]", dove string è una stringa non vuota che non contiene spazi,
	 * con [string].
	 * @throws NullPointerException se text == null
	 */
	static String removeTagSignatures(String text) {
		if (text == null) throw new NullPointerException();
		String[] t = text.split("@");
		String r = "";
		for (String w : t) r = r + w;
		return r;
	}
	
	/**
	 * @requires text != null
	 * @param text
	 * 			Il testo di cui bisogna verificare la lunghezza.
	 * @return true se text.length() &le; 140, false altrimenti.
	 * @throws NullPointerException se text == null
	 */
	public static boolean checkTextLength(String text) {
		String t = Post.removeTagSignatures(text);
		return (t.length() <= 140);
	}
	
	/**
	 * @requires 
	 * @return L'id del post.
	 */
	public int getId();
	
	/**
	 * @requires
	 * @return L'autore del post.
	 */
	public User getAuthor();
	
	/**
	 * @requires
	 * @return Il testo del post.
	 */
	public String getText();
	
	/**
	 * @requires
	 * @return La data e l'ora di pubblicazione del post.
	 */
	public Date getTimeStamp();
}