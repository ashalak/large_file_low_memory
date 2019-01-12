## Problem description is:

Given with a line separated text file of integers ranging anywhere from Integer.MIN to Integer.MAX of size 1024MB,
the program should be able to produce line separated text file which has the sorted content of the input file.

## Following preconditions:
 * the program should be able to run with a memory constraint of 100MB,i.e. the -Xmx100m.
 * the file can have duplicate integers.
 * the text in the file has only integers which are line separated and no other characters.

## Run

By using "mvn test" from project command line.

## Information

Memory constraint of 100MB sets in .mvn/jvm.config.

## Author
Alexey Shalak