public class Child extends Parent{
    private  int senior;
    public Child(int senior){
        super(18,"ZhangQIming");
        this.senior = senior;
        System.out.println("Child 有参构造器");
    }
    public static void main(String[] args) {
        System.out.println("Hello World");
        Parent p = new Child(8);
        System.out.println(p.getName());
        int a =0;
        int b =1;
        for (int i = 0; i < 10; i++) {
            a=++a;
        }
        System.out.println(a);
    }
}
