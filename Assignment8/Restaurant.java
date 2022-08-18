// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Represents a restaurant/review

import java.io.Serializable;

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 205L;
    private String restaurantName;
    private int stars;
    private String review;
    private int priceRange;
    private String location;
    private Cuisine cuisine;

    // constructor
    public Restaurant(String restaurantName, int stars, String review, int priceRange, String location, Cuisine cuisine) {
        this.restaurantName = restaurantName;
        this.stars = stars;
        this.review = review;
        this.priceRange = priceRange;
        this.location = location;
        this.cuisine = cuisine;
    }

    // restaurant name getter
    public String getRestaurantName() {
        return restaurantName;
    }

    // rating getter
    public int getStars() {
        return stars;
    }

    // price range getter
    public int getPriceRange() {
        return priceRange;
    }

    // location getter
    public String getLocation() {
        return location;
    }

    // review getter
    public String getReview() {
        return review;
    }

    // cuisine getter
    public Cuisine getCuisine() {
        return cuisine;
    }

    // to string override
    @Override
    public String toString() {
        return restaurantName + " restaurant\n" + repeatChar('*', stars) + "\t\t" + repeatChar('$', priceRange) + "\n" +  cuisine.toString() + "Location: " + location + "\n" + "Review:\t" + review + "\n\n";
    }

    // helper method, returns char c repeated n times as String
    // for example, repeatChar('*', 4) --> ****
    private String repeatChar(char c, int n) {
        if(n <= 0)
            return "";
        
        String str = c + "";

        for(int i=1; i <= n-1; i++) {
            str += c + "";
        }

        return str;
    }
}