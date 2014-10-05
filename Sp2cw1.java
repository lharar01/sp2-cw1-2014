// Software and Programming 2, Coursework One.
// Submitted on 5.10.2014 by Liran Harary, username lharar01, student ID 12837230.

// This program reads integers into two arrays and then presents the user with a comparison of the arrays, which comprises the similar numbers in the arrays
// (if any) and the unique numbers in each array (if any).
 
import java.util.Scanner;

public class Sp2cw1 {
	private final static int ARRAY_SIZE = 100;
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {		
		int[] array1 = new int[ARRAY_SIZE];
		int[] array2 = new int[ARRAY_SIZE];
		// User instructions
		System.out.print("** This program reads whole numbers into two arrays and compares them. **\n\n");
		// Call method to read numbers to both arrays
		scanUniqueIntsToArray(array1, 1);
		System.out.print("\nWell done. Next:\n\n");
		scanUniqueIntsToArray(array2, 2);
		
		// Close the Scanner object as it is no longer needed. This is done to prevent a memory leak.
		input.close();
		
		// Call method to compare the two arrays
		compareIntArrays(array1, array2);
	}
	
	// The following method, named "scanUniqueIntsToArray", has 3 parameters:
	// - An integer array "arr" - to scan input into.
	// - An integer "arrNumber" - to display the array number to the user.
	// - A Scanner object "input" - to read input from the user.
	// It does not return a value.
	// The method prints instructions (and warnings if applicable) for the user and reads input from the keyboard. The input gets stored in the array, provided
	// that it does not already exist inside it.
	// This method assumes that all input is integers and does not exceed 100 distinct values.
	private static void scanUniqueIntsToArray(int[] arr, int arrNumber) {
		int i = -1;
		int inValue = 0;
		// Get input while inValue (input value) is different from 0. Only store distinct values in the array (check performed via the method "inIntArray").
		do {
			if(i >= 0 && inIntArray(arr, i+1, inValue)) {
				System.out.println("WARNING: Value discarded as it already exists in the array.");
			}
			else {
				if(i >= 0) {
					arr[i] = inValue;
				}
				i++;
			}
			System.out.printf("Enter numbers for array%d (or 0 to finish): ", arrNumber);
			if(input.hasNextInt()) {
				inValue = input.nextInt();
			}
		}
		while(inValue != 0);
	}
	
