import javax.swing.*;
import java.awt.*;

/**
 * 定义四个线程，分别定义其优先级
 * 利用线程sleep时间不同区分
 * 可以很明显的看出其中的变化
 */
public class PriortyTest extends JFrame{
    private  Thread threadA;
    private  Thread threadB;
    private  Thread threadC;
    private  Thread threadD;

    final JProgressBar progressBar = new JProgressBar();
    final JProgressBar progressBar2 = new JProgressBar();
    final JProgressBar progressBar3 = new JProgressBar();
    final JProgressBar progressBar4 = new JProgressBar();
    int count = 0;
    public PriortyTest(){

        super();
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(progressBar);
        getContentPane().add(progressBar2);
        getContentPane().add(progressBar3);
        getContentPane().add(progressBar4);

        progressBar.setStringPainted(true);
        progressBar2.setStringPainted(true);
        progressBar3.setStringPainted(true);
        progressBar4.setStringPainted(true);

        threadA = new Thread(new MyThread(progressBar,1));
        threadB = new Thread(new MyThread(progressBar2,5));
        threadC = new Thread(new MyThread(progressBar3,10));
        threadD = new Thread(new MyThread(progressBar4,100));

        setPriority("threadA",3,threadA);
        setPriority("threadB",4,threadB);
        setPriority("threadC",5,threadC);
        setPriority("threadD",5,threadD);


    }

    public static void main(String[] args) {
        init(new PriortyTest(),200,400);
    }

    public static void init(JFrame frame,int width,int height){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setVisible(true);
    }

    public void setPriority(String threadName, int priority, Thread t){
        t.setPriority(priority);
        t.setName(threadName);
        t.start();
    }

    private final  class  MyThread implements Runnable{
        private final  JProgressBar bar;
        int time = 0;
        int count = 0;

        private MyThread(JProgressBar bar,int time) {
            this.bar = bar;
            this.time = time;
        }
        @Override
        public void run() {
            while (true){
                bar.setValue(count+=1);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    System.out.println("当前线程被中断");
                }
            }
        }
    }
}
