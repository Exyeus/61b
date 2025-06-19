/** Array based list.
 *  @author Josh Hug
 */

public class AList {
    /** Creates an empty list. */
    public AList() {
        data = new int[100]{0};
        int size = 0;
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        data[size] = x;
        size += 1
    }

    /** Returns the item from the back of the list. */
    public int getLast(){
        if (size > 0) {
            return data[size - 1];
        }
        else{
            return 0;
        }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return data[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public int removeLast() {
        output = data[size-1];
        data[size-1]=0;
        return output;
    }

} 