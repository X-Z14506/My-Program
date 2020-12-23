package operation;

import book.Book;
import book.BookList;


public class FindOperation  implements IOperation{
    public void work(BookList bookList) {
        System.out.println("查找图书！");
        String name = this.scan.next();
        int currentSize = bookList.getUsedSize();
        int i = 0;
        for (; i < currentSize; i++) {
            Book book = bookList.getBook(i);
            if(book.getName().equals(name)) {
                System.out.println(book);
                break;
            }
        }
        if(i == currentSize) {
            System.out.println("没有此书！");
        }
    }
}
