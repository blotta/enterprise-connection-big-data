import static java.lang.Math.sqrt;

public class KeyWordCandidate {
    public String word;
    public int frequency;
    public double score = 0;

    public KeyWordCandidate(String word) {
        this.word = word;
        this.frequency = 1;
    }
}
