<template>
  <div class="side-menu-wrapper">
    <!-- collapse 属性：控制菜单收缩展开 -->
    <el-menu
        class="side-menu "
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
          :route="{ name: 'file', query: { fileType: 6 } }"
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
const activeIndex = computed(() => String(route.query.fileType)) //  获取当前路由参数中包含的文件类型

watch(activeIndex, (newValue) => {
  document.title = `${myFileMenuMap[Number(newValue)]} - ${config.siteName}`
})

onMounted(() => {
  document.title = `${myFileMenuMap[Number(activeIndex)]} - ${config.siteName}`
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

    .el-menu-item.is-active {
      background: #ecf5ff;
    }

    .my-file, .recovery {
      box-shadow: 0 4px 12px 0 $BorderExtralight;
    }
  }

  >>> .el-menu {
    background: transparent;
  }

// 对展开状态下的菜单设置宽度

  .side-menu:not(.el-menu--collapse) {
    width: 210px;
    height: calc(100vh - 61px);
  }

// 存储空间展示区

  .storage-wrapper {
    position: absolute;
    bottom: 0;
    left: 0;
    box-shadow: 0 -2px 12px 0 $BorderExtralight;
    border-right: solid 1px #e6e6e6;
    box-sizing: border-box;
    width: calc(100% - 11px);
    height: 66px;
    padding: 16px;
    z-index: 2;
    color: $PrimaryText;

    .text {
      margin-top: 8px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 12px;
      flex-wrap: wrap;
    }
  }

  .storage-wrapper.fold {
    padding: 0;

    >>> .el-progress--circle {
      margin: 0 auto;
      width: 32px;
      display: block;
    }

    .text {
      font-size: 12px;
      justify-content: center;
    }
  }

// 折叠图标调整样式

  .aside-title {
    position: absolute;
    top: calc(50% - 50px);
    right: 0;
    z-index: 2;
    background: $BorderBase;
    color: #fff;
    width: 12px;
    height: 100px;
    line-height: 100px;
    cursor: pointer;
    border-radius: 0 16px 16px 0;

    &:hover {
      opacity: 0.7;
    }

    .icon {
      font-size: 12px;
    }
  }
}
</style>
