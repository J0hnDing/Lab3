package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private Map<String, String> lcoConverter = new HashMap<>();
    private Map<String, String> colConverter = new HashMap<>();

    private int numLanguages;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource("language-codes.txt").toURI()));

            lines.remove(0);
            for (String line : lines) {
                String[] parts = line.split("\\s+");
                String language = String.join(" ", java.util.Arrays.copyOf(parts, parts.length - 1));
                if (parts.length > 2) {
                    lcoConverter.put(language.toLowerCase(), parts[parts.length - 1]);
                    colConverter.put(parts[parts.length - 1], language);
                }
                else {
                    lcoConverter.put(parts[0].toLowerCase(), parts[1]);
                    colConverter.put(parts[1], parts[0]);
                }

                numLanguages++;
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        return colConverter.get(code.toLowerCase());
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        return lcoConverter.get(language.toLowerCase());
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return numLanguages;
    }
}
