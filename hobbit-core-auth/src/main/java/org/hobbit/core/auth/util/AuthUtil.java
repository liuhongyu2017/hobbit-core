package org.hobbit.core.auth.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import org.hobbit.core.auth.HobbitUser;
import org.hobbit.core.jwt.JwtUtil;
import org.hobbit.core.jwt.props.JwtProperties;
import org.hobbit.core.tool.constant.RoleConstant;
import org.hobbit.core.tool.constant.TokenConstant;
import org.hobbit.core.tool.support.Kv;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.ObjectUtil;
import org.hobbit.core.tool.utils.SpringUtil;
import org.hobbit.core.tool.utils.StringPool;
import org.hobbit.core.tool.utils.StringUtil;
import org.hobbit.core.tool.utils.WebUtil;

/**
 * Auth 工具类
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public class AuthUtil {

  private static final String HOBBIT_USER_REQUEST_ATTR = "_HOBBIT_USER_REQUEST_ATTR_";

  private final static String HEADER = TokenConstant.HEADER;
  private final static String ACCOUNT = TokenConstant.ACCOUNT;
  private final static String USER_NAME = TokenConstant.USER_NAME;
  private final static String NICK_NAME = TokenConstant.NICK_NAME;
  private final static String USER_ID = TokenConstant.USER_ID;
  private final static String DEPT_ID = TokenConstant.DEPT_ID;
  private final static String POST_ID = TokenConstant.POST_ID;
  private final static String ROLE_ID = TokenConstant.ROLE_ID;
  private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
  private final static String TENANT_ID = TokenConstant.TENANT_ID;
  private final static String OAUTH_ID = TokenConstant.OAUTH_ID;
  private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
  private final static String DETAIL = TokenConstant.DETAIL;

  private static JwtProperties jwtProperties;

  /**
   * 获取配置类
   *
   * @return jwtProperties
   */
  private static JwtProperties getJwtProperties() {
    if (jwtProperties == null) {
      jwtProperties = SpringUtil.getBean(JwtProperties.class);
    }
    return jwtProperties;
  }

  /**
   * 获取用户信息
   *
   * @return HobbitUser
   */
  public static HobbitUser getUser() {
    HttpServletRequest request = WebUtil.getRequest();
    if (request == null) {
      return null;
    }
    // 优先从 request 中获取
    Object hobbitUser = request.getAttribute(HOBBIT_USER_REQUEST_ATTR);
    if (hobbitUser == null) {
      hobbitUser = getUser(request);
      if (hobbitUser != null) {
        // 设置到 request 中
        request.setAttribute(HOBBIT_USER_REQUEST_ATTR, hobbitUser);
      }
    }
    return (HobbitUser) hobbitUser;
  }

  /**
   * 获取用户信息
   *
   * @param request request
   * @return HobbitUser
   */
  @SuppressWarnings("unchecked")
  public static HobbitUser getUser(HttpServletRequest request) {
    Claims claims = getClaims(request);
    if (claims == null) {
      return null;
    }
    String clientId = Func.toStr(claims.get(AuthUtil.CLIENT_ID));
    Long userId = Func.toLong(claims.get(AuthUtil.USER_ID));
    String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
    String oauthId = Func.toStr(claims.get(AuthUtil.OAUTH_ID));
    String deptId = Func.toStrWithEmpty(claims.get(AuthUtil.DEPT_ID), StringPool.MINUS_ONE);
    String postId = Func.toStrWithEmpty(claims.get(AuthUtil.POST_ID), StringPool.MINUS_ONE);
    String roleId = Func.toStrWithEmpty(claims.get(AuthUtil.ROLE_ID), StringPool.MINUS_ONE);
    String account = Func.toStr(claims.get(AuthUtil.ACCOUNT));
    String roleName = Func.toStr(claims.get(AuthUtil.ROLE_NAME));
    String userName = Func.toStr(claims.get(AuthUtil.USER_NAME));
    String nickName = Func.toStr(claims.get(AuthUtil.NICK_NAME));
    Kv detail = Kv.create().setAll((Map<? extends String, ?>) claims.get(AuthUtil.DETAIL));
    HobbitUser HobbitUser = new HobbitUser();
    HobbitUser.setClientId(clientId);
    HobbitUser.setUserId(userId);
    HobbitUser.setTenantId(tenantId);
    HobbitUser.setOauthId(oauthId);
    HobbitUser.setAccount(account);
    HobbitUser.setDeptId(deptId);
    HobbitUser.setPostId(postId);
    HobbitUser.setRoleId(roleId);
    HobbitUser.setRoleName(roleName);
    HobbitUser.setUserName(userName);
    HobbitUser.setNickName(nickName);
    HobbitUser.setDetail(detail);
    return HobbitUser;
  }

  /**
   * 是否为超管
   *
   * @return boolean
   */
  public static boolean isAdministrator() {
    return StringUtil.containsAny(getUserRole(), RoleConstant.ADMINISTRATOR);
  }

  /**
   * 获取用户id
   *
   * @return userId
   */
  public static Long getUserId() {
    HobbitUser user = getUser();
    return (null == user) ? -1 : user.getUserId();
  }

  /**
   * 获取用户id
   *
   * @param request request
   * @return userId
   */
  public static Long getUserId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? -1 : user.getUserId();
  }

  /**
   * 获取用户账号
   *
   * @return userAccount
   */
  public static String getUserAccount() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getAccount();
  }

  /**
   * 获取用户账号
   *
   * @param request request
   * @return userAccount
   */
  public static String getUserAccount(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getAccount();
  }

  /**
   * 获取用户名
   *
   * @return userName
   */
  public static String getUserName() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getUserName();
  }

  /**
   * 获取用户名
   *
   * @param request request
   * @return userName
   */
  public static String getUserName(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getUserName();
  }

  /**
   * 获取昵称
   *
   * @return userName
   */
  public static String getNickName() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getNickName();
  }

  /**
   * 获取昵称
   *
   * @param request request
   * @return userName
   */
  public static String getNickName(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getNickName();
  }

  /**
   * 获取用户部门
   *
   * @return userName
   */
  public static String getDeptId() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getDeptId();
  }

  /**
   * 获取用户部门
   *
   * @param request request
   * @return userName
   */
  public static String getDeptId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getDeptId();
  }

  /**
   * 获取用户岗位
   *
   * @return userName
   */
  public static String getPostId() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getPostId();
  }

  /**
   * 获取用户岗位
   *
   * @param request request
   * @return userName
   */
  public static String getPostId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getPostId();
  }

  /**
   * 获取用户角色
   *
   * @return userName
   */
  public static String getUserRole() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getRoleName();
  }

  /**
   * 获取用角色
   *
   * @param request request
   * @return userName
   */
  public static String getUserRole(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getRoleName();
  }

  /**
   * 获取租户ID
   *
   * @return tenantId
   */
  public static String getTenantId() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getTenantId();
  }

  /**
   * 获取租户ID
   *
   * @param request request
   * @return tenantId
   */
  public static String getTenantId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getTenantId();
  }

  /**
   * 获取第三方认证ID
   *
   * @return tenantId
   */
  public static String getOauthId() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getOauthId();
  }

  /**
   * 获取第三方认证ID
   *
   * @param request request
   * @return tenantId
   */
  public static String getOauthId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getOauthId();
  }

  /**
   * 获取客户端id
   *
   * @return clientId
   */
  public static String getClientId() {
    HobbitUser user = getUser();
    return (null == user) ? StringPool.EMPTY : user.getClientId();
  }

  /**
   * 获取客户端id
   *
   * @param request request
   * @return clientId
   */
  public static String getClientId(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? StringPool.EMPTY : user.getClientId();
  }

  /**
   * 获取用户详情
   *
   * @return clientId
   */
  public static Kv getDetail() {
    HobbitUser user = getUser();
    return (null == user) ? Kv.create() : user.getDetail();
  }

  /**
   * 获取用户详情
   *
   * @param request request
   * @return clientId
   */
  public static Kv getDetail(HttpServletRequest request) {
    HobbitUser user = getUser(request);
    return (null == user) ? Kv.create() : user.getDetail();
  }

  /**
   * 获取Claims
   *
   * @param request request
   * @return Claims
   */
  public static Claims getClaims(HttpServletRequest request) {
    String auth = request.getHeader(AuthUtil.HEADER);
    Claims claims = null;
    String token;
    // 获取 Token 参数
    if (StringUtil.isNotBlank(auth)) {
      token = JwtUtil.getToken(auth);
    } else {
      String parameter = request.getParameter(AuthUtil.HEADER);
      token = JwtUtil.getToken(parameter);
    }
    // 获取 Token 值
    if (StringUtil.isNotBlank(token)) {
      claims = AuthUtil.parseJWT(token);
    }
    // 判断 Token 状态
    if (ObjectUtil.isNotEmpty(claims) && getJwtProperties().getState()) {
      String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
      String userId = Func.toStr(claims.get(AuthUtil.USER_ID));
      String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
      if (!token.equalsIgnoreCase(accessToken)) {
        return null;
      }
    }
    return claims;
  }

  /**
   * 获取请求头
   *
   * @return header
   */
  public static String getHeader() {
    return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
  }

  /**
   * 获取请求头
   *
   * @param request request
   * @return header
   */
  public static String getHeader(HttpServletRequest request) {
    return request.getHeader(HEADER);
  }

  /**
   * 解析jsonWebToken
   *
   * @param jsonWebToken jsonWebToken
   * @return Claims
   */
  public static Claims parseJWT(String jsonWebToken) {
    return JwtUtil.parseJWT(jsonWebToken);
  }

}
