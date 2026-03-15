import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AttendanceApp {

    public static void main(String[] args) {

        // a.
        ArrayList<Student> students = new ArrayList<>();

        
        // add sample students
        addStudent(students, "Taylor Swift");
        addStudent(students, "Ariana Grande");

        // record attendance
        if (!recordAttendance(students, "Taylor Swift", 1)) 
        if (!recordAttendance(students, "Taylor Swift", 0)) 
        if (!recordAttendance(students, "Ariana Grande", 1))
        

        // for ghost student
        if (!recordAttendance(students, "Katy Perry", 1)) {
            System.out.println("Student Katy Perry does not exist!");
        }

        saveStudents(students, "attendance.txt");

        ArrayList<Student> loadedStudents = loadStudents("attendance.txt");

        displayAllStudents(loadedStudents);

        // attempt to load a missing file
        loadStudents("missing.txt");
    }
    
    // b.
    // i. add a new student
    public static void addStudent(ArrayList<Student> students, String name) {
        Student s = new Student();
        s.name = name;
        students.add(s);
    }

    // ii. record attendance mark for a student
    public static boolean recordAttendance(ArrayList<Student> students, String studentName, int mark) {
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(studentName)) {
                s.attendanceMarks.add(mark);
                return true; 
            }
        }
        return false; 
    }

    // iii. calculate attendance percentage 
    public static double getAttendancePercentage(Student student) {
        int total = student.attendanceMarks.size();
        if (total == 0)
            return 0;

        int present = 0;
        for (int mark : student.attendanceMarks) {
            if (mark == 1)
                present++;
        }

        double percentage = ((double) present / total) * 100;
        return percentage;
    }

    // iv. return formatted student info
    public static String getDisplayInfo(Student student) {
        double percent = getAttendancePercentage(student);
        return String.format("Name: %s%nAttendance: %.2f%%", student.name, percent);
    }

    // v. display all students
    public static void displayAllStudents(ArrayList<Student> students) {
        for (Student s : students) {
            double percent = getAttendancePercentage(s);
            System.out.printf("\nName: %s%nAttendance: %.2f%%%n", s.name, percent);
        }
    }

    // file/io
    // save students to file
    public static void saveStudents(ArrayList<Student> students, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                writer.write(s.name);
                for (int mark : s.attendanceMarks) {
                    writer.write("," + mark);
                }
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    // load students from file
    public static ArrayList<Student> loadStudents(String filename) {
        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Student student = new Student();
                student.name = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    try {
                        int mark = Integer.parseInt(parts[i]);
                        student.attendanceMarks.add(mark);
                    } catch (NumberFormatException e) {
                        
                    }
                }
                students.add(student);
            }

        } catch (FileNotFoundException e) {
            System.out.println("\n" + filename + " is not found!");
        } catch (IOException e) {
            System.out.println("Load error: " + e.getMessage());
        }

        return students;
    }
}