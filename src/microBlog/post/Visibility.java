package microBlog.post;

import java.io.Serializable;

public enum Visibility implements Serializable {
	ME, FOLLOWERS, ALL;
}