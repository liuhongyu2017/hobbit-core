package org.hobbit.core.mybatis.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.hobbit.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 基础实体类
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
@Data
public class BaseEntity implements Serializable {

  /**
   * 主键id
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty(value = "主键id")
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  /**
   * 创建人
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty(value = "创建人")
  private Long createUser;

  /**
   * 创建部门
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty(value = "创建部门")
  private Long createDept;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
  @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  /**
   * 更新人
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty(value = "更新人")
  private Long updateUser;

  /**
   * 更新时间
   */
  @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
  @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
  @ApiModelProperty(value = "更新时间")
  private LocalDateTime updateTime;

  /**
   * 状态[1:正常]
   */
  @ApiModelProperty(value = "业务状态")
  private Integer status;

  /**
   * 状态[0:未删除,1:删除]
   */
  @TableLogic
  @ApiModelProperty(value = "是否已删除")
  private Integer deleted;
}
