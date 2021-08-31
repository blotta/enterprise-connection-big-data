import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RawTextExtractor {
    private static final String ptChars = "çáéíóúâêôãõàÇÁÉÍÓÚÂÊÔÃÕÀ";
    private static final String ptReplaceChars = "caeiouaeoaoaCAEIOUAEOAOA";
    private static final String ptAllChars = "A-Za-z0-9"+ ptChars;

    private final String filepath;
    private ArrayList<String> words;

    public ArrayList<String> GetWords() throws RuntimeException {
        if (words == null)
            throw new RuntimeException("Text not extracted");
        return words;
    }

    public RawTextExtractor(String filepath) {
        this.filepath = filepath;
    }

    public void Extract() {
        File file = new File(filepath);

        words = new ArrayList<>();

        try {
            Scanner reader = new Scanner(file);
            reader.useDelimiter("\\s+");
            while (reader.hasNext()) {
                // String data = reader.next();

                String data = reader.next()
                        .toLowerCase() // lowercase all
                        .replaceAll("[^" + ptAllChars + "]", " "); // replace all non pt chars with a space

                // replace all pt only chars with equivalent ascii chars
                data = StringUtils.replaceChars(data, ptChars, ptReplaceChars);

                for (String w : data.split("\\s+")) {
                    if (w.length() > 0) {
                        this.words.add(w);
                    }
                }
            }

            System.out.println(words.size());

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.out.println(file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
