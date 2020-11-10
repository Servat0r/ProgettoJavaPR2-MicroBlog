package microBlog.post;

public enum Visibility {
	ME, //Solo io posso vedere (e stampare) i miei post
	FOLLOWERS, //Io e i miei followers possiamo vedere (e stampare) i miei post
	ALL; //Tutti possono vedere (e stampare) i miei post
}