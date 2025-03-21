#!/bin/sh

echo
echo "Compiling the source code"
echo
javac *.java

if [ ! -f HashtableExperiment.class ]; then
    echo
    echo "HashtableExperiment.class not found! Not able to test!!"
    echo
    exit 1
fi

echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Running tests for word-list for multiple load factors"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo

# Convert test-cases files to Unix line endings (if present)
if [ -d test-cases ]; then
    dos2unix test-cases/* > /dev/null 2>&1
fi

debugLevel=1

# List of load factors to test
loads="0.5 0.6 0.7 0.8 0.9 0.95 0.99"

for load in $loads
do
    echo "--------------------------------------------------------------"
    echo "Running: java HashtableExperiment 3 $load $debugLevel"
    echo "--------------------------------------------------------------"

    # Run the experiment (data source 3 = word list) with the specified load factor and debug level 1
    # Redirect the console output to /dev/null because we are interested only in the dump files.
    java HashtableExperiment 3 $load $debugLevel > /dev/null

    # Convert newly generated dump files to Unix line endings
    dos2unix linear-dump.txt double-dump.txt > /dev/null 2>&1

    # Compare the linear probing dump with the expected dump file.
    diff -w -B linear-dump.txt test-cases/word-list-$load-linear-dump.txt > diff-linear-$load.out
    if [ $? -eq 0 ]; then
        echo "Test PASSED for linear probing (load=$load)"
        rm -f diff-linear-$load.out
    else
        echo "==> Test FAILED for linear probing (load=$load)!!"
        echo "       Check diff-linear-$load.out for differences"
    fi

    # Compare the double hashing dump with the expected dump file.
    diff -w -B double-dump.txt test-cases/word-list-$load-double-dump.txt > diff-double-$load.out
    if [ $? -eq 0 ]; then
        echo "Test PASSED for double hashing (load=$load)"
        rm -f diff-double-$load.out
    else
        echo "==> Test FAILED for double hashing (load=$load)!!"
        echo "       Check diff-double-$load.out for differences"
    fi

    echo
done
