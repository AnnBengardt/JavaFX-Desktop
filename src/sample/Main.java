package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.PersonEditPageController;
import sample.controllers.PersonOverviewController;
import sample.models.Person;

import java.io.IOException;


public class Main extends Application {

    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public Main(){
        for (int i=0; i < 25;i++){
            personData.add(new Person("Name " + i ,"Last name " + i*2));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Clients");
        this.primaryStage.getIcons().add(new Image("file:../../pics/icon.png"));

        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean showPersonEditPage(Person person){
        try{ FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/personEditPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogueStage = new Stage();
            dialogueStage.setTitle("EDIT");
            dialogueStage.initOwner(primaryStage);
            dialogueStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogueStage.setScene(scene);

            PersonEditPageController controller = loader.getController();
            controller.setDialogueStage(dialogueStage);
            controller.setPerson(person);
            dialogueStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void showPersonOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/main.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personOverview);
            PersonOverviewController PController= loader.getController();
            PController.setMainApp(this);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
