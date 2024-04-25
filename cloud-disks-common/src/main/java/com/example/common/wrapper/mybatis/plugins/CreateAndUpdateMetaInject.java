package com.example.common.wrapper.mybatis.plugins;//package com.alinesno.infra.common.facade.wrapper.mybatis.plugins;
//
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.http.HttpStatus;
//import com.alinesno.infra.common.facade.account.CurrentAccountBean;
//import com.alinesno.infra.common.facade.account.CurrentAccountHandle;
//import com.alinesno.infra.common.facade.exception.ServiceException;
//import com.alinesno.infra.common.facade.mapper.entity.BaseEntity;
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Date;
//
///**
// * MyBaits-Plus 时间和更新对比
// *
// * @author luoxiaodong
// * @version 1.0.0
// */
//public class CreateAndUpdateMetaInject implements MetaObjectHandler {
//
//	private static final Logger log = LoggerFactory.getLogger(CreateAndUpdateMetaInject.class);
//
//	@Override
//	public void insertFill(MetaObject metaObject) {
//		try {
//			if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
//				BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
//
//				Date current = ObjectUtil.isNotNull(baseEntity.getAddTime()) ? baseEntity.getAddTime() : new Date();
//
//				baseEntity.setAddTime(current);
//				baseEntity.setUpdateTime(current);
//
//				long userId = baseEntity.getOperatorId() != 0 ? baseEntity.getOperatorId() : getUserId();
//
//				// 当前已登录 且 创建人为空 则填充
//				baseEntity.setOperatorId(userId);
//
//				// 当前已登录 且 更新人为空 则填充
//				baseEntity.setLastUpdateOperatorId(userId);
//			}
//		} catch (Exception e) {
//			throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
//		}
//	}
//
//	@Override
//	public void updateFill(MetaObject metaObject) {
//		try {
//			if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
//
//				BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
//				Date current = new Date();
//
//				// 更新时间填充(不管为不为空)
//				baseEntity.setUpdateTime(current);
//				Long userId = getUserId();
//
//				// 当前已登录 更新人填充(不管为不为空)
//				if (userId != 0) {
//					baseEntity.setLastUpdateOperatorId(userId);
//				}
//			}
//		} catch (Exception e) {
//			throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
//		}
//	}
//
//	/**
//	 * 获取登录用户名
//	 */
//	private Long getUserId() {
//		CurrentAccountBean loginUser;
//		try {
//			loginUser = CurrentAccountHandle.getCurrentAccount();
//		} catch (Exception e) {
//			log.warn("自动注入警告 => 用户未登录");
//			return null;
//		}
//		return ObjectUtil.isNotNull(loginUser) ? loginUser.getId()  : null;
//	}
//
//}
