package microBlog.post;

/**
 * Eccezione unchecked che rappresenta un errore causato da un post "scorretto"
 * fornito in input (ad esempio, non appartenente alla rete sociale corrente,
 * oppure il cui autore non appartiene alla medesima oppure non esiste etc.)
 * 
 * @author Salvatore Correnti
 * 
 * @see Post
 */
public final class PostException extends RuntimeException {

	private static final long serialVersionUID = -3940853241188114607L;

	public PostException() {
		super();
	}

	public PostException(String message) {
		super(message);
	}
}