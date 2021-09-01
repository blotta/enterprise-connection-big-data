import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordTextAnalyzer {
    private final ArrayList<KeyWordCandidate> keyWords;
    private List<KeyWordCandidate> resultKeyWords;
    private final ArrayList<String> words;

    public KeywordTextAnalyzer(ArrayList<String> words) {
        keyWords = new ArrayList<>();
        this.words = words;
    }

    public void AnalyzeKeywords(int n, IKeywordValidator validator) {
        assert (n > 0);

        for (int i = 0; i < words.size(); i++) {
            this.add(words.get(i), i);
        }

        for (var kwc: keyWords) {
            kwc.calculateScore(validator);
        }

        keyWords.sort(new Comparator<KeyWordCandidate>() {
            @Override
            public int compare(KeyWordCandidate o1, KeyWordCandidate o2) {
                return Double.compare(o2.getScore(), o1.getScore());
            }
        });

        resultKeyWords = keyWords.stream().limit(n).collect(Collectors.toList());
    }

    private void add(String word, int pos) {
        for (var candidate : this.keyWords) {
            if (candidate.getWord().equals(word)) {
                candidate.tick(pos);
                return;
            }
        }

        keyWords.add(new KeyWordCandidate(word, pos));
    }

    public void printResults() {
        int maxLen = resultKeyWords.stream().max(Comparator.comparing(c -> c.getWord().length())).get().getWord().length();

        System.out.printf("%5s %" + maxLen +"s %4s %10s %s\n", "RANK", "KEYWORD", "FREQ", "SCORE", "COMMENT");
        for (int i = 0; i < resultKeyWords.size(); i++) {
            var kw = resultKeyWords.get(i);
            System.out.format(
                "%5d %"+maxLen+"s %4d %10f %s\n",
                i + 1, kw.getWord(), kw.getFrequency(), kw.getScore(), kw.getScoreComment());
        }
    }
}
