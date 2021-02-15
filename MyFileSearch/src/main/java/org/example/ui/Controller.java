package org.example.ui;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.example.model.FileMeta;
import org.example.service.FileService;
import org.example.task.FileScanner;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    public GridPane rootPane;

    @FXML
    public Label srcDirectory;

    @FXML
    public TextField searchField;
    @FXML
    public TableView<FileMeta> fileTable;
    @FXML
    public TableColumn<FileMeta,String> nameColumn;
    @FXML
    public TableColumn<FileMeta,String> sizeColumn;
    @FXML
    public TableColumn<FileMeta,String> lastModifiedColumn;

    private final FileService fileService = new FileService();
    private final FileScanner fileScanner = new FileScanner();

    //绑定鼠标点击事件
    @FXML
    public void choose(MouseEvent mouseEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        //弹出对话框
        File rootFile = chooser.showDialog(rootPane.getScene().getWindow());
        if (rootFile == null) {
            return;
        }
        Thread thread = new Thread(()->
                //启动扫描任务
                fileScanner.scan(rootFile)
        );
        thread.start();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //惠爱爱FXMLLoader.load执行时，实例化好AppController后调用
        StringProperty stringProperty = searchField.textProperty();
        //绑定一个搜索框输入时间，搜索框文本内容一旦更新，就会触发这个事件
        stringProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<FileMeta> fileList = fileService.query(newValue.trim());
                //查询出来的数据显示在表中
                //涉及到UI的部分放在主线程中，因为会存在线程不安全问题
                Platform.runLater(()->{
                    //先清空表中显示的前一次查询的数据
                    fileTable.getItems().clear();
                    //将新查询到的数据添加进去
                    fileTable.getItems().addAll(fileList);
                });
            }
        });
    }
}
