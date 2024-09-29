package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator(null);
        LanguageCodeConverter lcc = new LanguageCodeConverter(null);
        CountryCodeConverter ccc = new CountryCodeConverter(null);

        runProgram(translator, ccc, lcc);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator, CountryCodeConverter ccc, LanguageCodeConverter lcc) {
        String quit = "quit";
        while (true) {
            String ac = promptForCountry(translator, ccc);
            String country = ccc.fromCountry(ac);
            if (quit.equals(country)) {
                break;
            }
            String al = promptForLanguage(translator, country, lcc);
            String language =lcc.fromLanguage(al);
            if (quit.equals(language)) {
                break;
            }

            System.out.println(ac + " in " + al + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator, CountryCodeConverter ccc) {
        List<String> countries = translator.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            countries.set(i, ccc.fromCountryCode(countries.get(i)));
        }
        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    @SuppressWarnings({"checkstyle:WhitespaceAround", "checkstyle:SuppressWarnings"})
    private static String promptForLanguage(Translator translator, String country, LanguageCodeConverter lcc) {
        List<String> count = translator.getCountryLanguages(country);
        // TODO Task: replace the line below so that we sort the languages
        //  alphabetically and print them out; one per line
        // TODO Task: convert the language codes to the actual language names before sorting
        for (int i = 0; i < count.size(); i++){
            count.set(i, lcc.fromLanguageCode(count.get(i)));
        }
        Collections.sort(count);
        for (String c : count) {
            System.out.println(c);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
