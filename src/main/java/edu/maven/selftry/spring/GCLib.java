package edu.maven.selftry.spring;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class GCLib {
    public static void main(String[] args) {
        MinPQInterface<String> GCProxy = (MinPQInterface) new GCLibProxy().createProxyInstance(new MinPQHeap<>());
        GCProxy.add("秦老大",3);
    }
}

class GCLibProxy implements MethodInterceptor {
    private Object target;

    public Object createProxyInstance(Object obj) {
        this.target = obj;
        //处理代理逻辑的对象
        Enhancer enhancer = new Enhancer();
        //设为子类
        enhancer.setSuperclass(obj.getClass());
        //拦截所有目标类方法
        enhancer.setCallback(this);
        //得到代理对象
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        //@Before
        System.out.println("---before invoke----");
        System.out.println("Mathod: " + method.getName() + ", Params: " + Arrays.asList(args));
        Object result = method.invoke(target, args);
        //@After
        System.out.println("Returning: " + result);
        System.out.println("---after invoke----");
        return result;
    }
}
