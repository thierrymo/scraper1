import javafx.scene.image.*;

public class Animation
{
    protected Image[] frames;

    // We defined the Animation object who will be constitute by 9 pictures
    public Animation(){
        frames = new Image[9];
        frames[0]= new Image("Ressources/pendule_centre.png");
        frames[1]= new Image("Ressources/pendule_half_droit.png");
        frames[2]= new Image("Ressources/pendule_droit.png");
        frames[3]= frames[1];
        frames[4]= frames[0];
        frames[5]= new Image("Ressources/pendule_half_gauche.png");
        frames[6]= new Image("Ressources/pendule_gauche.png");
        frames[7]= frames[5];
        frames[8]= frames[0];

    }
    public Image getFrame(int i)
    {
        return frames[i];
    }

}



