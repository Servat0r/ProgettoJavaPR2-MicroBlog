package microBlog.test;

import microBlog.user.*;
import microBlog.post.*;
import microBlog.network.*;

final class MyTestAll {

	private static void print(Object o) {
		System.out.println(o);
	}
	
	public static void main(String[] args) {
		SocialNetwork sn = new MicroBlog();
		User u = new UserImpl("Salvatore", sn);
		print(u.isRegistered());
		print(u.getRegistrationDate());
		print(u.getUsername());
		u.writeTextPost("Hello World");
		int[] salvPosts = u.getTextPostIds(u.getUsername());
		print(salvPosts.length);
		User v = new UserImpl("Giuseppe", sn);
		print(v.isRegistered());
		v.addLike(salvPosts[0], u.getUsername());
		print(sn.influencers());
		u.writeTextPost("Ciao @Giuseppe");
	}

}
