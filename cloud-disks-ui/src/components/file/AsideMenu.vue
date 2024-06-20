<template>
  <div class="side-menu-wrapper">
    <!-- collapse 属性：控制菜单收缩展开 -->
    <el-menu
        class="side-menu"
        :default-active="activeIndex"
        :router="true"
        :collapse="isCollapse"
    >
      <el-sub-menu index="myFile" class="my-file">
        <template #title>
          <el-icon :size="20">
            <Files></Files>
          </el-icon>
          <span>我的文件</span>
        </template>
        <el-menu-item
            index="0"
            :route="{ name: 'file', query: { fileType: 0, filePath: '/' } }"
        >
          <el-icon :size="20">
            <Menu></Menu>
          </el-icon>
          <span>全部</span>
        </el-menu-item>
        <el-menu-item
            index="1"
            :route="{ name: 'file', query: { fileType: 1 } }"
        >
          <el-icon :size="20">
            <Picture></Picture>
          </el-icon>
          <span>图片</span>
        </el-menu-item>
        <el-menu-item
            index="2"
            :route="{ name: 'file', query: { fileType: 2 } }"
        >
          <el-icon :size="20">
            <Document></Document>
          </el-icon>
          <span>文档</span>
        </el-menu-item>
        <el-menu-item
            index="3"
            :route="{ name: 'file', query: { fileType: 3 } }"
        >
          <el-icon :size="20">
            <VideoCamera></VideoCamera>
          </el-icon>
          <span>视频</span>
        </el-menu-item>
        <el-menu-item
            index="4"
            :route="{ name: 'file', query: { fileType: 4 } }"
        >
          <el-icon :size="20">
            <Headset></Headset>
          </el-icon>
          <span>音乐</span>
        </el-menu-item>
        <el-menu-item
            index="5"
            :route="{ name: 'file', query: { fileType: 5 } }"
        >
          <el-icon :size="20">
            <TakeawayBox></TakeawayBox>
          </el-icon>
          <span>其他</span>
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item
          index="6"
          :route="{ name: 'recycle', query: { fileType: 6 } }"
          class="recovery"
      >
        <el-icon :size="20">
          <Delete></Delete>
        </el-icon>
        <span>回收站</span>
      </el-menu-item>
      <el-menu-item
          index="8"
          :route="{ name: 'file', query: { fileType: 8, filePath: '/' } }"
          class="my-share"
      >
        <el-icon :size="20">
          <Share></Share>
        </el-icon>
        <span>我的分享</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import {Delete, Document, Files, Headset, Menu, Picture, Share, TakeawayBox, VideoCamera} from "@element-plus/icons-vue";
import {computed, onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import config from "@/config/index.js";

const route = useRoute()
const isCollapse = ref(false) //  控制菜单收缩展开
const myFileMenuMap = ref({ // 菜单 index 和名称 Map
  0: '全部',
  1: '图片',
  2: '文档',
  3: '视频',
  4: '音乐',
  5: '其他',
  6: '回收站',
  8: '我的分享'
})
// 当前激活菜单的 index
const activeIndex = computed(() => route.query.fileType ? String(route.query.fileType) : String(0)); //  获取当前路由参数中包含的文件类型

watch(activeIndex, (newValue) => {
  document.title = `${myFileMenuMap.value[Number(newValue)]} - ${config.siteName}`
})

onMounted(() => {
  document.title = `${myFileMenuMap.value[Number(activeIndex)]} - ${config.siteName}`
})


</script>

<style lang="stylus" scoped>

.side-menu-wrapper {
  position: relative;
  height: calc(100vh - 61px);
  padding-right: 11px;

  .side-menu {
  // 高度设置为屏幕高度减去顶部导航栏的高度 height: calc(100vh - 127px);
    overflow: auto;
  // 调整滚动条样式 setScrollbar(6 px, transparent, #C0C4CC);
    .my-file, .recovery {
      box-shadow: 0 4px 12px 0 $BorderExtralight;
    }
  }

// 对展开状态下的菜单设置宽度

  .side-menu:not(.el-menu--collapse) {
    width: 210px;
    height: calc(100vh - 61px);
  }
}
</style>
