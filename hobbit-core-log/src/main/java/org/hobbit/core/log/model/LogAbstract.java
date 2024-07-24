package org.hobbit.core.log.model;

import java.io.Serializable;
import java.util.Date;
import org.hobbit.core.tool.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author lhy
 * @version 1.0.0 2023/1/13
 */
@Data
public class LogAbstract implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键id
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  protected Long id;
  /**
   * 租户ID
   */
  private String tenantId;
  /**
   * 服务ID
   */
  protected String serviceId;
  /**
   * 服务器 ip
   */
  protected String serverIp;
  /**
   * 服务器名
   */
  protected String serverHost;
  /**
   * 环境
   */
  protected String env;
  /**
   * 操作IP地址
   */
  protected String remoteIp;
  /**
   * 用户代理
   */
  protected String userAgent;
  /**
   * 请求URI
   */
  protected String requestUri;
  /**
   * 操作方式
   */
  protected String method;
  /**
   * 方法类
   */
  protected String methodClass;
  /**
   * 方法名
   */
  protected String methodName;
  /**
   * 操作提交的数据
   */
  protected String params;
  /**
   * 创建人
   */
  protected String createBy;
  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
  @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
  protected Date createTime;
}
