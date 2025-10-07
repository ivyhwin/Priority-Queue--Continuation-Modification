# Priority-Queue--Continuation-Modification
This project implements a Priority Queue for Student objects using Strategy, Iterator, and Command (Undo) design patterns. It refactors the original Assignment 1 implementation into a cleaner, modular design aligned with Java’s collection standards.

Features
Priority Calculation:
priority = 0.7 * (units / 150) + 0.3 * (gpa / 4.0)
Tie-breakers: Units → GPA → Name → Red ID.
Strategy Pattern: Allows dynamic ordering logic.
DefaultStudentStrategy: Combined GPA + Units (70/30).
GPAFirstStrategy: GPA priority first.
Iterator Pattern: Iterates in descending priority without modifying the queue.
Command Pattern (Undo): Supports undo for add and remove operations.
Collection Integration: Extends AbstractQueue<Student> with standard methods (offer, poll, peek, remove, iterator, toArray).


src/
 ├── Student.java
 ├── PriorityStrategies.java
 ├── StudentPriorityQueue.java
 ├── Undo.java
 └── Main.java

test/
 ├── StudentPriorityQueueTest.java
 ├── UndoTest.java
 
