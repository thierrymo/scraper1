public class Cinema {

    private String nom;
    private String url;
    private int[] localisation;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return this.nom;
    }

    public int[] getLocalisation() {
        return localisation;
    }

    public void setLocalisation(int[] localisation) {
        this.localisation = localisation;
    }
}


