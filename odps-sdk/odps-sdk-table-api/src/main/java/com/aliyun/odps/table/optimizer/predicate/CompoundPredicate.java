package com.aliyun.odps.table.optimizer.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.aliyun.odps.utils.StringUtils;
import com.google.common.collect.ImmutableList;

/**
 * @author dingxin (zhangdingxin.zdx@alibaba-inc.com)
 */
public class CompoundPredicate extends Predicate {

  public enum Operator {
    /**
     * 复合谓词运算符
     */
    AND("and"),
    OR("or"),
    NOT("not");
    private final String description;

    Operator(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  private final Operator logicalOperator;
  private final List<Predicate> predicates;

  public CompoundPredicate(Operator logicalOperator) {
    this(logicalOperator, new ArrayList<>());
  }

  public CompoundPredicate(Operator logicalOperator, List<Predicate> predicates) {
    super(PredicateType.COMPOUND);
    this.logicalOperator = logicalOperator;
    this.predicates =
        predicates.stream().filter(this::validatePredicate).collect(Collectors.toList());
    if (logicalOperator == Operator.NOT && predicates.size() > 1) {
      throw new IllegalArgumentException("NOT operator should only have one operand");
    }
  }

  public static CompoundPredicate and(Predicate... predicates) {
    return new CompoundPredicate(Operator.AND,
                                 Arrays.stream(predicates).collect(Collectors.toList()));
  }

  public static CompoundPredicate or(Predicate... predicates) {
    return new CompoundPredicate(Operator.OR,
                                 Arrays.stream(predicates).collect(Collectors.toList()));
  }

  public static CompoundPredicate not(Predicate predicates) {
    return new CompoundPredicate(Operator.NOT, ImmutableList.of(predicates));
  }

  public void addPredicate(Predicate predicate) {
    if (validatePredicate(predicate)) {
      predicates.add(predicate);
    }
  }

  @Override
  public String toString() {
    if (predicates.isEmpty()) {
      return Predicate.NO_PREDICATE.toString();
    }

    String opStr = logicalOperator.getDescription();
    StringBuilder sb = new StringBuilder();

    // 对于 NOT 运算符，我们确保只有一个操作数
    if (logicalOperator == Operator.NOT) {
      sb.append(opStr).append(" ");
      Predicate predicate = predicates.get(0);
      if (predicate instanceof CompoundPredicate) {
        sb.append('(').append(predicate).append(')');
      } else {
        sb.append(predicate.toString());
      }
      return sb.toString();
    }

    for (int i = 0; i < predicates.size(); i++) {
      Predicate currentPredicate = predicates.get(i);
      if (currentPredicate instanceof CompoundPredicate
          && ((CompoundPredicate) currentPredicate).logicalOperator != this.logicalOperator) {
        sb.append('(').append(currentPredicate).append(')');
      } else {
        sb.append(currentPredicate.toString());
      }

      if (i < predicates.size() - 1) {
        sb.append(" ").append(opStr).append(" ");
      }
    }

    return sb.toString();
  }
}
