import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var extractor = new RawTextExtractor("texto.txt");
//        var extractor = new RawTextExtractor("domCasmurro.txt");
//        var extractor = new RawTextExtractor("test.txt");

        extractor.Extract();

        var analyzer = new KeywordTextAnalyzer(extractor.GetWords());

        KeywordValidator validator = new KeywordValidator();
        validator
            .setLengthValidation(3, 1)
            .setFrequencyValidation(1, 1)
            .setPositionValidation(10)
            .setForbiddenWords(new ArrayList<>(Arrays.asList(
                    "que", "dos", "com", "para", "nao", "mais", "mas",
                    "era", "nos", "por", "uma", "tem")));

        analyzer.AnalyzeKeywords(100, validator);
        analyzer.printResults();
    }
}
