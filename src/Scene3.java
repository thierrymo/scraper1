import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
// to build the third scene where the user can see a basic description of the selected movie
// it is going to be the same structure then scene2... no need of graphic context here neither
public class Scene3 {
    static Scene createSceneThree(Film film, Scene scene, int height, int width, int decalx, int decaly) throws IOException {
        Group root3 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);

        // here we needed a scrollpane to make the movie description scrallable
        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);

        // the return button who will bring back the user to the scene2
        Button buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            Render.switchScenes(scene);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root3.getChildren().add(buttonBack);

        Scene scene3 = new Scene(root3, height, width);
        scene3.setFill(Color.YELLOW);

        // here we call the Method MethodPres from Presentation class who make the text layout of the movie synopsis
        String synopsis = Presentation.MethodPres(height, film.getSynopsis());

        // definition of what the user will see on this screen : movie title, the team and the basic synopsis from Allocine
        Text textTitre = new Text();
        textTitre.setFont(new Font(20));
        textTitre.setFill(Color.RED);
        textTitre.setText(film.getTitre());

        Text textRealisateur = new Text();
        textRealisateur.setFont(new Font(15));
        textRealisateur.setFill(Color.FORESTGREEN);
        textRealisateur.setText(film.getRealisateur());

        Text textSynopsis = new Text();
        textSynopsis.setFont(new Font(15));
        textSynopsis.setFill(Color.DARKBLUE);
        textSynopsis.setText(synopsis);


        vBox.getChildren().addAll(textTitre,textRealisateur,textSynopsis);
        scrollPane.setContent(vBox);
        root3.getChildren().addAll(scrollPane);

        return scene3;
    }

}


