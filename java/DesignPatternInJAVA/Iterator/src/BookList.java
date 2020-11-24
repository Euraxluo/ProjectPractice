import java.util.ArrayList;
import java.util.List;

public class BookList {
    private int index;
    private List<Book> books;
    private Iterator iterator;

    public BookList() {
        books = new ArrayList<Book>();
    }
    public void addBook(Book book){
        books.add(book);
    }
    public void delBook(Book book){
        int bookIndex = books.indexOf(book);
        books.remove(bookIndex);
    }
    public Iterator Iterator(){
        return new Itr();
    }

    private class Itr implements Iterator {
        @Override
        public boolean hasNext() {
            if(index >= books.size()){
                return false;
            }
            return true;
        }

        @Override
        public Object next() {
            return books.get(index++);
        }
    }
}
