package org.hobbit.core.mybatis.base;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 基础业务接口
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
public interface IBaseService<T> extends IService<T> {

  /**
   * 逻辑删除
   *
   * @param ids id集合
   */
  boolean deleteLogic(@NotEmpty List<Long> ids);

  /**
   * 变更状态
   *
   * @param ids    id集合
   * @param status 状态值
   */
  boolean changeStatus(@NotEmpty List<Long> ids, Integer status);
}
