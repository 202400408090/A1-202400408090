import java.util.List;

/**
 * Represents a specialist, such as a cardiologist, paediatrician or
 * radiologist. In addition to the shared details, a specialist records their
 * particular field of medicine.
 */
public class Specialist extends HealthProfessional {

    /** Medical field, e.g. "Cardiologist". */
    private final String subSpecialty;

    /**
     * Full constructor.
     *
     * @param professionalId digits-only identifier
     * @param name           specialist's full name
     * @param availableDays  fixed working days each week
     * @param subSpecialty   medical field the specialist practices in
     */
    public Specialist(String professionalId, String name, List<String> availableDays,
                      String subSpecialty) {
        super(professionalId, name, availableDays);
        if (subSpecialty == null || subSpecialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialist field must not be blank.");
        }
        this.subSpecialty = subSpecialty.trim();
    }

    /**
     * Convenience constructor using the default Monday-to-Friday availability.
     *
     * @param professionalId digits-only identifier
     * @param name           specialist's full name
     * @param subSpecialty   medical field the specialist practices in
     */
    public Specialist(String professionalId, String name, String subSpecialty) {
        super(professionalId, name);
        if (subSpecialty == null || subSpecialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialist field must not be blank.");
        }
        this.subSpecialty = subSpecialty.trim();
    }

    /**
     * Returns the specialty label for this type, including the field.
     *
     * @return "Specialist (<field>)"
     */
    @Override
    public String getSpecialty() {
        return "Specialist (" + subSpecialty + ")";
    }

    /**
     * Returns the specialist's medical field.
     *
     * @return sub-specialty field
     */
    public String getSubSpecialty() {
        return subSpecialty;
    }

    /**
     * Returns the shared details followed by the specialist's field.
     *
     * @return readable summary of the specialist
     */
    @Override
    public String toString() {
        return super.toString() + " | Field: " + subSpecialty;
    }
}
