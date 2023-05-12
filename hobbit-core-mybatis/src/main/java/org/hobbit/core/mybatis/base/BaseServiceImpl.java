package org.hobbit.core.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.hobbit.core.auth.HobbitUser;
import org.hobbit.core.auth.util.AuthUtil;
import org.hobbit.core.tool.constant.HobbitConstant;
import org.hobbit.core.tool.utils.BeanUtil;
import org.hobbit.core.tool.utils.DateUtil;
import org.hobbit.core.tool.utils.Func;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends
    ServiceImpl<M, T> implements IBaseService<T> {

  @Override
  public boolean save(T entity) {
    this.resolveEntity(entity);
    return super.save(entity);
  }

  @Override
  public boolean saveBatch(Collection<T> entityList, int batchSize) {
    entityList.forEach(this::resolveEntity);
    return super.saveBatch(entityList, batchSize);
  }

  @Override
  public boolean updateById(T entity) {
    this.resolveEntity(entity);
    return super.updateById(entity);
  }

  @Override
  public boolean updateBatchById(Collection<T> entityList, int batchSize) {
    entityList.forEach(this::resolveEntity);
    return super.updateBatchById(entityList, batchSize);
  }

  @Override
  public boolean saveOrUpdate(T entity) {
    if (entity.getId() == null) {
      return this.save(entity);
    } else {
      return this.updateById(entity);
    }
  }

  @Override
  public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
    entityList.forEach(this::resolveEntity);
    return super.saveOrUpdateBatch(entityList, batchSize);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteLogic(List<Long> ids) {
    HobbitUser user = AuthUtil.getUser();
    List<T> list = new ArrayList<>();
    ids.forEach(id -> {
      T entity = BeanUtil.newInstance(currentModelClass());
      if (user != null) {
        entity.setUpdateUser(user.getUserId());
      }
      entity.setUpdateTime(LocalDateTime.now());
      entity.setId(id);
      list.add(entity);
    });
    return super.updateBatchById(list) && super.removeByIds(ids);
  }

  @Override
  public boolean changeStatus(List<Long> ids, Integer status) {
    HobbitUser user = AuthUtil.getUser();
    List<T> list = new ArrayList<>();
    ids.forEach(id -> {
      T entity = BeanUtil.newInstance(currentModelClass());
      if (user != null) {
        entity.setUpdateUser(user.getUserId());
      }
      entity.setUpdateTime(LocalDateTime.now());
      entity.setId(id);
      entity.setStatus(status);
      list.add(entity);
    });
    return super.updateBatchById(list);
  }

  @SneakyThrows
  private void resolveEntity(T entity) {
    HobbitUser user = AuthUtil.getUser();
    LocalDateTime now = LocalDateTime.now();
    if (entity.getId() == null) {
      // 处理新增逻辑
      if (user != null) {
        entity.setCreateUser(user.getUserId());
        entity.setCreateDept(Func.firstLong(user.getDeptId()));
        entity.setUpdateUser(user.getUserId());
      }
      if (entity.getStatus() == null) {
        entity.setStatus(HobbitConstant.DB_STATUS_NORMAL);
      }
      entity.setCreateTime(now);
    } else if (user != null) {
      // 处理修改逻辑
      entity.setUpdateUser(user.getUserId());
    }
    // 处理通用逻辑
    entity.setUpdateTime(now);
    entity.setDeleted(HobbitConstant.DB_NOT_DELETED);
  }
}
