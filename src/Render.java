import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale
 * Y sont stockés des attributs :
 * <u>
 *     <li>statiques qu'on utilise dans d'autres classes,</li>
 *     <li>ou non, qu'on passera en argument de méthode en méthode</li>
 * </u>
 */
public class Render extends Application {

    public static Stage stage;
    public static Scene scene1;
    int height = 1200;    //  Scene size
    int width= 700;       //  Scene size
    int decalx = 100;     // arround the scene
    int decaly = 100;     // arround the scene
    static int image_size = 75;     //  Film pictures size
    static public String cinema; // attribut permettant de stocker le nom du cinéma sélectionné par l'utilisateur
    static public LocalDate date = LocalDate.now(); // idem, concernant la date
    static String urlPremierePage = "https://www.allocine.fr/salle/cinema/ville-115755/"; // l'url d'où tout découle
    String paris = "https://www.plandeparis.info/plans-de-paris/plan-de-paris.jpg"; // image d'un plan de paris
    String clap = "https://www.lebonhommepicard.fr/wp-content/uploads/2020/02/Cine%CC%81ma.jpg"; // image permettant
    // de localiser un cinéma sur la carte

    @Override
    public void start(Stage primStage) throws Exception {
        stage = primStage;
        scene1 = Scene1.createSceneOne(height,width,decalx,decaly,paris,clap);
        stage.setScene(scene1);
        stage.show();
    }

    // 3 scenes have been built, now make it possible to switch from one to the others
    public static void switchScenes(Scene scene) {stage.setScene(scene);
    }

    public static void main (String[] args) {
        Application.launch(args);
    }
}






