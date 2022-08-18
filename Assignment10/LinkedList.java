// Assignment: 10
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Linked List class for Table class. Allows addition of, searching, displaying, and removing of tables
//              from Linked List data structure.

public class LinkedList {
    Table first;
    int size;

    // constructor
    public LinkedList() {
        first = null;
        size = 0;
    }

    // adds a Table to end of list
    public void add(int numberOfGuests, String name) {
        Table newGuest = new Table(numberOfGuests, name);
        if(first == null){
            first = newGuest;
        }
        else{
            Table pointer = first;
            while(pointer.next != null)
                pointer = pointer.next;

            pointer.next = newGuest;
        }
        size++;
    }

    // removes the first element
    public Table removeFirst() {
        // linked list empty
        if(first == null) {
            size = 0;
            return new Table.EmptyTable();
        }
        
        Table ret = first;
        first = first.next; // move head one forward
        size--;

        return ret;
    }

    // removes the last element
    public Table removeLast() {
        // linked list empty
        if(first == null) {
            size = 0;
            return new Table.EmptyTable();
        }

        // only one element in list
        if(first != null && first.next == null) {
            Table ret = first;
            size = 0;
            first = null;
            return ret;
        }

        // if this point reached, list has min. two elements

        Table slowPtr = null;
        Table fastPtr = first;

        while(fastPtr.next != null) { // traverse
            slowPtr = fastPtr;
            fastPtr = fastPtr.next;
        } // loop broke, found end

        // "break off" end by deleting reference to it
        slowPtr.next = null;
        size--;

        return fastPtr;
    }

    // removes any element after the first (including the last)
    public Table removeMiddle(String name) {
        Table ret = null;
        // linked list empty
        if(first == null) {
            size = 0;
            first = null;
            return new Table.EmptyTable();
        }

        // only one element in list
        if(first != null && first.next == null) {
            if(first.name.equals(name)) {
                ret = first;
                size = 0;
                first = null;
                return ret;
            } else {
                return new Table.EmptyTable();
            }
        }

        // two elements minimum by this point
        ret = new Table.EmptyTable();

        Table slowPtr = null;
        Table fastPtr = first;

        while(fastPtr.next != null) { // traverse
            slowPtr = fastPtr;
            fastPtr = fastPtr.next;

            if(fastPtr.name.equals(name)) {
                // either connects slowPtr to node after fastPtr
                // OR connects slowPtr to null (if fastPtr is end, .next = null)
                slowPtr.next = fastPtr.next; // cut off fastPtr
                ret = fastPtr;
                size--;
                break;
            }
        } // loop broke, found or reached end

        return ret;
    }

    public Table removeGuest(String name) {
        if(first == null)
            return new Table.EmptyTable(); // empty list
        
        if(first.name.equals(name))
            return removeFirst(); // first element

        return removeMiddle(name); // potentially any other element
    }

    public int getPosition(String name) {
        int pos=0;

        if(first == null)
            return -1; // list empty
        
        Table ptr = first;

        while(ptr != null) {
            if(ptr.name.equals(name))
                return pos; // found table
            ptr = ptr.next; // traverse
            pos++; // increment position
        } // loop broken, reached end

        return -1;
    }

    public int getNumberOfParties(int numGuests) {
        int numParties = 0;

        if(first == null)
            return 0; // empty list

        Table ptr = first;

        while(ptr != null) {
            if(ptr.guests == numGuests)
                numParties++; // found table with criteria
            ptr = ptr.next; // traverse
        } // loop broken, reached end

        return numParties; // returns numParties
    }

    public String listReservations() {
        if(size == 0)
            return "No reservations in queue at this time.\n";

        String ret = "";
        Table ptr = first;

        while(ptr != null) { // traverse and append
            ret += ptr.toString();
            ptr = ptr.next;
        }

        ret += "\n";
        ret += "Total reservations: " + size + ".\n";

        return ret;
    }
}
