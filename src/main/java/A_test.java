public class A_test implements B{
    public static void main(String args[]){
        int i;
        A_test a1=new  A_test();
        i =a1.k;
        System.out.println("i="+i);
    }
}
interface B{
    int k=10;

}