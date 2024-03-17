package org.hobbit.core.tool.support;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.hobbit.core.tool.utils.Exceptions;
import org.springframework.lang.Nullable;

/**
 * Lambda 受检异常处理 <a href="https://segmentfault.com/a/1190000007832130"/>
 *
 * @author Chill
 */
public class Try {

  public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
    Objects.requireNonNull(mapper);
    return t -> {
      try {
        return mapper.apply(t);
      } catch (Exception e) {
        throw Exceptions.unchecked(e);
      }
    };
  }

  public static <T> Consumer<T> of(UncheckedConsumer<T> mapper) {
    Objects.requireNonNull(mapper);
    return t -> {
      try {
        mapper.accept(t);
      } catch (Exception e) {
        throw Exceptions.unchecked(e);
      }
    };
  }

  public static <T> Supplier<T> of(UncheckedSupplier<T> mapper) {
    Objects.requireNonNull(mapper);
    return () -> {
      try {
        return mapper.get();
      } catch (Exception e) {
        throw Exceptions.unchecked(e);
      }
    };
  }

  @FunctionalInterface
  public interface UncheckedFunction<T, R> {

    /**
     * apply
     */
    @Nullable
    R apply(@Nullable T t);
  }

  @FunctionalInterface
  public interface UncheckedConsumer<T> {

    /**
     * accept
     */
    void accept(@Nullable T t);
  }

  @FunctionalInterface
  public interface UncheckedSupplier<T> {

    /**
     * get
     */
    @Nullable
    T get();
  }
}
