package com.example.common.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.dto.FieldDto;
import com.example.common.dto.HasStatusVo;
import com.example.common.entity.BaseEntity;
import com.example.common.pageable.DatatablesPageBean;
import com.example.common.pageable.TableDataInfo;
import com.example.common.response.AjaxResult;
import com.example.common.response.HttpStatus;
import com.example.common.services.IBaseService;
import com.example.common.wrapper.RpcWrapper;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * BaseController类
 * 对外REST接口基类
 * 提供通用的操作方法和响应处理
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@Getter
public abstract class BaseController<E extends BaseEntity, S extends IBaseService<E>> extends SuperController {

	@Autowired
	protected S feign;

	@Autowired
	private HttpServletRequest request ;

	/**
	 * 驼峰转换工具
	 */
	private static final Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

	/**
	 * 更新实体状态
	 *
	 * @param id 实体ID
	 * @return 更新结果
	 */
	@GetMapping("modifyHasStatus")
	protected boolean modifyHasStatus(@RequestParam("id") Long id) {
		return feign.updateHasStatus(id);
	}

	/**
	 * 查询状态正常列表
	 *
	 * @return 查询结果
	 */
	@ApiOperation("查询状态正常列表")
	@ResponseBody
	@GetMapping("findAllHasStatus")
	protected AjaxResult findAllHasStatus() {
		List<E> list = this.getFeign().findAllByHasStatus();
		return toAjax(list);
	}

	/**
	 * 根据应用程序ID查询实体列表
	 *
	 * @param applicationId 应用程序ID
	 * @return 查询结果
	 */
	@GetMapping("findAllByApplicationId")
	protected List<E> findAllByApplicationId(@RequestParam("applicationId") String applicationId) {
		return null;
	}

	/**
	 * 根据租户ID查询实体列表
	 *
	 * @param tenantId 租户ID
	 * @return 查询结果
	 */
	@GetMapping("findAllByTenantId")
	protected List<E> findAllByTenantId(@RequestParam("tenantId") String tenantId) {
		return null;
	}

	/**
	 * 进行分页查询
	 *
	 * @param model          数据模型
	 * @param feign          业务服务
	 * @param page           分页信息
	 * @param wrapper        RPC包装器
	 * @param pageableResult 分页查询结果
	 * @return 表格数据信息
	 */
	protected TableDataInfo toPage(Model model, S feign, DatatablesPageBean page, RpcWrapper<E> wrapper, Page<E> pageableResult) {
		RpcWrapper<E> restWrapper = page.buildWrapper(request);

		if (pageableResult == null) {
			pageableResult = new Page<E>(page.getPageNum(), page.getPageSize());
			pageableResult = feign.pageRpc(pageableResult, restWrapper);
		}

		TableDataInfo dInfo = new TableDataInfo();
		dInfo.setCode(HttpStatus.SUCCESS);
		dInfo.setMsg("查询成功");
		dInfo.setRows(pageableResult.getRecords());
		dInfo.setTotal((int) pageableResult.getTotal());

		return dInfo;
	}

	/**
	 * 进行分页查询
	 *
	 * @param model 数据模型
	 * @param feign 业务服务
	 * @param page  分页信息
	 **/
	protected TableDataInfo toPage(Model model, S feign, DatatablesPageBean page, RpcWrapper<E> wrapper) {
		return toPage(model, feign, page, wrapper, null);
	}

	protected TableDataInfo toPage(Model model, S feign, DatatablesPageBean page) {
		return toPage(model, feign, page, null, null);
	}

	/**
	 * 保存实体
	 *
	 * @param model  数据模型
	 * @param entity 实体对象
	 * @return AjaxResult
	 * @throws Exception 异常
	 */
	@ApiOperation("保存实体")
	@ResponseBody
	@PostMapping("save")
	public AjaxResult save(Model model, @RequestBody E entity) throws Exception {
		getFeign().save(entity);
		return ok();
	}

	/**
	 * 通过ID列表查询实体
	 *
	 * @param list ID列表
	 * @return AjaxResult
	 */
	@ApiOperation("通过id列表查询")
	@ResponseBody
	@PostMapping("findIds")
	public AjaxResult findIds(@RequestBody List<String> list) {
		List<E> data = getFeign().listByIds(list);
		return AjaxResult.success(data);
	}

