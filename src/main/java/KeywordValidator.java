import java.util.ArrayList;

public class KeywordValidator implements IKeywordValidator {
    private int minLength;
    private double lengthWeight = 2.5;

    private int minFrequency;
    private double frequencyWeight = 0.7;

    private ArrayList<String> forbiddenWords;

    public KeywordValidator() {
        this.minLength = 0;
        this.lengthWeight = 1;

        this.minFrequency = 0;
        this.frequencyWeight = 1;
        this.forbiddenWords = new ArrayList<>();
    }

    public KeywordValidator setLengthValidation(int minLength, double weight) {
        this.minLength = minLength;
        this.lengthWeight = weight;
        return this;
    }

    public KeywordValidator setFrequencyValidation(int minFrequency, double weight) {
        this.minFrequency = minFrequency;
        this.frequencyWeight = weight;
        return this;
    }

    public KeywordValidator setForbiddenWords(ArrayList<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
        return this;
    }

    @Override
    public boolean validate(KeyWordCandidate kwc) {
        if (kwc.word.length() < minLength) return false;
        if (kwc.frequency < minFrequency) return false;
        if (forbiddenWords.contains(kwc.word)) return false;
        return true;
    }

    @Override
    public double score(KeyWordCandidate kwc) {
        if (!validate(kwc)) return 0;

        double freqScore = kwc.frequency * this.frequencyWeight;
        double lenScore = (kwc.word.length()/(double)minLength) * this.lengthWeight;
        kwc.score = freqScore + lenScore;

        return kwc.score;
    }
}
