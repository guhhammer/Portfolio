


public class Person {

    
    
    private String nome;
    private String lastName;
    private int age;
    private double salary;
    
    public Person(String n, String sn, int i, double s){
        nome = n;
        lastName = sn;
        age = i;
        salary = s;
    }
    
    public String getName(Person p){
        return p.nome;
    }
    
    public String getLastName(Person p){
        return p.lastName;
    }
    
    public int getAge(Person p){
        return p.age;
    }
    
    public double getSalary(Person p){
        return p.salary;
    }
    
    public void printPerson(Person p){
        System.out.println("Name: "+getName(p)+
                           " \nLast name: "+getLastName(p)+
                           " \nAge: "+getAge(p)+
                           "\nSalary: "+getSalary(p));
    }
    
}
