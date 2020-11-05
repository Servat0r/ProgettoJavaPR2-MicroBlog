package microBlog.network.offensive;

import microBlog.network.*;
import microBlog.post.*;

public interface SNOffensiveContents extends SocialNetwork {

	/**
	 * Modella una rete sociale come quella in microBlog.network in cui è possibile segnalare
	 * contenuti offensivi presenti nei post (i quali verranno eliminati).
	 */
	
	/**
	 * @requires post != null && this.containsPost(post)
	 * @param post
	 * 			Il post con contenuti offensivi da segnalare.
	 * @effects Se il post è presente nella rete, viene rimosso.
	 * @throws NPE se post == null
	 * @throws IllegalArgumentException se !this.containsPost(post)
	 */
	public void signalOffensiveContent(Post post);
}