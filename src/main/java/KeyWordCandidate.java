import java.util.ArrayList;

public class KeyWordCandidate {
    private final String word;
    private int frequency;
    private double score = 0;
    private final ArrayList<Integer> positions;

    private String scoreComment;

    public int getFrequency() {
        return frequency;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public double getScore() {
        return score;
    }

    public String getScoreComment() {return scoreComment;}
    public void setScoreComment(String comment) {
        this.scoreComment = comment;
    }

    public KeyWordCandidate(String word, int firstPos) {
        this.word = word;
        this.frequency = 1;
        positions = new ArrayList<>();
        positions.add(firstPos);
    }

    public void tick(int pos) {
        this.frequency += 1;
        positions.add(pos);
    }

    public void calculateScore(IKeywordValidator validator) {
        double score = validator.calculateScore(this);
        this.score = score;
    }
}
