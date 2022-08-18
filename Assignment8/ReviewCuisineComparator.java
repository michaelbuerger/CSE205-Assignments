// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Compares restaurants/reviews with respect to cuisine type first

import java.util.Comparator;

// returns < 0 if first comes before second
// returns 0 if same
// returns > 0 if seconds comes before first
public class ReviewCuisineComparator implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant first, Restaurant second) {
        // check if same pointer to save time on all the below checks
        if(first == second)
            return 0;
        
        Cuisine cuisine1 = first.getCuisine(), cuisine2 = second.getCuisine();

        /* cuisine name alphabetical */
        if(!cuisine1.getName().equals(cuisine2.getName()))
            return -strComesFirstAlpha(cuisine1.getName(), cuisine2.getName());
            
        int priceRange1 = first.getPriceRange();
        int priceRange2 = second.getPriceRange();

        // price range check
        if(priceRange1 != priceRange2)
            return priceRange1 - priceRange2;
        
        /* rest. name alphabetical */
        if(!first.getRestaurantName().equals(second.getRestaurantName()))
            return -strComesFirstAlpha(first.getRestaurantName(), second.getRestaurantName());

        /* location alphabetical */
        if(!first.getLocation().equals(second.getLocation()))
            return -strComesFirstAlpha(first.getLocation(), second.getLocation());

        /* review alphabetical */
        if(!first.getReview().equals(second.getReview()))
            return -strComesFirstAlpha(first.getReview(), second.getReview());

        return 0; // exactly equal
    }

    // returns 1 if str1 comes alphabetically first
    // returns 0 if str1 == str2
    // returns -1 if str2 comes alphabetically first
    private static int strComesFirstAlpha(String str1, String str2) {
        int index1 = 0, index2 = 0;
        String lowerStr1, lowerStr2;

        lowerStr1 = str1.toLowerCase();
        lowerStr2 = str2.toLowerCase();

        while(index1 < lowerStr1.length() && index2 < lowerStr2.length()) {
            if((int)lowerStr1.charAt(index1) < (int)lowerStr2.charAt(index2))
                return 1;

            if((int)lowerStr1.charAt(index1) > (int)lowerStr2.charAt(index2))
                return -1;
            
            index1++;
            index2++;
        }

        return 0;
    }
}