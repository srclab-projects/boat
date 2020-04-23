package xyz.srclab.common.bytecode.provider.spring;

import org.springframework.cglib.beans.BeanGenerator;
import xyz.srclab.common.pattern.builder.CachedBuilder;
import xyz.srclab.common.bytecode.BeanClass;

/**
 * @author sunqian
 */
final class BeanClassBuilderImpl<T>
        extends CachedBuilder<BeanClass<T>> implements BeanClass.Builder<T> {

    private final BeanGenerator beanGenerator;

    BeanClassBuilderImpl(Class<T> superClass) {
        this.beanGenerator = new BeanGenerator();
        this.beanGenerator.setSuperclass(superClass);
    }

    @Override
    public BeanClass.Builder<T> addProperty(String name, Class<?> type) {
        updateState();
        beanGenerator.addProperty(name, type);
        return this;
    }

    @Override
    protected BeanClass<T> buildNew() {
        return () -> (T) beanGenerator.create();
    }
}