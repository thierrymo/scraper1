import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;


/**
 Classe dédiée au scraping :
 * <ul>
 * <li>des cinémas parisiens (leurs noms et urls)</li>
 * <li>des films de la journée sélectionnée par l'utilisateur, pour une salle de cinéma donnée.</li>
 * Le loader et le parser ont été réunis.
 */
public class Scrap {

    private ArrayList<Film> filmsJournee;

    public Scrap(String urlSalle) throws IOException {

        // récupération du code html de la page d'accueil d'allociné liée à un cinéma :
        Document doc = Jsoup.connect(urlSalle).get();

        // .. et on scrappe :
        Elements listeFilms = doc.select("div[class=card entity-card entity-card-list movie-card-theater cf hred]");

        ArrayList<Film> films = new ArrayList<>();

        for (Element filmElement :listeFilms) {
            // Création d'un objet Film :
            Film film = new Film();

            Element titreElement = filmElement.select("div").first().select("h2").first().select("a").first();
            film.setTitre(titreElement.text());

            Element synopsisElement = filmElement.select("div[class=synopsis]").first().select("div[class=content-txt]").first();
            film.setSynopsis(synopsisElement.text());

            Element realisateurElement = filmElement.select("div").first().select("div").first().select("div").first().child(1);
            film.setRealisateur(realisateurElement.text());

            // les attributs des balises concernant les urls des images ne sont pas toujours désignés de la même manière,
            // d'où cette conditionnalité :
            Element srcElement = filmElement.select("figure").first().child(0).select("img").first();
            if (srcElement.attr("data-src") == "") {
                film.setAfficheUrl(srcElement.attr("src"));
            } else {
                film.setAfficheUrl(srcElement.absUrl("data-src"));
            }

            Elements horairesElements = filmElement.select("div[class=showtimes-anchor]").first().child(0).child(0).child(1).select("div[class=showtimes-hour-block]");
            ArrayList<String> listeHoraires = new ArrayList<>();
            for (Element hourElement : horairesElements){
                listeHoraires.add(hourElement.child(0).child(0).text());
            }
            film.setHorairesJournee(listeHoraires);

            // Après avoir donné les valeurs srappées aux attributs de notre instance de Film,
            // on ajoute ce film à notre liste de films.
            films.add(film);
        }

        // Une fois les films "réceptionnés" dans notre liste, on passe cette dernière à l'attribut de notre classe Scrap :
        filmsJournee = films;
    }


    public ArrayList<Film> getFilmsJournee() {
            return filmsJournee;
        }

    // Cette méthode nous renvoie la liste des cinémas parisiens, sous forme d'objets Cinéma (nom + url)
    public static ArrayList<Cinema> scrapCinema() throws IOException {
        ArrayList<String> listePages = new ArrayList<>();
        listePages.add(Render.urlPremierePage);

        Document doc = Jsoup.connect(Render.urlPremierePage).get();
        Elements pages = doc.select("div[class=pagination-item-holder]").first().select("a");
        int a = 0;
        for (Element page : pages){
            listePages.add("https://www.allocine.fr"+page.attr("href"));
        }

        ArrayList<Cinema> listeCinemas = new ArrayList<>();

        for (String url:listePages){
            doc = Jsoup.connect(url).get();
            Elements cineSection = doc.select("li[class=hred]");
            for (Element e : cineSection){
                Cinema cine = new Cinema();
                cine.setUrl("https://allocine.fr"+e.child(0).child(1).child(0).child(0).child(0).attr("href"));
                cine.setNom(e.child(0).child(1).child(0).child(0).child(0).text());
                listeCinemas.add(cine);
            }
        }
        return listeCinemas;
    }
}

