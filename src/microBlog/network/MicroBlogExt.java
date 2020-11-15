package microBlog.network;

import java.util.ArrayList;
import java.util.List;

import microBlog.post.*;
import microBlog.user.*;

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
	 * Lista di stringhe che contiene le parole non accettate nella rete.
	 */
	private List<String> forbiddenWords;
	
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
	 * Overriding del metodo definito in MicroBlog.java; la specifica è identica.
	 * @see SocialNetwork 
	 */
	@Override
	public boolean storePost(Post post) {
		if (post == null) throw new NullPointerException();
		/* post.getText() != null automaticamente perché tale controllo è fatto
		in writePost in User.java */
		String text = post.getText();
		for(String fw : this.forbiddenWords) {
			if (text.indexOf(fw) != -1) {
				System.out.println("Errore: il tuo post contiene contenuti offensivi,"
						+ " pertanto non sarà aggiunto alla rete sociale.");
				return false;
			}
		}
		//Tutte le altre possibili eccezioni sono nel caso lanciate qui
		return super.storePost(post);
	}
}