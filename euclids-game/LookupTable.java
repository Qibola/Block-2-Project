import java.util.HashMap;

/**
 * Lookup table for calculated states
 */
public class LookupTable {

    public LookupTable() {
        this.entries = new HashMap<>();
    }

    public void put(State key, int utility) {
        int minUtility = utility;
        int maxUtility = utility;
        if (key.getPlayer() == Player.MAX_PLAYER) {
            minUtility = -maxUtility;
        } else {
            maxUtility = -minUtility;
        }
        MinMaxUtility minMaxUtility = new MinMaxUtility(minUtility, maxUtility);
        entries.put(key, minMaxUtility);
    }

    public int get(State key) {
        MinMaxUtility minMaxUtility = entries.get(key);
        if (key.getPlayer() == Player.MAX_PLAYER) {
            return minMaxUtility.getMaxUtility();
        } else {
            return minMaxUtility.getMinUtility();
        }
    }

    public boolean contains(State key) {
        return entries.containsKey(key);
    }

    private HashMap<State, MinMaxUtility> entries;

}
