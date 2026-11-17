import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration class for the health service appointment system.
 *
 * <p>The main method stands in for real users: it builds a mix of health
 * professional objects (Part 3), then exercises the appointment manager
 * end-to-end - adding, filtering, sorting, and cancelling appointments,
 * including the cases where an operation cannot be performed (Part 6). All
 * results are printed to the console.</p>
 */
public class AssignmentOne {

    /**
     * Runs the full scripted demonstration.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println(" PROG2004 Assessment 1 - Health Service Appointment System");
        System.out.println("========================================================");

        List<HealthProfessional> professionals = buildProfessionals();
        demonstrateProfessionals(professionals);

        AppointmentManager manager = new AppointmentManager();
        demonstrateAppointments(manager, professionals);
    }

    /**
     * Builds the mix of professionals used in the demonstration: three GPs,
     * two specialists, two nurses and one dietitian.
     *
     * @return list of professionals held through the common base type
     */
    private static List<HealthProfessional> buildProfessionals() {
        List<HealthProfessional> professionals = new ArrayList<>();

        professionals.add(new GeneralPractitioner("1001", "Dr Alice Nguyen",
                days("Monday", "Wednesday", "Friday"), 12));
        professionals.add(new GeneralPractitioner("1002", "Dr Ben Carter",
                days("Tuesday", "Thursday"), 8));
        professionals.add(new GeneralPractitioner("1003", "Dr Chloe Martin", 15));

        professionals.add(new Specialist("2001", "Dr David Lee",
                days("Monday", "Wednesday"), "Cardiologist"));
        professionals.add(new Specialist("2002", "Dr Emma Wilson",
                days("Tuesday", "Thursday", "Friday"), "Paediatrician"));

        professionals.add(new Nurse("3001", "Nurse Fiona Brown", "Ward 3A"));
        professionals.add(new Nurse("3002", "Nurse George Davis",
                days("Tuesday", "Thursday"), "Emergency"));

        professionals.add(new Dietitian("4001", "Dietitian Hannah Clark",
                days("Monday", "Wednesday", "Friday"), "Accredited Practising Dietitian"));

        return professionals;
    }

    /**
     * Part 3: displays the full details of every professional. They are stored
     * and processed together as the common {@link HealthProfessional} type, and
     * each object prints its own details polymorphically.
     *
     * @param professionals professionals to display
     */
    private static void demonstrateProfessionals(List<HealthProfessional> professionals) {
        System.out.println("\n----- Part 3: Health Professionals -----");
        for (HealthProfessional professional : professionals) {
            System.out.println("  - " + professional);
        }
    }

    /**
     * Part 6: demonstrates the appointment system end-to-end, covering both
     * normal cases and error cases.
     *
     * @param manager      the appointment manager to exercise
     * @param professionals professionals available for booking
     */
    private static void demonstrateAppointments(AppointmentManager manager,
                                                List<HealthProfessional> professionals) {
        // Look professionals up by ID for the booking script.
        HealthProfessional gp1 = findById(professionals, "1001");
        HealthProfessional gp2 = findById(professionals, "1002");
        HealthProfessional gp3 = findById(professionals, "1003");
        HealthProfessional cardiologist = findById(professionals, "2001");
        HealthProfessional nurse1 = findById(professionals, "3001");
        HealthProfessional nurse2 = findById(professionals, "3002");
        HealthProfessional dietitian = findById(professionals, "4001");

        Patient jack = new Patient("Jack Thompson", "0412345678");
        Patient mia = new Patient("Mia Taylor", "0423456789");
        Patient noah = new Patient("Noah Wilson", "0434567890");
        Patient olivia = new Patient("Olivia Brown", "0445678901");

        System.out.println("\n----- Part 6: Adding Appointments -----");
        System.out.println(manager.addAppointment(jack, gp1, "09:00"));
        System.out.println(manager.addAppointment(mia, cardiologist, "09:00"));
        System.out.println(manager.addAppointment(noah, nurse1, "10:30"));
        System.out.println(manager.addAppointment(olivia, dietitian, "11:00"));
        System.out.println(manager.addAppointment(mia, gp2, "13:30"));
        System.out.println(manager.addAppointment(jack, nurse2, "14:00"));

        printAppointments("All appointments (sorted by time):",
                manager.getSortedAppointments(manager.getAllAppointments()));

        // Filtering demonstrations.
        printAppointments("Appointments with professional 1001:",
                manager.filterByProfessionalId("1001"));
        printAppointments("Appointments for patient 0423456789 (sorted):",
                manager.getSortedAppointments(manager.filterByPatientPhone("0423456789")));

        System.out.println("\n----- Error Cases -----");
        // Same professional already booked in this slot.
        System.out.println(manager.addAppointment(noah, gp1, "09:00"));
        // Slot outside the fixed clinic schedule.
        System.out.println(manager.addAppointment(olivia, gp1, "17:00"));
        // No professional supplied.
        System.out.println(manager.addAppointment(jack, null, "10:00"));
        // Cancelling an appointment that does not exist.
        System.out.println(manager.cancelByProfessionalAndSlot("1003", "15:00"));

        System.out.println("\n----- Cancelling Appointments -----");
        System.out.println(manager.cancelByProfessionalAndSlot("1002", "13:30"));
        System.out.println(manager.cancelByPatientPhone("0412345678"));

        printAppointments("Appointments remaining after cancellation:",
                manager.getSortedAppointments(manager.getAllAppointments()));
        System.out.println("\nTotal appointments remaining: " + manager.appointmentCount());
    }

    /**
     * Finds a professional in a list by their ID.
     *
     * @param professionals list to search
     * @param id            ID to match
     * @return matching professional, or null if none matches
     */
    private static HealthProfessional findById(List<HealthProfessional> professionals, String id) {
        for (HealthProfessional professional : professionals) {
            if (professional.getProfessionalId().equals(id)) {
                return professional;
            }
        }
        return null;
    }

    /**
     * Helper that turns a vararg list of day names into an ArrayList.
     *
     * @param names working day names
     * @return list of those names
     */
    private static List<String> days(String... names) {
        List<String> result = new ArrayList<>();
        for (String name : names) {
            result.add(name);
        }
        return result;
    }

    /**
     * Prints a titled list of appointments, with a message when the list is empty.
     *
     * @param title   heading to print above the list
     * @param appointments appointments to display
     */
    private static void printAppointments(String title, List<Appointment> appointments) {
        System.out.println("\n" + title);
        if (appointments.isEmpty()) {
            System.out.println("  (no appointments)");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println("  - " + appointment);
        }
    }
}
