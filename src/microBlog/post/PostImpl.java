package microBlog.post;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public final class PostImpl implements Post {
	
	private int id;
	private String author;
	private String text;
	private Visibility scope;
	private Date timestamp;
	private Set<Tag> tags;
	
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
		PostImpl other = (PostImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public PostImpl(int id, String author, String text, Visibility scope, Set<Tag> tags) {
	if (id <= 0) throw new IllegalArgumentException("Id cannot be <= 0!");
	if (author == null) throw new NullPointerException("");
	if (text == null) throw new NullPointerException("");
	if (scope == null) throw new NullPointerException("");
	if (tags == null) throw new NullPointerException("");
	this.timestamp = new Date();
	this.id = id;
	this.author = author;
	this.text = text;
	this.scope = scope;
	this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}
	
	public Visibility getVisibilityScope() {
		return scope; //Una enum è immutable
	}
	
	public Date getTimeStamp() {
		return (Date)this.timestamp.clone();
	}
	
	public Set<Tag> getTags() {
		Set<Tag> t = new HashSet<Tag>();
		for (Tag tag : this.tags) {
			TagImpl tagi = (TagImpl)tag;
			Tag tag2 = (TagImpl)(tagi.clone());
			t.add(tag2);
		}
		return t;
	}
	
	public Object clone() {
		return new PostImpl(this.id, this.getAuthor(), this.getText(), this.getVisibilityScope(),
				this.getTags());
	}
}