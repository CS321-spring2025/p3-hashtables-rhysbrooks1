// Linear Probing Implementation
class LinearProbing<T> extends Hashtable<T> {
    public LinearProbing(int capacity) {
        super(capacity);
    }
    // Insert function
    @Override
    public boolean insert(HashObject<T> obj) {
        int index = positiveMod(obj.hashCode(), size);
        int probes = 0;
        while (table[index] != null && !table[index].equals(obj)) {
            index = (index + 1) % size;
            probes++;
        }
        if (table[index] == null) {
            table[index] = obj;
            numElements++;
            obj.incrementProbes(probes);
            return true;
        } else {
            table[index].incrementFrequency();
            return false;
        }
    }
    // Search Function
    @Override
    public boolean search(HashObject<T> obj) {
        int index = positiveMod(obj.hashCode(), size);
        while (table[index] != null) {
            if (table[index].equals(obj)) return true;
            index = (index + 1) % size;
        }
        return false;
    }
}