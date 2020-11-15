/**
 * 
 */
package microBlog.user;

/**
 * Eccezione unchecked che rappresenta un errore causato da un utente "scorretto"
 * fornito in input (ad esempio, non registrato alla rete corrente, oppure non
 * esistente etc.)
 * 
 * @author Salvatore Correnti
 * 
 * @see User
 */
public final class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 1313288492757496328L;

	public UserException() {
		super();
	}

	public UserException(String message) {
		super(message);
	}
}