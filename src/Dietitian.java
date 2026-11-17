import java.util.List;

/**
 * Represents a dietitian. In addition to the shared details, a dietitian
 * records their professional accreditation.
 */
public class Dietitian extends HealthProfessional {

    /** Professional accreditation, e.g. "Accredited Practising Dietitian". */
    private final String accreditation;

    /**
     * Full constructor.
     *
     * @param professionalId digits-only identifier
     * @param name           dietitian's full name
     * @param availableDays  fixed working days each week
     * @param accreditation professional accreditation held by the dietitian
     */
    public Dietitian(String professionalId, String name, List<String> availableDays,
                     String accreditation) {
        super(professionalId, name, availableDays);
        if (accreditation == null || accreditation.trim().isEmpty()) {
            throw new IllegalArgumentException("Accreditation must not be blank.");
        }
        this.accreditation = accreditation.trim();
    }

    /**
     * Convenience constructor using the default Monday-to-Friday availability.
     *
     * @param professionalId digits-only identifier
     * @param name           dietitian's full name
     * @param accreditation  professional accreditation held by the dietitian
     */
    public Dietitian(String professionalId, String name, String accreditation) {
        super(professionalId, name);
        if (accreditation == null || accreditation.trim().isEmpty()) {
            throw new IllegalArgumentException("Accreditation must not be blank.");
        }
        this.accreditation = accreditation.trim();
    }

    /**
     * Returns the specialty label for this type.
     *
     * @return "Dietitian"
     */
    @Override
    public String getSpecialty() {
        return "Dietitian";
    }

    /**
     * Returns the dietitian's accreditation.
     *
     * @return accreditation description
     */
    public String getAccreditation() {
        return accreditation;
    }

    /**
     * Returns the shared details followed by the dietitian's accreditation.
     *
     * @return readable summary of the dietitian
     */
    @Override
    public String toString() {
        return super.toString() + " | Accreditation: " + accreditation;
    }
}
