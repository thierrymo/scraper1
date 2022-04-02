/**
 * Cinema est la classe qui définit un cinéma.
 * Une instance est caractérisée par les informations suivantes :
 * <ul>
 * <li>le nom du cinéma,</li>
 * <li>l'url menant à la page du film projeté au jour J dans le cinéma</li>
 * <li>sa localisation sur la carte, pour les 2 premiers cinémas de notre liste</li>
 * </ul>
 */
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


