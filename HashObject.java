import java.util.Objects;

/**
 * HashObject holds:
 *   - A generic key of type Object
 *   - A frequency count (number of duplicates)
 *   - A probe count (number of probes for the first insertion)
 *
 * It overrides equals() and toString() and provides getKey().
 */
public class HashObject {
    private Object key;
    private int frequency;
    private int probeCount;

    public HashObject(Object key) {
        this.key = key;
        this.frequency = 1;    // first time we see this key
        this.probeCount = 0;   // will be set when we first insert
    }

    public Object getKey() {
        return this.key;
    }

    /**
     * Increment the frequency if a duplicate is found.
     */
    public void incrementFrequency() {
        this.frequency++;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }

    public int getProbeCount() {
        return this.probeCount;
    }

    /**
     * Two HashObjects are equal if their keys are equal (using .equals on the keys).
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        HashObject that = (HashObject) other;
        // Compare the actual keys using the key's own equals method
        return Objects.equals(this.key, that.key);
    }

    /**
     * toString format: "key frequency probeCount"
     */
    @Override
    public String toString() {
        // We assume the key’s own toString is appropriate here.
        return String.format("%s %d %d", key.toString(), frequency, probeCount);
    }
}
