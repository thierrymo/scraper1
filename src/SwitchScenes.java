import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SwitchScenes extends Application {

    private Stage stage;
    private Scene scene1;
    private Group root1;
    private Button buttonIntro;
    private Scene scene2;
    private Group root2;
    private Button buttonBack;
    int height = 1200;
    int width= 700;
    int decalx = 100;
    int decaly = 100;
    int image_size = 75;
    private String cinema;
    public LocalDate date = LocalDate.now();
    String paris = "https://www.plandeparis.info/plans-de-paris/plan-de-paris.jpg";
    String clap = "https://www.lebonhommepicard.fr/wp-content/uploads/2020/02/Cine%CC%81ma.jpg";

    @Override
    public void start(Stage primStage) throws Exception {
        stage = primStage;
        scene1 = createSceneOne();
        stage.setScene(scene1);
        stage.show();
    }

    private Scene createSceneOne() throws IOException {

        root1 = new Group();
        Canvas canvas = new Canvas(height,width);
        root1.getChildren().add(canvas);
        GraphicsContext gc1 = canvas.getGraphicsContext2D();
        Image image = new Image(paris);
        gc1.drawImage( image, decalx, decaly,height-(2*decaly), width-(2*decalx));
        Image image2 = new Image(clap,60,60,false,false);
        ImageView pic2 = new ImageView(image2);
        gc1.drawImage( image2, 650, 300);



        ArrayList<Cinema> listeCinemas = Scrap.scrapCinema();
        int[] loc0 = new int[2];
        loc0[0] = 650;
        loc0[1] = 300;
        listeCinemas.get(0).setLocalisation(loc0);
        int[] loc1 = new int[2];
        loc1[0] = 880;
        loc1[1] = 480;
        listeCinemas.get(1).setLocalisation(loc1);


        Dictionary<String, String> dicCinemas = new Hashtable<String, String>();
        for (Cinema cine : listeCinemas){
            String nomCinema = cine.getNom();
            dicCinemas.put(nomCinema,cine.getUrl());
        }

        ObservableList<Cinema> oListeCinemas = FXCollections.observableArrayList(listeCinemas);

        ChoiceBox<Cinema> choiceBox = new ChoiceBox<Cinema>(oListeCinemas);
        cinema = listeCinemas.get(0).getNom();
        choiceBox.setValue(listeCinemas.get(0)); // valeur par défaut

        ChangeListener<Cinema> changeListener = new ChangeListener<Cinema>() {
            @Override
            public void changed(ObservableValue<? extends Cinema> observable, //
                                Cinema oldValue, Cinema newValue) {
                if (newValue != null) {
                    cinema = newValue.getNom();
                    gc1.drawImage( image, decalx, decaly,height-(2*decaly), width-(2*decalx));
                    if (newValue.getLocalisation() != null){
                        gc1.drawImage( image2, newValue.getLocalisation()[0], newValue.getLocalisation()[1]);
                    }

                }
            }
        };

        choiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);

        ObservableList<LocalDate> oDates = FXCollections.observableArrayList();
        Dictionary<LocalDate, Integer> dicDates = new Hashtable<LocalDate, Integer>();
        for (int i =0 ; i<=6 ; i++){
            LocalDate d = LocalDate.now().plusDays(i);
            oDates.add(d);
            dicDates.put(d,i);
        }
        ChoiceBox<LocalDate> choiceBox2 = new ChoiceBox<LocalDate>(oDates);
        date = LocalDate.now();
        choiceBox2.setValue(date); // valeur par défaut

        ChangeListener<LocalDate> changeListener2 = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 != null) {
                    date = t1;
                }
            }
        };

        choiceBox2.getSelectionModel().selectedItemProperty().addListener(changeListener2);

        HBox hBox = new HBox(choiceBox);//Add choiceBox to hBox
        hBox.setAlignment(Pos.TOP_CENTER);
        HBox hBox2 = new HBox(choiceBox2);
        hBox2.setAlignment(Pos.BOTTOM_LEFT);

        final VBox vbox = new VBox();
        vbox.setLayoutX(90);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(35, 0, 0, 10));
        vbox.getChildren().addAll(hBox,hBox2);
        root1.getChildren().add(vbox);

        buttonIntro = new Button("Recherche");
        buttonIntro.setOnAction(e -> {
            try {
                String url = dicCinemas.get(cinema);
                if (dicDates.get(date) != 0) {
                    String[] urlSplit = url.split("salle_gen");
                    url = urlSplit[0] + "d-" + dicDates.get(date) + "/salle_gen" + urlSplit[1];
                }
                Scrap scrap = new Scrap(url);
                scene2 = createSceneTwo(scrap);
                switchScenes(scene2);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        buttonIntro.setPrefSize(150, 50);
        buttonIntro.setFont(new Font(15));
        buttonIntro.setTextFill(Color.BLACK);
        buttonIntro.setLayoutX(decalx);
        buttonIntro.setLayoutY(width/2+2*decalx+70);

        root1.getChildren().add(buttonIntro);

        scene1 = new Scene(root1, height, width);
        scene1.setFill(Color.YELLOW);

        return scene1;
    }



    private Scene createSceneTwo(Scrap scrap) throws IOException {
        root2 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);


        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);


        buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            switchScenes(scene1);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root2.getChildren().add(buttonBack);

        scene2 = new Scene(root2, height, width);
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
                        scene3 = createSceneThree(filmDetail);
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


    private Scene createSceneThree(Film film) throws IOException {
        Group root3 = new Group();

        VBox vBox= new VBox();
        vBox.setLayoutX(decalx);
        vBox.setLayoutY(decaly);
        vBox.setPrefSize(height-2*decalx, width-2*decaly);

        ScrollPane scrollPane= new ScrollPane();
        scrollPane.setLayoutX(decalx);
        scrollPane.setLayoutY(decaly);
        scrollPane.setMaxSize(height,width);

        buttonBack = new Button("Retour");
        buttonBack.setOnAction(e -> {
            switchScenes(scene2);
        });

        buttonBack.setPrefSize(150,50);
        buttonBack.setFont(new Font(15));
        buttonBack.setTextFill(Color.RED);
        buttonBack.setLayoutX(decalx);
        buttonBack.setLayoutY(25);

        root3.getChildren().add(buttonBack);

        Scene scene3 = new Scene(root3, height, width);
        scene3.setFill(Color.YELLOW);

        String synopsis = MethodPres(film.getSynopsis());

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


    public String MethodPres(String synopsis) {
        final int NB_MAX = 150*height/1200;
        StringBuilder maChaine = new StringBuilder(synopsis);
        int index = 0;

        do {
            index = maChaine.indexOf(" ", index + NB_MAX);
            if (index < 0) {
                break;
            }
            maChaine.setCharAt(index, '\n');
        }
        while (true);
        return maChaine.toString();

    }


    public void switchScenes (Scene scene) {
        stage.setScene (scene);
    }

    public static void main (String[] args) {
        Application.launch(args);
    }


}



