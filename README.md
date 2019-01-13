## Problem description is:

Given with inputDataFile a line separated text file of integers ranging anywhere from Integer.MIN to Integer.MAX of size 1024MB,
the program should be able to produce line separated text file which has the sorted content of the input file.

## Following preconditions:
 * the program should be able to run with a memory constraint of 100MB, i.e. the -Xmx100m.
 * the file can have duplicate integers.
 * the text in the file has only integers which are line separated and no other characters.

## Solution
* Step 1. Split the large file into valid for memory temp files.
* Step 2. Sort temp file's data.
* Step 3. Merge sorted temp files into a sorted outputDataFile.
* Step 4. Delete temp files.

## Run

For run only tests: use "mvn clean test" in command line from project root directory.
For run program: use "mvn clean package exec:java" in command line from project root directory.

## Information

Memory constraint of 100MB sets in .mvn/jvm.config, for tests in maven-surefire-plugin settings.
Both input_data_file.txt and output_data_file.txt files are created in project root directory.

## Author
Alexey Shalak 