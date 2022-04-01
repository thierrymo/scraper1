public class Presentation{

    // Method to show the Synopsis into the scene 3
    public static String MethodPres(int height, String synopsis) {
        final int NB_MAX = 120* height /1200;
        StringBuilder maChaine = new StringBuilder(synopsis);
        int index = 0;

        do {
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


