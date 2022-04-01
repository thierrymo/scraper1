import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

    public class Scrap {
        private ArrayList<Film> filmsJournee;

        public Scrap(String urlSalle) throws IOException {
            Document doc = Jsoup.connect(urlSalle).get();

            Elements listeFilms = doc.select("div[class=card entity-card entity-card-list movie-card-theater cf hred]");

            ArrayList<Film> films = new ArrayList<>();

            for (Element filmElement :listeFilms) {
                Film film = new Film();

                Element titreElement = filmElement.select("div").first().select("h2").first().select("a").first();
                film.setTitre(titreElement.text());

                Element synopsisElement = filmElement.select("div[class=synopsis]").first().select("div[class=content-txt]").first();
                film.setSynopsis(synopsisElement.text());

                Element realisateurElement = filmElement.select("div").first().select("div").first().select("div").first().child(1);
                film.setRealisateur(realisateurElement.text());

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
                films.add(film);
            }

            filmsJournee = films;
        }

        public ArrayList<Film> getFilmsJournee() {
            return filmsJournee;
        }

        public static ArrayList<Cinema> scrapCinema() throws IOException {
            String urlPremierePage = "https://www.allocine.fr/salle/cinema/ville-115755/";
            ArrayList<String> listePages = new ArrayList<>();
            listePages.add(urlPremierePage);

            Document doc = Jsoup.connect(urlPremierePage).get();
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

