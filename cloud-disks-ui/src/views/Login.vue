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
        <!-- 密码 -->
        <el-form-item prop="pwd" v-if="opType == 1">
          <el-input type="password" size="large" placeholder="请输入密码" v-model.trim="formData.pwd" show-password
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
          <el-form-item prop="pwd" v-if="opType == 2 || opType == 0">
            <el-input type="password" size="large" placeholder="请输入密码" show-password
                      v-model.trim="formData.pwd">
              <template #prefix>
                <span class="iconfont icon-password"></span>
              </template>
            </el-input>
          </el-form-item>

        </div>
        <!-- 登录 -->
        <el-form-item v-if="opType == 1">
          <div class="rememberme-panel">
            <el-checkbox>记住我</el-checkbox>
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
import {useRoute, useRouter} from "vue-router";
import {ref, reactive, getCurrentInstance, nextTick} from "vue";
// import md5 from "js-md5";
import {login, register} from "@/api/user.js"
import axios from 'axios';
import {ElMessage} from "element-plus";

const {proxy} = getCurrentInstance();
const router = useRouter();

//操作类型0.注册 1.登录 2.忘记密码
const opType = ref(1);
const showPanel = (type) => {
  opType.value = type;
  // restForm();
};
var formData = ref({});
var formDataRef = ref();
const rules = {
  userName: [
    {required: true, message: "请输入正确的账号"},
    // { validator: proxy.Verify.UserName, message: "请输入正确的账号" }

  ],
  pwd:
    [{required: true, message: "请输入正确的密码"},
      {validator: proxy.Verify.password, message: "请输入正确的密码"}],

  nickName: [{required: true, message: "请输入正确的昵称"}],
};

const doSubmit = async () => {
  try {
    if (opType.value === 1) {
      // 登录操作
      login(formData.value).then(res => {
        console.log(res)
        if (res.code === 200) {
          ElMessage.success("登录成功");
          window.sessionStorage.setItem('token', res.data.token);
          router.push('/main/all');
        } else {
          ElMessage.error("账号或密码错误");
        }

      }).catch(err => {
        //请求失败，做相应处理
        console.log(err)
      })
    } else if (opType.value === 0) {
      // 注册操作

      register(formData.value).then(res => {
        console.log(res)
        if (res.code === 200) {
          ElMessage.success("注册成功");
          showPanel(1); // 假设showPanel(1)会将表单切换到登录面板
        } else {

          ElMessage.error("注册失败");
        }
      }).catch(err => {
        //请求失败，做相应处理
        console.log(err)
      })
    }
    // 可以根据需要添加更多操作类型的情况
  } catch (error) {
    console.error('Error submitting form:', error);
    // 可以根据错误类型进行更详细的错误处理
  }
};


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