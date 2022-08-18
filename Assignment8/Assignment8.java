// Assignment: 8
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Handles user interface of review system, prints out menu of functions and excepts input to execute these functions

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Assignment8 {
    // main method
    public static void main(String[] args) {
        // Menu options
        char inputOpt = ' ';
        String inputLine;
        // Restaurant and Cuisine information
        String restaurantName, cuisineName;
        String review = null, location, signatureDish, priceRange;

        int rating;
        // Output information
        String outFilename, inFilename;
        String outMsg, inMsg;
        // Restaurant manager
        ReviewManager reviewManager = new ReviewManager();
        // Operation result
        boolean opResult;     
        
        try {
            printMenu();

            // setup readers for input
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader stdin = new BufferedReader(isr);

            do {
                System.out.print("\nWhat action would you like to perform?\n");
                inputLine = stdin.readLine().trim();
                if (inputLine.isEmpty()) { // checks for empty input (e.g. user presses enter)
                    System.out.println("Empty input");
                    continue;
                }
                // gets first character of input and sets it to upper case for std comparisons
                inputOpt = inputLine.charAt(0);
                inputOpt = Character.toUpperCase(inputOpt);

                switch (inputOpt) {
                    case 'A': // Add a new Restaurant Review
                        System.out.print("Please enter the restaurant information:\n");
                        System.out.print("Enter the restaurant name:\n");
                        restaurantName = stdin.readLine().trim();
                        System.out.print("Enter the review:\n");
                        review = stdin.readLine().trim();
                        System.out.print("Enter the price range:\n");
                        priceRange = stdin.readLine().trim();
                        System.out.print("Enter the rating:\n");
                        rating = Integer.parseInt(stdin.readLine().trim());
                        System.out.print("Enter the cuisine name:\n");
                        cuisineName = stdin.readLine().trim();
                        System.out.print("Enter the location:\n");
                        location = stdin.readLine().trim();
                        System.out.print("Enter the signature dish\n");
                        signatureDish = stdin.readLine().trim();
                        
                        // add review to review manager, print relevant message
                        if(reviewManager.addReview(restaurantName, rating, review, priceRange, cuisineName, location, signatureDish)) {
                            System.out.println("Restaurant added");
                        } else {
                            System.out.println("Restaurant NOT added");
                        }
                        break;

                    case 'D': // Search a Restaurant
                        System.out.print("Please enter the restaurant name to search:\n");
                        restaurantName = stdin.readLine().trim();
                        System.out.print("Please enter the restaurant's location':\n");
                        location = stdin.readLine().trim();
                        
                        // find index of restaurant based on name and location
                        int restIndex = reviewManager.restaurantExists(restaurantName, location);
                        if(restIndex == -1) // restaurant not found
                            System.out.println("Restaurant not found. Please try again");
                        else
                            System.out.println("Restaurant found. Here's the review:\n" + reviewManager.getRestaurant(restIndex).getReview());
                        break;
                    case 'E': // Search a cuisine
                        System.out.print("Please enter the cuisine name to search:\n");
                        cuisineName = stdin.readLine().trim();

                        // find cuisine(s) with given name 
                        ArrayList<Integer> cuisinesFound = reviewManager.cuisineExists(cuisineName);
                        int cuisinesFoundNum = cuisinesFound.size();

                        if(cuisinesFoundNum > 0) { // found any cuisines
                            System.out.println(cuisinesFoundNum + " Restaurants matching " + cuisineName + " cuisine were found:");
                            for(int i=0; i < cuisinesFoundNum; i++)
                                System.out.print(reviewManager.getRestaurant(cuisinesFound.get(i)));
                            System.out.println("");
                        } else // found no cuisines
                            System.out.println("Cuisine: " + cuisineName + " was NOT found");
                        break;
                    case 'L': // List restaurant's reviews
                        String reviews = reviewManager.listReviews();

                        if(reviews.isEmpty()) // if no reviews in manager
                            System.out.println("\n\nNo Reviews available\n\n");
                        else // if any reviews in manager
                            System.out.println("\n" + reviews);
                        break;
                        
                     /******************************************************************************************
                     * Complete the code by adding two cases:                                                  *
                     * case 'N': sorts the restaurant reviews by rating and prints "sorted by rating\n"        *
                     * case 'P': sorts the restaurant reviews by cuisine name and prints "sorted by cuisine\n" *
                     ******************************************************************************************/                        
                    case 'N':
                        // sort reviews on a rating-first basis
                        reviewManager.sortByRating();
                        System.out.println("sorted by rating");
                        break;

                    case 'P':
                        // sort reviews on a cuisine-name-first basis
                        reviewManager.sortByCuisine();
                        System.out.println("sorted by cuisine");
                        break;
                        
                    case 'Q': // Quit
                        break;

                    case 'R': // Remove a review
                        System.out.print("Please enter the restaurant name of the review to remove:\n");
                        restaurantName = stdin.readLine().trim();
                        System.out.print("Please enter the location to remove:\n");
                        location = stdin.readLine().trim();
                        
                        // remove review if exists, print relevant message
                        if(!reviewManager.removeReview(restaurantName, location))
                            System.out.println(restaurantName + ", " + location + " was NOT removed");
                        else
                            System.out.println(restaurantName + ", " + location + " was removed");
                        break;
                        
                    case 'T': // Close reviewList
                        // clears review list in manager
                        reviewManager.closeReviewManager();
                        System.out.print("Restaurant management system was reset\n");
                        break;

                    case 'U': // Write restaurant names and reviews to a text file
                        System.out.print("Please enter a file name that we will write to:\n");
                        outFilename = stdin.readLine().trim();
                        System.out.print("Please enter the name of the restaurant:\n");
                        restaurantName = stdin.readLine().trim();
                        System.out.println("Please enter a review to save locally:\n");
                        review = stdin.readLine().trim();
                        outMsg = restaurantName + "\n" + review + "\n";
                                       
                        try {
                            // create filewriter
                            FileWriter fw = new FileWriter(outFilename);
                            fw.write(outMsg); // write file
                            fw.close();
                        } catch (IOException e) {
                            System.out.println("Write string inside the file error");
                            break;
                        }
                        // no errors
                        System.out.println(outFilename + " is written");
                        break;

                    case 'V': // Read strings from a text file
                        System.out.print("Please enter a file name which we will read from:\n");
                        inFilename = stdin.readLine().trim();
                        
                        String contents = "";
                        try {
                            // create file reader and buffered reader
                            FileReader fr = new FileReader(inFilename);
                            BufferedReader br = new BufferedReader(fr);
                            
                            // read first line (can be empty)
                            String line = br.readLine();
                            while(line != null) { // if line not empty, append to contents then read another line, continue
                                contents += line + '\n';
                                line = br.readLine();
                            }

                            br.close();
                            fr.close();
                        } catch (FileNotFoundException e) {
                            System.out.println(inFilename + " was not found");
                            break;
                        } catch (IOException e) {
                            System.out.println("Read string from file error");
                            break;
                        }
                        // no errors
                        System.out.println(inFilename + " was read");
                        System.out.println("The contents of the file are:\n"+contents+'\n');
                        break;
 
                    case 'W': // Serialize ReviewManager to a data file
                        System.out.print("Please enter a file name to write:\n");
                        outFilename = stdin.readLine().trim();
                                        
                        try {
                            // create file output stream and object output stream
                            FileOutputStream fos = new FileOutputStream(outFilename);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);

                            oos.writeObject(reviewManager); // write object to dat file, serialize
                            oos.close();
                            fos.close();
                        } catch (NotSerializableException e) {
                            System.out.println("Not serializable exception");
                            break;
                        } catch (IOException e) {
                            System.out.println("Data file written exception");
                            break;
                        }
                        // no errors
                        break;

                    case 'X': // Deserialize ReviewManager from a data file
                        System.out.print("Please enter a file name which we will read from:\n");
                        inFilename = stdin.readLine().trim();

                        try {
                            // create file input stream and object input stream
                            FileInputStream fis = new FileInputStream(inFilename);
                            ObjectInputStream ois = new ObjectInputStream(fis);

                            // read object from dat file, deserialize
                            reviewManager = (ReviewManager)ois.readObject();

                            ois.close();
                            fis.close();
                        } catch (ClassNotFoundException e) {
                            System.out.println("Class not found exception");
                            break;
                        } catch (NotSerializableException e) {
                            System.out.println("Not serializable exception");
                            break;
                        } catch (IOException e) {
                            System.out.println("Data file read exception");
                            break;
                        }
                        // no errors
                        System.out.println(inFilename + " was read");
                        break;

                    case '?': // Display help
                        printMenu();
                        break;

                    default:
                        System.out.print("Unknown action\n");
                        break;
                }

            } while (inputOpt != 'Q' || inputLine.length() != 1);
        }
        catch (IOException exception) {
            System.out.print("IO Exception\n");
        }
    }

    // print formatted menu of functions
    public static void printMenu() {
        System.out.println("Welcome to Kelp! ");
        System.out.println("Find or post reviews for your favorite (and not so favorite) restaurants.");

        System.out.print("Choice\t\tAction\n" + "------\t\t------\n" + "A\t\tAdd a review\n"
                + "D\t\tSearch for a restaurant\n" + "E\t\tSearch for a cuisine\n"
                + "L\t\tList all reviews\n" + "N\t\tSort by stars\n" + "P\t\tSort by cuisine\n"
                + "Q\t\tQuit\n" + "R\t\tRemove a review\n"
                + "U\t\tAdd personal review to a local file\n" + "V\t\tRetrieve personal review from a local file\n"
                + "W\t\tSave reviews to a file\n"
                + "X\t\tUpload reviews from a file\n"
                + "T\t\t(admin) reset database\n"
                + "?\t\tDisplay Help\n");
    }
}
