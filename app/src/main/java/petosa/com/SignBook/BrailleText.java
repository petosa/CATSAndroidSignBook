package petosa.com.SignBook;

public class BrailleText {

    private String english;
    private String ascii;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private BrailleText() {
    }

    BrailleText(String english, String ascii) {
        this.english = english;
        this.ascii = ascii;
    }

    public String getEnglish() {
        return english;
    }

    public String getAscii() {
        return ascii;
    }
}
