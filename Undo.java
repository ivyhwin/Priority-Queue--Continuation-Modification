/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Command pattern implementation for undo functionality in the priority queue.
 * This enables reversible operations by encapsulating each operation as a command object
 * that knows how to execute and undo itself.
 */

/** Command interface for undoable operations. */
interface Command {
    void execute();
    void undo();
    String name();
}

/**
 * Manages execution and undo stack for command pattern.
 * Provides history tracking and reversible operation support.
 */
final class UndoManager {
    private final Deque<Command> undoStack = new ArrayDeque<>();

    /**
     * Executes a command and adds it to the undo stack.
     * @param command The command to execute
     */
    public void execute(Command command) {
        Objects.requireNonNull(command, "Command must not be null").execute();
        undoStack.push(command);
    }

    /** Checks if there are operations that can be undone. */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /** Undoes the most recent operation. */
    public void undo() {
        if (!canUndo()) throw new IllegalStateException("Nothing to undo");
        undoStack.pop().undo();
    }

    /** Returns the number of operations in the undo history. */
    public int getHistorySize() {
        return undoStack.size();
    }
}

/**
 * Command implementation for adding a student to the queue.
 * Stores the necessary state to reverse the operation.
 */
final class AddStudentCommand implements Command {
    private final StudentPriorityQueue queue;
    private final Student student;

    AddStudentCommand(StudentPriorityQueue queue, Student student) {
        this.queue = queue;
        this.student = student;
    }

    @Override public void execute() {
        queue.offer(student);
    }

    @Override public void undo() {
        queue.remove(student);
    }

    @Override public String name() {
        return "Add Student: " + student.getName() + " (" + student.getRedId() + ")";
    }
}

/**
 * Command implementation for removing the highest priority student.
 * Stores the removed student to enable undo.
 */
final class RemoveTopCommand implements Command {
    private final StudentPriorityQueue queue;
    private Student removedStudent;

    RemoveTopCommand(StudentPriorityQueue queue) {
        this.queue = queue;
    }

    @Override public void execute() {
        removedStudent = queue.poll();
    }

    @Override public void undo() {
        if (removedStudent != null) queue.offer(removedStudent);
    }

    @Override public String name() {
        return "Remove Top: " + (removedStudent != null ?
                removedStudent.getName() + " (" + removedStudent.getRedId() + ")" : "null");
    }
}