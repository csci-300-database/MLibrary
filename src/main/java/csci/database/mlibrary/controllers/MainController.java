package csci.database.mlibrary.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import csci.database.mlibrary.MainView;
import csci.database.mlibrary.enums.TableTypes;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventHandler<Event> {

    private Stage primaryStage;

    /*@FXML
    private AnchorPane main_view;*/

    @FXML
    private StackPane content_pane;

    @FXML
    private Label app_bar;

    @FXML
    private JFXDrawer nav_drawer;

    @FXML
    private JFXHamburger nav_stack;

    @FXML
    private ImageView close_icon;

    @FXML
    private ImageView min_icon;

    private double offsetX = 0;
    private double offsetY = 0;

    private HamburgerBasicCloseTransition closeTransition;

    private TableTypes currentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("/layouts/nav_view.fxml"));
            Parent root = loader.load();

            nav_drawer.setSidePane(root);

            closeTransition = new HamburgerBasicCloseTransition(nav_stack);
            closeTransition.setRate(-1);

            nav_stack.setOnMouseClicked(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        app_bar.setOnMousePressed(this);
        app_bar.setOnMouseDragged(this);
        close_icon.setOnMouseClicked(this);
        min_icon.setOnMouseClicked(this);

        currentTable = null;
    }

    @Override
    public void handle(Event event) {
        String name = event.getEventType().getName();
        switch (name) {
            case "MOUSE_CLICKED" :
                handleMouseEvent((MouseEvent) event);
                break;
            case "MOUSE_DRAGGED":
                handleMouseDragged((MouseEvent) event);
                break;
            case "MOUSE_PRESSED" :
                handleMousePressed((MouseEvent) event);
                break;
            default:
                System.out.println("Unknown event occurred");
                break;
        }
    }

    private void handleMouseEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Node node = (Node) event.getSource();
            String id = node.getId();
            switch (id) {
                case "nav_stack" :
                    handleNavStackClick();
                    break;
                case "close_icon" :
                    handleCloseClick();
                    break;
                case "min_icon" :
                    handleMinClick();
                    break;
                default :
                    System.out.println("Unknown node id");
                    break;
            }
        }
    }

    private void handleNavStackClick() {
        closeTransition.setRate(closeTransition.getRate() * -1);
        closeTransition.play();
        if (nav_drawer.isOpened()) {
            nav_drawer.close();
        } else {
            nav_drawer.open();
        }
    }

    private void handleMouseDragged(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            Node node = (Node) e.getSource();
            String id = node.getId();
            if (id.equals(app_bar.getId())) {
                relocateWindow(e.getScreenX() - offsetX, e.getScreenY() - offsetY);
            }
        }
    }

    private void handleMousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            Node node = (Node) e.getSource();
            String id = node.getId();
            if (id.equals(app_bar.getId())) {
                offsetX = e.getX();
                offsetY = e.getY();
            }
        }
    }

    private void relocateWindow(double x, double y) {
        primaryStage.setX(x);
        primaryStage.setY(y);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void handleCloseClick() {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("MLibrary"));
        layout.setBody(new Text("Are you sure you would like to exit the application?"));

        JFXButton closeBtn = new JFXButton("Yes");
        JFXButton cancelBtn = new JFXButton("No");

        Node[] actions = new Node[]{closeBtn, cancelBtn};
        layout.setActions(actions);

        JFXDialog dialog = new JFXDialog(content_pane, layout, JFXDialog.DialogTransition.CENTER);
        dialog.show();

        closeBtn.setOnAction(event -> Platform.exit());
        cancelBtn.setOnAction(event -> dialog.close());
    }

    private void handleMinClick() {
        primaryStage.setIconified(true);
    }

}
