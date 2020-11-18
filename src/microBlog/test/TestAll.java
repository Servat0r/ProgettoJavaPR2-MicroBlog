/**
 * 
 */
package microBlog.test;

import java.util.ArrayList;
import java.util.List;

import microBlog.network.*;
import microBlog.post.*;
import microBlog.user.*;

/**
 * Batteria di test per il progetto.
 * 
 * @author Giuseppe Gabriele Russo
 *
 */
public final class TestAll {

	
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		TestAll.firstTest();
		TestAll.secondTest();
	}
	
	public static void firstTest(){
		SocialNetwork blog1=new MicroBlog();
		SocialNetwork blog2=new MicroBlog();
		SocialNetwork blog3=new MicroBlog();
		User user_prova=new UserImpl("user_prova", blog1);
		User user_prova2=new UserImpl("user_prova_2", blog1);
		User user_prova3=new UserImpl("user_prova_3", blog1);
		User user_blog2=new UserImpl("user_blog_2", blog2);
		
		Post post_prova=new TextPost(user_prova,"testo di prova");
		Post post_prova2=new TextPost(user_blog2,"testo di prova 2");
		Post post_prova3=new TextPost(user_blog2,"testo di prova 3");
		
		user_prova.publicPost(post_prova);
		user_blog2.publicPost(post_prova2);
		
		user_prova.writeTextPost(" altro messaggio con like");
		user_prova2.writeTextPost("messaggio con like");
		user_blog2.writeTextPost("messaggio user blog 2");
		
		user_prova2.addLike(user_prova.getTextPost("user_prova")[0]);
		
		
		List<Post> mappa_prova= new ArrayList<Post>();
		mappa_prova.add(post_prova);
		mappa_prova.add(null);
		
		List<Post> mappa_prova2= new ArrayList<Post>();
		mappa_prova2.add(post_prova2);
		
		List<Post> mappa_prova3= new ArrayList<Post>();
		mappa_prova3.add(post_prova);
		
		
		List<String> lista_prova=new ArrayList<String>();
		lista_prova.add("parola");
		lista_prova.add(null);
		
		
		
		
		
		System.out.println("************TEST USER******************");
		
		// username null
		try {
			User user1= new UserImpl(null, blog1);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserException e) {
			System.out.println(e);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}

		//net null
		try {
			User user1= new UserImpl("user1", null);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserException e) {
			System.out.println(e);
		}	
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}

		//presente nella rete sociale precedentemente
		try {
			User user1= new UserImpl("user_prova", blog1);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserException e) {
			System.out.println(e);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		
		
		
		//creazione di un utente con spazi nello username
		try {
			User user1= new UserImpl("user spazio prova", blog1);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserException e) {
			System.out.println(e);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}


		//creazione di un post con testo null
		try {
			System.out.println(user_prova.writeTextPost(null));
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}
		
		
		//creazione di un post con tag nel testo a utente non presente nella rete
		try {
			System.out.println(user_prova.writeTextPost("A @giuseppe"));
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}
		
		


		//creazione di un post con testo >140 caratteri 
		try {
			System.out.println(user_prova.writeTextPost("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}

		//rimozione di un post con id<0
		try{
			System.out.println(user_prova.removeTextPost(-1));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}

		//aggiunta di un like ad un post con id<0
		try{
			System.out.println(user_prova.addLike(-1));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(PermissionDeniedException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}

		//aggiunta di un like ad un post con username null
		try{
			System.out.println(user_prova.addLike(user_prova.getTextPost("user_prova_2")[0]));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(PermissionDeniedException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}

	
		//aggiunta di un like ad un post con username non registrato nella stessa rete 
		try{
			System.out.println(user_prova.addLike(user_prova.getTextPost("user_blog_2")[0]));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(PermissionDeniedException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}
		
		
		//pubblicazione di un post null
		try {
			user_prova.publicPost(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//pubblicazione di un post gia' presente nella rete
		try {
			user_prova.publicPost(post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		
		
		//pubblicazione di un post con autore diverso da this
		try {
			user_prova.publicPost(post_prova2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		

		System.out.println("************TEXT POST******************");
		
		
		//check di un testo con text null
		try {
			System.out.println(Post.checkTextLength(null));
		}
		catch(PostException e){
			System.out.println(e);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		
		
		//creazione di un post con author null
		try {
			Post post_wrong=new TextPost(null,"testo di prova per post");
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}
		catch(PostException e){
			System.out.println(e);
		}
		
		
		
		//creazione di un post con text null
		try {
			Post post_wrong=new TextPost(user_prova,null);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}
		catch(PostException e){
			System.out.println(e);
		}
		
		
		//creazione di un post con testo >140 caratteri
		try {
			Post post_wrong=new TextPost(user_prova,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}
		catch(PostException e){
			System.out.println(e);
		}		
		
		
		
		//creazione di un post con text null
		try {
			Post post_wrong=new TextPost(user_prova,null);
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		catch(UserException e){
			System.out.println(e);
		}
		catch(PostException e){
			System.out.println(e);
		}
		
		//safe-copy di un post con set<like> null
		try {
			Post post_not_create=((TextPost) post_prova).copy(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {		
			System.out.println(e);
		}
		
	
		
		
		System.out.println("************SOCIAL NETWORK******************");
		
		
		//richiesta di check di un post null
		try {
			blog1.containsPost(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		
		//store di un post null
		try {
			blog1.storePost(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		
		//store di un post precedentemente postato
		try {
			blog1.storePost(post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
	
		
		
		// store di un post da utente non registrato
		try {
			blog1.storePost(post_prova2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		
		// store di un post senza autorizzazione utente
		try {
			blog1.storePost(post_prova3);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		
		
		//rimozione di un post null
		try {
			blog1.removePost(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		
		//rimozione di un post da una rete vuota
		try {
			blog3.removePost(post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//rimozione di un post senza autorizzazione utente
		try {
			blog1.removePost(post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//check di registrazione di utente null
		try {
		blog1.isRegistered(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		
		
		
		//registrazione di un utente null
		try {
		blog1.registerUser(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		
		//registrazione di un utente precedentemente registrato
		try {
			blog1.registerUser(user_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//registrazione di un utente senza autorizzazione
		try {
			blog1.registerUser(user_blog2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//ceck di un like a post null
		try {
			blog1.isLikedByUser(null,"user_prova2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//ceck di un like con username null
		try {
			blog1.isLikedByUser(post_prova,null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		
		//ceck di un like di un utente non registrato
		try {
			blog1.isLikedByUser(post_prova,"user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//ceck di un like di un post di un un utente non presente nella rete 
		try {
			blog1.isLikedByUser(post_prova2,"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like da utente null
		try {
			blog1.addLike(null,post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like da post null
		try {
			blog1.addLike(user_prova,null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like da utente non registrato
		try {
			blog1.addLike(user_blog2,post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		//aggiunta di un like ad un post non presente
		try {
			blog1.addLike(user_prova,post_prova2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like ad un post a cui si e' gia' messo like
		try {
			blog1.addLike(user_prova2,post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like ad un post a cui si e' gia' messo like
		try {
			blog1.addLike(user_prova3,post_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//ceck del follow ad utente null
		try {
			blog1.isFollowing(null,"user_prova_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		
		
		
		//ceck del follow da utente null
		try {
			blog1.isFollowing("user_prova",null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		
		
		//mappa di sottorete sociale di una lista null
		try {
			blog1.guessFollowers(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//mappa di sottorete sociale di una lista con un post null
		try {
			blog1.guessFollowers(mappa_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//mappa di sottorete sociale di una lista con un post null
		try {
			blog1.guessFollowers(mappa_prova2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//set di stringhe di user taggati in una list di post null
		try {
			blog1.getMentionedUsers(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//set di stringhe di user taggati in una list di post dove uno e' null
		try {
			blog1.getMentionedUsers(mappa_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		
		//set di stringhe di user taggati in una list di post dove uno non e' nella rete sociale
		try {
			blog1.getMentionedUsers(mappa_prova2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//lista di post scritto da utente null
		try {
			blog1.writtenBy(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//lista di post scritto da utente non presente nella rete sociale
		try {
			blog1.writtenBy("user_blog2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		//lista di post scritti da utente in una lista di post null
		try {
			blog1.writtenBy(null,"user_blog2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		
		//lista di post scritti da utente null in una lista di post
		try {
			blog1.writtenBy(mappa_prova3,null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//lista di post scritti da utente non presente nella rete sociale in una lista di post
		try {
			blog1.writtenBy(mappa_prova3,"user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//lista di post scritti da utente in una lista di post con un post null
		try {
			blog1.writtenBy(mappa_prova,"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//lista di post scritti da utente in una lista di post con un post scritto da un utente
		//non presente nella rete sociale
		try {
			blog1.writtenBy(mappa_prova2,"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		
		//lista di parole presenti in una lista di post null
		try {
			blog1.containing(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		
		
		
		//lista di parole presenti in una lista di post dove almneo un post e' null
		try {
			blog1.containing(lista_prova);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		
		
		
		//prende un post dello user dalla rete sociale con id<0
		try {
			((MicroBlog) blog1).getPost(-1,"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		//prende un post con id di uno user null dalla rete sociale 
		try {
			((MicroBlog) blog1).getPost(user_prova.getTextPost("user_prova")[0],null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		//prende un post con id di uno user non presente nella rete sociale 
		try {
			((MicroBlog) blog1).getPost(user_prova.getTextPost("user_prova")[0],"user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		//prende un post con id non appartenente allo user nella rete sociale 
		try {
			((MicroBlog) blog1).getPost(user_prova.getTextPost("user_prova_2")[0],"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		
	}
	
	public static void secondTest(){
		
	}
}

	


