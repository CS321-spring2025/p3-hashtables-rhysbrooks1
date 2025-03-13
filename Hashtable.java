
// Abstract Hashtable Class
abstract class Hashtable<T> {
    protected HashObject<T>[] table;
    protected int size;
    protected int numElements;
    
    @SuppressWarnings("unchecked")
    public Hashtable(int capacity) {
        this.size = capacity;
        this.numElements = 0;
        this.table = new HashObject[capacity];
    }
    
    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) quotient += divisor;
        return quotient;
    }
    
    public abstract boolean insert(HashObject<T> obj);
    public abstract boolean search(HashObject<T> obj);
    
    // not writen yet
    public void dumpToFile(String filename) {
    }
}