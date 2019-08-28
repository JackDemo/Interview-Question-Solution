public class Test03 {
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0){
            return false;
        }else {
            VerifySquenceOfBST(0,sequence.length-1,sequence);
        }
        return flag;
    }
    public static boolean flag = true;
    public void VerifySquenceOfBST(int start,int end,int [] sequence){
        int flag_A = 0;
        int flag_B = 0;
        if(start<end&&start>=0&&end<=sequence.length-1){
            int temp = sequence[sequence.length-1];

            int i = sequence.length-1;
            while(i>=0){
                if(sequence[i]<temp){
                    flag_A = i+1;
                    break;
                }
                i--;
            }
            int j = 0;
            while(j<=sequence.length-1){
                if(sequence[j]>temp){
                    flag_B = j;
                    break;
                }
                j++;
            }
            if(flag_A!=flag_B){
                Test03.flag = false;
            }
        }
        else{
            Test03.flag = false;
        }
        VerifySquenceOfBST(start,flag_B-1,sequence);
        VerifySquenceOfBST(flag_B,end,sequence);
    }
    void waitForSignal()
    {
        Object obj = new Object();
        synchronized(Thread.currentThread())
        {

            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obj.notify();

        }
    }
    public static void main(String[] args) {
//        new Test03().VerifySquenceOfBST(new int[]{7, 4, 6, 5});
//        ArrayList<Integer> test = new ArrayList<Integer>();
//        test.add(1);
//        test.add(2);
//        test.add(3);
//        for (int value : test) {
//            System.out.println(value);
//            break;
//        }
//        System.out.println(3*0.1==0.3);
//        for(int i =0;i<8;i++){
//            System.out.println(test.get(i));
//            return;
//        }
        new Test03().waitForSignal();
    }
}