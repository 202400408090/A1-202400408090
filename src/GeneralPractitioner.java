import java.util.List;

/**
 * Represents a general practitioner (GP). In addition to the shared details
 * held by {@link HealthProfessional}, a GP records their years of experience.
 */
public class GeneralPractitioner extends HealthProfessional {

    /** Years of clinical experience. */
    private final int yearsOfExperience;

    /**
     * Full constructor.
     *
     * @param professionalId    digits-only identifier
     * @param name              GP's full name
     * @param availableDays     fixed working days each week
     * @param yearsOfExperience years of clinical experience (zero or more)
     */
    public GeneralPractitioner(String professionalId, String name, List<String> availableDays,
                              int yearsOfExperience) {
        super(professionalId, name, availableDays);
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Convenience constructor using the default Monday-to-Friday availability.
     *
     * @param professionalId    digits-only identifier
     * @param name              GP's full name
     * @param yearsOfExperience years of clinical experience (zero or more)
     */
    public GeneralPractitioner(String professionalId, String name, int yearsOfExperience) {
        super(professionalId, name);
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Returns the specialty label for this type.
     *
     * @return "General Practitioner"
     */
    @Override
    public String getSpecialty() {
        return "General Practitioner";
    }

    /**
     * Returns the GP's years of experience.
     *
     * @return years of experience
     */
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Returns the shared details followed by the GP-specific experience field.
     *
     * @return readable summary of the GP
     */
    @Override
    public String toString() {
        return super.toString() + " | Experience: " + yearsOfExperience + " years";
    }
}
