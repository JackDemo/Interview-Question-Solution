public class Parent {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
