package org.hobbit.core.mybatis.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * 分页模型
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
@Data
public class HobbitPage<T> implements Serializable {

  /**
   * 查询数据列表
   */
  private List<T> records = Collections.emptyList();
  /**
   * 总数
   */
  private long total = 0;
  /**
   * 每页显示条数，默认 10
   */
  private long size = 10;
  /**
   * 当前页
   */
  private long current = 1;

  /**
   * mybatis-plus分页模型转化
   *
   * @param page 分页实体类
   */
  public static <T> HobbitPage<T> of(IPage<T> page) {
    HobbitPage<T> hobbitPage = new HobbitPage<>();
    hobbitPage.setRecords(page.getRecords());
    hobbitPage.setTotal(page.getTotal());
    hobbitPage.setSize(page.getSize());
    hobbitPage.setCurrent(page.getCurrent());
    return hobbitPage;
  }
}
