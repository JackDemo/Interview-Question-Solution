package cglib;

public class Parent {
    public static String testname;
    static {
        System.out.println("Parent静态代码块被执行");
    }
    public static String getTestname(){
        testname = "Parent testname 在静态方法中被赋值";
        return testname;
    }
    {
        System.out.println("Parent普通代码块被执行");
    }

    private int age;
    private String name;
    public int a = 0;
    public Parent(){
        System.out.println("Parent 无参构造器");
    }
    public Parent(int age, String name){
        this.age = age;
        this.name = name;

        System.out.println("Parent 有参构造器");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
