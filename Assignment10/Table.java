// Assignment: 10
// Name: Michael Buerger
// StudentID: 1214351462
// Lecture: Tu Th 9:00 AM - 10:15 AM
// Description: Single node in Linked List.
//              Represents one table with a reservation name, number of guests, and pointer to the next reservation in line.

public class Table {
    String name;
    int guests;
    Table next;

    public Table(int n, String name) {
        this.name = name;
        guests = n;
        next = null;
    }

    public String toString() {
        return "\nReservation for " + name + ": party of "
                + guests + ".\n";
    }


    public static class EmptyTable extends Table {

        public EmptyTable() {
            super(-1, "");
        }
    }
}
