package microBlog.network;

/**
 * Eccezione unchecked sollevata in caso di accesso illegale a metodi di SocialNetwork, 
 * in particolare ai metodi per manipolare post se non sono chiamati correttamente da 
 * un utente registrato.
 * 
 * @author Salvatore Correnti
 * 
 * @see SocialNetwork
 */
public final class PermissionDeniedException extends RuntimeException {
	
	private static final long serialVersionUID = 8376594615034946133L;

	public PermissionDeniedException() {
		super();
	}

	public PermissionDeniedException(String message) {
		super(message);
	}
}