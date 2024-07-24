package org.hobbit.core.tool.beans;

import java.security.ProtectionDomain;
import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ReflectUtils;
import lombok.Setter;

/**
 * 重写 cglib BeanMap，支持链式bean
 *
 * @author L.cm
 */
public abstract class HobbitBeanMap extends BeanMap {

  protected HobbitBeanMap() {
  }

  protected HobbitBeanMap(Object bean) {
    super(bean);
  }

  public static HobbitBeanMap create(Object bean) {
    HobbitGenerator gen = new HobbitGenerator();
    gen.setBean(bean);
    return gen.create();
  }

  /**
   * newInstance
   *
   * @param o Object
   * @return HobbitBeanMap
   */
  @Override
  public abstract HobbitBeanMap newInstance(Object o);

  public static class HobbitGenerator extends AbstractClassGenerator<Object> {

    private static final Source SOURCE = new Source(HobbitBeanMap.class.getName());

    private Object bean;
    /**
     * -- SETTER -- Set the class of the bean that the generated map should support. You must call
     * either this method or { #setBeanClass } before .
     * <p>
     * beanClass the class of the bean
     */
    @Setter
    private Class<?> beanClass;
    /**
     * -- SETTER -- Limit the properties reflected by the generated map.
     * <p>
     * require any combination of {@link #REQUIRE_GETTER} and {@link #REQUIRE_SETTER}; default is
     * zero (any property allowed)
     */
    @Setter
    private int require;

    public HobbitGenerator() {
      super(SOURCE);
    }

    /**
     * Set the bean that the generated map should reflect. The bean may be swapped out for another
     * bean of the same type using {#setBean}. Calling this method overrides any value previously
     * set using {@link #setBeanClass}. You must call either this method or {@link #setBeanClass}
     * before {@link #create}.
     *
     * @param bean the initial bean
     */
    public void setBean(Object bean) {
      this.bean = bean;
      if (bean != null) {
        beanClass = bean.getClass();
      }
    }

    @Override
    protected ClassLoader getDefaultClassLoader() {
      return beanClass.getClassLoader();
    }

    @Override
    protected ProtectionDomain getProtectionDomain() {
      return ReflectUtils.getProtectionDomain(beanClass);
    }

    /**
     * Create a new instance of the <code>BeanMap</code>. An existing generated class will be reused
     * if possible.
     *
     * @return {HobbitBeanMap}
     */
    public HobbitBeanMap create() {
      if (beanClass == null) {
        throw new IllegalArgumentException("Class of bean unknown");
      }
      setNamePrefix(beanClass.getName());
      HobbitBeanMapKey key = new HobbitBeanMapKey(beanClass, require);
      return (HobbitBeanMap) super.create(key);
    }

    @Override
    public void generateClass(ClassVisitor v) {
      new HobbitBeanMapEmitter(v, getClassName(), beanClass, require);
    }

    @Override
    protected Object firstInstance(@SuppressWarnings("rawtypes") Class type) {
      return ((BeanMap) ReflectUtils.newInstance(type)).newInstance(bean);
    }

    @Override
    protected Object nextInstance(Object instance) {
      return ((BeanMap) instance).newInstance(bean);
    }
  }

}
