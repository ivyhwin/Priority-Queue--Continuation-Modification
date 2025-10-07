/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

/**
 * The main driver class demonstrating the enhanced priority queue functionality.
 * This class showcases:
 * - Strategy pattern with different prioritization approaches
 * - Iterator pattern for non-destructive traversal
 * - Command pattern for undo functionality
 * - Integration with Java Collections framework
 
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== CS635 Priority Queue Assignment Demo ===\n");

        // Demo 1: Default Strategy (Same as Assignment 1)
        System.out.println("1. DEFAULT STRATEGY DEMO (70% units, 30% GPA)");
        System.out.println("Using same test cases from Assignment 1");
        StudentPriorityQueue pq = new StudentPriorityQueue();

        // ---------- Add typical, realistic students (same as Assignment 1) ----------
        pq.offer(new Student("Ivy Huynh",      "R1234567", "ivy.huynh@university.edu",      3.7, 128));
        pq.offer(new Student("Alex Kim",       "R2000001", "alex.kim@university.edu",       3.2, 96));
        pq.offer(new Student("Jordan Lee",     "R2000002", "jordan.lee@university.edu",     2.9, 72));
        pq.offer(new Student("Priya Patel",    "R2000003", "priya.patel@university.edu",    3.95, 110));
        pq.offer(new Student("Sam Rivera",     "R2000004", "sam.rivera@university.edu",     3.4, 140));
        pq.offer(new Student("Casey Nguyen",   "R2000005", "casey.nguyen@university.edu",   3.8, 100));
        pq.offer(new Student("Taylor Brooks",  "R2000006", "taylor.brooks@university.edu",  3.1, 145));

        // ---------- Add boundary values to test edge cases (same as Assignment 1) ----------
        pq.offer(new Student("ZeroZero", "R100", "zero.zero@university.edu", 0.0, 0));     // worst possible score
        pq.offer(new Student("MaxGPA",   "R101", "max.gpa@university.edu",   4.0, 0));     // max GPA, min units
        pq.offer(new Student("MaxUnits", "R102", "max.units@university.edu", 0.0, 150));   // min GPA, max units
        pq.offer(new Student("MaxBoth",  "R103", "max.both@university.edu",  4.0, 150));   // max GPA, max units (should be top)

        // ---------- Add students to test tie-breaking rules (same as Assignment 1) ----------
        // These three have identical normalized scores (100/150 units & 3.0/4.0 GPA).
        // Order should be: Adam Xiong (name "Xiong" < "Young"), then Adam Young, then Bella Young.
        pq.offer(new Student("Adam Young",  "R010", "adam.young@university.edu", 3.0, 100));
        pq.offer(new Student("Bella Young", "R009", "bella.young@university.edu",3.0, 100));
        pq.offer(new Student("Adam Xiong",  "R008", "adam.xiong@university.edu", 3.0, 100));

        // ----- Demo Outputs (Enhanced from Assignment 1) -----
        System.out.println("Top (peek) — should be the max of everything (likely 'MaxBoth'):");
        System.out.println(pq.peek()); // Peek at the highest priority student non-destructively

        System.out.println("\nPriority order using new iterator (non-destructive):");
        pq.printPriorityOrder();

        // Demo 2: Collection Integration Features
        System.out.println("\n2. COLLECTION INTEGRATION DEMO");
        System.out.println("Queue size: " + pq.size());
        System.out.println("Queue as string: " + pq);
        System.out.println("Queue to array length: " + pq.toArray().length);

        // Demo 3: Command Pattern - Undo Functionality
        System.out.println("\n3. COMMAND PATTERN - UNDO FUNCTIONALITY");
        StudentPriorityQueue undoDemoQueue = new StudentPriorityQueue();
        UndoManager undoManager = new UndoManager();

        // Add students using command pattern
        undoManager.execute(new AddStudentCommand(undoDemoQueue,
                new Student("Ivy Huynh", "R1234567", "ivy.huynh@university.edu", 3.7, 128)));
        undoManager.execute(new AddStudentCommand(undoDemoQueue,
                new Student("Alex Kim", "R2000001", "alex.kim@university.edu", 3.2, 96)));

        System.out.println("After adding 2 students:");
        undoDemoQueue.printPriorityOrder();

        // Remove top student
        undoManager.execute(new RemoveTopCommand(undoDemoQueue));
        System.out.println("After removing top student:");
        undoDemoQueue.printPriorityOrder();

        // Undo the removal
        undoManager.undo();
        System.out.println("After undoing removal:");
        undoDemoQueue.printPriorityOrder();

        // Demo 4: Strategy Pattern - Alternate Ordering
        System.out.println("\n4. STRATEGY PATTERN - ALTERNATE ORDERING");
        StudentPriorityQueue gpaFirstQueue = new StudentPriorityQueue(new GPAFirstStrategy());


        gpaFirstQueue.offer(new Student("Ivy Huynh", "R1234567", "ivy.huynh@university.edu", 3.7, 128));
        gpaFirstQueue.offer(new Student("Alex Kim", "R2000001", "alex.kim@university.edu", 3.2, 96));
        gpaFirstQueue.offer(new Student("MaxBoth", "R103", "max.both@university.edu", 4.0, 150));
        gpaFirstQueue.offer(new Student("MaxGPA", "R101", "max.gpa@university.edu", 4.0, 0));

        System.out.println("GPA-First strategy results:");
        gpaFirstQueue.printPriorityOrder();
        System.out.println("Top with GPA-first: " + gpaFirstQueue.peek());

        // Demo 5: Destructive popping (same as Assignment 1 but using new method names)
        System.out.println("\n5. DESTRUCTIVE POPPING PROCESS (same as Assignment 1)");
        StudentPriorityQueue popDemo = new StudentPriorityQueue();

        // Add a subset of original students
        popDemo.offer(new Student("Ivy Huynh", "R1234567", "ivy.huynh@university.edu", 3.7, 128));
        popDemo.offer(new Student("Alex Kim", "R2000001", "alex.kim@university.edu", 3.2, 96));
        popDemo.offer(new Student("MaxBoth", "R103", "max.both@university.edu", 4.0, 150));

        System.out.println("Popping in priority order (destructive):");
        while (!popDemo.isEmpty()) {
            Student s = popDemo.poll(); // Remove and get the highest priority student
            // Print details along with their calculated score for verification
            System.out.printf("%s  -  %s  (score=%.5f)%n", s.getRedId(), s.getName(), s.priorityScore());
        }

        // Demo 6: Iterator demonstration
        System.out.println("\n6. ITERATOR PATTERN DEMONSTRATION");
        StudentPriorityQueue iteratorDemo = new StudentPriorityQueue();
        iteratorDemo.offer(new Student("Ivy Huynh", "R1234567", "ivy.huynh@university.edu", 3.7, 128));
        iteratorDemo.offer(new Student("Alex Kim", "R2000001", "alex.kim@university.edu", 3.2, 96));
        iteratorDemo.offer(new Student("MaxBoth", "R103", "max.both@university.edu", 4.0, 150));

        System.out.println("Iterating using enhanced for-loop (non-destructive):");
        for (Student student : iteratorDemo) {
            System.out.println("  - " + student.getRedId() + ": " + student.getName() +
                    " (Units: " + student.getUnits() + ", GPA: " + student.getGpa() +
                    ", Score: " + String.format("%.5f", student.priorityScore()) + ")");
        }

        // Verify queue is unchanged after iteration
        System.out.println("\nQueue size after iteration: " + iteratorDemo.size());
        System.out.println("Top student after iteration: " + iteratorDemo.peek());

        System.out.println("\n=== Demo Complete ===");
    }
}