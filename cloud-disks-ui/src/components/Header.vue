<template>
  <el-header class="the-header">
      <div class="logo">
        <!-- 应用图标和名字 -->
        <img src="@/assets/云.svg" alt="App Logo" />
        <span class="app-name">QST网盘</span>
      </div>

      <!-- 用户名和退出登录按钮 -->
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="user-name">
          <el-icon><User></User></el-icon> {{ userName }}
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
  </el-header>
</template>

<script setup>
import {ElMessage} from "element-plus";
import {onMounted, ref} from "vue";
import {checkUserLoginInfo} from "@/api/user.js";
import common from "@/libs/globalFunction/common.js";
import config from "@/config/index.js";
import router from "@/router/index.js";
import {User} from "@element-plus/icons-vue";

let userName = ref('张三')

function getUserName() {
  checkUserLoginInfo().then(res => {
    if(res.code === 200) {
      userName = res.data.userName;
    }
  })
}

function handleCommand(command) {
  if (command === 'logout') {
    common.removeCookies(config.tokenKeyName)
    router.push('/login')
  }
}

getUserName()
</script>

<style scoped>
.the-header {
  background-color: #409eff; /* 修改为所需的蓝色 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px; /* 可根据需要设置header的高度 */
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  margin-right: 20px;
}

.app-name {
  font-size: 25px; /* 设置应用名称的字体大小 */
  font-weight: bold; /* 设置为粗体 */
  color: white; /* 设置字体颜色，如果有需要 */
}

.user-name {
  font-size: 25px; /* 设置应用名称的字体大小 */
  color: white; /* 设置字体颜色，如果有需要 */
}
</style>