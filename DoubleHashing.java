public class DoubleHashing extends Hashtable {

    public DoubleHashing(int size) {
        super(size);
    }

    /**
     * Compute the probe index using double hashing:
     *   (primaryHash + attempt * h2(primaryHash)) mod tableSize,
     * where h2(k) = 1 + (k mod (tableSize - 2))
     */
    @Override
    protected int probeIndex(int primaryHash, int attempt) {
        int secondaryHash = 1 + (primaryHash % (tableSize - 2));
        return (primaryHash + attempt * secondaryHash) % tableSize;
    }
}
