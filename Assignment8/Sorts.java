// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Sorting methods with generic comparators, uses selection sort

import java.util.ArrayList;
import java.util.Comparator;

public class Sorts {
    // sort reviewList with given comparator using selection sort
    public static void sort(ArrayList<Restaurant> reviewList, Comparator<Restaurant> xComparator) {
        int start = 0;
        int size = reviewList.size();

        // traverse reviewList
        while(start < size) {
            int minIndex = start; // start with baseline minimum at most left-hand element of right-hand list
            for(int i=start; i < size; i++) {
                if(xComparator.compare(reviewList.get(i), reviewList.get(minIndex)) < 0) {
                    minIndex = i;
                }
            }
            // at this point, minIndex --> minimum element in right-hand list
            // swaps left most element of right-hand list with min element
            swapReviews(reviewList, start, minIndex);

            // pushes start of right-hand list to the right by one
            start++;
        }
    }

    // swaps two elements in reviewList given their indices
    private static void swapReviews(ArrayList<Restaurant> reviewList, int index1, int index2) {
        Restaurant r1 = reviewList.get(index1);
        reviewList.set(index1, reviewList.get(index2));
        reviewList.set(index2, r1);
    }
}