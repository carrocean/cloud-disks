<template>
  <div class="login-body">
    <div class="bg"></div>
    <div class="login-panel">
      <el-form class="login-register" :model="formData" :rules="rules" ref="formDataRef" @submit.prevent>
        <div class="login-title">QST网盘</div>
        <!-- 账号 -->
        <el-form-item prop="UserName">
          <el-input size="large" clearable placeholder="请输入账号" v-model.trim="formData.UserName" maxLength="150">
            <template #prefix>
              <span class="iconfont icon-account"></span>
            </template>
          </el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password" v-if="opType == 1">
          <el-input type="password" size="large" placeholder="请输入密码" v-model.trim="formData.password" show-password
            maxLength="150">
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>
        <!-- 注册 -->
        <div v-if="opType == 0 || opType == 2">
          <!-- 输入昵称 -->
          <el-form-item prop="nickName" v-if="opType == 0">
            <el-input size="large" placeholder="请输入昵称" v-model.trim="formData.nickName" maxlength="20">
              <template #prefix>
                <span class="iconfont icon-edit"></span>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="registerPassword" v-if="opType == 2 || opType == 0">
            <el-input type="password" size="large" placeholder="请输入密码" show-password
              v-model.trim="formData.registerPassword">
              <template #prefix>
                <span class="iconfont icon-password"></span>
              </template>
            </el-input>
          </el-form-item>
          <!-- 重复密码 -->
          <el-form-item prop="reRegisterPassword" v-if="opType == 2 || opType == 0">
            <el-input type="password" size="large" placeholder="请再次输入密码" show-password
              v-model.trim="formData.reRegisterPassword">
              <template #prefix>
                <span class="iconfont icon-password"></span>
              </template>
            </el-input>
          </el-form-item>
        </div>
        <!-- 注册密码,找回密码 -->

        <!-- 验证码 -->
        <!-- <el-form-item prop="checkCode">
          <div class="check-code-panel">
            <el-input
              size="large"
              placeholder="请输入验证码"
              v-model.trim="formData.checkCode"
            >
              <template #prefix>
                <span class="iconfont icon-checkcode"></span>
              </template>
            </el-input>
            <img
              :src="checkCodeUrl"
              class="check-code"
              @click="changeChekCode(1)"
            />
          </div>
        </el-form-item> -->
        <!-- 登录 -->
        <el-form-item v-if="opType == 1">
          <div class="rememberme-panel">
            <el-checkbox v-model="formData.rememberMe">记住我</el-checkbox>
          </div>
          <div class="no-account">
            <a href="#" class="a-link" @click="showPanel(2)">忘记密码</a>
            <a href="#" class="a-link" @click="showPanel(0)">注册</a>
          </div>
        </el-form-item>
        <!-- 忘记密码 -->
        <el-form-item v-if="opType == 2">
          <a href="#" class="a-link" @click="showPanel(1)">去登录？</a>
        </el-form-item>
        <el-form-item v-if="opType == 0">
          <a href="#" class="a-link" @click="showPanel(1)">已有账号？去登录</a>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="op-btn" size="large" @click="doSubmit">
            <span v-if="opType == 1">登录</span>
            <span v-if="opType == 0">注册</span>
            <span v-if="opType == 2">重置密码</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
// import md5 from "js-md5";
import {login, register} from "@/api/user.js"
import axios from 'axios';
import {ElMessage} from "element-plus";

const { proxy } = getCurrentInstance();
const router = useRouter();


const checkRePassword = (rule, value, callback) => {
  if (value !== formData.value.registerPassword) {
    callback(new Error(rule.message));
  } else {
    callback();
  }
};
var formData = ref({});
var formDataRef = ref();
const rules = {
  UserName: [
    { required: true, message: "请输入正确的账号" },
    // { validator: proxy.Verify.UserName, message: "请输入正确的账号" }

  ],
  password: [{ required: true, message: "请输入正确的密码" },
  { validator: proxy.Verify.password, message: "请输入正确的密码" }],
  nickName: [{ required: true, message: "请输入正确的昵称" }],
  registerPassword: [
    { required: true, message: "请输入正确的密码" },
    {
      validator: proxy.Verify.password,
      message: "密码只能为数字、字母和字符",
    },
  ],
  reRegisterPassword: [
    { required: true, message: "请再次输入正确的密码" },
    {
      validator: checkRePassword,
      message: "两次输入密码不一致",
    },
  ],
};




//操作类型0.注册 1.登录 2.忘记密码
const opType = ref(1);
const showPanel = (type) => {
  opType.value = type;
  restForm();
};
// const restForm = () => {
//   changeChekCode(0);
//   formDataRef.value.resetFields();
//   formData.value = {};
//   if (opType.value == 1) {
//     const cookieLoginInfo = proxy.VueCookies.get("loginInfo");
//     if (cookieLoginInfo) {
//       formData.value = cookieLoginInfo;
//     }
//   }
// };
//提交表单（登录、注册等）
// const doSubmit = () => {
//   if (opType.value == 1) {
//         loginApi(loginForm).then(res => {
//           console.log('login', res)
//           if (res.status === 0) {
//             ElMessage.success(res.message)
//             window.sessionStorage.setItem('token', res.token)
//             router.push('/Login')
//           }
//         }).catch(error => {
//           console.log(error);
//         })
//   }
//   if (opType.value == 0) {
//         registerApi(registerForm).then(res => {
//           if (res.status === 0) {
//             ElMessage.success(res.message)
//             showPanel(1);
//           }
//         }).catch(error => {
//           console.log(error);
//         })

