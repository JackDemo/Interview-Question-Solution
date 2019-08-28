interface Constants{
    public static final int Constants_A = 1;
    public static final int Constants_B = 2;
}
public class ConstantsTest {
    enum ConstantsTwo{
        Constants_A,Constants_B;
    }
    public static void doit(int c){
        switch (c)
        {
            case Constants.Constants_A:
                System.out.println("doit() ConstantsA");
                break;
            case Constants.Constants_B:
                System.out.println("doit() ConstantsB");
        }
    }

    public static void doit2(ConstantsTwo c){
        switch (c){
            case Constants_A:
                System.out.println("doit2() ConstantsA");
                break;
            case Constants_B:
                System.out.println("doit2() ConstantsB");
                break;
        }
    }

    public static void main(String[] args) {
        ConstantsTest.doit(Constants.Constants_A);
        ConstantsTest.doit(Constants.Constants_B);
        ConstantsTest.doit2(ConstantsTwo.Constants_A);
        ConstantsTest.doit2(ConstantsTwo.Constants_B);
        ConstantsTest.doit(5);
    }
}