	// The following method, named "compareIntArrays", has 2 parameters, both integer arrays. It does not return a value.
	// It utilises the methods "lengthUntil0", "inIntArray" and "printIntArrayUntilNumber" to compute and display the similar and unique numbers in the arrays
	// received as arguments.
	// If both arrays start with a 0 (which in this program means that no numbers have been read from the user), the program will print an error message.
	private static void compareIntArrays(int[] arr1, int[] arr2) {
		// First check if both arrays are empty. If they are, print an error message. If not, proceed.
		if(arr1[0] == 0 && arr2[0] == 0) {
			System.out.print("\nBoth arrays are empty. Please try again.");
		}
		else
		{
			// Use the method "lengthUntil0" to calculate the length of the input in each of the arrays. 
			int arr1Length = lengthUntil0(arr1);
			int arr2Length = lengthUntil0(arr2);
			// "results" is a 2d array with 3 rows and a dynamic number of columns, which is determined by the sum of the lengths of both "arr1" and "arr2".
			// Row 0 is reserved for the similar number in the arrays; row 1, for the unique numbers of "arr1"; and row 2, for the unique numbers of "arr2". 
			int[][] results = new int[3][arr1Length + arr2Length];
			// The array "resultsCounter" has 3 elements, which keeps track of the amount of similar numbers, unique numbers of "arr1" and unique numbers of
			// "arr2", respectively.
			int[] resultsCounter = {0, 0, 0};
			
			// For each number in "arr1", use the method "inIntArray" to check if it exists in "arr2". If it does, store it in row 0 of the 2d array "results"
			// and increase element 0 of the array "resultsCounter" by 1. If it doesn't, store it in row 1 of the 2d array "results"
			// and increase element 1 of the array "resultsCounter" by 1.
			for(int i=0; i<arr1Length; i++) {
				if(inIntArray(arr2, arr2Length, arr1[i])) {
					results[0][resultsCounter[0]] = arr1[i];
					resultsCounter[0]++;
				}
				else {
					results[1][resultsCounter[1]] = arr1[i];
					resultsCounter[1]++;
				}
			}
			
			// For each number in "arr2", use the method "inIntArray" to check if it exists in "arr1". If it doesn't, store it in row 2 of the 2d array "results"
			// and increase element 2 of the array "resultsCounter" by 1.
			// Note that there is no need to check for similar numbers here, as these have gathered by the "for" loop above.
			for(int i=0; i<arr2Length; i++) {
				if(!inIntArray(arr1, arr1Length, arr2[i])) {
					results[2][resultsCounter[2]] = arr2[i];
					resultsCounter[2]++;
				}
			}
		
			// Utilise the method "printIntArrayUntilNumber" to print the values of each array. 
			System.out.printf("\nResults:\n\nFor the arrays Array1{");
			printIntArrayUntilNumber(arr1, arr1Length);
			System.out.printf("} and Array2{");
			printIntArrayUntilNumber(arr2, arr2Length);
			System.out.printf("}:\n\n");
			
			// For each row in "results", if the corresponding element in "resultsCounter" is bigger than 0, utilise the method "printIntArrayUntilNumber"
			// to print the array values. If not, print an appropriate message.
			if(resultsCounter[0] > 0)
			{
				System.out.printf("- There are %d similar numbers: ", resultsCounter[0]);
				printIntArrayUntilNumber(results[0], resultsCounter[0]);
			}
			else
			{
				System.out.print("- The are NO similar numbers.");
			}
			
			if(resultsCounter[1] > 0)
			{
				System.out.printf("\n- Array1 has %d unique numbers: ", resultsCounter[1]);
				printIntArrayUntilNumber(results[1], resultsCounter[1]);
			}
			else
			{
				System.out.print("\n- Array1 has NO unique numbers.");
			}
			
			if(resultsCounter[2] > 0)
			{
				System.out.printf("\n- Array2 has %d unique numbers: ", resultsCounter[2]);
				printIntArrayUntilNumber(results[2], resultsCounter[2]);
			}
			else
			{
				System.out.print("\n- Array2 has NO unique numbers.");
			}
		}
	}
	
	// The following method, named "lengthUntil0", has 1 parameter: an integer array. It returns as an integer the length of the array until an element
	// with a value of 0  is reached (not inclusive). If 0 is not found, the length of the whole array is returned.
	private static int lengthUntil0(int[] arr) {
		int i;
		for(i=0; i<arr.length; i++) {
			if(arr[i] == 0) {
				return i;
			}
		}
		return i;
	}
	
	// The following method, named "inIntArray", looks for a given number in a given array, within a given range (int length).
	// It has 3 parameters:
	// - An integer array - to look in.
	// - An integer "length" - which represents the length required to search within. 
	// - Another integer "needle" - which represents the number to search for in the array.
	// It returns a boolean: "true" if the number exists in the array, and false if it does not. 
	private static boolean inIntArray(int[] haystack, int length, int needle) {
		for(int i=0; i<length; i++) {
			if(haystack[i] == needle) {
				return true;
			}
		}
		return false;
	}
	
	// The following method, named "printIntArrayUntilNumber", takes an array "arr" and an integer length, and prints all array values in the specified length.
	// It does not return a value.
	private static void printIntArrayUntilNumber(int[] arr, int length) {
		for(int i=0; i<length; i++) {
			System.out.print(arr[i]);
			if(i != length-1) {
				System.out.print(", ");
			}
		}
	}
}