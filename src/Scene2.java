import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

// to build the second scene where the user can see all the movies and the time of the selecting date and cinema
public class Scene2 {
    static public Scene createSceneTwo(Scrap scrap, Scene scene, int height, int width, int decalx, int decaly, int image_size) throws IOException {
        Group root2 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);

        // here we needed a scrollpane to make the movies list scrallable
        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);

        // the return button who will bring back the user to the scene1
        Button buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            Render.switchScenes(scene);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root2.getChildren().add(buttonBack);

        // definition of what the user will have to the top of the screen : name of the selected cinema and date
        Text textSalle = new Text();
        textSalle.setFont(new Font(20));
        textSalle.setFill(Color.BLUEVIOLET);
        textSalle.setText(Render.cinema+"     "+Render.date);
        textSalle.setLayoutX(decalx*3);
        textSalle.setLayoutY(decaly*.6);

        root2.getChildren().add(textSalle);

        Scene scene2 = new Scene(root2, height, width);
        scene2.setFill(Color.YELLOW);

        // Building the movies list cliquable with hyperlink
        int numFilm = -1;
        ArrayList<Film> liste = scrap.getFilmsJournee();

        for (Film film : liste) {
            numFilm ++;
            String Affiche = film.getAfficheUrl();
            Image image_film = new Image(Affiche);

            Hyperlink hyperlink = new Hyperlink(film.getTitre());

            int finalNumFilm = numFilm;
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                // the action when the user is cliquing on one the movies name
                @Override
                public void handle(ActionEvent event) {
                    Film filmDetail = liste.get(finalNumFilm);
                    Scene scene3 = null;
                    try {
                        scene3 = Scene3.createSceneThree(filmDetail, scene2, height, width, decalx, decaly);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Render.switchScenes(scene3);
                }
            });

            // definition of what the user will see on this screen : movies poster, cliquable names, and time table
            Text text = new Text();
            text.setFont(new Font(15));
            text.setFill(Color.RED);
            text.setText("                               " +
                    film.getHorairesJournee());

            Text textSpace = new Text();
            textSpace.setFont(new Font(15));
            textSpace.setFill(Color.LIGHTPINK);
            textSpace.setText("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

            ImageView pic = new ImageView(image_film);
            pic.setFitHeight(image_size);
            pic.setFitWidth(image_size);

            vBox.getChildren().addAll(hyperlink,text);
            vBox.getChildren().add(pic);
            vBox.getChildren().add(textSpace);
        }
        scrollPane.setContent(vBox);
        root2.getChildren().addAll(scrollPane);

        return scene2;
    }

}


