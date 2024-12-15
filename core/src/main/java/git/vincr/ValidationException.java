package git.vincr;

public class ValidationException extends Exception {
    public enum FailureCase {
        SPREADSHEET_NOT_SQUARE,
        NAMES_DO_NOT_MATCH,
        VINC_NOT_A_NUMBER,
    }

    protected FailureCase fail;

    public ValidationException(FailureCase fail) {
        this.fail = fail;
    }

    public FailureCase getFail() {
        return fail;
    }
}
