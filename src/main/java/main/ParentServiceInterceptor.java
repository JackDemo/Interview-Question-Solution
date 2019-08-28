import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class ParentServiceInterceptor implements InvocationHandler {

    private Object realObj;
    private  static Logger logger = Logger.getLogger(ParentServiceInterceptor.class.getName());

    public ParentServiceInterceptor(Object realObj) {
        super();
        this.realObj = realObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(args!=null && args.length>0 && args[0] instanceof Parent){
            Parent p = (Parent)args[0];
            if (p.getAge()<18){
                throw new RuntimeException("用户年龄在18岁以下");
            }
        }
        Object ret = method.invoke(realObj,args);
        logger.info("数据库操作成功");
        return ret;
    }

    public Object getRealObj() {
        return realObj;
    }

    public void setRealObj(Object realObj) {
        this.realObj = realObj;
    }

    public static void main(String[] args) {
        Parent p = new Parent();
        p.setAge(15);
        p.setName("Zhang");
        ParentService ps = new ParentServiceImpl();
        ParentServiceInterceptor psi = new ParentServiceInterceptor(ps);
        //参数1.类加载器//2. 服务接口信息//3. invocationhandler 本身
        ParentService proxy = (ParentService) Proxy.newProxyInstance(ps.getClass().getClassLoader(),ps.getClass().getInterfaces(),psi);
        proxy.addParentService(p);
        System.out.println("完成代理");
    }
}
