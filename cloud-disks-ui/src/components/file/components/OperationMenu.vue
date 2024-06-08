<template>
  <div
      class="operation-menu-wrapper"
      :class="'file-type-' + fileType"
      ref="operationMenuRef"
  >
    <!-- 上传和新增 -->
    <el-button-group
        class="create-operate-group"
        v-if="(!selectedFiles.length || !isBatchOperation) && fileType === 0"
    >
      <el-dropdown class="upload-drop" trigger="hover">
        <el-button
            size="default"
            type="primary"
            icon="el-icon-upload2"
            id="uploadFileId"
        >上传<i class="el-icon-arrow-down el-icon--right"></i
        ></el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click.native="handleUploadFileBtnClick(1)">上传文件</el-dropdown-item>
            <el-dropdown-item @click.native="handleUploadFileBtnClick(2)">上传文件夹</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!--      <el-dropdown class="create-drop" trigger="hover">-->
      <!--        <el-button-->
      <!--            size="default"-->
      <!--            type="primary"-->
      <!--            icon="el-icon-plus"-->
      <!--            id="uploadFileId"-->
      <!--        >新建<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i-->
      <!--        ></el-button>-->
      <!--        <el-dropdown-menu slot="dropdown">-->
      <!--          <el-dropdown-item @click.native="handleClickAddFolderBtn">-->
      <!--            <div class="img-text-wrapper"><img :src="dirImg"/> 新建文件夹</div>-->
      <!--          </el-dropdown-item>-->
      <!--        </el-dropdown-menu>-->
      <!--      </el-dropdown>-->
    </el-button-group>


  </div>


</template>

<script setup>
import {computed, defineProps} from 'vue';
import store from "@/store/index.js";

const props = defineProps({
  fileType: {
    type: Number,
    required: true
  }
});

const selectedFiles = computed(() => store.state.fileList.selectedFiles);
const isBatchOperation = computed(() => store.state.fileList.isBatchOperation);

/**
 * 上传文件按钮点击事件
 * @description 通过Bus通信，开启全局上传文件流程
 * @param {boolean} uploadWay 上传方式 0-文件上传 1-文件夹上传 2-粘贴图片或拖拽上传
 */
function handleUploadFileBtnClick(uploadWay) {
  this.$openDialog.authWeChat({}).then((res) => {
    switch (res) {
      case 'confirm': {
        this.$common.goAccount('/settings/account')
        break
      }
      case 'go': {
        this.$openBox.uploadFile({
          params: this.uploadFileParams,
          uploadWay,
          serviceEl: this,
          callType: 1 //  callType 调用此服务的方式：1 - 顶部栏，2 - 右键菜单
        })
        break
      }
    }
  })
}

</script>

<style lang="stylus" scoped>

.operation-menu-wrapper.file-type-6 {
  margin: 8px 0;
  justify-content: flex-end;
}

.operation-menu-wrapper {
  padding: 16px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .create-operate-group {
    .upload-drop {
      float: left;

      >>> .el-button {
        border-radius: 4px 0 0 4px;
      }
    }

    .create-drop {
      float: left;
    }
  }

  .batch-operate-group {
    flex: 1;
  }

  .select-file-input {
    margin-right: 8px;
    width: 250px;

    .el-icon-search {
      cursor: pointer;
      font-size: 16px;

      &:hover {
        color: $Primary;
      }
    }
  }

  .batch-icon,
  .model-icon {
    margin-right: 8px;

    &:last-of-type {
      margin-right: 0;
    }
  }

  .model-icon.active {
    color: $Primary;
  }

  .refresh-icon, .batch-icon, .model-icon, .setting-icon {
    font-size: 20px;
    cursor: pointer;
    color: $SecondaryText;

    &:hover {
      color: $Primary;
    }
  }

  .batch-icon.active {
    color: $Primary;
  }
}

.split-line {
  margin: 8px 0;
}

.img-text-wrapper {
  display: flex;
  align-items: center;

  img {
    margin-right: 4px;
    height: 24px;
  }
}
</style>
