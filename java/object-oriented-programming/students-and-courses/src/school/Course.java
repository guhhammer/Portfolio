package school;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String name;
    private final Teacher teacher;
    private final List<Student> students = new ArrayList<>();

    public Course(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public boolean enrol(Student s) { return students.add(s); }

    public double classAverage() {
        double sum = 0.0;
        for (Student s : students) { sum += s.average(); }
        return sum / students.size();
    }

    public List<String> reports() {
        List<String> out = new ArrayList<>();
        System.out.println("Course: " + name + "\nTeacher: " + teacher.getFirstName() + " " + teacher.getLastName());
        for (Student s : students) { out.add(s.report()); }
        return out;
    }
}
