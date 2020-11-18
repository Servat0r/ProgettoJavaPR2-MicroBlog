package microBlog.post;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import microBlog.user.User;
import microBlog.user.UserException;

/**
 * Implementazione dell'interfaccia TextPost per rappresentare un post testuale.
 * Un TextPost ammette dei likes da parte degli utenti.
 * 
 * @author Salvatore Correnti
 *
 */
public final class TextPost implements Post {
	
	//Rappresenta il massimo id finora usato
	private static int currentMaxUsedId = 1;
	
	private final int id;
	private final User author;
	private final String text;
	private final Date timestamp;
	private final Set<Tag> tags;
	private final Set<Like> likes;
	
	//Due post sono uguali sse hanno lo stesso id.
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextPost other = (TextPost) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * Controlla in entrambi i costruttori le eccezioni comuni alle due specifiche
	 * dei due costruttori.
	 * 
	 * @requires author != null &amp; text != null &amp; author.isRegistered() &amp; 
	 * Post.removeTagSignatures(text).length() &le; 140
	 * @param author L'autore del post.
	 * @param text Il testo del post.
	 * @effects
	 * @throws NullPointerException se author == null
	 * @throws NullPointerException se text == null
	 * @throws UserException se !author.isRegistered()
	 * @throws PostException se !Post.checkTextLength(text)
	 */
	private static void checkForConstructorExceptions(User author, 
			String text) {
		if (author == null) throw new NullPointerException();
		if (text == null) throw new NullPointerException();
		if (!Post.checkTextLength(text)) throw new PostException(); 
	}
	
	/**
	 * Costruttore pubblico standard per un nuovo TextPost. Per identificare il
	 * nuovo oggetto, viene usato un contatore statico privato currentMaxUsedId.
	 * @requires Quanto specificato in:
	 * {@link #checkForConstructorExceptions(User, String)}
	 * @param author L'autore del post
	 * @param text Il testo del post
	 * @throws RuntimeException in {@link #checkForConstructorExceptions(User, String)}
	 */
	public TextPost(User author, String text) {
		TextPost.checkForConstructorExceptions(author, text);
		this.id = TextPost.currentMaxUsedId++;
		this.author = author;
		this.tags = Tag.getTagsFromText(text);
		this.text = Post.removeTagSignatures(text);
		this.timestamp = new Date();
		this.likes = new HashSet<Like>();
	}
	
	/**
	 * Costruttore privato utilizzato internamente per la deep copy di un TextPost.
	 * @requires Quanto specificato in:
	 * {@link #checkForConstructorExceptions(User, String)}
	 * per gli opportuni campi di tp.
	 * @requires tp.getId() &gt; 0 &amp; tp.getTags() != null &amp; tp.getTimeStamp() != null 
	 * &amp; forall (Tag t : tp.getTags()), t != null
	 * @param tp Il TextPost da copiare.
	 * @param likes I likes che il nuovo TextPost dovrà avere.
	 * @throws RuntimeException in {@link #checkForConstructorExceptions(User, String)}
	 * Si noti che non c'è bisogno di fare alcun controllo sugli altri campi di tp
	 * in quanto parte di un TextPost già esistente e dunque corretti.
	 */
	private TextPost(TextPost tp, Set<Like> likes) {
	TextPost.checkForConstructorExceptions(tp.getAuthor(), tp.getText());
	this.id = tp.getId();
	this.author = tp.getAuthor();
	this.text = tp.getText();
	this.tags = tp.getTags();
	this.timestamp = tp.getTimeStamp();
	this.likes = likes;
	}

	public int getId() {
		return id;
	}

	public User getAuthor() {
		return this.author;
	}

	public String getText() {
		return text;
	}
	
	public Date getTimeStamp() {
		return (Date)this.timestamp.clone();
	}
	
	/**
	 * @requires
	 * @return I tag del post.
	 */
	public Set<Tag> getTags() {
		return this.tags;
	}
	
	/**
	 * @return I likes di questo post.
	 */
	public Set<Like> getLikes() {
		Set<Like> sl = new HashSet<>(this.likes);
		return sl;
	}

	/**
	 * @requires newLikes != null &amp; forall Like l : newLikes, l.getPost() == this (!)
	 * @param newLikes Un Set di Like che saranno i like del nuovo post, se set vuoto
	 * si ottiene una copia effettiva del Post.
	 * @return Una safe-copy del Post corrente.
	 * @throws NullPointerException se newLikes == null
	 * @throws IllegalArgumentException se esiste Like l : newLikes t.c. l.getPost() != this
	 */
	public TextPost copy(Set<Like> newLikes) {
		newLikes.addAll(this.likes);
		return new TextPost(this, newLikes);
	}
	
	public String toString() {
		String w = "Post di " + this.getAuthor().getUsername();
		w = w + "\nId: " + this.getId();
		w = w + "\nTesto: " + this.getText();
		w = w + "\nData e ora: " + this.getTimeStamp().toString();
		w = w + "\nUtenti taggati: ";
		for (Tag t : this.getTags()) {
			w = w + t.getTagText() + "\n";
		}
		if (this.getTags().size() == 0) w = w + "\n";
		w = w + "Piace a: " + this.getLikes().size() + " persone";
		return w;
	}
}