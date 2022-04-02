import java.util.ArrayList;

/**
 * Film est la classe qui définit un film.
 * Ses instances, de type String, servent principalement à réceptionner et conserver les résultats de notre scraping.
 */
public class Film {
    private String titre;
    private String afficheUrl;
    private ArrayList<String> horairesJournee;
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

}


