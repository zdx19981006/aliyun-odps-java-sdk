package com.aliyun.odps.data;

import java.util.List;

import com.aliyun.odps.type.StructTypeInfo;
import com.aliyun.odps.type.TypeInfo;

/**
 * 一个简单 {@link Struct} 接口的实现
 *
 * Created by zhenhong.gzh on 16/8/22.
 */
public class SimpleStruct implements Struct {
  protected StructTypeInfo typeInfo;
  protected List<Object>  values;

  /**
   * A simple implements of {@link Struct}
   *
   * @param type
   *       type of the struct
   * @param values
   *       values of the struct
   *       be careful: the struct value list is a reference of this param
   */
  public SimpleStruct(StructTypeInfo type, List<Object> values) {
    if (type == null || values == null || values.size() != type.getFieldCount()) {
      throw new IllegalArgumentException("Illegal arguments for StructObject.");
    }

    this.typeInfo = type;
    this.values = values;
  }

  @Override
  public int getFieldCount() {
    return values.size();
  }

  @Override
  public String getFieldName(int index) {
    return typeInfo.getFieldNames().get(index);
  }

  @Override
  public TypeInfo getFieldTypeInfo(int index) {
    return typeInfo.getFieldTypeInfos().get(index);
  }

  @Override
  public Object getFieldValue(int index) {
    return values.get(index);
  }

  @Override
  public TypeInfo getFieldTypeInfo(String fieldName) {
    for (int i = 0; i < typeInfo.getFieldCount(); ++i) {
      if (typeInfo.getFieldNames().get(i).equalsIgnoreCase(fieldName)) {
        return typeInfo.getFieldTypeInfos().get(i);
      }
    }
    return null;
  }

  @Override
  public Object getFieldValue(String fieldName) {
    for (int i = 0; i < typeInfo.getFieldCount(); ++i) {
      if (typeInfo.getFieldNames().get(i).equalsIgnoreCase(fieldName)) {
        return values.get(i);
      }
    }
    return null;
  }

  @Override
  public List<Object> getFieldValues() {
    return values;
  }

}