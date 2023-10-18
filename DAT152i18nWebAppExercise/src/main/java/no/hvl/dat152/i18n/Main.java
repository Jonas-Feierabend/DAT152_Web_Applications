package no.hvl.dat152.i18n;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        // Set the default locale to use the "config_en" configuration
        Locale.setDefault(new Locale("en", "US")); // Change "en" and "US" to the appropriate values

        // Load the resource bundle with the specified base name
        ResourceBundle bundle = ResourceBundle.getBundle("no.hvl.dat152.i18n.Config");
        System.out.println("setting locale##########################################################################"); 
    }
}