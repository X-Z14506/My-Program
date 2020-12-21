package operation;

import book.Book;
import book.BookList;


public class BorrowOperation  implements IOperation{
    public void work(BookList bookList) {
        System.out.println("借阅图书！");
        System.out.println("请输入借阅图书的书名：");
        String name = this.scan.next();
        int currentSize = bookList.getUsedSize();
        int i = 0;
        for (; i < currentSize; i++) {
            Book book = bookList.getBook(i);
            if(book.getName().equals(name)) {
                //System.out.println(book);
                book.setBorrowed(true);
                break;
            }
        }
        if(i == currentSize) {
            System.out.println("没有此书！");
            return;
        }
    }
}
