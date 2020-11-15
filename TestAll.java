/**
 * 
 */
package microBlog.test;

import microBlog.network.*;
import microBlog.post.*;
import microBlog.user.*;

/**
 * Batteria di test per il progetto.
 * 
 * @author Giuseppe Russo
 *
 */
public final class TestAll {

	
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		Main.firstTest();
		Main.secondTest();
	}
	
	public static void firstTest(){
		SocialNetwork blog1=new MicroBlog();
		SocialNetwork blog2=new MicroBlog();
		User user_prova=new UserImpl("user prova", blog1);
		User user_prova2=new UserImpl("user prova 2", blog1);
		User user_denied=new UserImpl("user denied", blog1);
		user_denied.unregister();
		user_prova.writeTextPost("messaggio cancellato");
		user_prova2.writeTextPost("messaggio con like");

		System.out.println("************TEST USER******************");
		// username null

		try {
			User user1= new UserImpl(null, blog1);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserExeption e) {
			System.out.println(e);
		}

		//net null
		try {
			User user1= new UserImpl("user1", null);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserExeption e) {
			System.out.println(e);
		}

		//presente nel net precedentemente
		try {
			User user1= new UserImpl("user_prova", blog1);
		}
		catch (NullPointerException e) {
		System.out.println(e);
		}
		catch (UserExeption e) {
			System.out.println(e);
		}

		//rimozione di un utente non registato
		try {
			user_prova.unregister(blog2);
		}
		catch (UserException e) {
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

		//creazione di un post da un utente non registrato
		try {
			System.out.println(user_denied.writeTextPost("post non inviato"));
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
			System.out.println(user_prova.removePost(-1));
		}
		catch(IllegaArgumentExeption e){
			System.out.println(e);
		}

		//aggiunta di un like ad un post con id<0
		try{
			System.out.println(user_prova.addLike(-1, "user prova 2"));
		}
		catch(IllegaArgumentExeption e){
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
			System.out.println(user_prova.addLike(user_prova.getTextPostIds("user prova 2")[0], null));
		}
		catch(IllegaArgumentExeption e){
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

		//aggiunta di un like ad un post da un utente non registrato
		try{
			System.out.println(user_denied.addLike(user_denied.getTextPostIds("user prova 2")[0], "user prova 2"));
		}
		catch(IllegaArgumentExeption e){
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
	
		//aggiunta di un like ad un post con username non registrato nella stessa rete di this
		try{
			System.out.println(user_prova.addLike(user_prova.getTextPostIds("user denied")[0], "user prova 2"));
		}
		catch(IllegaArgumentExeption e){
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
		
	}

	
}

