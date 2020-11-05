package microBlog.post;

import java.io.Serializable;

enum Visibility implements Serializable {
	ME, FOLLOWERS, ALL;
}