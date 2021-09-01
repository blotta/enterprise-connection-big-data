import java.util.ArrayList;

public class KeywordValidator implements IKeywordValidator {
    private int minLength;
    private double lengthWeight = 2.5;

    private int minFrequency;
    private double frequencyWeight = 0.7;

    private double posWeigth = 1;

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

    public KeywordValidator setPositionValidation(double weight) {
        this.posWeigth = weight;
        return this;
    }

    public KeywordValidator setForbiddenWords(ArrayList<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
        return this;
    }

    public boolean validate(KeyWordCandidate kwc) {
        if (kwc.getWord().length() < minLength) return false;
        if (kwc.getFrequency() < minFrequency) return false;
        if (forbiddenWords.contains(kwc.getWord())) return false;
        return true;
    }

    @Override
    public double calculateScore(KeyWordCandidate kwc) {
        if (!validate(kwc)) return 0;

        double freqScore = kwc.getFrequency() * this.frequencyWeight;
        double lenScore = (kwc.getWord().length()/(double)minLength) * this.lengthWeight;

        // https://medium.com/mlearning-ai/10-popular-keyword-extraction-algorithms-in-natural-language-processing-8975ada5750c
        // 4. Position Rank

        double posScore = kwc.getPositions()
                .stream()
                .map(x -> (double)x)
                .reduce(0.0, (acc, curr) -> acc + 1/curr) * posWeigth;

        // kwc.setScore(freqScore + lenScore + posScore);
        // return kwc.getScore();
        kwc.setScoreComment(String.format("Freq: %f; Length: %f; Pos: %f", freqScore, lenScore, posScore));
        return freqScore + lenScore + posScore;
    }
}
