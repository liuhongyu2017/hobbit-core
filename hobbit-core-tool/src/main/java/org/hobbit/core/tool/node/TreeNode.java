package org.hobbit.core.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serial;
import java.util.Objects;
import lombok.Data;
import org.hobbit.core.tool.utils.Func;

/**
 * 树型节点类
 *
 * @author smallchill
 */
@Data
public class TreeNode extends BaseNode<TreeNode> {

  @Serial
  private static final long serialVersionUID = 1L;

  private String title;

  @JsonSerialize(using = ToStringSerializer.class)
  private Long key;

  @JsonSerialize(using = ToStringSerializer.class)
  private Long value;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    TreeNode other = (TreeNode) obj;
    return Func.equals(this.getId(), other.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, parentId);
  }

}
