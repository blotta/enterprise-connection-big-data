public interface IKeywordValidator {
    boolean validate(KeyWordCandidate kwc);
    double score(KeyWordCandidate kwc);
}
