package user;

import operation.*;

import java.util.Scanner;



public class c extends User{

    public NormalUser(String name) {
        super(name);
        this.iOperations =  new IOperation[] {
                new ExitOperation(),
                new FindOperation(),
                new BorrowOperation(),
                new ReturnOperation()
        };
    }

    @Override
    public int menu() {
        System.out.println("hello " + this.name + "欢迎你！");
        System.out.println("1.查找图书！");
        System.out.println("2.借阅图书！");
        System.out.println("3.归还图书！");
        System.out.println("0.退出图书！");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }


}
