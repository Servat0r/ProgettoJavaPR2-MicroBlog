package microBlog.post;

import java.util.HashSet;
import java.util.Set;

/**
 * Modella un tag aggiungibile a un Post per menzionare un altro utente.
 * Come scelta di progetto, si è deciso che un tag in un testo è definito come: 
 * "@[string]", dove string è una stringa non vuota che non contiene spazi.
 * Ogni utente può aggiungere quanti tag vuole nella creazione di un post.
 * La classe implementa Cloneable per poter copiare un tag in modo da non permettere
 * l'accesso al tag originale dall'esterno, e ha un metodo statico getTagsFromText
 * che genera un insieme di tag da un testo.
 * @RepInvariant tagText != null &amp; id &gt;0 
 * @AbstractFunction f(tagText, id) = &lt;testo_post, codice identificativo&gt;
 * 
 * @author Salvatore Correnti
 */
public class Tag implements Cloneable {

	//Rappresenta il massimo id finora utilizzato per creare un tag
	private static int currentMaxUsedId = 1;
	
	private final String tagText;
	private final int id;
	
	/**
	 * Costruttore pubblico (di default) dell'oggetto Tag.
	 * @requires tagText != null
	 * @param tagText Il testo del tag.
	 * @throws IllegalArgumentException se tagText == null
	 */
	public Tag(String tagText) {
		if (tagText == null) throw new IllegalArgumentException();
		this.tagText = tagText;
		this.id = Tag.currentMaxUsedId++;
	}
	
	/**
	 * @requires text != null
	 * @param text
	 * 			Il testo da cui estrarre i tag
	 * @return Un Set di Tag contenente tutti i tag che si sono potuti estrarre da
	 * text (vuoto se non se n'è estratto nessuno).
	 * @throws NullPointerException se text == null
	 */
	public static Set<Tag> getTagsFromText(String text){
		if (text == null) throw new NullPointerException("Testo non valido");
		String[] textArr = text.split("@");
		Set<Tag> tags = new HashSet<>();
		for (int i = 1; i < textArr.length; i++) {
			try {
			textArr[i] = textArr[i].split("[^a-zA-Z0-9_]")[0];	
			if (textArr[i].split("[0-9]")[0].length() > 0) tags.add(new Tag(textArr[i]));
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		return tags;
	}

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
		Tag other = (Tag) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * @requires
	 * @return Il testo del tag.
	 */
	public String getTagText() {
		return this.tagText;
	}

	/**
	 * @requires
	 * @return L'Id del tag.
	 */
	public int getId() {
		return this.id;
	}
}