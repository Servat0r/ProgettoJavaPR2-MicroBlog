package microBlog.test;

import microBlog.user.*;
import microBlog.post.*;

import java.util.ArrayList;
import java.util.List;

import microBlog.network.*;

final class MyTestAll {

	private static void print(Object o) {
		System.out.println(o);
	}
	
	private static void println() {
		System.out.println();
	}
	
	public static void main(String[] args) {
		SocialNetwork sn = new MicroBlog();
		User u1 = new UserImpl("Salvatore", sn);
		print(u1.getRegistrationDate());
		print(u1.getUsername());
		u1.writePost("Hello World");
		u1.writePost("Non c'è nessuno qui?");
		User u2 = new UserImpl("Giuseppe", sn);
		User u3 = new UserImpl("Giacomo", sn);
		User u4 = new UserImpl("Prof_Ferrari", sn);
		List<Post> l = u1.getPost(u1.getUsername());
		u2.addLike(l.get(1));
		u3.addLike(l.get(1));
		u4.addLike(l.get(1));
		u2.writePost("Ciao @Salvatore");
		u3.writePost("Oi ciao, ma c'è anche @Giuseppe");
		Post tp = u4.writePost("Salve @Salvatore, @Giuseppe e @Giacomo: pronto il progetto?");
		u1.addLike(tp);
		u2.addLike(tp);
		u3.addLike(tp);
		Post ntp = u2.writePost("Buonasera professore @Prof_Ferrari, abbiamo quasi finito");
		u1.addLike(ntp);
		Post mtp = u3.writePost("Dobbiamo finire di scrivere i test");
		u4.addLike(mtp);
		u4.writePost("Molto bene, allora quando avete fato fatemi sapere!");
		u4.writePost("Ahahah ho sbagliato a scrivere nel post preccedente");
		u4.writePost("Eh vabbé lo sapete che io non leggo mai le cose che scrivo");
		Post ferrp = u4.writePost("In verità io vi dico: a me interessano soltanto i tipi di dato: se voi"
				+ " vi studiate bene quelli l'esame l'avete già passato");
		List<Post> ferraris = sn.writtenBy(u4.getUsername());
		int n = ferraris.size()-1;
		print(ferrp == ferraris.get(n));
		ferraris.set(n, u1.addLike(ferraris.get(n)));
		ferraris.set(n, u2.addLike(ferraris.get(n)));
		ferraris.set(n, u3.addLike(ferraris.get(n)));
		//ferrp = u1.addLike(ferrp);
		//ferrp = u2.addLike(ferrp);
		//ferrp = u3.addLike(ferrp);
		List<Post> l1 = sn.writtenBy(u1.getUsername());
		List<Post> l2 = sn.writtenBy(u2.getUsername());
		List<Post> l3 = sn.writtenBy(u3.getUsername());
		List<Post> l4 = sn.writtenBy(u4.getUsername());
		/*for (Post p : l1) print(p);
		println();
		for (Post p : l2) print(p);
		println();
		for (Post p : l3) print(p);
		println(); */
		print(l4.get(l4.size()-1).getLikes().size());
		for (Post p : l4) print(p);
		println();
		print(sn.influencers());
	} 

}
