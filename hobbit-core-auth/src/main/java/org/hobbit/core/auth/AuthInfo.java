package org.hobbit.core.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author lhy
 * @version 1.0.0 2023/4/4
 */
@Data
@ApiModel(description = "认证信息")
public class AuthInfo implements Serializable {

  @ApiModelProperty(value = "令牌")
  private String accessToken;

  @ApiModelProperty(value = "令牌类型")
  private String tokenType;

  @ApiModelProperty(value = "头像")
  private String avatar = "";

  @ApiModelProperty(value = "角色名")
  private String authority;

  @ApiModelProperty(value = "用户名")
  private String userName;

  @ApiModelProperty(value = "账号名")
  private String account;

  @ApiModelProperty(value = "过期时间")
  private long expiresIn;

  @ApiModelProperty(value = "许可证")
  private String license = "made by hobbit";
}
