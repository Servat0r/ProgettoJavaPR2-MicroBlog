/**
 * 
 */
package microBlog.test;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import microBlog.network.*;
import microBlog.post.*;
import microBlog.user.*;

/**
 * Batteria di test per il progetto.
 * 
 * @author Giuseppe Russo
 *
 */
public final class MyTestAll {

	public static void main(String[] args) {
		Set<Integer> si = new TreeSet<Integer>();
		si.add(12);
		si.add(23);
		si.add(12);
		
		
		MicroBlog sn = new MicroBlog();
		User user1 = new UserImpl("Salvatore", sn);
		user1.writeTextPost("Sono un hacker");
		
		User user2 = new UserImpl("Giuseppe99", sn);
		
		user1.writeTextPost("@Giuseppe99 dove sei @ ?");
		user1.writeTextPost("Ho fatto la parte di back-end");
		user2.writeTextPost("Oi @Salvatore io sto facendo i test");
		
		int[] arr = user2.getTextPostIds("Salvatore");
		user2.addLike(arr[1], "Salvatore");
		user2.addLike(arr[2], "Salvatore");

		int[] arr2 = user1.getTextPostIds("Giuseppe99");
		user1.addLike(arr2[0], "Giuseppe99");
		System.out.println("IsLiked" + sn.isLikedByUser(sn.getPost(arr[1], "Salvatore"), "Giuseppe99"));
		user2.removeLike(arr[1], "Salvatore");
		user2.removeLike(arr[2], "Salvatore");
		System.out.println("IsLiked" + sn.isLikedByUser(sn.getPost(arr[1], "Salvatore"), "Giuseppe99"));
		System.out.println("Foll" + sn.isFollowing("Giuseppe99", "Salvatore"));
		user1.removeLike(arr2[0], "Giuseppe99");
		for (Post p : sn.writtenBy("Salvatore")) System.out.println(p.toString());
		user1.removePost(1);
		for (Post p : sn.writtenBy("Salvatore")) System.out.println(p.toString());
		user1.unregister();
		System.out.println(sn.getMentionedUsers());
		//user1.writeTextPost("We lo sai che non sono morto @Giuseppe99?");
	}

}