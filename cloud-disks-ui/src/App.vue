
<template>
  <el-config-provider :locale="locae" :message="config">
    <RouterView />
  </el-config-provider>
</template>


<script setup>
import { reactive } from "vue";
import zhCn from "element-plus/es/locale/lang/zh-cn";
const locae = zhCn;
const config = reactive({
  max: 1,
});
</script>

<style scoped>
</style>
