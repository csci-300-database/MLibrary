package csci.database.mlibrary.controllers;

import csci.database.mlibrary.enums.TableTypes;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class NavController implements Initializable, EventHandler<Event> {

    @FXML
    private Button nav_item_book;

    @FXML
    private Button nav_item_textbook;

    @FXML
    private Button nav_item_staff;

    @FXML
    private Button nav_item_tutor;

    @FXML
    private Button nav_item_media;

    @FXML
    private Button nav_item_customer;

    @FXML
    private Button nav_item_computer;

    @FXML
    private Button nav_item_book_loan;

    @FXML
    private Button nav_item_textbook_loan;

    @FXML
    private Button nav_item_media_loan;

    private MainController mainController;
    private Node selected;
    private TableTypes currentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nav_item_book.setOnMouseClicked(this);
        nav_item_textbook.setOnMouseClicked(this);
        nav_item_staff.setOnMouseClicked(this);
        nav_item_tutor.setOnMouseClicked(this);
        nav_item_media.setOnMouseClicked(this);
        nav_item_customer.setOnMouseClicked(this);
        nav_item_computer.setOnMouseClicked(this);
        nav_item_book_loan.setOnMouseClicked(this);
        nav_item_textbook_loan.setOnMouseClicked(this);
        nav_item_media_loan.setOnMouseClicked(this);
    }

    @Override
    public void handle(Event event) {
        String name = event.getEventType().getName();
        if ("MOUSE_CLICKED".equals(name)) {
            handleMouseEvent((MouseEvent) event);
        } else {
            System.out.println("Unknown event occurred");
        }
    }

    private void handleMouseEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Node node = (Node) event.getSource();
            String id = node.getId();
            switch (id) {
                case "nav_item_book" :
                    handleNavBook();
                    break;
                case "nav_item_textbook" :
                    handleNavTextbook();
                    break;
                case "nav_item_media" :
                    handleNavMedia();
                    break;
                case "nav_item_computer" :
                    handleNavComputer();
                    break;
                case "nav_item_tutor" :
                    handleNavTutor();
                    break;
                case "nav_item_book_loan" :
                    handleNavBookLoan();
                    break;
                case "nav_item_textbook_loan" :
                    handleNavTextBookLoan();
                    break;
                case "nav_item_media_loan" :
                    handleNavMediaLoan();
                    break;
                case "nav_item_staff" :
                    handleNavStaff();
                    break;
                case "nav_item_customer" :
                    handleNavCustomer();
                    break;
                default :
                    System.out.println("Unknown node id");
                    break;
            }
        }
    }

    private void handleNavBook() {
        if (currentTable != TableTypes.BOOK) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.BOOK;
            nav_item_book.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_book;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavTextbook() {
        if (currentTable != TableTypes.TEXTBOOK) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }
            currentTable = TableTypes.TEXTBOOK;
            nav_item_textbook.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_textbook;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavMedia() {
        if (currentTable != TableTypes.MEDIA) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }
            currentTable = TableTypes.MEDIA;
            nav_item_media.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_media;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavComputer() {
        if (currentTable != TableTypes.COMPUTER) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.COMPUTER;
            nav_item_computer.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_computer;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavTutor() {
        if (currentTable != TableTypes.TUTOR) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.TUTOR;
            nav_item_tutor.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_tutor;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavBookLoan() {
        if (currentTable != TableTypes.BOOK_LOAN) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.BOOK_LOAN;
            nav_item_book_loan.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_book_loan;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavTextBookLoan() {
        if (currentTable != TableTypes.TEXTBOOK_LOAN) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.TEXTBOOK_LOAN;
            nav_item_textbook_loan.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_textbook_loan;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavMediaLoan() {
        if (currentTable != TableTypes.MEDIA_LOAN) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.MEDIA_LOAN;
            nav_item_media_loan.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_media_loan;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavStaff() {
        if (currentTable != TableTypes.STAFF) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.STAFF;
            nav_item_staff.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_staff;
            mainController.setCurrentTable(currentTable);
        }
    }

    private void handleNavCustomer() {
        if (currentTable != TableTypes.CUSTOMER) {
            if (selected != null) {
                selected.setStyle("-fx-background-color: #FFFFFF");
            }

            currentTable = TableTypes.CUSTOMER;
            nav_item_customer.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_customer;
            mainController.setCurrentTable(currentTable);
        }
    }

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
