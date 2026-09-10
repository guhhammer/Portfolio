package school;

/** Classes, composition and collections: a course with a teacher and enrolled students. OOP course, PUCPR (2018). */
public class Main {

    public static void main(String[] args) {
        Teacher ed = new Teacher("Ed", "Scar");
        Course oop = new Course("OOP", ed);

        oop.enrol(new Student("Gustavo", "Hammer", 9, 9, 9, 9));
        oop.enrol(new Student("Andre", "Wlodka", 9, 9, 9, 8));
        oop.enrol(new Student("Vinicius", "Abade", 8, 7, 6, 8));

        System.out.println(oop.reports());
        System.out.println("Class average: " + oop.classAverage());
    }
}
