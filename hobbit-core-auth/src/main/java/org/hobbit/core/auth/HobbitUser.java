package org.hobbit.core.auth;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import org.hobbit.core.tool.support.Kv;

/**
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@Data
public class HobbitUser implements Serializable {

  @ApiModelProperty(hidden = true)
  private String accessToken;

  @ApiModelProperty(hidden = true)
  private String refreshToken;

  /**
   * 客户端id
   */
  @ApiModelProperty(hidden = true)
  private String clientId;

  /**
   * 用户id
   */
  @ApiModelProperty(hidden = true)
  private Long userId;
  /**
   * 账号
   */
  @ApiModelProperty(hidden = true)
  private String account;
  /**
   * 用户名
   */
  @ApiModelProperty(hidden = true)
  private String realName;
  /**
   * 昵称
   */
  @ApiModelProperty(hidden = true)
  private String nickName;
  /**
   * 部门id
   */
  @ApiModelProperty(hidden = true)
  private String deptId;
  /**
   * 岗位id
   */
  @ApiModelProperty(hidden = true)
  private String postId;
  /**
   * 角色id
   */
  @ApiModelProperty(hidden = true)
  private String roleId;
  /**
   * 角色名
   */
  @ApiModelProperty(hidden = true)
  private String roleName;
  /**
   * 用户详情
   */
  @ApiModelProperty(hidden = true)
  private Kv detail;
}
