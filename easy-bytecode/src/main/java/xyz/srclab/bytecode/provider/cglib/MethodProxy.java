package xyz.srclab.bytecode.provider.cglib;

interface MethodProxy {

    Object invoke(Object object, Object[] args) throws Throwable;

    Object invokeSuper(Object object, Object[] args) throws Throwable;
}