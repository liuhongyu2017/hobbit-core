package org.hobbit.core.mybatis.injector;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.mybatis.injector.methods.InsertIgnore;
import org.hobbit.core.mybatis.injector.methods.Replace;

/**
 * 自定义sql注入
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
@RequiredArgsConstructor
public class HobbitSqlInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
    List<AbstractMethod> methodList = new ArrayList<>();
    methodList.add(new InsertIgnore());
    methodList.add(new Replace());
    methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
    methodList.addAll(super.getMethodList(mapperClass, tableInfo));
    return Collections.unmodifiableList(methodList);
  }
}
