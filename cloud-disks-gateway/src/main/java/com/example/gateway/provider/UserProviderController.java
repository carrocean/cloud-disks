package com.example.gateway.provider;

import com.example.common.response.AjaxResult;
import com.example.dto.UserDto;
import com.example.entity.UserEntity;
import com.example.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/cloud/disks/user")
public class UserProviderController extends BaseProvider {

    @Autowired
    private IUserService userService ;

    /**
     * 用户注册信息，用于前端业务用户注册
     * @param dto
     * @return
     */
    @PostMapping("/registerAccount")
    public UserDto registerAccount(@RequestBody UserDto dto){

        String loginName = dto.getUserName();
        String password = dto.getPwd();

        UserDto b = userService.registAccount(loginName, password) ;
        log.debug("registerAccount = {}" , b);

        return b;
    }

    /**
     * 用户登陆
     * @return
     */
    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto loginBodyDto){

        UserDto dto = userService.loginAccount(loginBodyDto.getUserName() , loginBodyDto.getPwd()) ;
        log.debug("login account = {}" , dto);

        return dto ;
    }

    /**
     * 查询账户
     *
     * @param ids
     * @return
     */
    public List<UserDto> findAccountEntityByIds(List<String> ids){
        return null ;
    }


    /**
     * 通过id查询用户
     *
     * @param id
     * @return
     */
    public UserDto findById(String id){
        return null ;
    }

    /**
     * 通过用户名查询用户
     *
     * @param loginName
     * @return
     */
    @GetMapping("/findByLoginName")
    public UserDto findByLoginName(String loginName) {
        Assert.hasLength(loginName , "用户名称为空");

        UserEntity e = userService.findByLoginName(loginName) ;

        UserDto dto = new UserDto() ;
        BeanUtils.copyProperties(e , dto);
        dto.setPwd(null);

        return dto ;
    }

    /**
     * 更新用户密码
     * @param pwdDto
     * @return
     */
    public AjaxResult updatePwd(UserDto pwdDto) {
        return AjaxResult.success();
    }
}
