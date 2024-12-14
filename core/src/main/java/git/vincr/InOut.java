package git.vincr;

import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class InOut {

    public static Map<String, Map<String, Integer>> loadFile(FileHandle aFile) {
        String contents = aFile.readString();
        Map<String, Map<String, Integer>> results = new HashMap<>();
        String[] split = contents.split("[\r\n]{1,100}");
        String[] firstLine = split[0].split(",");
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
                    results.get(forName).put(firstLine[n], Integer.parseInt(lineSplit[n]));
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
            String asLine = "";
            for (String value : line) {
                asLine += value + ",";
            }
            asLine = asLine.substring(0, asLine.length() - 1);
            aFile.writeString(asLine + "\r\n", true);
        }
    }
}
