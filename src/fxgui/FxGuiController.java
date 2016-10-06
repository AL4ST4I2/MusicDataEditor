package fxgui;

import com.denis.musicdataeditor.config.References;
import com.denis.musicdataeditor.core.Canzone;
import com.denis.musicdataeditor.core.SongList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FxGuiController implements Initializable
{

    @FXML
    private AnchorPane tablePane;
    @FXML
    private AnchorPane detailsPane;

    @FXML
    private TextField fileName;
    @FXML
    private TextField title;
    @FXML
    private TextField artist;
    @FXML
    private TextField album;
    @FXML
    private TextField author;
    @FXML
    private TextField year;
    @FXML
    private TextField track;
    @FXML
    private ComboBox  genere;

    @FXML
    private TableView<Canzone> table;
    @FXML
    private TableColumn<Canzone, String> fileColumn;
    @FXML
    private TableColumn<Canzone, String> authorColumn;
    @FXML
    private TableColumn<Canzone, String> albumColumn;
    @FXML
    private TableColumn<Canzone, String> titleColumn;


    @FXML
    private ImageView cover;

    public static void evenn()
    {

    }


    private ObservableList<Canzone> musicLibrary()
    {
         return FXCollections.observableArrayList(new SongList(References.LIB_DIR).getLibreria());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println(table.getWidth());
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        table.getColumns().get(1).prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        table.getColumns().get(2).prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        table.getColumns().get(3).prefWidthProperty().bind(table.widthProperty().multiply(0.25));

        authorColumn.setCellValueFactory(new PropertyValueFactory<Canzone, String>("cdscsd"));
        // TODO i dont know if i will continue using this since i am working to new project
        table.setItems(musicLibrary());
    }
}
