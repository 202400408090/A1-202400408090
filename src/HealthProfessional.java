import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class that records the information and behaviour shared by
 * every health professional in the service, regardless of their specialty.
 *
 * <p>Common data held here: a numeric ID, a name, and the fixed set of working
 * days each week. Type-specific details (for example a specialist's field or a
 * nurse's ward) belong in the subclasses that extend this class.</p>
 *
 * <p>The class is abstract because a "plain" professional with no specialty
 * should never be created directly; every real professional must belong to one
 * of the concrete subclasses.</p>
 */
public abstract class HealthProfessional {

    /** The valid working days used to validate a professional's availability. */
    private static final List<String> VALID_DAYS = new ArrayList<>();

    static {
        VALID_DAYS.add("Monday");
        VALID_DAYS.add("Tuesday");
        VALID_DAYS.add("Wednesday");
        VALID_DAYS.add("Thursday");
        VALID_DAYS.add("Friday");
        VALID_DAYS.add("Saturday");
        VALID_DAYS.add("Sunday");
    }

    /** Numeric-only identifier for the professional. */
    private final String professionalId;

    /** Full name of the professional. */
    private final String name;

    /** Fixed working days for this professional (e.g. Monday, Wednesday). */
    private final List<String> availableDays;

    /**
     * Full constructor. Validates that the shared details are present and valid
     * before the object is allowed to exist, so an invalid professional cannot
     * be created.
     *
     * @param professionalId  digits-only identifier
     * @param name            professional's full name
     * @param availableDays   fixed working days each week
     */
    protected HealthProfessional(String professionalId, String name, List<String> availableDays) {
        if (professionalId == null || !professionalId.trim().matches("\\d+")) {
            throw new IllegalArgumentException("Professional ID must contain digits only.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Professional name must not be blank.");
        }
        if (availableDays == null || availableDays.isEmpty()) {
            throw new IllegalArgumentException("At least one available day must be provided.");
        }
        for (String day : availableDays) {
            if (day == null || !VALID_DAYS.contains(day)) {
                throw new IllegalArgumentException("Invalid working day: " + day);
            }
        }

        this.professionalId = professionalId.trim();
        this.name = name.trim();
        // Defensive copy: the internal list cannot be changed from outside.
        this.availableDays = new ArrayList<>(availableDays);
    }

    /**
     * Convenience constructor that defaults availability to Monday to Friday when
     * the caller does not supply a custom list of working days.
     *
     * @param professionalId digits-only identifier
     * @param name            professional's full name
     */
    protected HealthProfessional(String professionalId, String name) {
        this(professionalId, name, defaultWeekDays());
    }

    /**
     * Builds the default Monday-to-Friday availability list.
     *
     * @return a list of the standard weekday names
     */
    private static List<String> defaultWeekDays() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        return days;
    }

    /**
     * Returns the professional's ID.
     *
     * @return numeric ID
     */
    public String getProfessionalId() {
        return professionalId;
    }

    /**
     * Returns the professional's name.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the professional's working days. A copy is returned so
     * the internal collection cannot be modified through the getter.
     *
     * @return copy of the available days
     */
    public List<String> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }

    /**
     * Returns the specialty label of the concrete professional type. Each
     * subclass must provide its own implementation.
     *
     * @return specialty description
     */
    public abstract String getSpecialty();

    /**
     * Two professionals are considered equal when they share the same ID,
     * because the ID uniquely identifies a person in the system.
     *
     * @param obj object to compare against
     * @return true if the IDs match, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional other = (HealthProfessional) obj;
        return this.professionalId.equals(other.professionalId);
    }

    /**
     * Returns all shared details in a readable form. Subclasses append their
     * own type-specific details by calling super.toString().
     *
     * @return readable summary of the professional
     */
    @Override
    public String toString() {
        return "ID: " + professionalId
                + " | Name: " + name
                + " | Type: " + getSpecialty()
                + " | Available: " + String.join(", ", availableDays);
    }
}
