package operation;

import book.Book;
import book.BookList;

public class DisplayOperation  implements IOperation{
    public void work(BookList bookList) {
        System.out.println("显示图书！");
        int currentSize = bookList.getUsedSize();
        for (int i = 0; i < currentSize; i++) {
            Book book = bookList.getBook(i);
            System.out.println(book);
            //System.out.println(bookList[i]); error
        }
    }
}
