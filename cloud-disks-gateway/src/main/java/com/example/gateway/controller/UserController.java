package com.example.gateway.controller;

import com.example.common.constants.SpringInstanceScope;
import com.example.common.pageable.DatatablesPageBean;
import com.example.common.pageable.TableDataInfo;
import com.example.common.rest.BaseController;
import com.example.entity.UserEntity;
import com.example.service.IUserService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author Ocean
 * @version 1.0.0
 */
@Api(tags = "用户管理")
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/cloud/disks/user")
public class UserController extends BaseController<UserEntity, IUserService> {

    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign(), page);
    }


    @Override
    public IUserService getFeign() {
        return this.userService;
    }
}