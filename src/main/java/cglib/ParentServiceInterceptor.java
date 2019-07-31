package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ParentServiceInterceptor implements MethodInterceptor {
    private  static Logger logger = Logger.getLogger(ParentServiceInterceptor.class.getName());
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(objects!=null&&objects.length>0&&objects[0] instanceof Parent){
            Parent p = (Parent) objects[0];
            if(p.getAge()<18){
                throw new RuntimeException("用户年龄小于18");
            }
        }
        Object ret = methodProxy.invokeSuper(o,objects);
        logger.info("success");
        return ret;
    }

    public static void main(String[] args) {
        Parent p = new Parent();
        p.setAge(10);
        p.setName("ZhangM");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ParentServiceImpl.class);
        enhancer.setCallback(new ParentServiceInterceptor());
        ParentServiceImpl psi = (ParentServiceImpl) enhancer.create();
        psi.addParentService(p);

    }
}
