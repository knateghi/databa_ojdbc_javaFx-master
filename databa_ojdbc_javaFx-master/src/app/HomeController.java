package app;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    @FXML
    private TableView<BasketItem> basketTableView;

    @FXML
    private TableColumn<BasketItem, String> bProductNameColoumn;

    @FXML
    private TableColumn<BasketItem, String> bProducntDescColumn;

    @FXML
    private TableColumn<BasketItem, Double> bPriceColumn;

    @FXML
    private TableColumn<BasketItem, Integer> bQuantityColumn;

    @FXML
    private TableColumn<BasketItem, Integer> bSizeCodeColumn;

    @FXML
    private TableColumn<BasketItem, Integer> bFormCodeColumn;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField PriceTextField;

    @FXML
    private TextArea descTextField;

    @FXML
    private TextField ImgTextField;

    @FXML
    private Label InfoTextField;
    @FXML
    private Label editProdiuctLabel;

    @FXML
    private Label welcomeUserLabel;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, String> descColumn;

    @FXML
    private TableColumn<Product, String> imgColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TextArea oldDescTextField;

    private Statement statement;
    private Connection connection;
    private List<Product> products = new ArrayList<>();
    private List<BasketItem> basketItems = new ArrayList<>();
    private List<OrderStatus> orderStatus = new ArrayList<>();
    private Product selectedProduct;
    private BasketItem selectedBasketItem;
    private User user;

    void initData(User user,
                  Connection connection) throws SQLException {
        this.user = user;
        welcomeUserLabel.setText(String.format("Welcome %s %s", user.getFirstName(), user.getLastName()));
        this.statement = connection.createStatement();
        this.connection = connection;

        idStatusColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        iDBasketColumn.setCellValueFactory(new PropertyValueFactory<>("idBasket"));
        dtStageColumn.setCellValueFactory(new PropertyValueFactory<>("dtStage"));
        shipperColumn.setCellValueFactory(new PropertyValueFactory<>("shipper"));
        shippingNumberId.setCellValueFactory(new PropertyValueFactory<>("shippingNum"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<>("img"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        basketTableView.setEditable(true);
        bProductNameColoumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        bProducntDescColumn.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        bPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        bQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bQuantityColumn.setEditable(true);
        bSizeCodeColumn.setCellValueFactory(new PropertyValueFactory<>("sizeCode"));
        bFormCodeColumn.setCellValueFactory(new PropertyValueFactory<>("formCode"));

        updateTableVies();

        String[] states = {"VA", "NC", "SC"};
        stateComboBox.setItems(FXCollections.observableArrayList(states));
        productsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProduct = newSelection;
                oldDescTextField.setText(selectedProduct.getDesc());
            }
        });
        basketTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBasketItem = newSelection;
                editProdiuctLabel.setText("Edit Product - " + newSelection.getProductName());
                quantityTextField.setText(Integer.toString(newSelection.getQuantity()));
                sizeCodeTextField.setText(Integer.toString(newSelection.getSizeCode()));
                FormCodeTextField.setText(Integer.toString(newSelection.getFormCode()));
            }
        });
        updateBasketItemTableVies();
    }

    private void resetProducts() {
        try {
            ResultSet resultSet = statement.executeQuery("select * from bb_product order by idproduct");

            products.clear();
            while (resultSet.next()) {
                Product p = new Product(resultSet.getInt(1)
                        , resultSet.getString(2)
                        , resultSet.getString(3)
                        , resultSet.getString(4)
                        , resultSet.getDouble(5)
                        , resultSet.getInt(15));
                products.add(p);
                System.out.println(p);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetOrderStatus() {
        try {
            ResultSet resultSet = statement.executeQuery("select * from bb_basketstatus order by dtstage desc");
            orderStatus.clear();
            while (resultSet.next()) {
                OrderStatus o = new OrderStatus(resultSet.getInt(1)
                        , resultSet.getInt(2)
                        , resultSet.getDate(4)
                        , resultSet.getString(6)
                        , resultSet.getString(7)
                );
                orderStatus.add(o);
                System.out.println(o);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBasketItemTableVies() {
        ObservableList<BasketItem> tableItems = FXCollections.observableArrayList(basketItems);
        basketTableView.setItems(tableItems);
        basketTableView.refresh();
    }

    private void updateTableVies() {
        Runnable task = () -> {
            try {
                resetProducts();
                resetOrderStatus();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.runLater(() -> {
                ObservableList<Product> tableItems = FXCollections.observableArrayList(products);
                productsTableView.setItems(tableItems);
                ObservableList<OrderStatus> orderStatusTableItems = FXCollections.observableArrayList(orderStatus);
                orderStatesTableView.setItems(orderStatusTableItems);
            });
        };
        new Thread(task).start();
    }

    private void updateProductsTableView() {
        Runnable task = () -> {
            try {
                resetProducts();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.runLater(() -> {
                ObservableList<Product> tableItems = FXCollections.observableArrayList(products);
                productsTableView.setItems(tableItems);
                productsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedProduct = newSelection;
                        oldDescTextField.setText(selectedProduct.getDesc());
                    }
                });
            });
        };
        new Thread(task).start();
    }

    private void updateOrderStatusTableView() {
        Runnable task = () -> {
            try {
                resetOrderStatus();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Platform.runLater(() -> {
                ObservableList<OrderStatus> tableItems = FXCollections.observableArrayList(orderStatus);
                orderStatesTableView.setItems(tableItems);
            });
        };
        new Thread(task).start();
    }

    @FXML
    void onUpdateProduct() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call change_product_description(?, ?)}");
                callableStatement.setInt(1, selectedProduct.getId());
                callableStatement.setString(2, oldDescTextField.getText());
                callableStatement.execute();
                Platform.runLater(() -> {
                    InfoTextField.setText("Product " + selectedProduct.getId() + " is updated to database.");
                    updateProductsTableView();
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    void add() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call prod_add_sp(?, ?, ?, ?, 1)}");
                callableStatement.setString(1, NameTextField.getText());
                callableStatement.setString(2, descTextField.getText());
                callableStatement.setString(3, ImgTextField.getText());
                callableStatement.setDouble(4, Double.parseDouble(PriceTextField.getText()));
                callableStatement.execute();
                Platform.runLater(() -> {
                    InfoTextField.setText(NameTextField.getText() + " is added to database.");
                    updateProductsTableView();
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private TextField subTotalTextField;

    @FXML
    void onCalculateTax() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call Tax_Cost_SP(?, ?, ?)}");
                callableStatement.setString(1, stateComboBox.getValue());
                callableStatement.setDouble(2, Double.parseDouble(subTotalTextField.getText()));
                callableStatement.registerOutParameter(3, Types.NUMERIC);
                callableStatement.execute();
                Platform.runLater(() -> {
                    try {
                        InfoTextField.setText("The tax of subtotal " + subTotalTextField.getText() + " is CAD " + Double.toString(callableStatement.getDouble(3)));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private TextField basketIdTextField;

    @FXML
    private TextField shipperTextField;

    @FXML
    private TextField shippingNumTextField;

    @FXML
    private DatePicker dtStageDatePick;


    @FXML
    void onUpdateOrderStatus() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call Status_Ship_Sp (?, ?, ?, ?)}");
                callableStatement.setInt(1, Integer.parseInt(basketIdTextField.getText()));
                callableStatement.setDate(2, Date.valueOf(dtStageDatePick.getValue()));
                callableStatement.setString(3, shipperTextField.getText());
                callableStatement.setString(4, shippingNumTextField.getText());
                callableStatement.execute();
                Platform.runLater(() -> {
                    InfoTextField.setText("The order status " + basketIdTextField.getText() + " is updated");
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private TableView<OrderStatus> orderStatesTableView;

    @FXML
    private TableColumn<OrderStatus, Integer> idStatusColumn;

    @FXML
    private TableColumn<OrderStatus, Integer> iDBasketColumn;

    @FXML
    private TableColumn<OrderStatus, Date> dtStageColumn;

    @FXML
    private TableColumn<OrderStatus, String> shipperColumn;

    @FXML
    private TableColumn<OrderStatus, String> shippingNumberId;

    @FXML
    private TextField checkStockBasketIdTextField;

    @FXML
    private TextField shopperIdTextField;

    @FXML
    void onCalculateTotalSpending() {

        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{? = call TOT_PURCH_SP(?)}");
                callableStatement.registerOutParameter(1, Types.NUMERIC);
                callableStatement.setInt(2, Integer.parseInt(shopperIdTextField.getText()));
                callableStatement.execute();
                System.out.println(callableStatement.getString(1));
                Platform.runLater(() -> {
                    try {
                        InfoTextField.setText("Shopper id " + shopperIdTextField.getText() + " total spending is " + Double.toString(callableStatement.getDouble(1)));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();

    }

    @FXML
    void onCheckStock() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{call check_items_sp(?,?)}");
                callableStatement.setInt(1, Integer.parseInt(checkStockBasketIdTextField.getText()));
                callableStatement.registerOutParameter(2, Types.VARCHAR);
                callableStatement.execute();
                System.out.println(callableStatement.getString(2));
                Platform.runLater(() -> {
                    try {
                        InfoTextField.setText(callableStatement.getString(2));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private TextField productIdTextField;


    @FXML
    private DatePicker datePicker;

    @FXML
    void onCheckOnSale() {
        Runnable task = () -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{? = call CK_Sale_SF(?, ?)}");
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.setInt(2, Integer.parseInt(productIdTextField.getText()));
                callableStatement.setDate(3, Date.valueOf(datePicker.getValue()));
                callableStatement.execute();
                System.out.println(callableStatement.getString(1));
                Platform.runLater(() -> {
                    try {
                        InfoTextField.setText("Answer is " + callableStatement.getString(1));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    private TextField sizeCodeTextField;

    @FXML
    private TextField FormCodeTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    void onSaveBasket() {
        if (basketItems.size() == 0)
            InfoTextField.setText("No product to basket");
        else {
            Runnable task = () -> {
                try {
                    CallableStatement callableStatement = connection.prepareCall("{call proced_create_new_basket(?, ?)}");
                    callableStatement.setInt(1, user.getId());
                    callableStatement.registerOutParameter(2, Types.NUMERIC);
                    callableStatement.execute();
                    int idBasket = callableStatement.getInt(2);

                    for (BasketItem i : basketItems) {
                        callableStatement = connection.prepareCall("{call Basket_Add_SP(?, ?, ?, ?, ?, ?)}");
                        callableStatement.setInt(1, i.getProduct().getId());
                        callableStatement.setDouble(2, i.getPrice());
                        callableStatement.setInt(3, i.getQuantity());
                        callableStatement.setInt(4, idBasket);
                        callableStatement.setInt(5, i.getSizeCode());
                        callableStatement.setInt(6, i.getFormCode());
                        callableStatement.execute();
                    }
                    Platform.runLater(() -> {
                        InfoTextField.setText("Basket " + idBasket + " is created and added items.");

                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            };
            new Thread(task).start();
        }
    }

    @FXML
    void onSaveChange() {
        Runnable task = () -> {
            try {
                BasketItem bi = basketItems.get(basketItems.indexOf(selectedBasketItem));
                bi.setQuantity(Integer.parseInt(quantityTextField.getText()));
                bi.setSizeCode(Integer.parseInt(sizeCodeTextField.getText()));
                bi.setFormCode(Integer.parseInt(FormCodeTextField.getText()));
                basketTableView.setItems(null);
                Platform.runLater(this::updateBasketItemTableVies);
            } catch (Exception ex) {
                InfoTextField.setText("There is no selected basket item.");
//                ex.printStackTrace();
            }
        };
        new Thread(task).start();
    }

    @FXML
    void OnEmtypeBasket() {
        try {
            basketItems.clear();
            updateBasketItemTableVies();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onAddToBasket() {
        try {
            basketItems.add(new BasketItem(1, selectedProduct));
            updateBasketItemTableVies();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
