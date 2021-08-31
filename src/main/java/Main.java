public class Main {

    public static void main(String[] args) {
        var extractor = new RawTextExtractor("test.txt");

        extractor.Extract();

        var analyzer = new KeywordTextAnalyzer(extractor.GetWords());
        var keywords = analyzer.GetKeywords(100);

        for (var c : keywords) {
            System.out.format("%s:\t%d\t%f\n", c.word, c.frequency, c.score);
        }
    }
}
