public class ThreadSafeTest implements Runnable {
    int num = 10;
    private  boolean isContinue;

    public ThreadSafeTest() {
        isContinue = false;
    }

    @Override
    public void run() {
        while (true){
            //synchronized (""){
                if(num>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(Thread.currentThread().getName());
                    System.out.println("  tickets has "+ num-- +" . "+"sold one");
                }
                else if(num<0){
                    System.out.println("已售完");
                    isContinue = true;
                    break;
                }
                else if (isContinue){
                    break;
                }
            }
       //}
    }

    public void setContinue() {
        isContinue = true;
    }

    public static void main(String[] args)  {
        ThreadSafeTest t = new ThreadSafeTest();
        Thread tA= new Thread(t);
        Thread tB = new Thread(t);
        Thread tC = new Thread(t);
        Thread tD = new Thread(t);
        tA.start();
        tB.start();
        tC.start();
        tD.start();
    }
}
