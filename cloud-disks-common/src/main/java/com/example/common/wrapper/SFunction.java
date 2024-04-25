package com.example.common.wrapper;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 函数式实体类字段引用接口，用于实现强类型的查询语句封装
 *
 * @param <T>
 * @param <R>
 * @author luoxiaodong
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
