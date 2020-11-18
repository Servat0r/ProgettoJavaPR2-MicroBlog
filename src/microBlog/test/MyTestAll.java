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
	
	public static void main(String[] args) {
		SocialNetwork sn = new MicroBlog();
		User u1 = new UserImpl("Salvatore", sn);
		print(u1.getRegistrationDate());
		print(u1.getUsername());
		u1.writeTextPost("Hello World");
		u1.writeTextPost("Non c'è nessuno qui?");
		User u2 = new UserImpl("Giuseppe", sn);
		User u3 = new UserImpl("Giacomo", sn);
		User u4 = new UserImpl("Prof_Ferrari", sn);
		List<TextPost> l = u1.getTextPost(u1.getUsername());
		u2.addLike(l.get(1));
		u3.addLike(l.get(1));
		u4.addLike(l.get(1));
		u2.writeTextPost("Ciao @Salvatore");
		u3.writeTextPost("Oi ciao, ma c'è anche @Giuseppe");
		TextPost tp = u4.writeTextPost("Salve @Salvatore, @Giuseppe e @Giacomo: pronto il progetto?");
		u1.addLike(tp);
		u2.addLike(tp);
		u3.addLike(tp);
		TextPost ntp = u2.writeTextPost("Buonasera professore @Prof_Ferrari, abbiamo quasi finito");
		u1.addLike(ntp);
		TextPost mtp = u3.writeTextPost("Dobbiamo finire di scrivere i test");
		u4.addLike(mtp);
		u4.writeTextPost("Molto bene, allora quando avete fato fatemi sapere!");
		u4.writeTextPost("Ahahah ho sbagliato a scrivere nel post preccedente");
		u4.writeTextPost("Eh vabbé lo sapete che io non leggo mai le cose che scrivo");
		/*List<TextPost> l = new ArrayList<>();
		l.add();
		User v = new UserImpl("Giuseppe", sn);
		l.set(0, v.addLike(l.get(0)));
		print(l.get(0));
		l.add(u.writeTextPost("Ciao @Giuseppe"));
		l.set(1, v.addLike(l.get(1)));
		print(l.get(1));
		print(sn.influencers());
		List<Post> lp = sn.writtenBy(u.getUsername());
		print("Likes a " + u.getUsername() + ": " +
		((MicroBlog)sn).getTotalFollowersCount(u.getUsername()));
		User w = new UserImpl("Prof_Ferrari", sn);
		w.writeTextPost("Ricordatevi sempre che un like è anche un post");
		w.writeTextPost("Ricordatevi che i tipi sono la cosa più bella del mondo");
		w.writeTextPost("Ricordatevi che io non leggo mai le mie slide");
		w.writeTextPost("In verità io vi dico: a me interessano soltanto i tipi di dato");
		List<TextPost> fp = u.getTextPost("Prof_Ferrari");
		print(fp.size());
		for (int i = 0; i < fp.size(); i++) {
			fp.set(i, u.addLike(fp.get(i)));
		}
		TextPost u3 = u.writeTextPost("Che belle parole @Prof_Ferrari");
		u3 = v.addLike(u3);
		u3 = w.addLike(u3);
		l.set(0, w.addLike(l.get(0)));
		l.set(1, w.addLike(l.get(1)));
		print(sn.influencers()); */
	} 

}
