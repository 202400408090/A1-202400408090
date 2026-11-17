import java.util.List;

/**
 * Represents a nurse. In addition to the shared details, a nurse records the
 * ward or unit they are assigned to.
 */
public class Nurse extends HealthProfessional {

    /** Ward or unit the nurse works in, e.g. "Emergency". */
    private final String ward;

    /**
     * Full constructor.
     *
     * @param professionalId digits-only identifier
     * @param name            nurse's full name
     * @param availableDays   fixed working days each week
     * @param ward            ward or unit the nurse is assigned to
     */
    public Nurse(String professionalId, String name, List<String> availableDays, String ward) {
        super(professionalId, name, availableDays);
        if (ward == null || ward.trim().isEmpty()) {
            throw new IllegalArgumentException("Ward must not be blank.");
        }
        this.ward = ward.trim();
    }

    /**
     * Convenience constructor using the default Monday-to-Friday availability.
     *
     * @param professionalId digits-only identifier
     * @param name            nurse's full name
     * @param ward            ward or unit the nurse is assigned to
     */
    public Nurse(String professionalId, String name, String ward) {
        super(professionalId, name);
        if (ward == null || ward.trim().isEmpty()) {
            throw new IllegalArgumentException("Ward must not be blank.");
        }
        this.ward = ward.trim();
    }

    /**
     * Returns the specialty label for this type.
     *
     * @return "Nurse"
     */
    @Override
    public String getSpecialty() {
        return "Nurse";
    }

    /**
     * Returns the nurse's ward.
     *
     * @return ward or unit
     */
    public String getWard() {
        return ward;
    }

    /**
     * Returns the shared details followed by the nurse's ward.
     *
     * @return readable summary of the nurse
     */
    @Override
    public String toString() {
        return super.toString() + " | Ward: " + ward;
    }
}
