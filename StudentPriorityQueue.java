/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

import java.util.*;

/**
 * A max-heap based priority queue for Student objects that integrates with Java Collections.
 * This implementation extends AbstractQueue and incorporates multiple design patterns:
 * - Strategy Pattern: For flexible ordering mechanisms
 * - Iterator Pattern: For non-destructive iteration
 * - Integration with Java Collections Framework
 *
 * The queue maintains O(log N) insertion and removal while providing comprehensive
 * collection functionality.
 */
public class StudentPriorityQueue extends AbstractQueue<Student> {

    /** Underlying max-heap storage using ArrayList for O(1) access and dynamic resizing */
    private final ArrayList<Student> heap = new ArrayList<>();

    /** Strategy pattern implementation for flexible ordering */
    private final PriorityStrategy strategy;

    /**
     * Default constructor using the default prioritization strategy.
     */
    public StudentPriorityQueue() {
        this(new DefaultStudentStrategy());
    }

    /**
     * Constructor with custom strategy for dynamic ordering behavior.
     * @param strategy The priority strategy to use for ordering students
     */
    public StudentPriorityQueue(PriorityStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy must not be null");
    }

    // ... [rest of the StudentPriorityQueue class remains the same] ...

    // ---- Core Heap Operations ----

    /** Returns the number of elements in the queue. */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Retrieves, but does not remove, the highest priority student.
     * Time Complexity: O(1)
     */
    @Override
    public Student peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    /**
     * Adds a student to the priority queue.
     * Time Complexity: O(log N)
     * @param student The student to add
     * @return true if the student was added successfully
     */
    @Override
    public boolean offer(Student student) {
        Objects.requireNonNull(student, "Student must not be null");
        heap.add(student);
        siftUp(heap.size() - 1);
        return true;
    }

    /**
     * Removes and returns the highest priority student.
     * Time Complexity: O(log N)
     * @return The highest priority student, or null if empty
     */
    @Override
    public Student poll() {
        if (heap.isEmpty()) return null;
        Student top = heap.get(0);
        Student last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return top;
    }

    // ---- Heap Helper Methods ----

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    /** Compares two elements for max-heap ordering using the current strategy */
    private boolean greater(int i, int j) {
        return strategy.compare(heap.get(i), heap.get(j)) > 0;
    }

    private void swap(int i, int j) {
        Student temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /** Restores heap property after insertion by moving element up */
    private void siftUp(int i) {
        while (i > 0) {
            int parent = parent(i);
            if (greater(i, parent)) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    /** Restores heap property after removal by moving element down */
    private void siftDown(int i) {
        int size = heap.size();
        while (true) {
            int left = left(i);
            int right = right(i);
            int largest = i;

            if (left < size && greater(left, largest)) largest = left;
            if (right < size && greater(right, largest)) largest = right;

            if (largest == i) break;

            swap(i, largest);
            i = largest;
        }
    }

    // ---- Collection Integration Methods ----

    /**
     * Returns an array containing all students in heap order.
     * @return Array of students in current heap order
     */
    @Override
    public Object[] toArray() {
        return heap.toArray();
    }

    /**
     * Returns a string representation of the queue in heap order.
     * @return String representation of the queue
     */
    @Override
    public String toString() {
        return heap.toString();
    }

    /**
     * Removes a specific student from the queue.
     * Time Complexity: O(N) for search + O(log N) for heap restoration
     * @param o The student to remove
     * @return true if the student was found and removed
     */
    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Student)) return false;

        int index = heap.indexOf(o);
        if (index == -1) return false;

        int lastIndex = heap.size() - 1;
        if (index == lastIndex) {
            heap.remove(lastIndex);
            return true;
        }

        // Swap with last element and remove
        swap(index, lastIndex);
        heap.remove(lastIndex);

        // Restore heap property
        if (index < heap.size()) {
            siftUp(index);
            siftDown(index);
        }
        return true;
    }

    // ---- Iterator Pattern Implementation ----

    /**
     * Returns an iterator that provides elements in descending priority order.
     * This implementation is NON-DESTRUCTIVE and does not modify the original queue.
     * The iterator works by maintaining a copy of the heap and performing heapsort.
     * @return Iterator over students in priority order
     */
    @Override
    public Iterator<Student> iterator() {
        return new PriorityOrderIterator();
    }

    /**
     * Internal iterator class that provides elements in descending priority order
     * without modifying the original queue. Uses a defensive copy and heapsort.
     */
    private class PriorityOrderIterator implements Iterator<Student> {
        private final ArrayList<Student> heapCopy;
        private int currentIndex;

        public PriorityOrderIterator() {
            // Create a defensive copy of the heap
            this.heapCopy = new ArrayList<>(heap);
            this.currentIndex = 0;

            // Sort the copy in descending priority order using heapsort
            if (!heapCopy.isEmpty()) {
                heapSort(heapCopy);
            }
        }

        /** Sorts the list in descending order using heapsort */
        private void heapSort(ArrayList<Student> list) {
            // Build max heap
            int n = list.size();
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(list, n, i);
            }

            // Extract elements from heap
            for (int i = n - 1; i > 0; i--) {
                // Move current root to end
                Student temp = list.get(0);
                list.set(0, list.get(i));
                list.set(i, temp);

                // Call heapify on the reduced heap
                heapify(list, i, 0);
            }
        }

        /** Maintains heap property for heapsort */
        private void heapify(ArrayList<Student> list, int n, int i) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && strategy.compare(list.get(left), list.get(largest)) > 0) {
                largest = left;
            }

            if (right < n && strategy.compare(list.get(right), list.get(largest)) > 0) {
                largest = right;
            }

            if (largest != i) {
                Student swap = list.get(i);
                list.set(i, list.get(largest));
                list.set(largest, swap);

                heapify(list, n, largest);
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < heapCopy.size();
        }

        @Override
        public Student next() {
            if (!hasNext()) throw new NoSuchElementException();
            return heapCopy.get(currentIndex++);
        }
    }

    // ---- Convenience Methods ----

    /**
     * Prints students in descending priority order to console.
     * This is a non-mutating operation that uses the iterator.
     */
    public void printPriorityOrder() {
        System.out.println("Priority Order (highest first):");
        int rank = 1;
        for (Student student : this) {
            System.out.printf("%2d. %s  -  %s%n", rank++, student.getRedId(), student.getName());
        }
    }
}