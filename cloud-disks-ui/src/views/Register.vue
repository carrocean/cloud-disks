<template>
  <div class="login-body">
    <div class="bg"></div>
    <div class="login-panel">
      <el-form class="login-register" :model="formData" :rules="rules" ref="formDataRef" @submit.prevent>
        <div class="login-title">QST网盘</div>
        <!-- 账号 -->
        <el-form-item prop="userName">
          <el-input size="large" clearable placeholder="请输入账号" v-model.trim="formData.userName" maxLength="150">
            <template #prefix>
              <span class="iconfont icon-account"></span>
            </template>
          </el-input>
        </el-form-item>
        <!-- 输入昵称 -->
        <el-form-item prop="nickName">
          <el-input size="large" placeholder="请输入昵称" v-model.trim="formData.nickName" maxlength="20">
            <template #prefix>
              <span class="iconfont icon-edit"></span>
            </template>
          </el-input>
        </el-form-item>
        <!-- 输入密码 -->
        <el-form-item prop="pwd">
          <el-input type="password" size="large" placeholder="请输入密码" show-password
                    v-model.trim="formData.pwd">
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <a href="#" class="a-link" @click="toLogin">已有账号？去登录</a>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="op-btn" size="large" @click="doSubmit">
            <span>注册</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {register} from "@/api/user.js"
import {getCurrentInstance, ref} from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const formData = ref({}); // 表单数据

// 表单验证规则
const rules = {
  userName: [{required: true, message: "请输入正确的账号"},],
  pwd: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    {min: 8, message: '密码长度至少为8位', trigger: 'blur'},
    {max: 20, message: '密码长度最多为20位', trigger: 'blur'},
  ],
  nickName: [{required: true, message: "请输入正确的昵称"}],
};

/**
 * 跳转到登录页面
 */
function toLogin() {
  router.push('/login')
}

/**
 * 提交表单方法
 */
function doSubmit() {
  register(formData.value).then(res => {
    if (res.code === 200) {
      ElMessage.success("注册成功");
      toLogin()
    } else {
      ElMessage.error(res.message);
    }
  }).catch(err => {
    //请求失败，做相应处理
    console.log(err)
  })
}

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