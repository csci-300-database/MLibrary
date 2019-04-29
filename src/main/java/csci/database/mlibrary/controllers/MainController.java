package csci.database.mlibrary.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import csci.database.mlibrary.MainView;
import csci.database.mlibrary.database.SQLManager;
import csci.database.mlibrary.enums.TableTypes;
import csci.database.mlibrary.structures.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("unchecked")
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

    @FXML
    private Label label_instruct;

    @FXML
    private JFXTreeTableView library_table;

    @FXML
    private JFXProgressBar table_progress;

    private boolean isWorking;

    private double offsetX = 0;
    private double offsetY = 0;

    private HamburgerBasicCloseTransition closeTransition;

    private TableTypes currentTable;

    private ObservableList<Book> bookObservables = FXCollections.observableArrayList();
    private ObservableList<TextBook> textBookObservables = FXCollections.observableArrayList();
    private ObservableList<Media> mediaObservables = FXCollections.observableArrayList();
    private ObservableList<Tutor> tutorObservables = FXCollections.observableArrayList();
    private ObservableList<Computer> computerObservables = FXCollections.observableArrayList();

    private SQLManager sqlManager;

    private ExecutorService pool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainView.class.getResource("/layouts/nav_view.fxml"));
            Parent root = loader.load();

            NavController navController = loader.getController();
            navController.setMainController(this);

            nav_drawer.setSidePane(root);

            closeTransition = new HamburgerBasicCloseTransition(nav_stack);
            closeTransition.setRate(-1);

            nav_stack.setOnMouseClicked(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        label_instruct.setVisible(true);

        app_bar.setOnMousePressed(this);
        app_bar.setOnMouseDragged(this);
        close_icon.setOnMouseClicked(this);
        min_icon.setOnMouseClicked(this);

        currentTable = null;
        isWorking = false;
        pool = Executors.newFixedThreadPool(5);
    }

    @Override
    public void handle(Event event) {
        String name = event.getEventType().getName();
        switch (name) {
            case "MOUSE_CLICKED" :
                handleMouseEvent((MouseEvent) event);
                break;
            case "MOUSE_DRAGGED" :
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
            content_pane.toFront();
        } else {
            nav_drawer.open();
            nav_drawer.toFront();
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

    public void setSqlManager(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
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

    void setCurrentTable(TableTypes currentTable) {
        this.currentTable = currentTable;
        if (this.currentTable != null) {
            label_instruct.setVisible(false);
            drawTable();
        } else {
            label_instruct.setVisible(true);
        }
    }

    private void drawTable() {
        switch (currentTable) {
            case BOOK:
                if (!isWorking) {
                    table_progress.setVisible(true);
                    pool.execute(booksRunnable);
                    drawBookTable();
                }
                break;
            case TEXTBOOK:
                if (!isWorking) {
                    table_progress.setVisible(true);
                    pool.execute(textbooksRunnable);
                }
                break;
            case MEDIA:
                if (!isWorking) {
                    table_progress.setVisible(true);
                    pool.execute(mediaRunnable);
                }
                break;
            case TUTOR:
                if (!isWorking) {
                    table_progress.setVisible(true);
                    pool.execute(tutorRunnable);
                    drawTutor();
                }
                break;
            case COMPUTER:
                if (!isWorking) {
                    table_progress.setVisible(true);
                    pool.execute(computerRunnable);
                    drawComputer();
                }
                break;
            default :
                System.out.println("Unknown Table");
                break;
        }
    }

    private void drawBookTable() {
        JFXTreeTableColumn<Book, String> bookTitleCol = new JFXTreeTableColumn<>("Book Title");
        bookTitleCol.setPrefWidth(150);
        bookTitleCol.setCellValueFactory(param -> param.getValue().getValue().title);

        JFXTreeTableColumn<Book, String> bookAuthorCol = new JFXTreeTableColumn<>("Book Author");
        bookAuthorCol.setPrefWidth(150);
        bookAuthorCol.setCellValueFactory(param -> param.getValue().getValue().author);

        JFXTreeTableColumn<Book, String> bookGenreCol = new JFXTreeTableColumn<>("Book Genre");
        bookGenreCol.setPrefWidth(150);
        bookGenreCol.setCellValueFactory(param -> param.getValue().getValue().genre);

        JFXTreeTableColumn<Book, String> bookISBNCol = new JFXTreeTableColumn<>("Book ISBN");
        bookISBNCol.setPrefWidth(150);
        bookISBNCol.setCellValueFactory(param -> param.getValue().getValue().ISBN);

        JFXTreeTableColumn<Book, String> bookIsleCol = new JFXTreeTableColumn<>("Book Isle");
        bookIsleCol.setPrefWidth(150);
        bookIsleCol.setCellValueFactory(param -> param.getValue().getValue().isle);

        JFXTreeTableColumn<Book, String> bookIdCol = new JFXTreeTableColumn<>("Book Id");
        bookIdCol.setPrefWidth(150);
        bookIdCol.setCellValueFactory(param -> param.getValue().getValue().bookId);

        final TreeItem<Book> root = new RecursiveTreeItem<>(bookObservables, RecursiveTreeObject::getChildren);
        List<JFXTreeTableColumn<Book, String>> columns = Arrays.asList(bookTitleCol, bookAuthorCol, bookGenreCol, bookISBNCol, bookIsleCol, bookIdCol);
        library_table.getColumns().setAll(columns);
        library_table.setRoot(root);
        library_table.setShowRoot(false);
    }

    private void drawTextBook() {
        JFXTreeTableColumn<TextBook, String> bookTitleCol = new JFXTreeTableColumn<>("Book Title");
        bookTitleCol.setPrefWidth(150);
        bookTitleCol.setCellValueFactory(param -> param.getValue().getValue().title);

        JFXTreeTableColumn<TextBook, String> bookAuthorCol = new JFXTreeTableColumn<>("Book Author");
        bookAuthorCol.setPrefWidth(150);
        bookAuthorCol.setCellValueFactory(param -> param.getValue().getValue().author);

        JFXTreeTableColumn<TextBook, String> bookGenreCol = new JFXTreeTableColumn<>("Book Subject");
        bookGenreCol.setPrefWidth(150);
        bookGenreCol.setCellValueFactory(param -> param.getValue().getValue().subject);

        JFXTreeTableColumn<TextBook, String> bookISBNCol = new JFXTreeTableColumn<>("Book ISBN");
        bookISBNCol.setPrefWidth(150);
        bookISBNCol.setCellValueFactory(param -> param.getValue().getValue().ISBN);

        JFXTreeTableColumn<TextBook, String> bookIsleCol = new JFXTreeTableColumn<>("Book Isle");
        bookIsleCol.setPrefWidth(150);
        bookIsleCol.setCellValueFactory(param -> param.getValue().getValue().isle);

        JFXTreeTableColumn<TextBook, String> bookIdCol = new JFXTreeTableColumn<>("Book Id");
        bookIdCol.setPrefWidth(150);
        bookIdCol.setCellValueFactory(param -> param.getValue().getValue().textId);

        final TreeItem<TextBook> root = new RecursiveTreeItem<>(textBookObservables, RecursiveTreeObject::getChildren);
        List<JFXTreeTableColumn<TextBook, String>> columns = Arrays.asList(bookTitleCol, bookAuthorCol, bookGenreCol, bookISBNCol, bookIsleCol, bookIdCol);
        library_table.getColumns().setAll(columns);
        library_table.setRoot(root);
        library_table.setShowRoot(false);
    }

    private void drawMedia() {
        JFXTreeTableColumn<Media, String> mediaTitleCol = new JFXTreeTableColumn<>("Media Title");
        mediaTitleCol.setPrefWidth(150);
        mediaTitleCol.setCellValueFactory(param -> param.getValue().getValue().title);

        JFXTreeTableColumn<Media, String> mediaPublisherCol = new JFXTreeTableColumn<>("Media Publisher");
        mediaPublisherCol.setPrefWidth(150);
        mediaPublisherCol.setCellValueFactory(param -> param.getValue().getValue().publisher);

        JFXTreeTableColumn<Media, String> mediaGenreCol = new JFXTreeTableColumn<>("Media Genre");
        mediaGenreCol.setPrefWidth(150);
        mediaGenreCol.setCellValueFactory(param -> param.getValue().getValue().genre);

        JFXTreeTableColumn<Media, String> mediaIsleCol = new JFXTreeTableColumn<>("Media Isle");
        mediaIsleCol.setPrefWidth(150);
        mediaIsleCol.setCellValueFactory(param -> param.getValue().getValue().isle);

        JFXTreeTableColumn<Media, String> mediaIdCol = new JFXTreeTableColumn<>("Media Id");
        mediaIdCol.setPrefWidth(150);
        mediaIdCol.setCellValueFactory(param -> param.getValue().getValue().mediaId);

        final TreeItem<Media> root = new RecursiveTreeItem<>(mediaObservables, RecursiveTreeObject::getChildren);
        List<JFXTreeTableColumn<Media, String>> columns = Arrays.asList(mediaTitleCol, mediaPublisherCol, mediaGenreCol, mediaIsleCol, mediaIdCol);
        library_table.getColumns().setAll(columns);
        library_table.setRoot(root);
        library_table.setShowRoot(false);
    }

    private void drawComputer() {
        JFXTreeTableColumn<Computer, String> computerTypeCol = new JFXTreeTableColumn<>("Computer Type");
        computerTypeCol.setPrefWidth(150);
        computerTypeCol.setCellValueFactory(param -> param.getValue().getValue().type);

        JFXTreeTableColumn<Computer, String> computerAvailabilityCol = new JFXTreeTableColumn<>("Available");
        computerAvailabilityCol.setPrefWidth(150);
        computerAvailabilityCol.setCellValueFactory(param -> param.getValue().getValue().available);

        JFXTreeTableColumn<Computer, String> computerNumberCol = new JFXTreeTableColumn<>("Computer Id");
        computerNumberCol.setPrefWidth(150);
        computerNumberCol.setCellValueFactory(param -> param.getValue().getValue().computerId);

        final TreeItem<Computer> root = new RecursiveTreeItem<>(computerObservables, RecursiveTreeObject::getChildren);
        List<JFXTreeTableColumn<Computer, String>> columns = Arrays.asList(computerTypeCol, computerAvailabilityCol, computerNumberCol);
        library_table.getColumns().setAll(columns);
        library_table.setRoot(root);
        library_table.setShowRoot(false);
    }

    private void drawTutor() {
        JFXTreeTableColumn<Tutor, String> tutorNameCol = new JFXTreeTableColumn<>("Tutor Name");
        tutorNameCol.setPrefWidth(150);
        tutorNameCol.setCellValueFactory(param -> param.getValue().getValue().staffName);

        JFXTreeTableColumn<Tutor, String> tutorSubjectCol = new JFXTreeTableColumn<>("Tutor Subject");
        tutorSubjectCol.setPrefWidth(150);
        tutorSubjectCol.setCellValueFactory(param -> param.getValue().getValue().subject);

        JFXTreeTableColumn<Tutor, String> tutorStatusCol = new JFXTreeTableColumn<>("Tutor Status");
        tutorStatusCol.setPrefWidth(150);
        tutorStatusCol.setCellValueFactory(param -> param.getValue().getValue().present);

        JFXTreeTableColumn<Tutor, String> hoursFromCol = new JFXTreeTableColumn<>("Hours From");
        hoursFromCol.setPrefWidth(150);
        hoursFromCol.setCellValueFactory(param -> param.getValue().getValue().startAvailability);

        JFXTreeTableColumn<Tutor, String> hoursToCol = new JFXTreeTableColumn<>("Hours To");
        hoursToCol.setPrefWidth(150);
        hoursToCol.setCellValueFactory(param -> param.getValue().getValue().endAvailability);

        JFXTreeTableColumn<Tutor, String> tutorNumberCol = new JFXTreeTableColumn<>("Tutor Number");
        tutorNumberCol.setPrefWidth(150);
        tutorNumberCol.setCellValueFactory(param -> param.getValue().getValue().tutorNo);

        final TreeItem<Tutor> root = new RecursiveTreeItem<>(tutorObservables, RecursiveTreeObject::getChildren);
        List<JFXTreeTableColumn<Tutor, String>> columns = Arrays.asList(tutorNameCol, tutorSubjectCol, tutorStatusCol,
                hoursFromCol, hoursToCol, tutorNumberCol);
        library_table.getColumns().setAll(columns);
        library_table.setRoot(root);
        library_table.setShowRoot(false);
    }

    private Runnable booksRunnable = new Runnable() {
        @Override
        public void run() {
            List<Book> books = sqlManager.collectBooks();
            bookObservables.addAll(books);
            Platform.runLater(() -> {
                drawBookTable();
                isWorking = false;
                table_progress.setVisible(false);
            });
        }
    };

    private Runnable textbooksRunnable = new Runnable() {
        @Override
        public void run() {
            List<TextBook> books = sqlManager.collectTextBooks();
            textBookObservables.addAll(books);

            Platform.runLater(() -> {
                drawTextBook();
                isWorking = false;
                table_progress.setVisible(false);
            });
        }
    };

    private Runnable tutorRunnable = new Runnable() {
        @Override
        public void run() {
            List<Tutor> tutors = sqlManager.collectTutors();
            tutorObservables.addAll(tutors);
            System.out.println();
            Platform.runLater(() -> {
                drawTutor();
                isWorking = false;
                table_progress.setVisible(false);
            });
        }
    };

    private Runnable mediaRunnable = new Runnable() {
        @Override
        public void run() {
            List<Media> media = sqlManager.collectMedia();
            mediaObservables.addAll(media);
            Platform.runLater(() -> {
                drawMedia();
                isWorking = false;
                table_progress.setVisible(false);
            });
        }
    };

    private Runnable computerRunnable = new Runnable() {
        @Override
        public void run() {
            List<Computer> computers = sqlManager.collectComputers();
            computerObservables.addAll(computers);
            Platform.runLater(() -> {
                drawComputer();
                isWorking = false;
                table_progress.setVisible(false);
            });
        }
    };

}