public class Presentation{

    // Method to show the Synopsis with a nice layout into the scene 3
    public static String MethodPres(int height, String synopsis) {
        // NB_MAX is the caracteres number we want to put on 1 line
        final int NB_MAX = 100* height /1200;
        StringBuilder maChaine = new StringBuilder(synopsis);
        int index = 0;
        do {
            // this function to cut the sentences not at the middle of a word
            // we found it with thanks to google
            index = maChaine.indexOf(" ", index + NB_MAX);
            if (index < 0) {
                break;
            }
            maChaine.setCharAt(index, '\n');
        }
        while (true);
        return maChaine.toString();
    }
}




