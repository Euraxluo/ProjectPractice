public class Main {

    public static void main(String[] args) {
        BookList bookList = new BookList();
        Book b1 = new Book("1","book1",12.12);
        Book b2 = new Book("2","book2",13.13);
        bookList.addBook(b1);
        bookList.addBook(b2);
        Iterator iter = bookList.Iterator();

//        while (iter.hasNext()){
//            Book book = (Book) iter.next();
//            System.out.println(book.toString());
//        }

        for (Iterator i = bookList.Iterator();i.hasNext();){
            Book book = (Book) iter.next();
            System.out.println(book.toString());
        }
    }
}
