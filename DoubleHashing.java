// Double Hashing Implementation
class DoubleHashing<T> extends Hashtable<T> {
    public DoubleHashing(int capacity) {
        super(capacity);
    }
    
    private int hash2(T key) {
        return 1 + positiveMod(key.hashCode(), size - 2);
    }
    
    @Override
    public boolean insert(HashObject<T> obj) {
        int index = positiveMod(obj.hashCode(), size);
        int step = hash2(obj.getKey());
        int probes = 0;
        while (table[index] != null && !table[index].equals(obj)) {
            index = (index + step) % size;
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

    @Override
    public boolean search(HashObject<T> obj) {
        int index = positiveMod(obj.hashCode(), size);
        int step = hash2(obj.getKey());
        while (table[index] != null) {
            if (table[index].equals(obj)) return true;
            index = (index + step) % size;
        }
        return false;
    }
}