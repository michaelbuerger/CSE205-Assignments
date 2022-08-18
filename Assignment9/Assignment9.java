// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Prints a menu and processes relevant input to perform 4 tasks (along with the ability to quit)
//              Utilizing recursion:
//              Find largest num in array of integers,
//              Calc. product of prime nums in array of integers,
//              Find element with largest sum of digits in array of integers,
//              Remove adjacent duplicate characters in a String.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Assignment9 {

    public static void main(String[] args) {
        // Menu options
        char inputOpt = ' ';
        String inputLine;

		try {
            // setup readers for input
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader stdin = new BufferedReader(isr);

            do {
                printMenu();

                inputLine = stdin.readLine().trim();
                inputOpt = inputLine.charAt(0);

                switch(inputOpt) {
                    case '1':
                        System.out.println("The largest number in the array is: " + largestNumRecursive(parseInts(stdin), 0, 0));
                        break;
                    case '2':
                        System.out.println("The product of all prime numbers in the array is: " + productOfPrimes(parseInts(stdin), 0));
                        break;
                    case '3':
                        System.out.println("The largest sum of digits in the array is: " + largestSumOfDigits(parseInts(stdin), 0, 0));
                        break;
                    case '4':
                        System.out.println("Please enter String:");
                        System.out.println("String after adjacent duplicate characters were removed: " + removeConcurrentChars(stdin.readLine().trim(), "", ' ', 0));
                        break;
                    case '5':
                        break;
                    default:
                        System.out.println("Please choose a number between 1 and 5.");
                        break;
                }
            } while (inputOpt != '5');
        } catch (IOException e) {
            System.out.print("IO Exception\n");
        }
    }

    /* TASK METHODS */

    // task 1: find largest num in array recursively
    // when calling, start max at 0, index at 0
    public static int largestNumRecursive(int[] arr, int max, int index) {
        if(index >= arr.length)
            return max; // reached end of array, max is ACTUAL maximum here
        
        int newMax = max; // initial state of newMax is current (max), in the case that current index is not greater
        if(arr[index] > max) // compare current index to current max
            newMax = arr[index]; // if current index is greater, set newMax to value at this index
        
        // "next iteration": with same array, newMax (which may be same as before), next index
        return largestNumRecursive(arr, newMax, index+1);
    }

    // task 2: find product of prime numbers in array recursively
    // when calling, start max at 0
    public static int productOfPrimes(int[] arr, int index) {
        if(index >= arr.length)
            return 1; // reached end, just multiply by 1 (give a root for the recursive tree of multiplication)
        
        int mult = 1; // base multiplication, if num is not prime, this will basically not affect the overall result
        if(isPrime(arr[index], arr[index]-1)) // check if num is prime
            mult = arr[index];
        
        return mult * productOfPrimes(arr, index+1);
    }

    // task 3: find largest sum of digits in array of integers recursively 
    // initially, maxSum and index should be set to 0
    public static int largestSumOfDigits(int[] arr, int maxSum, int index) {
        if(index >= arr.length)
            return maxSum; // reached end, max known

        return largestSumOfDigits(arr, Math.max(maxSum, sumOfDigits(arr[index])), index + 1); // set new maxSum respectively, increment index
    }

    // task 4: remove concurrent chars in string recrusively
    // Initially: newStr --> "", lastChar = ' ', index = 0
    public static String removeConcurrentChars(String str, String newStr, char lastChar, int index) {
        if(index >= str.length())
            return newStr;
        
        char currentChar = str.charAt(index);
        String toAppend = "";

        if(currentChar != lastChar)
            toAppend += currentChar;

        return removeConcurrentChars(str, newStr + toAppend, currentChar, index + 1);
    }

    /* HELPER FUNCTIONS */

    // find sum of digits in integer 
    public static int sumOfDigits(int num) {
        // Example Process:
        // 157 % 10 == (7) <-- remainder
        // 157 - 7 = 150
        // 150 / 10 == 15

        // 15 % 10 == (5) <-- remainder
        // 15 - 5 == 10 <-- just gets added

        if(num == 10)
            return 1; // num is two digits, but we know it will sum to 1 + 0 == 1
        
        if(num < 10)
            return num; // num is single digit

        int remainder = (int) (num % 10); // digit to add
        return remainder + sumOfDigits((num - remainder) / 10);
    }

    // checks if num is prime by checking remainder of division from # - 1 down to 2, if any divisions return 0 then num is not prime
    // call method with initial = # - 1, num = # - 1 
    public static boolean isPrime(int initial, int num) {
        if(initial <= 1) // 1 is not a prime number
            return false;

        if(num <= 1) // reached 1 without getting an even division, # is prime
            return true;

        if(initial % num == 0) // found even division, # is not prime
            return false;

        return isPrime(initial, num-1); // didn't find even division yet and may still find one, keep checking
    }

    // Utility method for printing the menu 
    public static void printMenu() {
        System.out.print("\nWhat would you like to do?\n\n");
        System.out.print("1: Find the largest number in an array of integers\n");
        System.out.print("2: Calculate the product of all prime numbers in an array of integers\n");
        System.out.print("3: Find the element with the largest sum of digits in an array of integers\n");
        System.out.print("4: Remove adjacent duplicate characters in a String\n");
        System.out.print("5: Quit\n\n");
    }

    // utility method for parsing integers from standard input
    public static int[] parseInts(BufferedReader reader) {
        String line = "";
        ArrayList<Integer> container = new ArrayList<>();
        try {
            System.out.print("Please enter integers:\n");
            line = reader.readLine();
            int num = Integer.parseInt(line);

            while (num > 0) {
                container.add(num);
                line = reader.readLine();
                num = Integer.parseInt(line);
            }

        } catch (IOException ex) {
            System.out.println("IO Exception");
        }

        int[] result = new int[container.size()];
         for(int i = 0; i < container.size(); i++){
             result[i] = container.get(i);
         }
        return result;
    }
}
