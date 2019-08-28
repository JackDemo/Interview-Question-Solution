class Person {
    String name = "No name";
    public Person(String nm) {
        name = nm;
    }

    Person() {
    }
}
class Employee extends Person {
    String empID = "0000";
    public Employee(String id) {
        empID = id;
    }

    public String getEmpID(int a,String b) {
        return empID+"1";
    }
    public String getEmpID(String b, int a) {
        return empID+"2";
    }

}
public class Test {
    public static void main(String args[]) {
        Employee e = new Employee("123");
        System.out.println(e.getEmpID(1,"1"));
        System.out.println(e.getEmpID("1",1));
    }
}