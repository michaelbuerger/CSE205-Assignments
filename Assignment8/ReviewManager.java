// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Manages a list of reviews, with methods for sorting, searching, removing, and resetting

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewManager implements Serializable {
    // The serialVersionUID is used to verify compatibility of senders and
    // receivers. See the document for more details:
    // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/Serializable.html
    private static final long serialVersionUID = 205L;

    private ArrayList<Restaurant> reviewList; // list of restaurant reviews

    // setup empty review list
    public ReviewManager() {
        reviewList = new ArrayList<>();
    }

    // returns -1 if restaurant does not exist, otherwise returns index of restaurant (in reviewList)
    public int restaurantExists(String restaurantName, String location) {
        for(int i=0; i < reviewList.size(); i++) {
            Restaurant review = reviewList.get(i);

            if(review.getRestaurantName().equals(restaurantName) && review.getLocation().equals(location))
                return i;
        }

        return -1;
    }

    // returns empty arraylist if no cuisines found, otherwise, returns list of indexes of cuisines with given name
    public ArrayList<Integer> cuisineExists(String cuisineName) {
        ArrayList<Integer> cuisineIndices = new ArrayList<Integer>();
        for(int i=0; i < reviewList.size(); i++) {
            Cuisine cuisine = reviewList.get(i).getCuisine();
            if(cuisine.getName().equals(cuisineName))
                cuisineIndices.add(i);
        }

        return cuisineIndices;
    }

    // get review/restaurant at given index
    public Restaurant getRestaurant(int reviewIndex) {
        return reviewList.get(reviewIndex);
    }

    /**
     * Add a Restaurant object to the reviewList and return true if such an object
     * is added successfully. Otherwise, return false. Two restaurants are
     * considered duplicated if they have exactly the same restaurant name and
     * cuisine name.
     * 
     * @param  restaurantName the name of the restaurant
     * @param  stars the number of stars for the restaurant
     * @param  review   the restaurant review
     * @param  priceRange    the integer price range
     * @param  cuisineName  the Cuisine's name
     * @param  location   the restaurant location (street address)
     * @param  signatureDish  most famous dish
     * @return            true if the operation is successful; false otherwise
     */
    public boolean addReview(String restaurantName, int stars, String review, String priceRange, String cuisineName, String location, String signatureDish) {
        if (restaurantExists(restaurantName, location) == -1) {
            int price = priceRange.length();
            Cuisine newCuisine = new Cuisine(signatureDish, cuisineName);
            Restaurant newRestaurant = new Restaurant(restaurantName, stars, review, price, location, newCuisine);
            reviewList.add(newRestaurant);
            return true;
        }
        return false;
    }

    // removes review/restaurant with given name and location
    // if restaurant was found, it is removed and return = true
    // if restaurant was NOT found, return = false
    public boolean removeReview(String restaurantName, String restaurantLocation) {
        int restaurantIndex = restaurantExists(restaurantName, restaurantLocation);

        if(restaurantIndex != -1) {
            reviewList.remove(restaurantIndex);
            return true;
        }

        return false;
    }

    // calls sorting method on review list with ReviewRatingComparator
    public void sortByRating() {
        Sorts.sort(reviewList, new ReviewRatingComparator());
    }

    // calls sorting method on review list with ReviewCuisineComparator
    public void sortByCuisine() {
        Sorts.sort(reviewList, new ReviewCuisineComparator());
    }

    // lists reviews in order they are found in list (calling above sort methods changes this)
    public String listReviews() {
        String ret = "";
        for(int i=0; i < reviewList.size(); i++)
            ret += reviewList.get(i).toString();

        return ret;
    }

    // clears review list
    public void closeReviewManager() {
        reviewList.clear();
    }
}
