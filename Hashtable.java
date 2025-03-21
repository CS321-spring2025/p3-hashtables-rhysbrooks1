import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Abstract class implementing common functionality for both
 * linear probing and double hashing. 
 *
 * Subclasses (LinearProbing, DoubleHashing) will provide the
 * logic for computing the correct array index on each probe attempt.
 */
public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize;

    // Track total probes (for new insertions, not duplicates)
    protected long totalProbes;

    // Track how many actual (unique) inserts happened
    protected long insertCount;

    // Track how many duplicates we found
    protected long duplicateCount;

    public Hashtable(int size) {
        this.tableSize = size;
        this.table = new HashObject[size];
        this.totalProbes = 0;
        this.insertCount = 0;
        this.duplicateCount = 0;
    }

    /**
     * Subclasses must implement how to calculate the index on a given
     * probe attempt. E.g.,
     *   - Linear probing: (h1 + i) mod m
     *   - Double hashing: (h1 + i*h2) mod m
     *
     * @param keyHash  the hashCode() of the object’s key
     * @param attempt  the current attempt number (0, 1, 2, ...)
     * @return         the table index to check on this attempt
     */
    protected abstract int probeIndex(int keyHash, int attempt);

    /**
     * Insert a new object or, if duplicate, increment its frequency.
     *
     * Returns the number of probes used for a successful new insertion,
     * or 0 if the object was a duplicate (i.e., no new insertion).
     */
    public int insert(HashObject obj) {
        int keyHash = obj.getKey().hashCode();
        // Make sure we always handle negative mod results properly
        int primaryHash = positiveMod(keyHash, tableSize);

        // Attempt from 0, 1, 2, ...
        int attempt = 0;
        while (attempt < tableSize) {
            int index = probeIndex(primaryHash, attempt);

            if (table[index] == null) {
                // We found an empty slot, do new insertion
                table[index] = obj;
                obj.setProbeCount(attempt + 1);  // how many checks it took
                insertCount++;
                totalProbes += (attempt + 1);
                return (attempt + 1); // number of probes for insertion
            } else {
                // Check if this is a duplicate
                if (table[index].equals(obj)) {
                    // It's a duplicate, increment freq
                    table[index].incrementFrequency();
                    duplicateCount++;
                    // No new insertion, so no new probes counted
                    return 0;
                }
            }
            attempt++;
        }

        // If we get here, the table is essentially full
        // (though for this assignment, we expect n <= m,
        // so presumably we shouldn’t completely run out of slots)
        // Return 0 to indicate we couldn’t insert
        return 0;
    }

    /**
     * Retrieve an object from the table, or null if not found.
     * Not strictly required by the project, but included for completeness.
     */
    public HashObject search(HashObject obj) {
        int keyHash = obj.getKey().hashCode();
        int primaryHash = positiveMod(keyHash, tableSize);

        int attempt = 0;
        while (attempt < tableSize) {
            int index = probeIndex(primaryHash, attempt);
            if (table[index] == null) {
                // no match, and no further place to search
                return null;
            } else if (table[index].equals(obj)) {
                // found it
                return table[index];
            }
            attempt++;
        }
        // Not found
        return null;
    }

    /**
     * A proper modulus operation that always returns a positive remainder.
     */
    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }

    /**
     * Dump non-null entries to a file. Format:
     * table[index]: key frequency probeCount
     */
    public void dumpToFile(String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (int i = 0; i < tableSize; i++) {
                if (table[i] != null) {
                    out.printf("table[%d]: %s\n", i, table[i].toString());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not open " + fileName + " for writing.");
        }
    }

    /**
     * Get total number of unique inserted objects
     */
    public long getInsertCount() {
        return insertCount;
    }

    /**
     * Get total number of duplicates
     */
    public long getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Return the average number of probes for all *new* insertions.
     */
    public double getAverageProbes() {
        if (insertCount == 0) return 0.0;
        return (double) totalProbes / (double) insertCount;
    }
}
