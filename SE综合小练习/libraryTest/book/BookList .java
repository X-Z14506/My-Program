package book;




public class BookList {

    private Book[] elem = new Book[10];

    private int usedSize;

    public BookList() {
        this.elem[0] = new Book("三国演义","罗贯中",14,"小说");
        this.elem[1] = new Book("Java语言程序设计","梁勇",54,"学习");
        this.elem[2] = new Book("西游记","吴承恩",24,"小说");
        this.usedSize = 3;
    }

    //尾插
    public void setBook(int pos,Book book) {
        this.elem[pos] = book;
    }

    public Book getBook(int pos) {
        return this.elem[pos];
    }

    public int getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(int usedSize) {
        this.usedSize = usedSize;
    }

}
