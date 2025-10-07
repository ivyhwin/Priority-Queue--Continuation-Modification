/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Simple test runner without JUnit dependency
 */
public class StudentPriorityQueueTest {

    private static int testCount = 0;
    private static int passCount = 0;

    // Helper method to create test students
    private Student createStudent(int units, double gpa, String id) {
        return new Student("Name" + id, id, id + "@university.edu", gpa, units);
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        testCount++;
        if (expected == null ? actual == null : expected.equals(actual)) {
            passCount++;
            System.out.println("✓ PASS: " + message);
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        testCount++;
        if (condition) {
            passCount++;
            System.out.println("✓ PASS: " + message);
        } else {
            System.out.println("✗ FAIL: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        testCount++;
        if (!condition) {
            passCount++;
            System.out.println("✓ PASS: " + message);
        } else {
            System.out.println("✗ FAIL: " + message);
        }
    }

    private static void assertNull(Object object, String message) {
        testCount++;
        if (object == null) {
            passCount++;
            System.out.println("✓ PASS: " + message);
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected: null");
            System.out.println("  Actual: " + object);
        }
    }

    private static void assertNotNull(Object object, String message) {
        testCount++;
        if (object != null) {
            passCount++;
            System.out.println("✓ PASS: " + message);
        } else {
            System.out.println("✗ FAIL: " + message);
            System.out.println("  Expected: not null");
            System.out.println("  Actual: null");
        }
    }

    public void testDefaultStrategyOrdering() {
        System.out.println("\n=== Testing Default Strategy Ordering ===");
        Student highUnits = createStudent(140, 3.6, "R001");
        Student highGPA = createStudent(100, 3.9, "R002");
        Student lowBoth = createStudent(80, 3.2, "R003");

        StudentPriorityQueue pq = new StudentPriorityQueue();
        pq.offer(highGPA);
        pq.offer(highUnits);
        pq.offer(lowBoth);

        assertEquals(highUnits, pq.peek(), "High units student should be at top");
        assertEquals(highUnits, pq.poll(), "Poll should return high units student");
        assertEquals(highGPA, pq.poll(), "Poll should return high GPA student");
        assertEquals(lowBoth, pq.poll(), "Poll should return low both student");
        assertNull(pq.poll(), "Poll should return null when empty");
    }

    public void testGPAFirstStrategy() {
        System.out.println("\n=== Testing GPA First Strategy ===");
        Student highUnits = createStudent(140, 3.6, "R001");
        Student highGPA = createStudent(100, 3.9, "R002");

        StudentPriorityQueue pq = new StudentPriorityQueue(new GPAFirstStrategy());
        pq.offer(highUnits);
        pq.offer(highGPA);

        assertEquals(highGPA, pq.peek(), "GPA-first strategy should prioritize high GPA");
    }

    public void testIteratorNonDestructive() {
        System.out.println("\n=== Testing Iterator Non-Destructive ===");
        Student a = createStudent(120, 3.8, "R001");
        Student b = createStudent(140, 3.6, "R002");
        Student c = createStudent(100, 3.9, "R003");

        StudentPriorityQueue pq = new StudentPriorityQueue();
        pq.offer(a);
        pq.offer(b);
        pq.offer(c);

        List<Student> iterated = new ArrayList<>();
        for (Student s : pq) {
            iterated.add(s);
        }

        assertEquals(3, pq.size(), "Queue size should remain unchanged after iteration");
        assertNotNull(pq.peek(), "Queue should not be empty after iteration");
        assertEquals(b, iterated.get(0), "Iterator should return highest priority first");
        assertEquals(3, iterated.size(), "Iterator should return all elements");
    }

    public void testToArrayAndToString() {
        System.out.println("\n=== Testing ToArray and ToString ===");
        Student a = createStudent(120, 3.8, "R001");
        Student b = createStudent(140, 3.6, "R002");

        StudentPriorityQueue pq = new StudentPriorityQueue();
        pq.offer(a);
        pq.offer(b);

        Object[] array = pq.toArray();
        assertEquals(2, array.length, "toArray should return correct length");
        assertTrue(pq.toString().contains("R001"), "toString should contain student data");
        assertTrue(pq.toString().contains("R002"), "toString should contain student data");
    }

    public void testUndoOperations() {
        System.out.println("\n=== Testing Undo Operations ===");
        StudentPriorityQueue pq = new StudentPriorityQueue();
        UndoManager undo = new UndoManager();
        Student student = createStudent(100, 3.5, "R001");

        undo.execute(new AddStudentCommand(pq, student));
        assertEquals(1, pq.size(), "Queue should have one student after add");
        assertEquals(student, pq.peek(), "Added student should be at top");

        undo.undo();
        assertEquals(0, pq.size(), "Queue should be empty after undo");
        assertNull(pq.peek(), "Peek should return null after undo");
    }

    public void runAllTests() {
        System.out.println("Running Student Priority Queue Tests...");

        testDefaultStrategyOrdering();
        testGPAFirstStrategy();
        testIteratorNonDestructive();
        testToArrayAndToString();
        testUndoOperations();

        System.out.println("\n=== Test Summary ===");
        System.out.println(passCount + "/" + testCount + " tests passed");

        if (passCount == testCount) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed");
        }
    }

    public static void main(String[] args) {
        StudentPriorityQueueTest testRunner = new StudentPriorityQueueTest();
        testRunner.runAllTests();

        // Also run the main demo
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Running Main Demo...");
        System.out.println("=".repeat(50));
        Main.main(args);
    }
}