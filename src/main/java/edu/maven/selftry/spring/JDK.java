package edu.maven.selftry.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDK{
    public static void main(String[] args) {
        MinPQInterface<String> JDKProxy = new JDKProxy().createProxyInstance(new MinPQHeap<>());
        JDKProxy.add("秦老大",3);
    }
}

class JDKProxy implements InvocationHandler {
        //代理对象
        private MinPQInterface target;

        public MinPQInterface createProxyInstance(MinPQInterface target) {
            this.target = target;
            //返回Proxy代理对象
            return (MinPQInterface) Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
                    this.target.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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