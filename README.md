# Project 3 - Experiments with Hashing

**Author:** Rhys Brooks
**Class:** CS321 Section 2
**Semester:** Fall 2023  

3 late passes used (2 left)
---

## Overview

This project implements a hash table with open addressing to experiment with the effects of load factor on the average number of probes required using two different collision resolution methods: linear probing and double hashing. The program inserts `HashObject` objects into a hash table until a specified load factor is reached. The table capacity is determined by a twin prime number generator, and duplicate keys update a frequency count rather than being inserted again. Different data sources (random integers, date values, and word lists) are used to compare the performance of the two probing methods.

---

## Reflection

Working on this project provided me with valuable insights into how different collision resolution strategies affect the performance of hash tables. I found it particularly interesting to see how small changes in the probing sequence or table capacity could significantly impact the number of probes required for insertion. One challenge I encountered was ensuring that the output dump files matched the expected format exactly, which involved careful adjustments in both the hash function implementations and the file output formatting.

Another aspect I enjoyed was the opportunity to run experiments with large datasets and analyze the performance under various load factors. Debugging the insertion process with multiple debug levels helped me understand the inner workings of open addressing. Although reconciling the differences between my output and the expected results took some time, the iterative testing process ultimately deepened my understanding of hashing and open addressing in Java.

---

## Compiling and Using

To compile the project, navigate to the project directory and run:


```bash
javac *.java
```
To Run the Project navigate to the correct directory and use the command

```bash
java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]

```

The project also includes a test script (run-tests.sh) to compare the output dump files with the expected files provided in the test-cases folder. Additionally, the project was executed on AWS, and a screenshot of the experiment running in the cloud is provided as AWS-Screenshot.png.



**Output Table**

## Date-Long Results

| Load Factor | Inserted (LP) | Duplicates (LP) | Avg Probes (LP) | Inserted (DH) | Duplicates (DH) | Avg Probes (DH) |
|-------------|---------------|-----------------|-----------------|---------------|-----------------|-----------------|
| 0.50        | 47,896        | 0               | 1.28            | 47,896        | 0               | 1.30            |
| 0.60        | 57,475        | 0               | 1.44            | 57,475        | 0               | 2.11            |
| 0.70        | 67,054        | 0               | 1.60            | 67,054        | 0               | 1.79            |
| 0.80        | 76,633        | 0               | 1.82            | 76,633        | 0               | 2.43            |
| 0.90        | 86,212        | 0               | 2.18            | 86,212        | 0               | 2.86            |
| 0.95        | 91,002        | 0               | 2.70            | 91,002        | 0               | 6.17            |
| 0.99        | 94,834        | 0               | 5.41            | 94,834        | 0               | 4.83            |

---

## Random-Integer Results

| Load Factor | Inserted (LP) | Duplicates (LP) | Avg Probes (LP) | Inserted (DH) | Duplicates (DH) | Avg Probes (DH) |
|-------------|---------------|-----------------|-----------------|---------------|-----------------|-----------------|
| 0.50        | 47,896        | 0               | 1.50            | 47,896        | 0               | 1.44            |
| 0.60        | 57,473        | 2               | 1.75            | 57,473        | 2               | 1.61            |
| 0.70        | 67,052        | 2               | 2.16            | 67,052        | 2               | 1.85            |
| 0.80        | 76,633        | 0               | 3.01            | 76,633        | 0               | 2.23            |
| 0.90        | 86,211        | 1               | 5.29            | 86,211        | 1               | 2.96            |
| 0.95        | 91,001        | 1               | 10.48           | 91,001        | 1               | 3.67            |
| 0.99        | 94,834        | 0               | 38.20           | 94,834        | 0               | 5.52            |

---

## Word-List Results

| Load Factor | Inserted (LP) | Duplicates (LP) | Avg Probes (LP) | Inserted (DH) | Duplicates (DH) | Avg Probes (DH) |
|-------------|---------------|-----------------|-----------------|---------------|-----------------|-----------------|
| 0.50        | 5,352         | 42,544          | 1.03            | 5,352         | 42,544          | 1.03            |
| 0.60        | 6,130         | 51,345          | 1.03            | 6,130         | 51,345          | 1.03            |
| 0.70        | 6,768         | 60,286          | 1.04            | 6,768         | 60,286          | 1.03            |
| 0.80        | 7,430         | 69,203          | 1.04            | 7,430         | 69,203          | 1.04            |
| 0.90        | 8,044         | 78,168          | 1.05            | 8,044         | 78,168          | 1.04            |
| 0.95        | 8,387         | 82,615          | 1.05            | 8,387         | 82,615          | 1.05            |
| 0.99        | 8,576         | 86,258          | 1.05            | 8,576         | 86,258          | 1.05            |
