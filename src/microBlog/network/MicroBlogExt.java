package microBlog.network;

import microBlog.post.Post;

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
	 * @requires post != null && this.containsPost(post)
	 * @param post
	 * 			Il post con contenuti offensivi da segnalare.
	 * @return true se il post è presente nella rete (e allora viene rimosso), false altrimenti
	 * (e non succede nulla).
	 * @throws NPE se post == null
	 * @throws IllegalArgumentException se !this.containsPost(post)
	 * 
	 */
	public boolean removeOffensivePost(Post post) {
		if (post == null) throw new NullPointerException();
		if (!this.containsPost(post)) throw new IllegalArgumentException();
		return this.removePost(post);
	}
}