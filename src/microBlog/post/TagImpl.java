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

	@Override
	public Post getPost() {
		return (Post)((PostImpl)this.post).clone();
	}

	@Override
	public String getTagText() {
		return this.tagText;
	}

	@Override
	public int getId() {
		return this.id;
	}

	protected Object clone() {
		Tag t = new TagImpl(this.getPost(), this.getTagText(), this.getId());
		return t;
	}
}