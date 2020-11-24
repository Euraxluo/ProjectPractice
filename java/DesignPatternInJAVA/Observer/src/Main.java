public class Main {

    public static void main(String[] args) {
        BlogPuber puber = new BlogPuber();
        puber.addObserver(new BlogSuber());
        puber.publishBlog("HELLO WORDL","初来乍到,请多指教");
    }
}
