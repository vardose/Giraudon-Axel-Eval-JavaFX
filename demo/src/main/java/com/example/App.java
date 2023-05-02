package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    Scene consultingScene;
    Scene downloadScene;
    Scene proposeScene;
    Scene bonusScene;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        VBox layout = new VBox(10);
        layout.minHeight(200);
        layout.minWidth(200);

        //Création des buttons
        Button consult = new Button("Consulter les données");
        Button download = new Button("Télécharger le fichier");
        Button propose = new Button("Mettre la météo à jour");
        Button bonus = new Button("Bonus");
        
        HBox layout2 = new HBox(10);
        
        layout2.minWidth(100);
        layout2.minHeight(100);

        layout.getChildren().addAll(consult,download,propose,bonus);
        scene = new Scene(layout);
        

        consult.setOnAction(e -> { 
            Label lab = new Label("Nom de la ville :");
            TextField textField = new TextField ();
            Button submit = new Button("Submit");
            layout2.getChildren().addAll(lab,textField,submit);
            submit.setOnAction(i -> {
                try {
                    JsonNode texte = Api.callApi(textField.getText());
                    Image img = new Image( texte.path("current").get("weather_icons").get(0).asText());
                    Label label1 = new Label(texte.path("current").get("temperature").asText()+" °C");
                    Label label2 = new Label(texte.path("current").get("wind_speed").asText()+" km/h");
                    ImageView view = new ImageView(img);
                    Button escape = new Button("revenir au menu");
                    escape.setOnAction(j -> stage.setScene(scene));
                    layout2.getChildren().removeAll(textField,submit);
                    layout2.getChildren().addAll(view,label1,label2,escape);

                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }});

            consultingScene = new Scene(layout2,300,600);
            stage.setScene(consultingScene);
            
        });
        download.setOnAction(e -> { 
            Label label1 = new Label("Nom de la ville :");
            TextField textField = new TextField ();
            Button submit = new Button("Submit");
            submit.setOnAction(i -> {
                try {
                    Api.writer(textField.getText());
                    // writer(temp);
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
                stage.setScene(scene);
                return;
            });
            HBox hb = new HBox();
            hb.getChildren().addAll(label1, textField, submit);
            hb.setSpacing(10);
            downloadScene = new Scene(hb);
            stage.setScene(downloadScene);
        
            stage.show();
            
        });
        propose.setOnAction(e -> { stage.setScene(proposeScene);
            
        });
        bonus.setOnAction(e -> { stage.setScene(bonusScene);
            
        });
        
        stage.setScene(scene);
        
        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}