/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

import java.util.Comparator;

/**
 * Strategy pattern implementation for priority queue ordering mechanisms.
 * This interface allows dynamic selection of different prioritization strategies
 * at queue creation time, providing flexibility in how students are ordered.
 */

/** Strategy interface: how to order Students in the queue. */
interface PriorityStrategy extends Comparator<Student> {}

/**
 * Default strategy: 70% units, 30% GPA; tie-breakers on GPA, name, redId.
 * This follows the original assignment requirements for student prioritization.
 */
final class DefaultStudentStrategy implements PriorityStrategy {
    private static final double EPS = 1e-9;

    @Override
    public int compare(Student a, Student b) {
        double sA = a.priorityScore();
        double sB = b.priorityScore();
        if (Math.abs(sA - sB) > EPS) return Double.compare(sB, sA); // Descending for max-heap
        if (Math.abs(a.getGpa() - b.getGpa()) > EPS) return Double.compare(b.getGpa(), a.getGpa());
        int nameCmp = a.getName().compareTo(b.getName());
        if (nameCmp != 0) return nameCmp;
        return a.getRedId().compareTo(b.getRedId());
    }
}

/**
 * Example alternate strategy: GPA only, then units, then name, id.
 * Demonstrates the flexibility of the strategy pattern for different prioritization needs.
 */
final class GPAFirstStrategy implements PriorityStrategy {
    private static final double EPS = 1e-9;

    @Override
    public int compare(Student a, Student b) {
        if (Math.abs(a.getGpa() - b.getGpa()) > EPS) return Double.compare(b.getGpa(), a.getGpa());
        if (a.getUnits() != b.getUnits()) return Integer.compare(b.getUnits(), a.getUnits());
        int nameCmp = a.getName().compareTo(b.getName());
        if (nameCmp != 0) return nameCmp;
        return a.getRedId().compareTo(b.getRedId());
    }
}