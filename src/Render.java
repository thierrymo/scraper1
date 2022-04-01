import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Render extends Application {

    private static Stage stage;
    private static Scene scene1;
    int height = 1200;    //  Scene size
    int width= 700;       //  Scene size
    int decalx = 100;     // arround the scene
    int decaly = 100;     // arround the scene
    static int image_size = 75;     //  Film pictures size
    static public String cinema;
    static public LocalDate date = LocalDate.now();
    String paris = "https://www.plandeparis.info/plans-de-paris/plan-de-paris.jpg";
    String clap = "https://www.lebonhommepicard.fr/wp-content/uploads/2020/02/Cine%CC%81ma.jpg";

    @Override
    public void start(Stage primStage) throws Exception {
        stage = primStage;
        scene1 = Scene1.createSceneOne(height,width,decalx,decaly,paris,clap);
        stage.setScene(scene1);
        stage.show();
    }



    static public Scene createSceneTwo(Scrap scrap, int height, int width, int decalx, int decaly) throws IOException {
        Group root2 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);


        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);

     // button retour   scene 2
        Button buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            switchScenes(scene1);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root2.getChildren().add(buttonBack);

        Scene scene2 = new Scene(root2, height, width);
        scene2.setFill(Color.YELLOW);

        // Building the screen with film image, cliquable name and time table
        int numFilm = -1;
        ArrayList<Film> liste = scrap.getFilmsJournee();

        for (Film film : liste) {
            numFilm ++;
            String Affiche = film.getAfficheUrl();
            Image image_film = new Image(Affiche);

            Hyperlink hyperlink = new Hyperlink(film.getTitre());


            int finalNumFilm = numFilm;
            hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Film filmDetail = liste.get(finalNumFilm);
                    Scene scene3 = null;
                    try {
                        scene3 = createSceneThree(filmDetail, scene2, height, width, decalx, decaly);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switchScenes(scene3);
                }
            });

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


    private static Scene createSceneThree(Film film, Scene scene, int height, int width, int decalx, int decaly) throws IOException {
        Group root3 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);

        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);

        // button retour   scene 3
        Button buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            switchScenes(scene);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root3.getChildren().add(buttonBack);

        Scene scene3 = new Scene(root3, height, width);
        scene3.setFill(Color.YELLOW);

        String synopsis = Presentation.MethodPres(height, film.getSynopsis());

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




    // 3 scenes have been built, now make it possible to switch from one to the others
    public static void switchScenes(Scene scene) {
        stage.setScene(scene);
    }

    public static void main (String[] args) {
        Application.launch(args);
    }


}






