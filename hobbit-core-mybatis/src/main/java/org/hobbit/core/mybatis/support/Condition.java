package org.hobbit.core.mybatis.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import org.hobbit.core.tool.constant.TokenConstant;
import org.hobbit.core.tool.support.Kv;
import org.hobbit.core.tool.utils.BeanUtil;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.StringUtil;

/**
 * 分页工具
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
public class Condition {

  /**
   * 转化成 mybatis plus 中的Page
   *
   * @param query 查询条件
   * @return IPage
   */
  public static <T> IPage<T> getPage(Query query) {
    Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
    String[] ascArr = Func.toStrArray(query.getAscs());
    for (String asc : ascArr) {
      page.addOrder(OrderItem.asc(StringUtil.cleanIdentifier(asc)));
    }
    String[] descArr = Func.toStrArray(query.getDescs());
    for (String desc : descArr) {
      page.addOrder(OrderItem.desc(StringUtil.cleanIdentifier(desc)));
    }
    return page;
  }

  /**
   * 获取 mybatis plus 中的 QueryWrapper
   *
   * @param entity 实体
   * @param <T>    类型
   * @return QueryWrapper
   */
  public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
    return new QueryWrapper<>(entity);
  }

  /**
   * 获取 mybatis plus 中的 QueryWrapper
   *
   * @param query 查询条件
   * @param clazz 实体类
   * @param <T>   类型
   * @return QueryWrapper
   */
  public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
    Kv exclude = Kv.create().set(TokenConstant.HEADER, TokenConstant.HEADER)
        .set("current", "current").set("size", "size").set("ascs", "ascs").set("descs", "descs");
    return getQueryWrapper(query, exclude, clazz);
  }

  /**
   * 获取 mybatis plus 中的 QueryWrapper
   *
   * @param query   查询条件
   * @param exclude 排除的查询条件
   * @param clazz   实体类
   * @param <T>     类型
   * @return QueryWrapper
   */
  public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query,
      Map<String, Object> exclude, Class<T> clazz) {
    exclude.forEach((k, v) -> query.remove(k));
    QueryWrapper<T> qw = new QueryWrapper<>();
    qw.setEntity(BeanUtil.newInstance(clazz));
    SqlKeyword.buildCondition(query, qw);
    return qw;
  }
}
