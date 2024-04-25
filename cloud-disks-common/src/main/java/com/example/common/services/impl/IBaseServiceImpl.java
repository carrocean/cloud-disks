package com.example.common.services.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.entity.BaseEntity;
import com.example.common.enums.HasStatusEnums;
import com.example.common.repository.IBaseMapper;
import com.example.common.services.IBaseService;
import com.example.common.wrapper.RpcWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现基类
 *
 * @param <Entity>
 * @author luoxiaodong
 * @since 2018年11月20日 下午8:05:00
 */
@Transactional
public class IBaseServiceImpl<Entity extends BaseEntity, M extends IBaseMapper<Entity>> extends ServiceImpl<M, Entity>
		implements IBaseService<Entity> {

	private static final Logger log = LoggerFactory.getLogger(IBaseServiceImpl.class);

	@Autowired
	protected M mapper;

	@Override
	public void deleteByIds(@NotNull Long[] ids) {

		List<Long> idList = new ArrayList<Long>();
		CollectionUtils.addAll(idList, ids);

		log.debug("delete id list:{}", idList.size());

		mapper.deleteBatchIds(idList);
	}

	@Override
	public void update(Entity e) {
		mapper.updateById(e);
	}

	@Override
	public Entity findOne(Serializable id) {
		return this.getById(id);
	}

	@Override
	public void updateById(Entity e, @NotNull Long id) {
		e.setId(id);
		mapper.updateById(e);
	}

	@Override
	public boolean updateHasStatus(@NotNull Long id) {
		Entity e = mapper.selectById(id);

		int modStatus = e.getHasStatus() % 2;
		int realStatus = modStatus == 0 ? 1 : 0;
		e.setHasStatus(realStatus);
		return mapper.updateById(e) == 1;
	}

	@Override
	public List<Entity> findAll(RpcWrapper<Entity> wrapper) {
		return mapper.selectList(wrapper.toQueryWrapper());
	}

	@Override
	public List<Entity> findAll() {
		return mapper.selectList(null);
	}

	@Override
	public List<Entity> findAllByFieldProp(@NotNull String prop) {
		RpcWrapper<Entity> wrapper = RpcWrapper.build();
		wrapper.eq("field_prop", prop);
		return this.findAllByHasStatus(wrapper);
	}

	@Override
	public List<Entity> findAllByHasStatus(RpcWrapper<Entity> wrapper) {
		wrapper.eq("has_status", HasStatusEnums.LEGAL.value);
		return mapper.selectList(wrapper.toQueryWrapper());
	}

	@Override
	public List<Entity> findAllByHasStatus() {
		RpcWrapper<Entity> wrapper = RpcWrapper.build();
		return this.findAllByHasStatus(wrapper);
	}

	@Override
	public Entity findEntityById(Serializable id) {
		return this.getById(id);
	}

	@Override
	public List<Entity> findTop(int number, RpcWrapper<Entity> wrapper) {
		IPage<Entity> page = new Page<Entity>(0, number);
		page = mapper.selectPage(page, wrapper.toQueryWrapper());
		return page.getRecords();
	}

	@Override
	public void deleteByWrapper(RpcWrapper<Entity> wrapper) {
		mapper.delete(wrapper.toQueryWrapper());
	}

	@Override
	public List<Entity> findAllByApplicationId(@NotNull String applicationId, RpcWrapper<Entity> wrapper) {
		return this.findAllByTenantIdAndApplicationId(null, applicationId, wrapper);
	}

	@Override
	public List<Entity> findAllByApplicationId(@NotNull String applicationId) {
		return this.findAllByApplicationId(applicationId, null);
	}

	@Override
	public List<Entity> findAllByTenantId(@NotNull String tenantId, RpcWrapper<Entity> wrapper) {
		return this.findAllByTenantIdAndApplicationId(tenantId, null, wrapper);
	}

	@Override
	public List<Entity> findAllByTenantId(@NotNull String tenantId) {
		return this.findAllByTenantId(tenantId, null);
	}

	@Override
	public List<Entity> findAllByTenantIdAndApplicationId(@NotNull String tenantId, @NotNull String applicationId) {
		return this.findAllByTenantIdAndApplicationId(tenantId, applicationId, null);
	}

	@Override
	public List<Entity> findAllByTenantIdAndApplicationId(String tenantId, String applicationId,
			RpcWrapper<Entity> wrapper) {

		wrapper = wrapper == null ? RpcWrapper.build() : wrapper;

		if (StringUtils.isNotBlank(tenantId)) {
			wrapper.eq("tenant_id", tenantId);
		}
		if (StringUtils.isNotBlank(applicationId)) {
			wrapper.eq("application_id", applicationId);
		}

		return mapper.selectList(wrapper.toQueryWrapper());
	}

	@Override
	public Entity findById(Serializable id) {
		return this.getById(id);
	}

	@Override
	public List<Entity> findByIds(List<String> resourceIds) {
		return mapper.selectBatchIds(resourceIds);
	}

	@Override
	public Page<Entity> pageRpc(Page<Entity> page, RpcWrapper<Entity> wrapper) {
		if (wrapper == null) {
			wrapper = RpcWrapper.build();
		}
		return mapper.selectPage(page, wrapper.toQueryWrapper());
	}

	@Override
	public Entity findOne(RpcWrapper<Entity> wrapper) {
		if (wrapper == null) {
			wrapper = RpcWrapper.build();
		}
		return mapper.selectOne(wrapper.toQueryWrapper());
	}

}
