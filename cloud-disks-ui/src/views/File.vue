<template>
  <div class="file-wrapper" @dragenter="showUploadMask">
    <el-container class="el-container">
      <el-aside width="auto">
        <AsideMenu></AsideMenu>
      </el-aside>
      <el-container>
        <el-main class="el-main">
          <FileList></FileList>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import AsideMenu from '@/components/file/AsideMenu.vue'
// import FileList from '_c/file/FileList.vue'

// 显示拖拽上传文件遮罩
function showUploadMask(e) {
  e.stopPropagation();
  e.preventDefault();
  console.log("showUploadMask")
  this.$store.commit("showUploadMask");
}


</script>

<style lang="stylus" scoped>

.file-wrapper {
  width: 100% !important;
}

.el-container {
  .el-aside {
    height: calc(100vh - 61px);
    overflow: hidden;
  }
}

.el-main {
  padding: 0px 16px !important;
  overflow: hidden;
}
</style>
