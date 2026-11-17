/**
 * Records the basic details of a patient: a name and a mobile phone number.
 * Patient details are kept in their own class so an appointment can refer to
 * one patient object rather than duplicate name and phone fields.
 */
public class Patient {

    /** Patient's full name. */
    private final String name;

    /** Patient's contact mobile number (digits only). */
    private final String mobilePhone;

    /**
     * Constructs a patient and validates that both required details are present.
     *
     * @param name        patient's full name
     * @param mobilePhone contact mobile number, digits only
     */
    public Patient(String name, String mobilePhone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name must not be blank.");
        }
        if (mobilePhone == null || !mobilePhone.trim().matches("\\d{8,12}")) {
            throw new IllegalArgumentException("Mobile phone must contain 8 to 12 digits.");
        }
        this.name = name.trim();
        this.mobilePhone = mobilePhone.trim();
    }

    /**
     * Returns the patient's name.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's mobile number.
     *
     * @return mobile phone number
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Returns the patient's details in a readable form.
     *
     * @return readable summary of the patient
     */
    @Override
    public String toString() {
        return "Patient: " + name + " (mobile: " + mobilePhone + ")";
    }
}
