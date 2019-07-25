interface A{
    int a = 0;
    public abstract int getA();
    default int setA(){
        int b=5;
        b=b++;
        return b;
    };
    static String getString(){
        return "静态方法";
    }
}

public class TestInterface implements A{

    @Override
    public int getA() {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(new TestInterface().setA());
        System.out.println(A.getString());
    }
}