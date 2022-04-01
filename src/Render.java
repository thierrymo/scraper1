import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Render extends Application {

    public static Stage stage;
    public static Scene scene1;
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

    // 3 scenes have been built, now make it possible to switch from one to the others
    public static void switchScenes(Scene scene) {stage.setScene(scene);
    }

    public static void main (String[] args) {
        Application.launch(args);
    }
}






