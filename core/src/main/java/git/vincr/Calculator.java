package git.vincr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculator {
    protected static Random random = new Random();

    public static Map<String, Map<String, Integer>> calculateVinc(Map<String, Map<String, Integer>> input) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> participant : input.entrySet()) {
            result.putIfAbsent(participant.getKey(), new HashMap<>());
            for (Map.Entry<String, Integer> withWhom : participant.getValue().entrySet()) {
                if (withWhom.getKey().equals(participant.getKey())) {
                    result.get(participant.getKey()).put(participant.getKey(), -1);
                }
                else if (getModifier(withWhom.getValue()) + random.nextInt(20) + 1 >= 10) {
                    result.get(participant.getKey()).put(withWhom.getKey(), withWhom.getValue() + 1);
                } else {
                    result.get(participant.getKey()).put(withWhom.getKey(), withWhom.getValue() - 1);
                }
            }
        }
        return result;
    }

    protected static int getModifier(int ofValue) {
        if (ofValue <= 2) {
            return 5;
        }
        return 6 - ofValue;
    }

}
