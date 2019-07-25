import java.lang.reflect.Constructor;

public class Main04 {
    public static void main(String[] args) {
        int flag = 0;
        Test04 test = new Test04("10","20","30");
        Class testC = test.getClass();
        Constructor[] declaredConstructor = testC.getDeclaredConstructors();
        for (Constructor constructor: declaredConstructor) {
            System.out.println("是否允许带有可变数量的参数:"+constructor.isVarArgs());
            System.out.println("该构造方法的入口参数类型依次为");
            Class[] params = constructor.getParameterTypes();
            for (Class param: params) {
                System.out.print(param);
            }
            System.out.println("该构造方法的可能抛出的异常类型依次为");
            Class[] exceptionTypes = constructor.getExceptionTypes();
            for (Class exception : exceptionTypes) {
                System.out.print(exception);
            }
            Test04 test2 = null;
            while (test2==null){
                try {
                    if(flag==2){
                        test2 = (Test04) constructor.newInstance();
                        flag+=1;
                    }else if(flag==1){
                        test2 = (Test04) constructor.newInstance("7",5);
                        flag+=1;
                    }else {
                        test2 = (Test04) constructor.newInstance(new Object[]{new String[]{"100","200","300"}});
                        flag+=1;
                    }

                }catch (Exception e){
                    System.out.println("创建对象时抛出异常，下面执行setAccessible()方法");
                    constructor.setAccessible(true);
                }
            }
            if (test2!=null){
                test2.print();
                System.out.println();
            }
        }
    }
}
