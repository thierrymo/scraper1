import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

// To build the first scene where whe have to choose the cinema and the day
public class Scene1 {
    static Scene createSceneOne(int height, int width, int decalx, int decaly, String paris, String clap) throws IOException {

        Group root1 = new Group();
        Canvas canvas = new Canvas(height,width);
        root1.getChildren().add(canvas);
        GraphicsContext gc1 = canvas.getGraphicsContext2D();
        Image image = new Image(paris);
        gc1.drawImage( image, decalx, decaly,height-(2*decaly), width-(2*decalx));
        Image image2 = new Image(clap,60,60,false,false);
        ImageView pic2 = new ImageView(image2);
        gc1.drawImage( image2, 650, 300);

// The localisation of the cinema on the map
// The localisation of the chosen cinema has been defined for the 2 first cinema loc0 and loc1
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
// Definition of the box made to chose the cinema (one of the Paris cinema on Allocine)
// ... initialisation to the first in the list
        ChoiceBox<Cinema> choiceBox = new ChoiceBox<Cinema>(oListeCinemas);
        Render.cinema = listeCinemas.get(0).getNom();
        choiceBox.setValue(listeCinemas.get(0)); // valeur par défaut

        ChangeListener<Cinema> changeListener = new ChangeListener<Cinema>() {
            @Override
            public void changed(ObservableValue<? extends Cinema> observable, //
                                Cinema oldValue, Cinema newValue) {
                if (newValue != null) {
                    Render.cinema =newValue.getNom();
                    gc1.drawImage( image, decalx, decaly,height-(2*decaly), width-(2*decalx));
                    if (newValue.getLocalisation() != null){
                        gc1.drawImage( image2, newValue.getLocalisation()[0], newValue.getLocalisation()[1]);
                    }
                }
            }
        };

        choiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
// Definition of the rolling box to chose the date (7 days possible)
// initialisation to today
        ObservableList<LocalDate> oDates = FXCollections.observableArrayList();
        Dictionary<LocalDate, Integer> dicDates = new Hashtable<LocalDate, Integer>();
        for (int i =0 ; i<=6 ; i++){
            LocalDate d = LocalDate.now().plusDays(i);
            oDates.add(d);
            dicDates.put(d,i);
        }
        ChoiceBox<LocalDate> choiceBox2 = new ChoiceBox<LocalDate>(oDates);
        Render.date = LocalDate.now();
        choiceBox2.setValue(Render.date); // valeur par défaut

        ChangeListener<LocalDate> changeListener2 = new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 != null) {
                    Render.date = t1;
                }
            }
        };
// to put the 2 choices box in the vbox who will join our Group root1
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

// Definition of the action for "Recherche" button
// the structure of the url for today and the 6 others days are a bit different...

        Button buttonIntro = new Button("Recherche");
        buttonIntro.setOnAction(e -> {  // The action when pressing Reseach button
            try {
                String url = dicCinemas.get(Render.cinema);   // the cinema and the date have been selected
                if (dicDates.get(Render.date) != 0) {
                    String[] urlSplit = url.split("salle_gen");
                    url = urlSplit[0] + "d-" + dicDates.get(Render.date) + "/salle_gen" + urlSplit[1];
                }

                Scrap scrap = new Scrap(url);     // Calling the scraping of the corresponding url

                // and building the scene2 but waiting to switch
                Scene scene2 = Scene2.createSceneTwo(scrap, Render.scene1, height, width, decalx, decaly, Render.image_size);

                Animation animation = new Animation();  // Object Animation with our 9 pictures to draw the animation

                AnimationTimer h = new AnimationTimer() { // Definition of the animation with 9 pictures
                    private long lastUpdate; // Last time `handle()` was called
                    int t=0;
                    @Override
                    public void start() {
                        lastUpdate = System.nanoTime();
                        super.start();
                    }
                    @Override
                    public void handle(long now) {
                        long elapsedNanoSeconds = now - lastUpdate;
                        // 1 second = 1,000,000,000 (1 billion) nanoseconds
                        if (elapsedNanoSeconds> 0.1e9){  // changing picture every 0.1 s
                            double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
                            gc1.drawImage(animation.getFrame(t%9), decalx*4.5, 150);
                            t++;
                            lastUpdate = now;
                            if (t>30) { // after 30 pictures (3 sec) we gona stop the animation, and draw the Paris Map on the top
                                t=0;
                                super.stop();
                                gc1.drawImage( image, decalx, decaly,height-(2*decaly), width-(2*decalx));
                                Render.switchScenes(scene2);  // ... And... switch to the scene2
                            }}
                    }
                };

                h.start();  // calling the animation

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // the situation and the appearance of the research button
        buttonIntro.setPrefSize(150, 50);
        buttonIntro.setFont(new Font(15));
        buttonIntro.setTextFill(Color.BLACK);
        buttonIntro.setLayoutX(decalx);
        buttonIntro.setLayoutY(width/2+2*decalx+70);

        root1.getChildren().add(buttonIntro);

        // the appearance of scene1
        Scene scene1 = new Scene(root1, height, width);
        scene1.setFill(Color.YELLOW);

        return scene1;
    }
}


