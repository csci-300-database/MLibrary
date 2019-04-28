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
                default :
                    System.out.println("Unknown node id");
                    break;
            }
        }
    }

    private void handleNavBook() {
        if (selected != null) {
            selected.setStyle("-fx-background-color: #FFFFFF");
        }
        if (currentTable != TableTypes.BOOK) {
            currentTable = TableTypes.BOOK;
            nav_item_book.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_book;
            System.out.println("Selected: " + currentTable.name());
            //show books
        }
    }

    private void handleNavTextbook() {
        if (selected != null) {
            selected.setStyle("-fx-background-color: #FFFFFF");
        }
        if (currentTable != TableTypes.TEXTBOOK) {
            currentTable = TableTypes.TEXTBOOK;
            nav_item_textbook.setStyle("-fx-background-color: #8D7C7C");
            selected = nav_item_textbook;
            System.out.println("Selected: " + currentTable.name());
            //show text books
        }
    }

}
