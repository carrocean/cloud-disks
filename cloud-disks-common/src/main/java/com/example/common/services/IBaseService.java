package com.example.common.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.wrapper.RpcWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * 业务服务基类，统一使用同一前缀，避免与其它混乱，主要为以下
 * 
 * 查询 --> find* 更新--> update* 删除--> delete*
 * 
 * @author luoxiaodong
 * @since 2018年11月20日 下午7:51:58
 */
public interface IBaseService<Entity> extends IService<Entity> {

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	void deleteByIds(Long[] ids);

	/**
	 * 条件删除
	 * 
	 * @param wrapper
	 */
	void deleteByWrapper(RpcWrapper<Entity> wrapper);

	/**
	 * 更新实体，这里只更新部分字段，并不会全部更新
	 */
	void update(Entity e);

	/**
	 * 更新实体，这里只更新部分字段，并不会全部更新
	 * 
	 * @param id
	 */
	void updateById(Entity e, Long id);

	/**
	 * 修改状态
	 * 
	 * @param id
	 * @return
	 */
	boolean updateHasStatus(Long id);

	/**
	 * 通过id进行查询
	 * 
	 * @param resourceIds
	 * @return
	 */
	List<Entity> findByIds(List<String> resourceIds);

	/**
	 * 查询一个实例
	 * 
	 * @param restWrapper
	 * @return
	 */
	Entity findOne(RpcWrapper<Entity> restWrapper);

	/**
	 * 查询单个
	 * 
	 * @param id
	 * @return
	 */
	Entity findOne(Serializable id);

	/**
	 * 查询单个
	 * 
	 * @param id
	 * @return
	 */
	Entity findById(Serializable id);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	List<Entity> findAll();

	/**
	 * 通过属性查询所有
	 * 
	 * @param prop
	 * @return
	 */
	List<Entity> findAllByFieldProp(String prop);

	/**
	 * 通过状态查询
	 * 
	 * @param prop
	 * @return
	 */
	List<Entity> findAllByHasStatus(RpcWrapper<Entity> wrapper);

	/**
	 * 通过状态查询
	 * 
	 * @return
	 */
	List<Entity> findAllByHasStatus();

	/**
	 * 条件查询
	 * 
	 * @param wrapper
	 * @return
	 */
	List<Entity> findAll(RpcWrapper<Entity> wrapper);

	/**
	 * 通过ID查询
	 * 
	 * @param <ID>
	 * @param id
	 * @return
	 */
	Entity findEntityById(Serializable id);

	/**
	 * 找出前几条数据
	 * 
	 * @param i
	 * @return
	 */
	List<Entity> findTop(int number, RpcWrapper<Entity> wrapper);

	/**
	 * 通过应用查询
	 * 
	 * @param applicationId
	 * @return
	 */
	List<Entity> findAllByApplicationId(String applicationId, RpcWrapper<Entity> wrapper);

	/**
	 * 查询应用
	 * 
	 * @param applicationId
	 * @param wrapper
	 * @return
	 */
	List<Entity> findAllByApplicationId(String applicationId);

	/**
	 * 查询租户信息
	 * 
	 * @param tenantId
	 * @return
	 */
	List<Entity> findAllByTenantId(String tenantId, RpcWrapper<Entity> wrapper);

	/**
	 * 查询租户
	 * 
	 * @param tenantId
	 * @param wrapper
	 * @return
	 */
	List<Entity> findAllByTenantId(String tenantId);

	/**
	 * 查询租户所属应用
	 * 
	 * @param tenantId
	 * @param applicationId
	 * @return
	 */
	List<Entity> findAllByTenantIdAndApplicationId(String tenantId, String applicationId);

	/**
	 * 查询租户所属应用
	 * 
	 * @param tenantId
	 * @param applicationId
	 * @param wrapper
	 * @return
	 */
	List<Entity> findAllByTenantIdAndApplicationId(String tenantId, String applicationId, RpcWrapper<Entity> wrapper);

	/**
	 * 分页处理
	 * 
	 * @param pageableResult
	 * @param wrapper
	 * @return
	 */
	Page<Entity> pageRpc(Page<Entity> pageableResult, RpcWrapper<Entity> wrapper);

}