//   }
  const doSubmit = async () => {
  try {
    let response = null;
    if (opType.value === 1) {
      // 登录操作
      login(formData.value).then(res=>{
        ElMessage.success(response.data.message);
        window.sessionStorage.setItem('token', response.data.token);
        router.push('/Framework');
      }).catch(err=>{
        //请求失败，做相应处理
      })
    } else if (opType.value === 0) {
      // 注册操作
      register(formData.value).then(res=>{
        ElMessage.success(response.data.message);
        showPanel(1); // 假设showPanel(1)会将表单切换到登录面板
      }).catch(err=>{
        //请求失败，做相应处理
      })
    }
    // 可以根据需要添加更多操作类型的情况
  } catch (error) {
    console.error('Error submitting form:', error);
    // 可以根据错误类型进行更详细的错误处理
  }
};
  // if(opType.value==1)
  // {
  //   setTimeout(() => {
  //   proxy.Message.success("登录成功");
  //   router.push("./Framework");
  // }, 500);

  // }
  // else if(opType.value==0)
  // {
  //   proxy.Message.success("注册成功，请登录");
  //   showPanel(1);
  // }
  // formDataRef.value.valuedate(async (valid) => {
  //   if (!valid) {
  //     return ;
  //   }
  //   let params = {};
  //   Object.assign(params, formData.value);
  //   //注册
  //   if (opType.value == 0 || opType.value == 2) {
  //     params.password = params.registerPassword;
  //     delete params.registerPassword;
  //     delete params.reRegisterPassword;
  //   }
  //   //登录
  //   if (opType.value == 1) {
  //     let cookieLoginInfo = proxy.VueCookies.get("loginInfo");
  //     let cookiePassword =
  //       cookieLoginInfo == null ? null : cookieLoginInfo.password;
  //     if (params.password !== cookiePassword) {
  //       params.password = md5(params.password);
  //     }
  //   }

  //   let url = null;
  //   if (opType.value == 0) {
  //     url = api.registe;
  //   } else if (opType.value == 1) {
  //     url = api.login;
  //   }
  //   let result = await proxy.Request({
  //     url: url,
  //     params: params,
  //     errorCallback: () => {
  //       changeChekCode(0);
  //     },
  //   });
  //   if (!result) {
  //     return;
  //   }
  //   //注册返回
  //   if (opType.value == 0) {
  //     proxy.Message.success("注册成功，请登录");
  //     showPanel(1);
  //   } else if (opType.value == 1) {
  //     if (params.rememberMe) {
  //       const loginInfo = {
  //         UserName: params.UserName,
  //         password: params.password,
  //         rememberMe: params.rememberMe,
  //       };
  //       proxy.VueCookies.set("loginInfo", loginInfo, "7d");
  //     } else {
  //       proxy.VueCookies.remove("loginInfo");
  //     }
  //     proxy.Message.success("登录成功");
  //     // 存储cookies
  //     proxy.VueCookies.set("userInfo", result.data, 0);
  //     const redirectUrl = route.query.redirectUrl || "/";
  //     router.push(redirectUrl);
  //   } else if (opType.value == 2) {
  //     proxy.Message.success("重置密码成功,请登录");
  //     showPanel(1);
  //   }
  // });
// };

</script>

<style lang="scss" scoped>
.login-body {
  height: calc(100vh);
  background-size: cover;
  background-image: url("../assets/login背景.jpg");
  // background-color: rgb(16, 131, 232);
  display: flex;
}

.bg {
  flex: 1;
  background-size: cover;
  background-position: center;
  background-size: 800px;
  background-repeat: no-repeat;
  // background-image: url("../assets/cropped-1920-1080-1064066.png");
}

.login-panel {
  width: 430px;
  margin-right: 15%;
  margin-top: calc((100vh - 500px) / 2);

  .login-register {
    padding: 25px;
    background: #fff;
    border-radius: 5px;

    .login-title {
      text-align: center;
      font-size: 18px;
      font-weight: bold;
      margin-bottom: 20px;
    }

    .send-email-panel {
      display: flex;
      width: 100%;
      justify-content: space-between;

      .send-mail-btn {
        margin-left: 5px;
      }
    }

    .rememberme-panel {
      width: 100%;
    }

    .no-account {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }

    .op-btn {
      width: 100%;
    }
  }
}

.check-code-panel {
  width: 100%;
  display: flex;

  .check-code {
    margin-left: 5px;
    cursor: pointer;
  }
}

.login-btn-qq {
  margin-top: 20px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;

  img {
    cursor: pointer;
    margin-left: 10px;
    width: 20px;
  }
}
</style>