/************************************
 * Ivy Huynh
 * CS635-M1-Priority Queue Assignment
 * **********************************
 */

/**
 * An immutable data model class representing a Student.
 * Encapsulates all student data and validates it upon construction.
 * The class is final to prevent inheritance and ensure immutability is not broken.
 */
public final class Student {
    // Public class constants define the valid domain for GPA and units.
    public static final int MAX_UNITS = 150;
    public static final double MAX_GPA = 4.0;

    // All fields are private and final, ensuring immutability.
    private final String name;
    private final String redId;
    private final String email;
    private final double gpa;   // Must be in [0.0, 4.0]
    private final int units;    // Must be in [0, 150]

    /**
     * Constructs a Student object with validated parameters.
     * @param name Student's name, must not be null or empty.
     * @param redId Student's Red ID, must not be null or empty.
     * @param email Student's email, must not be null and must contain '@'.
     * @param gpa Student's GPA, must be between 0.0 and 4.0.
     * @param units Units taken by the student, must be between 0 and 150.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public Student(String name, String redId, String email, double gpa, int units) {
        // Input validation is crucial for ensuring object integrity.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (redId == null || redId.trim().isEmpty()) {
            throw new IllegalArgumentException("redId must be non-empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("email must contain '@'");
        }
        if (gpa < 0.0 || gpa > MAX_GPA) {
            throw new IllegalArgumentException("gpa must be in [0.0, " + MAX_GPA + "]");
        }
        if (units < 0 || units > MAX_UNITS) {
            throw new IllegalArgumentException("units must be in [0, " + MAX_UNITS + "]");
        }

        // Assign validated values to final fields.
        this.name = name;
        this.redId = redId;
        this.email = email;
        this.gpa = gpa;
        this.units = units;
    }

    // Standard getter methods provide read-only access to the fields.
    public String getName() { return name; }
    public String getRedId() { return redId; }
    public String getEmail() { return email; }
    public double getGpa() { return gpa; }
    public int getUnits() { return units; }

    /**
     * Calculates the student's normalized priority score using the default strategy.
     * The formula: 70% from normalized units, 30% from normalized GPA.
     * This ensures both factors contribute equally to the 0-1 scale despite their different ranges.
     * @return A priority score between 0.0 and 1.0.
     */
    public double priorityScore() {
        double normalizedUnits = units / (double) MAX_UNITS; // Normalize units to [0, 1]
        double normalizedGPA = gpa / MAX_GPA;               // Normalize GPA to [0, 1]
        return (0.7 * normalizedUnits) + (0.3 * normalizedGPA);
    }

    /**
     * Provides a string representation of the Student for debugging.
     * @return A string containing the student's key attributes.
     */
    @Override
    public String toString() {
        return "Student{" + redId + ", " + name + ", gpa=" + gpa + ", units=" + units + "}";
    }

    /**
     * Equals method for proper comparison and removal operations.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return redId.equals(student.redId);
    }

    /**
     * HashCode consistent with equals for proper collection behavior.
     */
    @Override
    public int hashCode() {
        return redId.hashCode();
    }
}