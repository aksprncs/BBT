/*
 * Approach to the problem:
 * Let's say there are total N thread and there are total F files and total no of bytes 
 * in all the files is B. Since the problem states that no two threads can have 
 * byte difference of more than 1. 
 * 
 * So in this case let's say there are P threads which are given X+1 bytes and (N-P) threads are given X bytes.
 * We can denote the above statement as the following equation:
 * 
 *  (N-P) * (X) + P*(X+1) = B
 *  upon  simplifying  the above we get 
 *  N*X + P = B
 *  
 *  Now we just need to figure out P and X such that above equation satisfies.
 *  *----------------------------------*
 *  *Constraints on the above equation *
 *  *----------------------------------*
 *  0<=P<= N and X is integer as well as X>=0.
 *  So we can try for all values of P between 0 and N such that we get an integer X.
 * */

 
 
Steps to test the code:

1. Enter the total number of files say N.
2. Enter N integer values(one value per line) which is length/no of bytes in each file.
3. Enter the number of threads say T.

The output will show you thread distribution for T threads  in the format:

Thread Number:
File Name, start offest, end offset
 
 