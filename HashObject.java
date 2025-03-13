// HashObject Class
class HashObject<T> {
    private T key;
    private int frequency;
    private int probes;
    
    public HashObject(T key) {
        this.key = key;
        this.frequency = 1;
        this.probes = 0;
    }
    
    public T getKey() {
        return key;
    }
    
    public void incrementFrequency() {
        frequency++;
    }
    
    public void incrementProbes(int count) {
        probes += count;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HashObject<?> other = (HashObject<?>) obj;
        return key.equals(other.key);
    }
    
    @Override
    public String toString() {
        return key + " " + frequency + " " + probes;
    }
}