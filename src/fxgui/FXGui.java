package fxgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXGui extends Application
{
    Stage primaryStage;
    FxGuiController controller = new FxGuiController();
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primary)
    {
        this.primaryStage = primary;
        this.primaryStage.setTitle("Music Data Editor");

        Parent root;
        Scene mainScene;
        try {
            root = FXMLLoader.load(this.getClass().getResource("FXGui.fxml"));
            mainScene = new Scene(root);


            this.primaryStage.setScene(mainScene);
            //this.primaryStage.setResizable(false);
            this.primaryStage.show();
            //System.out.println(controller.title.getWidth());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