	/**
	 * 通过ID删除实体
	 *
	 * @param ids ID字符串，多个ID用逗号分隔
	 * @return AjaxResult
	 */
	@ApiOperation("通过id删除")
	@ResponseBody
	@DeleteMapping("delete/{ids}")
	public AjaxResult delete(@PathVariable String ids) {
		if (StringUtils.isBlank(ids)) {
			return error();
		}

		String[] rowsId = ids.split(",");
		Long[] longIds = new Long[rowsId.length];

		for (int i = 0; i < rowsId.length; i++) {
			longIds[i] = Long.parseLong(rowsId[i]);
		}

		if (rowsId.length > 0) {
			getFeign().deleteByIds(longIds);
		}
		return ok();
	}

	/**
	 * 通过ID获取实体
	 *
	 * @param id ID
	 * @return AjaxResult
	 */
	@ApiOperation("通过id获取实体")
	@ResponseBody
	@GetMapping("detail/{id}")
	public AjaxResult detail(@PathVariable String id) {
		log.debug("id = {}", id);
		E bean = getFeign().findEntityById(id);
		return toAjax(bean);
	}

	/**
	 * 更新实体
	 *
	 * @param model  数据模型
	 * @param entity 实体对象
	 * @return AjaxResult
	 * @throws Exception 异常
	 */
	@ApiOperation("更新实体")
	@ResponseBody
	@PutMapping("modify")
	public AjaxResult update(Model model, @RequestBody E entity) throws Exception {
		if (entity != null) {
			E oldBean = getFeign().findOne(entity.getId());
			BeanUtil.copyProperties(entity, oldBean, CopyOptions.create().setIgnoreNullValue(true));
			S f = getFeign();
			f.update(oldBean);
		} else {
			save(model, null);
		}
		return ok();
	}

	/**
	 * 通过ID查询实体
	 *
	 * @param id ID
	 * @return AjaxResult
	 */
	@ApiOperation("通过id查询实体")
	@ResponseBody
	@GetMapping("findOne")
	public AjaxResult findOne(String id) {
		E e = getFeign().findOne(id);
		return toAjax(e);
	}

	/**
	 * 实体数量统计
	 *
	 * @return AjaxResult
	 */
	@ApiOperation("实体数量统计")
	@ResponseBody
	@GetMapping("count")
	public AjaxResult count() {
		long c = getFeign().count();
		return toAjax(c);
	}

	/**
	 * 通过ID删除实体
	 *
	 * @param id ID
	 * @return AjaxResult
	 */
	@ApiOperation("通过id删除实体")
	@ResponseBody
	@GetMapping("deleteById")
	public AjaxResult deleteById(String id) {
		getFeign().removeById(id);
		return ok();
	}

	/**
	 * 修改实体状态
	 *
	 * @param status 状态对象
	 * @return AjaxResult
	 */
	@ApiOperation("修改实体状态")
	@ResponseBody
	@PutMapping("changeStatus")
	public AjaxResult changeStatus(@RequestBody HasStatusVo status) {
		Assert.notNull(status, "对象参数为空.");
		Assert.hasLength(status.getId(), "对象参数为空.");
		boolean b = getFeign().updateHasStatus(Long.parseLong(status.getId()));
		return toAjax(b);
	}

	/**
	 * 修改某字段的值
	 *
	 * @param field 字段对象
	 * @return AjaxResult
	 */
	@ApiOperation("修改某字段的值")
	@ResponseBody
	@PostMapping("changeField")
	public AjaxResult changeFiled(@RequestBody FieldDto field) {
		log.debug("field = {}", field);
		Assert.isTrue(field != null && field.getId() != null, "实体对象为空");
		Assert.hasLength(field.getField(), "字段为空");

		UpdateWrapper<E> updateWrapper = new UpdateWrapper<E>();
		updateWrapper.set(converter.convert(field.getField()), field.getValue());
		updateWrapper.eq("id", field.getId());

		boolean b = this.getFeign().update(updateWrapper);

		return b ? ok() : error();
	}

	/**
	 * 实体转换为
	 * Map对象
	 *
	 * @param entity 实体对象
	 * @return AjaxResult
	 */
	@ApiOperation("实体转换为Map对象")
	@ResponseBody
	@PostMapping("entityToMap")
	public AjaxResult entityToMap(@RequestBody E entity) {
		Map<String, Object> map = BeanUtil.beanToMap(entity);
		return toAjax(map);
	}

	/**
	 * Map对象转换为实体
	 *
	 * @param map Map对象
	 * @return AjaxResult
	 */
	@ApiOperation("Map对象转换为实体")
	@ResponseBody
	@PostMapping("mapToEntity")
	public AjaxResult mapToEntity(@RequestBody Map<String, Object> map) {
		E entity = BeanUtil.toBean(map, getFeign().getEntityClass()) ;
		return toAjax(entity);
	}

	/**
	 * 获取业务服务对象
	 *
	 * @return 业务服务对象
	 */
	protected S getFeign() {
		return feign;
	}

}


