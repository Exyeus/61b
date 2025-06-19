public class SLList {
    public class IntNode {

        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;

    public SLList(int x) {
        // The constructor method!
        first = new IntNode(x, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        /* Your Code Here! */
        /** Take reference type into account? */
        /*
        * IntNode p = first;
        * while (p.next != null){
        *      p = p.next;
        * }
        * p.next = new IntNode(x, null);
        * */
        IntNode firstCopy = first;
        if (first.next == null){
            first.next = IntNode(x, null);
        }
        else{
            /*first.next.addLast(x); This make the compiler unable to
            * locate the symbol. Perhaps `addLast` is a method on the level of
            * SLList, while IntNode type serves only as data form.
            * */
            first = first.next;
        }
    }

    /** Returns the number of items in the list using recursion. */
    public int size() {
        /* Your Code Here! */
        if (first.next == null){
            return 1;
        }
        else{
            return (1 + first.next.size());
        }
    }

}