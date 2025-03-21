import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 * Driver program that:
 *   1) Finds a twin prime capacity in the range [95500..96000].
 *   2) Reads <dataSource> from the command line to decide if we generate:
 *      - random integers
 *      - date (long) objects
 *      - words from a word-list file
 *   3) Fills both a LinearProbing and DoubleHashing table up to a load factor alpha.
 *   4) Prints the summary (debugLevel=0), optionally dumps tables to files
 *      (debugLevel=1), or prints detailed insertion (debugLevel=2).
 */
public class HashtableExperiment {

    public static void main(String[] args) {

        // Check command-line arguments
        if (args.length < 2) {
            usage();
            return;
        }

        int dataSource = Integer.parseInt(args[0]); // 1, 2, or 3
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = 0;
        if (args.length > 2) {
            debugLevel = Integer.parseInt(args[2]);
        }

        // 1) Determine an appropriate twin prime for the hash table
        int capacity = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + capacity);

        // 2) Prepare the data
        // We'll need to insert n = ceil(alpha * capacity) elements
        int n = (int) Math.ceil(loadFactor * capacity);

        // Just a string for printing
        String inputType = switch (dataSource) {
            case 1 -> "Random-Integer";
            case 2 -> "Date-Long";
            case 3 -> "Word-List";
            default -> "Unknown";
        };

        System.out.println("HashtableExperiment: Input: " + inputType
                + "   Loadfactor: " + loadFactor + "\n");

        // 3) Create both hash tables
        LinearProbing linearTable = new LinearProbing(capacity);
        DoubleHashing doubleTable = new DoubleHashing(capacity);

        // Generate the data (common data for both tables for fair comparison)
        HashObject[] dataObjects = generateData(dataSource, n);

        // 4) Insert into both tables
        fillTable(linearTable, dataObjects, debugLevel, "Linear Probing");
        fillTable(doubleTable, dataObjects, debugLevel, "Double Hashing");

        // 5) Print summary and optionally dump to file
        printSummary("Using Linear Probing", linearTable);
        if (debugLevel == 1) {
            linearTable.dumpToFile("linear-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table\n");
        }

        printSummary("Using Double Hashing", doubleTable);
        if (debugLevel == 1) {
            doubleTable.dumpToFile("double-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table\n");
        }
    }

    /**
     * Insert all data from the array into the given table.
     * If debugLevel=2, print details for each insertion attempt.
     */
    private static void fillTable(Hashtable table, HashObject[] data, int debugLevel, String label) {
        for (HashObject obj : data) {
            // If any array positions remain null (because we ran out of words, etc.), skip them
            if (obj == null) {
                break;
            }
            int probesUsed = table.insert(obj);
            if (debugLevel == 2) {
                if (probesUsed == 0) {
                    System.out.println(label + ": Duplicate found for key=" + obj.getKey());
                } else {
                    System.out.println(label + ": Inserted key=" + obj.getKey() 
                        + " with " + probesUsed + " probes.");
                }
            }
        }
    }

    /**
     * Generate an array of HashObject of size n, according to dataSource.
     */
    private static HashObject[] generateData(int dataSource, int n) {
        HashObject[] arr = new HashObject[n];
        switch (dataSource) {
            case 1 -> {
                // Random integers
                Random rand = new Random();
                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    arr[i] = new HashObject(val);
                }
            }
            case 2 -> {
                // Date objects in increments of 1 second
                long current = System.currentTimeMillis();
                for (int i = 0; i < n; i++) {
                    arr[i] = new HashObject(new Date(current));
                    current += 1000; // increment by 1 second
                }
            }
            case 3 -> {
                // Word list from file "word-list.txt"
                arr = readWordsFromFile("word-list.txt", n);
            }
            default -> {
                System.err.println("Unknown data source. Using random integers as fallback.");
                Random fallbackRand = new Random();
                for (int i = 0; i < n; i++) {
                    arr[i] = new HashObject(fallbackRand.nextInt());
                }
            }
        }
        return arr;
    }

    /**
     * Read up to n words from "word-list.txt" (one word per line),
     * skipping blank/empty lines.
     */
    private static HashObject[] readWordsFromFile(String filename, int n) {
        HashObject[] arr = new HashObject[n];
        int count = 0;
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine() && count < n) {
                String line = sc.nextLine().trim();
                // Skip blank lines
                if (line.isEmpty()) {
                    continue;
                }
                arr[count++] = new HashObject(line);
            }
            // If we run out of words, the rest remain null
        } catch (FileNotFoundException e) {
            System.err.println("word-list.txt not found. Using random data instead.");
            Random rand = new Random();
            for (int i = 0; i < n; i++) {
                arr[i] = new HashObject(rand.nextInt());
            }
        }
        return arr;
    }

    /**
     * Print summary for a hashtable: number inserted, duplicates, average probes.
     */
    private static void printSummary(String label, Hashtable table) {
        System.out.println("\t" + label);
        System.out.println("HashtableExperiment: size of hash table is " + table.tableSize);
        System.out.println("\tInserted " + table.getInsertCount() 
                + " elements, of which " + table.getDuplicateCount() + " were duplicates");
        System.out.printf("\tAvg. no. of probes = %.2f \n\n", table.getAverageProbes());
    }

    private static void usage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println("   <dataSource>: 1 ==> random numbers");
        System.out.println("                 2 ==> date values");
        System.out.println("                 3 ==> word list");
        System.out.println("   <loadFactor>: the ratio alpha = n/m");
        System.out.println("   <debugLevel>: 0 ==> print summary only");
        System.out.println("                 1 ==> summary + dump to file");
        System.out.println("                 2 ==> print debugging for each insert");
    }
}
