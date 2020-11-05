package microBlog.post;

public class TagImpl implements Tag {

	private Post post;
	private String tagText;
	private int id;
	
	public TagImpl(Post post, String tagText, int id) {
		super();
		this.post = post;
		this.tagText = tagText;
		this.id = id;
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
		TagImpl other = (TagImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Post getPost() {
		return (Post)this.post.clone();
	}

	public String getTagText() {
		return this.tagText;
	}

	public int getId() {
		return this.id;
	}

	protected Object clone() {
		Tag t = new TagImpl(this.getPost(), this.getTagText(), this.getId());
		return t;
	}
}