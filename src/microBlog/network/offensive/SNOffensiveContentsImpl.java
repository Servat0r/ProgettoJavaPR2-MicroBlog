package microBlog.network.offensive;

import microBlog.network.*;
import microBlog.post.Post;

public final class SNOffensiveContentsImpl extends SocialNetworkImpl implements SNOffensiveContents {

	public boolean removeOffensivePost(Post post) {
		if (post == null) throw new NullPointerException();
		if (!this.containsPost(post)) throw new IllegalArgumentException();
		return this.removePost(post);
	}
}