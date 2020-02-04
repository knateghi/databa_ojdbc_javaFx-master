package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class CoffeeBreweryController {
    private User user;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    private Statement statement;
    Connection connection;

    @FXML
    void login(ActionEvent event) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    try {
//                        comp214_f19_ers_75
//                        password
                        connection = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:sqld", "comp214_f19_ers_75", "password");
                        System.out.println("connected to database");
                        statement = connection.createStatement();
//                        'kids2','steel'
                        ResultSet resultSet = statement.executeQuery(String.format("select IDSHOPPER, FIRSTNAME, LASTNAME from bb_shopper where idshopper = fun_authen_user('%s', '%s')", userNameTextField.getText(), passwordTextField.getText()));

                        resultSet.next();
                        user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource(
                                            "HomeScene.fxml"
                                    )
                            );
                            try {
                                userNameTextField.getScene().setRoot(loader.load());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            HomeController controller =
                                    loader.<HomeController>getController();
                            try {

                                controller.initData(user, connection);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            System.out.println();
                        }
                    });
                } catch (Exception ex) {
                    System.out.println(ex + "Fail to connect to database. try again");
                }
            }
        };
        new Thread(task).start();
    }
}