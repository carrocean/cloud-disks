<template>
  <Header v-if="isHeaderShow" id="headWrapper"></Header>
  <router-view class="main-content"></router-view>
  <Footer v-if="isFooterShow"></Footer>
</template>


<script setup>
import {computed, reactive} from "vue";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import {useRoute} from "vue-router";
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'

const locae = zhCn;

// 使用useRoute获取当前路由
const route = useRoute();

// 计算属性：头部是否显示
const isHeaderShow = computed(() => {
  const routerNameList = ['login', 'register'];
  return !routerNameList.includes(route.name);
});

// 计算属性：底部是否显示
const isFooterShow = computed(() => {
  const routerNameList = ['file', 'login', 'register'];
  return !routerNameList.includes(route.name);
});

const config = reactive({
  max: 1,
});
</script>

<style scoped>
</style>
