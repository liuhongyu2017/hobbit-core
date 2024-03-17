package org.hobbit.core.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serial;
import java.util.Objects;
import lombok.Data;

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    TreeNode treeNode = (TreeNode) o;
    return Objects.equals(title, treeNode.title) && Objects.equals(key,
        treeNode.key) && Objects.equals(value, treeNode.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), title, key, value);
  }
}
