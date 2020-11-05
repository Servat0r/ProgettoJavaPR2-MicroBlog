package microBlog.post;

import java.util.Set;

final class PostImpl implements Post {
	
	private int id;
	private String author;
	private String text;
	private Visibility scope;
	private String timestamp; //TODO Rivedere
	private Set<Tag> tags;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Visibility getVisibilityScope() {
		return scope;
	}

	@Override
	public String getTimeStamp() {
		return timestamp; //TODO Rivedere
	}

	@Override
	public Set<Tag> getTags() {
		return tags;
	}
}