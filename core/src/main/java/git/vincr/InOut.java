package git.vincr;

import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class InOut {

    public static Map<String, Map<String, Integer>> loadFile(FileHandle aFile) throws ValidationException {
        String contents = aFile.readString();

        Map<String, Map<String, Integer>> results = new HashMap<>();
        String[] split = contents.split("[\r\n]{1,100}");
        String[] firstLine = split[0].split(",");


        if (firstLine.length != split.length) {
            throw new ValidationException(ValidationException.FailureCase.SPREADSHEET_NOT_SQUARE);
        }

        for (int i = 1; i < firstLine.length; i++) {
            if (!firstLine[i].equals(split[i].split(",")[0])) {
                throw new ValidationException(ValidationException.FailureCase.NAMES_DO_NOT_MATCH);
            }
        }

        for (int i = 1; i < firstLine.length; i++) {
            results.put(firstLine[i], new HashMap<>());
        }

        for (int i = 1; i < split.length; i++) {
            String[] lineSplit = split[i].split(",");
            String forName = lineSplit[0];
            for (int n = 1; n < lineSplit.length; n++) {
                if (i == n) {
                    results.get(forName).put(forName, -1);
                } else {
                    try {
                        results.get(forName).put(firstLine[n], Integer.parseInt(lineSplit[n]));
                    } catch (NumberFormatException e) {
                        throw new ValidationException(ValidationException.FailureCase.VINC_NOT_A_NUMBER);
                    }
                }
            }
        }

        return results;
    }

    public static void writeFile(Map<String, Map<String, Integer>> contents, FileHandle aFile) {
        String[][] writeMe = new String[contents.size() + 1][contents.size() + 1];
        writeMe[0][0] = "";
        Map<String, Integer> columns = new HashMap<>();
        int iter = 1;
        for (String participant : contents.keySet()) {
            columns.put(participant, iter);
            writeMe[0][iter] = participant;
            writeMe[iter][0] = participant;
            writeMe[iter][iter] = "x";
            iter += 1;
        }

        for (String participant : contents.keySet()) {
            for (String participant2 : columns.keySet()) {
                if (participant.equals(participant2)) {
                    continue;
                }

                writeMe[columns.get(participant)][columns.get(participant2)] = "" + contents.get(participant).get(participant2);
            }
        }

        aFile.writeString("", false);
        for (String[] line : writeMe) {
            StringBuilder asLine = new StringBuilder();
            for (String value : line) {
                asLine.append(value);
                asLine.append(",");
            }
            String asString = asLine.toString();
            asString = asString.substring(0, asString.length() - 1);
            aFile.writeString(asString + "\r\n", true);
        }
    }
}
