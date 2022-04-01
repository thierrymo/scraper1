import javafx.scene.image.*;

public class Animation
{
    protected Image[] frames;
    protected double duration;

    public Animation(){
        frames = new Image[9];
        duration = 1;
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

    /**
     * Useful for animation and cinematic
     * @param duration frame duration on second (I guess)
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Useful to change the texture might be use on trigger or cinematic
     * @param frames new texture of the character
     */
    public void setFrames(Image[] frames) {
        this.frames = frames;
    }
}

