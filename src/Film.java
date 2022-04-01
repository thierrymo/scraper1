import java.util.ArrayList;

public class Film {
    private String titre;
    private String afficheUrl;
    private ArrayList<String> horairesJournee;
    private String dateSortie;
    private String realisateur;
    private String equipe;
    private String synopsis;

    public Film() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAfficheUrl() {
        return afficheUrl;
    }

    public void setAfficheUrl(String afficheUrl) {
        this.afficheUrl = afficheUrl;
    }

    public ArrayList<String> getHorairesJournee() {
        return horairesJournee;
    }

    public void setHorairesJournee(ArrayList<String> horairesJournee) {
        this.horairesJournee = horairesJournee;
    }


    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }


    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titre='" + titre + '\'' +
                ", afficheUrl='" + afficheUrl + '\'' +
                ", horairesJournee=" + horairesJournee +
                '}';
    }
}




