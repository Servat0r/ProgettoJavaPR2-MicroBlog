package microBlog.post;

public interface Tag extends Cloneable {

	/**
	 * @requires
	 * @return Il Post in cui questo tag è contenuto.
	 */
	public Post getPost();
	
	/**
	 * @requires
	 * @return Il testo del tag.
	 */
	public String getTagText();
	
	/**
	 * @requires
	 * @return L'Id del tag.
	 */
	public int getId();
}