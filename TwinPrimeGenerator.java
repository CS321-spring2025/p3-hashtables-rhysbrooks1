/**
 * Generates twin primes in a range [min, max].
 * A twin prime pair is two primes that differ by 2.
 * We return the larger of the twin prime pair (i.e., p+2).
 */
public class TwinPrimeGenerator {

    /**
     * Returns the smallest twin prime pair in [min, max], then returns
     * the larger of the two primes. For example, if (p, p+2) is the twin
     * prime pair found, we return p+2.
     */
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max - 2; i++) {
            if (isPrime(i) && isPrime(i + 2)) {
                // Found our twin prime pair
                return i + 2; // return the larger of the two
            }
        }
        // If none found, fallback (for this project we assume it is always found)
        return min; 
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0 && n > 2) return false; 
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
