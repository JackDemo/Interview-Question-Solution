public class Test02 extends Father{
    static {

        System.out.println("static");
    }
    {
        System.out.println("not static");
    }
    public static final int go = 10;
    String name ;
    int age;
    public void print(){
        System.out.println(name);
        System.out.println(age);
    }
    public Test02(){
        System.out.println("Test02 gouzao");
    }
    public static void main(String[] args) {
//        new Test02().print();
//        new Test02().print();
        int a = 10;

        int b = 5;

        a += ++b;
        System.out.println(a);
    }
}
class Father {
    static {
        System.out.println(" father static");
    }
    {
        System.out.println(" father not static");
    }
    String name="father" ;
    int age=56;
    public Father(){
        System.out.println("father gouzao");
    }
    public void print(){
        System.out.println(name);
        System.out.println(age);
    }
}
