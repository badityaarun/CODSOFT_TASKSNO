package com.codsoft.Task5;


import java.util.*;

class Course {

    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private Set<String> registeredStudentIds = new HashSet<>();

    public Course(String courseCode, String title,
                  String description, int capacity,
                  String schedule) {

        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return capacity - registeredStudentIds.size();
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean isRegistered(String studentId) {
        return registeredStudentIds.contains(studentId);
    }

    public boolean isFull() {
        return registeredStudentIds.size() >= capacity;
    }

    public boolean addStudent(String studentId) {
        if (isFull() || isRegistered(studentId)) {
            return false;
        }

        registeredStudentIds.add(studentId);
        return true;
    }

    public boolean removeStudent(String studentId) {
        return registeredStudentIds.remove(studentId);
    }

    public void displayCourse() {
        System.out.println("------------------------------");
        System.out.println("Course Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Capacity: " + capacity);
        System.out.println("Available Slots: " + getAvailableSlots());
    }
}

class Student {

    private String studentId;
    private String name;
    private Set<String> registeredCourses = new LinkedHashSet<>();

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public boolean hasCourse(String courseCode) {
        return registeredCourses.contains(courseCode);
    }

    public boolean addCourse(String courseCode) {
        return registeredCourses.add(courseCode);
    }

    public boolean removeCourse(String courseCode) {
        return registeredCourses.remove(courseCode);
    }

    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }
}

public class StudentCourseRegistrationSystem {

    private static Scanner sc = new Scanner(System.in);

    private static Map<String, Course> courses = new LinkedHashMap<>();
    private static Map<String, Student> students = new LinkedHashMap<>();

    public static void main(String[] args) {

        loadCourses();

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println(" STUDENT COURSE REGISTRATION");
            System.out.println("================================");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Student Registrations");
            System.out.println("5. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    displayCourses();
                    break;

                case 2:
                    registerCourse();
                    break;

                case 3:
                    dropCourse();
                    break;

                case 4:
                    viewRegistrations();
                    break;

                case 5:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    private static void loadCourses() {

        courses.put("J101", new Course(
            "J101", "Java Programming",
            "Learn Java fundamentals and OOP",
            2, "Monday, 10:00 AM"
        ));

        courses.put("P101", new Course(
            "P101", "Python Programming",
            "Learn Python programming basics",
            3, "Tuesday, 11:00 AM"
        ));

        courses.put("D101", new Course(
            "D101", "Database Management",
            "Learn SQL and database concepts",
            2, "Wednesday, 2:00 PM"
        ));
    }

    private static int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static void displayCourses() {

        System.out.println("\nAVAILABLE COURSES");

        for (Course course : courses.values()) {
            course.displayCourse();
        }
    }

    private static Student getOrCreateStudent() {

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return null;
        }

        if (students.containsKey(id)) {
            return students.get(id);
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            return null;
        }

        Student student = new Student(id, name);
        students.put(id, student);

        return student;
    }

    private static void registerCourse() {

        Student student = getOrCreateStudent();

        if (student == null) {
            return;
        }

        displayCourses();

        System.out.print("\nEnter Course Code to register: ");
        String code = sc.nextLine().trim().toUpperCase();

        Course course = courses.get(code);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.hasCourse(code)) {
            System.out.println("You are already registered for this course.");
            return;
        }

        if (course.isFull()) {
            System.out.println("Sorry, this course is full.");
            return;
        }

        if (course.addStudent(student.getStudentId())) {
            student.addCourse(code);
            System.out.println("Registration successful!");
            System.out.println("Student: " + student.getName());
            System.out.println("Course: " + course.getTitle());
            System.out.println(
                "Available slots: " + course.getAvailableSlots()
            );
        }
    }

    private static void dropCourse() {

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();

        Student student = students.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (student.getRegisteredCourses().isEmpty()) {
            System.out.println("You have no registered courses.");
            return;
        }

        viewStudentCourses(student);

        System.out.print("\nEnter Course Code to drop: ");
        String code = sc.nextLine().trim().toUpperCase();

        Course course = courses.get(code);

        if (course == null || !student.hasCourse(code)) {
            System.out.println("You are not registered for this course.");
            return;
        }

        student.removeCourse(code);
        course.removeStudent(id);

        System.out.println("Course dropped successfully!");
        System.out.println("Course: " + course.getTitle());
        System.out.println(
            "Available slots: " + course.getAvailableSlots()
        );
    }

    private static void viewRegistrations() {

        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();

        Student student = students.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nSTUDENT DETAILS");
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Name: " + student.getName());

        viewStudentCourses(student);
    }

    private static void viewStudentCourses(Student student) {

        System.out.println("\nREGISTERED COURSES");

        if (student.getRegisteredCourses().isEmpty()) {
            System.out.println("No registered courses.");
            return;
        }

        for (String code : student.getRegisteredCourses()) {
            Course course = courses.get(code);

            if (course != null) {
                System.out.println(
                    course.getCourseCode() + " - " +
                    course.getTitle() + " (" +
                    course.getSchedule() + ")"
                );
            }
        }
    }
}

