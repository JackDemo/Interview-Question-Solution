public class Child extends Parent{
    private  int senior;
    public Child(int senior){
        super(18,"ZhangQIming");
        this.senior = senior;
        System.out.println("Child 有参构造器");
    }
    public int setValue(int b,int c){
        int a = 0;
        try {
            a = b+c;
            return a+1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
 //           return a+3;
        }
        return a;
    }
    public static void main(String[] args) {
//        int[] child = new int[6];
//        System.out.println(child[4]);
//        String s = "hello";
//        System.out.print(s.equals(new String("hello")));
//        String str1 = "abc";
//        String str3 = new String("abc");
//        System.out.print(str1==str3);
//        System.out.print(new Integer(-7).toBinaryString(-7));
//        System.out.println();
//        byte[] message = { 1, 2, 3 };
//        for (byte b : message) {
//            System.out.println(Integer.toBinaryString(0x100 + b).substring(1));
//        }
        System.out.println(0x7fffffff+0x7fffffff);
    }
}
