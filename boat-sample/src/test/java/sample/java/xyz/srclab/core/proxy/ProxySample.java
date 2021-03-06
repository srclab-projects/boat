package sample.java.xyz.srclab.core.proxy;

import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;
import xyz.srclab.common.proxy.ProxyClass;
import xyz.srclab.common.proxy.ProxyMethod;
import xyz.srclab.common.proxy.SuperInvoke;
import xyz.srclab.common.test.TestLogger;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxySample {

    private static final TestLogger logger = TestLogger.DEFAULT;

    @Test
    public void testProxy() {
        ProxyClass<Object> proxyClass = ProxyClass.newProxyClass(
            Object.class,
            Arrays.asList(
                new ProxyMethod<Object>() {

                    @Override
                    public boolean proxy(@NotNull Method method) {
                        return method.getName().equals("toString")
                            && Arrays.equals(method.getParameterTypes(), new Class[0]);
                    }

                    @Override
                    public Object invoke(
                        @NotNull Object proxied,
                        @NotNull Method proxiedMethod,
                        @NotNull SuperInvoke superInvoke,
                        Object @NotNull [] args
                    ) {
                        return "Proxy[super: " + superInvoke.invoke(args) + "]";
                    }
                }
            )
        );
        String s = proxyClass.newInstance().toString();
        //Proxy[super: net.sf.cglib.empty.Object$$EnhancerByCGLIB$$4926690c@256f38d9]
        logger.log("s: {}", s);
    }
}
