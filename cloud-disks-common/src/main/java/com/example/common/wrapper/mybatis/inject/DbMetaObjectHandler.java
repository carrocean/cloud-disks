package com.example.common.wrapper.mybatis.inject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.common.enums.HasStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * 自动注入字段服务
 *
 * @author luoxiaodong
 * @since 1.0.0
 */
@Slf4j
@Component
public class DbMetaObjectHandler implements MetaObjectHandler {

    private final Date now = Date.from(Instant.now());

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");

        this.strictInsertFill(metaObject, "addTime", Date.class, now);
        this.strictInsertFill(metaObject, "hasStatus", Integer.class, HasStatusEnums.LEGAL.value);
        this.strictInsertFill(metaObject, "updateTime", Date.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", Date.class, now);
    }
}