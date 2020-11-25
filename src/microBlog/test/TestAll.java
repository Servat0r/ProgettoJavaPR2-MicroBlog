package microBlog.test;

import java.util.ArrayList;

import java.util.List;

import microBlog.network.*;
import microBlog.post.*;
import microBlog.user.*;

/**
 * Batteria di test per il progetto.
 * 
 * @author Salvatore Correnti
 *
 */
public final class TestAll {

	
	public static void main(String[] args) {
		TestAll.firstTest();
		TestAll.secondTest();
	}
	
	public static void firstTest(){
		System.out.println("************PRIMA BATTERIA DI TEST******************");
		
		
		SocialNetwork blog1=new MicroBlog();
		SocialNetwork blog2=new MicroBlog();
		SocialNetwork blog3=new MicroBlog();
		User user_prova=new UserImpl("user_prova", blog1);
		User user_prova2=new UserImpl("user_prova_2", blog1);
		User user_prova3=new UserImpl("user_prova_3", blog1);
		User user_blog2=new UserImpl("user_blog_2", blog2);
		
		int post_prova=user_prova.writePost("testo di prova");
		int post_prova2=user_prova2.writePost("testo di prova 2");
		int post_prova3=user_blog2.writePost("testo di prova 3");
			
		Post post_prova4=new TextPost(user_prova,"post prova 4");
		
		
		user_prova2.addLike(post_prova, "user_prova");
		
		
		List<Post> mappa_prova= new ArrayList<Post>();
		mappa_prova.add(blog1.getPost(post_prova,"user_prova"));
		mappa_prova.add(null);
		
		List<Post> mappa_prova2= new ArrayList<Post>();
		mappa_prova2.add(blog2.getPost(post_prova3,"user_blog_2"));
		
		List<Post> mappa_prova3= new ArrayList<Post>();
		mappa_prova3.add(blog1.getPost(post_prova,"user_prova"));
		
		
		List<String> lista_prova=new ArrayList<String>();
		lista_prova.add("parola");
		lista_prova.add(null);
		
		
		
		
		
		System.out.println("************TEST USER******************");
		
		// username null
		try {
			new UserImpl(null, blog1);
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
			new UserImpl("user1", null);
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
			new UserImpl("user_prova", blog1);
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
			new UserImpl("user spazio prova", blog1);
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
			System.out.println(user_prova.writePost(null));
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
			System.out.println(user_prova.writePost("A @giuseppe"));
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
			System.out.println(user_prova.writePost("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
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
			System.out.println(user_prova.removePost(-1));
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}
		
		
		
		//rimozione di un post di un'altro utente
		try{
			System.out.println(user_prova.removePost(post_prova2));
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}
		
		
		
		
		//rimozione di un post di un'altra rete
		try{
			System.out.println(user_prova.removePost(post_prova3));
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		catch (PermissionDeniedException e) {
			System.out.println(e);
		}
		catch (PostException e) {
			System.out.println(e);
		}
		
		
		

		//aggiunta di un like ad un utente null
		try{
			System.out.println(user_prova.addLike(post_prova,null));
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
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//aggiunta di un like ad unn post con id<0
		try{
			System.out.println(user_prova.addLike(-1,"user_prova"));
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
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//aggiunta di un like ad  un post non presente nella rete
		try{
			System.out.println(user_prova.addLike(post_prova3,"user_prova"));
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
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like ad  un post personale
		try{
			System.out.println(user_prova.addLike(post_prova,"user_prova"));
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
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		
		System.out.println("************TEXT POST******************");
		
		
		//check di un testo con text null
		try {
			System.out.println(Post.checkTextLength(null));
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		
		
		
		//rimozione tag di un testo null
		try {
			System.out.println(Post.removeTagSignatures(null));
		}
		catch(NullPointerException e){
			System.out.println(e);
		}
		
		
		
		
		//creazione di un post con author null
		try {
			new TextPost(null,"testo di prova per post");
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
			new TextPost(user_prova,null);
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
			new TextPost(user_prova,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
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
			blog1.getPost(post_prova, "user_prova").copy(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {		
			System.out.println(e);
		}
		
	
		System.out.println("************LIKE******************");
		
		
		
		//creazione di un like con author null
		try {
			new Like(null,blog1.getPost(post_prova, "user_prova"));
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//creazione di un like con post null
		try {
			new Like(user_prova,null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		//creazione di un like ad un post personale 
		try {
			new Like(user_prova,blog1.getPost(post_prova, "user_prova"));
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		//creazione di un like ad un post senza permesso utente
		try {
			Like like_wrong=new Like(user_prova,blog1.getPost(post_prova2, "user_prova_2"));
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
		
		
		
		
		System.out.println("************TAG******************");
		
		
		//costruzione ti un tag in un text null
		try {
			 Tag tag_null=new Tag(null);
		}
		catch(NullPointerException e){		
			System.out.println(e);
		}
		catch(IllegalArgumentException e){		
			System.out.println(e);
		}
		
		
		
		//set di tag da text null
		try {
			 Tag.getTagsFromText(null);
		}
		catch(NullPointerException e){		
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
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		
		
		
		
		//store di un post precedentemente postato
		try {
			blog1.storePost(blog1.getPost(post_prova, "user_prova"));
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
			blog1.storePost(blog2.getPost(post_prova3, "user_blog_2"));
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
			blog1.storePost(post_prova4);
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
		
		
		
		// store di un post con testo null
		try {
			blog1.storePost(new TextPost(user_prova,null));
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
			blog3.removePost(post_prova4);
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
		
		
		
		//rimozione di un post non presente nella rete
		try {
			blog2.removePost(blog1.getPost(post_prova, "user_prova"));
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
			blog1.removePost(blog1.getPost(post_prova, "user_prova"));
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
			blog1.isLikedByUser(blog1.getPost(post_prova, "user_prova"),null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		
		//ceck di un like di un utente non registrato
		try {
			blog1.isLikedByUser(blog1.getPost(post_prova, "user_prova"),"user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//ceck di un like di un post di autore non nella rete 
		try {
			blog1.isLikedByUser(blog2.getPost(post_prova3, "user_blog_2"),"user_prova");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//ceck di un like di un post di un utente non presente nella rete 
		try {
			blog1.isLikedByUser(blog1.getPost(post_prova, "user_prova"),"user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		
		//aggiunta di un like da utente null
		try {
			blog1.addLike(null,blog1.getPost(post_prova, "user_prova"));
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
			blog1.addLike(user_blog2,blog1.getPost(post_prova, "user_prova"));
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
			blog1.addLike(user_prova,blog2.getPost(post_prova3, "user_blog_2"));
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
			blog1.addLike(user_prova2,blog1.getPost(post_prova, "user_prova"));
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
		
		
		
		//aggiunta di un like ad un post senzapermesso utente
		try {
			blog1.addLike(user_prova3,blog1.getPost(post_prova,"user_prova"));
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
		
		
		
		//mappa di sottorete sociale di una lista con un post non presente nella rete
		try {
			blog1.guessFollowers(mappa_prova3);
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
		
		
		//lista di post scritti da utente in una lista di post non presenti nella rete
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
			((MicroBlog) blog1).getPost(post_prova,null);
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
			((MicroBlog) blog1).getPost(post_prova,"user_blog_2");
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
			((MicroBlog) blog1).getPost(post_prova3,"user_prova");
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
		
		
		
		//numero di followers di utente null
		try {
			blog1.getTotalFollowersCount(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
		
		//numero di followers di utente non presente nella rete
		try {
			blog1.getTotalFollowersCount("user_blog_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		
	}
	
	public static void secondTest(){
		
		System.out.println("************SECONDA BATTERIA DI TEST******************");
		
		
		SocialNetwork blog1=new MicroBlogExt();
		SocialNetwork blog2=new MicroBlogExt();
		User user1=new UserImpl("user_1",blog1);
		User user2=new UserImpl("user_2",blog2);
	
	
		int post1=user1.writePost("post numero 1");
	
	
		Post post2=new TextPost(user1,"post di prova 2");
		Post post3=new TextPost(user2,"post di prova 3");
	
	
		List<String> parole=new ArrayList<String>();
		parole.add("infame");
		parole.add(null);
	
	
	
		//creazione di un MicroBlogExt con lista di parole null
		try {
			new MicroBlogExt(null);
		}
		catch(NullPointerException e) {
		System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
	
	
		//creazione di un MicroBlogExt con lista di parole di cui una null
		try {
			new MicroBlogExt(parole);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
	
	
		//creazione di un post con forbidden words
		user1.writePost("prova post con muori");
	
	
		//store di un post null
		try {
			blog1.storePost(null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
	
	
		//store di un post precedentemente postato
		try {
		blog1.storePost(blog1.getPost(post1, "user_1"));
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
	
	
	
		//store di un post di autore non registrato
		try {
			blog1.storePost(post3);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
			System.out.println(e);
		}
	
	
	
		//store di un post senza permesso utente
		try {
			blog1.storePost(post2);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PermissionDeniedException e) {
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
	
	
	
		//rimozione di un post non presente nella rete
		try {
			blog1.removePost(post3);
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
	
	
	
	
		//rimozione di un post senza permesso utente
		try {
			blog1.removePost(blog1.getPost(post1, "user_1"));
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
	
	
		//registrazione di utente null
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
	
	
		//registrazione di utente  gia' nella rete
		try {
			blog1.registerUser(user1);
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
	
	
		//registrazione di utente senza la sua autorizzazione
		try {
			blog1.registerUser(user2);
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
	
	
		//richiesta check post offensivo con id<0
		try{
			((MicroBlogExt) blog1).isOffensive(-1,"user_1");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		
		//richiesta check post offensivo di utente null
		try{
			((MicroBlogExt) blog1).isOffensive(post3.getId(),null);
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
	
		
		
	
		//richiesta check post offensivo di post non presente
		try{
			((MicroBlogExt) blog1).isOffensive(post3.getId(),"user_2");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}
		
		
		//richiesta check post offensivo di id non associato a username
		try{
			((MicroBlogExt) blog1).isOffensive(200,"user_1");
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		catch(UserException e) {
			System.out.println(e);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(PostException e) {
			System.out.println(e);
		}	
	}
}