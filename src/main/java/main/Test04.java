public class Test04 {
    String s;
    int i,i2,i3;
    private Test04(){}
    protected Test04(String s ,int i){
        this.s = s;
        this.i = i;
    }
    public Test04(String... strings) throws NumberFormatException{
        if(0< strings.length)
            i = Integer.valueOf(strings[0]);
        if(1< strings.length)
            i2 = Integer.valueOf(strings[1]);
        if(2< strings.length)
            i3 = Integer.valueOf(strings[2]);
    }
    public  void print(){
        System.out.println("s="+s);
        System.out.println("i="+i);
        System.out.println("i2="+i2);
        System.out.println("i3="+i3);
    }
    public static void getTest(){

    }
    class Test{
        public void getI(){
            print();
            getTest();
        }
    }
    public static void main(String[] args) {
        int a =1;
        int b =2;
        System.out.println(a+b+" ");
    }

}
