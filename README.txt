PROG2004 Object Oriented Programming - Assessment 1
Health Service Appointment System
============================================================

PROJECT RENAMING
----------------
Replace "username" in the project/zip name with your own MySCU username.
Example: if your username is lfolkman, the folder and zip must be
"A1-lfolkman" and "A1-lfolkman.zip".

SOURCE FILES (src directory)
----------------------------
AssignmentOne.java        Main method: scripted demonstration (Part 3 & Part 6)
HealthProfessional.java   Abstract base class for all professionals (Part 1)
GeneralPractitioner.java  Concrete subclass: GP (Part 2)
Specialist.java           Concrete subclass: specialist (Part 2)
Nurse.java                Concrete subclass: nurse (Part 2)
Dietitian.java            Concrete subclass: dietitian (Part 2)
Patient.java              Patient name and mobile number (Part 4)
Appointment.java          Patient + professional + time slot, Comparable (Part 4)
AppointmentManager.java    Add / filter / sort / cancel appointments (Part 5)

HOW TO COMPILE AND RUN
----------------------
From the project root (the folder that contains src):

    javac -d build src\*.java
    java -cp build AssignmentOne

The console prints the Part 3 professional list and the Part 6
appointment demonstration, including success and warning messages.

HOW THE PARTS MAP TO THE CODE
-----------------------------
Part 1  HealthProfessional: shared ID (digits only), name, working days;
        private fields with getters; constructor validation; toString();
        abstract class so a plain professional cannot be created.
Part 2  Four subclasses inherit the shared code, add their own fields, call
        super(...), override getSpecialty() and toString() (super.toString()).
Part 3  Professionals are stored together as HealthProfessional and printed
        polymorphically in AssignmentOne.
Part 4  Appointment links a Patient to any HealthProfessional at one slot;
        required details are validated; implements Comparable for time order.
Part 5  AppointmentManager holds an ArrayList of appointments, builds the fixed
        08:00-16:00 / 30-minute slot schedule, rejects missing details, invalid
        slots and double-bookings, filters by professional ID or patient phone,
        sorts all or a filtered subset, and cancels by ID+slot or by phone.
Part 6  AssignmentOne exercises normal cases and error cases: double-booking,
        invalid slot, missing professional, cancelling a non-existent booking.

SUBMISSION CHECKLIST (items you must complete personally)
---------------------------------------------------------
[ ] Rename project and zip to A1-<your-username>.zip
[ ] Push all source files to a GitHub repository; commit regularly with clear
    messages. If private, invite your tutor and unit assessor as collaborators.
    Add the repository link to the submission comments.
[ ] Attach the WakaTime report/screenshots as A1-<your-username>-WakaTime.[png/jpg/pdf]
[ ] Record a video (max 5 minutes) explaining the four OOP pillars
    (encapsulation, inheritance, polymorphism, abstraction), pointing to where
    each is used in the code and why. Upload to SCU OneDrive and add the
    shareable link to the submission comments.
[ ] Include the GenAI declaration in the submission comments, choosing the
    statement that matches what you actually did.

GENAI DECLARATION STATEMENTS
-----------------------------
If you DID use GenAI:
"I acknowledge that I have used Generative AI tools to complete this
assessment in accordance with the conditions outlined in the Assessment
Brief. I used [insert name of GenAI tool(s)] to [briefly describe how the
tool(s) were used, e.g. generate ideas, check grammar, organise structure]."

If you DID NOT use GenAI:
"I acknowledge that I have not knowingly used GenAI to complete this
assessment."

Note: per the brief, GenAI may not be used to write the solution code or the
code comments, or to create the explanation video. Be prepared to explain
your code and how you used any tools.
