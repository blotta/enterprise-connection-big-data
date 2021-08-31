import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordTextAnalyzer {
    private final ArrayList<KeyWordCandidate> keyWords;
    private final ArrayList<String> words;

    public KeywordTextAnalyzer(ArrayList<String> words) {
        keyWords = new ArrayList<>();
        this.words = words;
    }

    public List<KeyWordCandidate> GetKeywords(int n) {
        assert (n > 0);

        for (var w: words) {
            this.Add(w);
        }

        KeywordValidator validator = new KeywordValidator();
        validator
            .setLengthValidation(3, 2)
            .setFrequencyValidation(1, 1.5)
            .setForbiddenWords(new ArrayList<>(Arrays.asList("que", "dos", "com", "para", "nao", "mais")));

        keyWords.sort(new Comparator<KeyWordCandidate>() {
            @Override
            public int compare(KeyWordCandidate o1, KeyWordCandidate o2) {
                return Double.compare(validator.score(o2), validator.score(o1));
            }
        });

        return keyWords.stream().limit(n).collect(Collectors.toList());
    }

    public void Add(String word) {
        for (var candidate : this.keyWords) {
            if (candidate.word.equals(word)) {
                candidate.frequency += 1;
                return;
            }
        }

        keyWords.add(new KeyWordCandidate(word));
    }
}
