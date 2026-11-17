import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the whole day's appointments in one place.
 *
 * <p>Responsibilities: adding validated appointments (rejecting missing details,
 * invalid slots and double-bookings), viewing and filtering appointments by
 * professional ID or patient phone number, sorting appointments by time, and
 * cancelling appointments. Every operation returns a readable success or
 * warning message so the caller can give the user clear feedback.</p>
 *
 * <p>The daily clinic schedule (08:00-16:00 in 30-minute intervals) is built
 * once in the constructor as a list of valid slots. Changing those start/end
 * times or interval later only affects that one setup step; the rest of the
 * system works against the slot list.</p>
 */
public class AppointmentManager {

    /** All appointments currently held for the day. */
    private final List<Appointment> appointments = new ArrayList<>();

    /** Fixed daily schedule of valid booking slots, e.g. "08:00". */
    private final List<String> timeSlots = new ArrayList<>();

    /**
     * Builds the default clinic schedule: 08:00 to 16:00, 30 minutes apart.
     */
    public AppointmentManager() {
        buildSchedule(8, 16, 30);
    }

    /**
     * Generates the fixed set of booking slots for the day.
     *
     * @param startHour     first slot hour (24-hour clock)
     * @param endHour       last booking ends at this hour
     * @param intervalMinutes gap between slots in minutes
     */
    private void buildSchedule(int startHour, int endHour, int intervalMinutes) {
        for (int minutes = startHour * 60; minutes < endHour * 60; minutes += intervalMinutes) {
            int hour = minutes / 60;
            int minute = minutes % 60;
            timeSlots.add(String.format("%02d:%02d", hour, minute));
        }
    }

    /**
     * Returns a copy of the valid daily time slots.
     *
     * @return list of slot strings "HH:mm"
     */
    public List<String> getTimeSlots() {
        return new ArrayList<>(timeSlots);
    }

    /**
     * Attempts to add an appointment. The booking is rejected with a warning
     * message when details are missing, the slot is not in the schedule, or the
     * professional is already booked in that same slot.
     *
     * @param patient      patient to book
     * @param professional professional to book
     * @param timeSlot     requested slot "HH:mm"
     * @return success or warning message
     */
    public String addAppointment(Patient patient, HealthProfessional professional, String timeSlot) {
        if (patient == null) {
            return "WARNING: Appointment not added - patient details are missing.";
        }
        if (professional == null) {
            return "WARNING: Appointment not added - a health professional must be chosen.";
        }
        if (timeSlot == null || timeSlot.trim().isEmpty()) {
            return "WARNING: Appointment not added - a time slot must be chosen.";
        }

        String slot = timeSlot.trim();
        if (!timeSlots.contains(slot)) {
            return "WARNING: " + slot + " is not a valid clinic time slot.";
        }

        // Reject a double-booking: same professional, same slot.
        for (Appointment existing : appointments) {
            if (existing.getProfessional().equals(professional)
                    && existing.getTimeSlot().equals(slot)) {
                return "WARNING: " + professional.getName() + " is already booked at " + slot + ".";
            }
        }

        appointments.add(new Appointment(patient, professional, slot));
        return "SUCCESS: " + patient.getName() + " booked with "
                + professional.getName() + " at " + slot + ".";
    }

    /**
     * Returns all current appointments.
     *
     * @return copy of the full appointment list
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * Filters appointments for one professional ID.
     *
     * @param professionalId ID to match
     * @return appointments booked with that professional (may be empty)
     */
    public List<Appointment> filterByProfessionalId(String professionalId) {
        List<Appointment> result = new ArrayList<>();
        if (professionalId == null) {
            return result;
        }
        String id = professionalId.trim();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getProfessionalId().equals(id)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Filters appointments for one patient phone number.
     *
     * @param mobilePhone patient mobile number to match
     * @return appointments booked by that patient (may be empty)
     */
    public List<Appointment> filterByPatientPhone(String mobilePhone) {
        List<Appointment> result = new ArrayList<>();
        if (mobilePhone == null) {
            return result;
        }
        String phone = mobilePhone.trim();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getMobilePhone().equals(phone)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Returns a sorted copy of the given appointments, earliest slot first.
     * The original list is left unchanged.
     *
     * @param subset appointments to sort
     * @return new time-ordered list
     */
    public List<Appointment> getSortedAppointments(List<Appointment> subset) {
        List<Appointment> sorted = new ArrayList<>(subset);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Cancels the appointment matching a professional ID and time slot.
     *
     * @param professionalId ID of the professional
     * @param timeSlot       slot to cancel "HH:mm"
     * @return success message, or warning if no matching appointment exists
     */
    public String cancelByProfessionalAndSlot(String professionalId, String timeSlot) {
        if (professionalId == null || professionalId.trim().isEmpty()
                || timeSlot == null || timeSlot.trim().isEmpty()) {
            return "WARNING: Professional ID and time slot are required to cancel.";
        }
        String id = professionalId.trim();
        String slot = timeSlot.trim();

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getProfessionalId().equals(id)
                    && appointment.getTimeSlot().equals(slot)) {
                appointments.remove(i);
                return "SUCCESS: Appointment cancelled for professional " + id + " at " + slot + ".";
            }
        }
        return "WARNING: No appointment found for professional " + id + " at " + slot + ".";
    }

    /**
     * Cancels all appointments matching a patient phone number.
     *
     * @param mobilePhone patient mobile number to match
     * @return success message with the count cancelled, or warning if none exist
     */
    public String cancelByPatientPhone(String mobilePhone) {
        if (mobilePhone == null || mobilePhone.trim().isEmpty()) {
            return "WARNING: Patient phone number is required to cancel.";
        }
        String phone = mobilePhone.trim();
        int removed = 0;

        // Remove from the end so indexes stay valid while deleting.
        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPatient().getMobilePhone().equals(phone)) {
                appointments.remove(i);
                removed++;
            }
        }

        if (removed == 0) {
            return "WARNING: No appointment found for patient " + phone + ".";
        }
        return "SUCCESS: Cancelled " + removed + " appointment(s) for patient " + phone + ".";
    }

    /**
     * Returns the number of current appointments.
     *
     * @return appointment count
     */
    public int appointmentCount() {
        return appointments.size();
    }
}
