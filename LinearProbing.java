public class LinearProbing extends Hashtable {

    public LinearProbing(int size) {
        super(size);
    }

    /**
     * Compute the probe index using linear probing:
     *   (primaryHash + attempt) mod tableSize
     */
    @Override
    protected int probeIndex(int primaryHash, int attempt) {
        return (primaryHash + attempt) % tableSize;
    }
}
