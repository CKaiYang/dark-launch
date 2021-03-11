package com.chanbook.dark.launch.feature;

/**
 * 灰度特性
 * @param <T>
 */
public interface IDarkFeature<T> {
    /**
     * 灰度规则是否开启
     * @return
     */
    boolean enabled();

    /**
     * 灰度规则执行
     * @param target
     * @return
     */
    boolean dark(T target);
}
