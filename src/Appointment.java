/**
 * Represents a single appointment: one patient booked with one health
 * professional at one clinic time slot on the current day.
 *
 * <p>The appointment refers to the general {@link HealthProfessional} type,
 * so it can be linked to any concrete professional subclass. Implementing
 * {@link Comparable} lets a collection of appointments be sorted from earliest
 * to latest time slot.</p>
 */
public class Appointment implements Comparable<Appointment> {

    /** The patient booking the appointment. */
    private final Patient patient;

    /** The professional the patient wants to see. */
    private final HealthProfessional professional;

    /** Booked time slot in zero-padded 24-hour format, e.g. "09:30". */
    private final String timeSlot;

    /**
     * Constructs an appointment and rejects any missing required detail, so an
     * incomplete appointment cannot be created.
     *
     * @param patient      patient for the appointment
     * @param professional chosen health professional
     * @param timeSlot     booked slot, formatted "HH:mm"
     */
    public Appointment(Patient patient, HealthProfessional professional, String timeSlot) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment requires a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("An appointment requires a health professional.");
        }
        if (timeSlot == null || timeSlot.trim().isEmpty()) {
            throw new IllegalArgumentException("An appointment requires a time slot.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeSlot = timeSlot.trim();
    }

    /**
     * Returns the patient.
     *
     * @return booked patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the professional.
     *
     * @return chosen professional
     */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /**
     * Returns the booked time slot.
     *
     * @return time slot "HH:mm"
     */
    public String getTimeSlot() {
        return timeSlot;
    }

    /**
     * Orders appointments by time slot. Because slots are stored as zero-padded
     * "HH:mm" strings, normal string comparison gives the correct time order.
     *
     * @param other appointment to compare against
     * @return negative, zero, or positive if this slot is earlier, equal, or
     *         later than the other
     */
    @Override
    public int compareTo(Appointment other) {
        return this.timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Returns the appointment details, including both the patient and the
     * professional, in a readable form.
     *
     * @return readable summary of the appointment
     */
    @Override
    public String toString() {
        return timeSlot + " | " + professional + " | " + patient;
    }
}
