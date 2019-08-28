import javax.swing.*;
import java.awt.*;

public class SwingAndThread extends JFrame{
    private JLabel J_Lable = new JLabel();
    private static Thread t;
    private int count = 0;
    private Container container = getContentPane();

    public SwingAndThread(){
        setBounds(300,200,250,100);
        container.setLayout(null);
        String url = "C:\\Users\\Administrator\\Desktop\\mavenTest01\\src\\main\\resoures\\ico.jpeg";
        System.out.print(url);
        Icon icon = new ImageIcon(url);
        J_Lable.setIcon(icon);
        J_Lable.setHorizontalAlignment(SwingConstants.LEFT);
        J_Lable.setBounds(0,0,250,100);
        J_Lable.setOpaque(true);
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (count<200){
                    J_Lable.setBounds(count,10,200,50);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count+=1;
                    if(count==200) count=0;
                }
            }
        });
        t.start();
        container.add(J_Lable);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new SwingAndThread();
    }
}
