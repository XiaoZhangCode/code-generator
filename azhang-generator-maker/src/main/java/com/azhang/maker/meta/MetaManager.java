package com.azhang.maker.meta;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {
    /**
     * meta属性 用于接受SON配置
     * volatile关键字修饰 确保多线程环境下的可见性
     */
    private static volatile Meta meta;
    /**
     * 私有化构造函数，防止外部实例化
     */
    private MetaManager() {

    }

    /**
     * 获取meta对象
     * @return meta对象
     */
    public static Meta getMetaObject() {
        if (meta == null) {
            synchronized (MetaManager.class) {
                if (meta == null) {
                    meta = initMeta();
                }
            }
        }
        return meta;
    }

    /**
     * @return 初始化meta
     */
    private static Meta initMeta() {
//        String string = ResourceUtil.readUtf8Str("meta.json");
        String string = ResourceUtil.readUtf8Str("springboot-init-meta.json");
        Meta newMeta = JSONUtil.toBean(string, Meta.class);
        // 校验和处理默认值
        MetaValidator.doValidAndFill(newMeta);
        return newMeta;
    }

    public static void main(String[] args) {
        Meta meta = getMetaObject();
        System.out.println(meta);
    }
}
