package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.service.DBService;

import java.net.URL;


public class MyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = MyApplication.class.getClassLoader().getResource("app.fxml");
        if (url == null) {
            throw new RuntimeException("没有找到该文件");
        }
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("zdy-本地文件搜索引擎");
        primaryStage.setHeight(900);
        primaryStage.setWidth(1000);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        DBService dbService = new DBService();
        //初始化，保证数据进来时，数据库就已经初始化好了
        dbService.init();
        //mian函数中必须调用launch方法，该方法会启动JavaFX程序。
        launch(args);
    }
}
