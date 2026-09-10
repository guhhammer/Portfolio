package circularlist;

/** A circular doubly linked list that keeps a pointer to its last node. */
public class CircularList {

    public class Node {
        Node next, previous;
        int data;
        public Node(int n) { this.data = n; }
    }

    private Node last = null;

    boolean isEmpty() { return last == null; }

    void insertFirst(int n) {
        Node node = new Node(n);
        if (isEmpty()) {
            last = node;
            last.next = last;
            last.previous = last;
        } else {
            node.next = last.next;
            node.previous = last;
            last.next = node;
        }
    }

    void print() {
        Node node = last.next;
        while (node != last) {
            System.out.println(node.data);
            node = node.next;
        }
        System.out.println(node.data);
    }

    public static void main(String[] args) {
        CircularList x = new CircularList();
        x.insertFirst(5);
        x.insertFirst(6);
        x.insertFirst(8);
        x.insertFirst(10);
        x.print();
    }
}
