<template>
  <div>
    <div class="top">
      <div class="top-op">
        <div class="btn">
          <el-upload
          :show-file-list="false"
          :with-credentials="true"
          :multiple="true"
          :http-request="addFile"
          :accept="fileAccept"
          >
            <el-button >
              <span class="iconfont icon-upload"> </span>
              上传
            </el-button>
          </el-upload>
        </div>
        <el-button>
          <span class="iconfont icon-folder-add"></span>
          新建文件夹
        </el-button>
        <el-button >
          <span class="iconfont icon-del"></span>
          批量删除
        </el-button>
        <el-button >
          <span><img src="@/assets/平移.svg"></span>
          批量移动
        </el-button>
        <!-- <div class="iconfont icon-refresh"></div>     -->
      </div>
      <!--导航-->
      <div style="padding-left: 20px; ">全部文件</div>
    </div>
    <div class="file-list">
      <Table 
        ref="dataTableRef" 
        :columns="columns"
        :dataSource="tableData"
        :fetch="loadDataList"
        :initFetch="false"
        :options="tableOptions"
        @rowSelected="rowSelected"
      >
        
            <template #fileName="index, row">
          <div class="file-item" @mouseenter="showOp(row)" @mouseleave="cancelShowOp(row)">
            <template v-if="(row.fileType ==3 || row.fileType==1)&&row.status ==2">
              <Icon :cover="row.fileCover" :width="32"></Icon>            
            </template>
            <template v-else>
              <Icon v-if="row.folderType == 0" :fileType="row.fileType"></Icon>
              <Icon v-if="row.folderType == 1" :fileType="0"></Icon>
            </template>
            <span class="file-name" :title="row.fileName">
            <span>{{ row.fileName}}</span>
            <span v-if="row.status==0" class="transfer-status">转码中</span>
            <span v-if="row.status==1" class="transfer-status transfer-fail">转码失败</span>
            </span>
            <span class="op">
              <template v-if="row.showOp &&row.fileId && row.status==2">
                <span class="iconfont icon-share1">分享</span>
              </template>
            </span>
          </div>
        </template>
    </Table>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();

const api = {
  loadDataList:"/file/loadDataList",
  rename:"/file/rename",
  newFoloder:"/file/newFoloder",
  getFolderInfo:"/file/getFolderInfo",
  delFile: "/file/delFile",
  changeFileFolder:"/file/changeFileFolder",
  createDownloadUrl: "/file/createDownloadUrl",
  download:"/api/file/download",
};

const columns = [
  {
    label:"文件名",
    prop:"fileName",
    scopedSlots:"fileName",
  },
  {
    label:"修改时间",
    prop:"lastUpdateTime",
    width: 200,
  },
  {
    label:"大小",
    prop:"fileSize",
    scopedSlots:"fileSize",
    width: 200,
  },
];

const tableData = ref({});
const tableOptions = ref({
  extHeight: 50,
  selectType: "checkbox",
});

const fileNameFuzzy = ref();
const category = ref();

const loadDataList = async () => {
  let params = {
    pageNo: tableData.value.pageNo,
    pageSize:tableData.value.pageSize,
    fileNameFuzzy:fileNameFuzzy.value,
    filePid:0,
  };
  if(params.category!=="all"){
    delete params.filePid;
  }
  let result = await proxy.Request({
    url:api.loadDataList,
    params:params,
  })
  if(!result){
    return;
  }
  tableData.value = result.data;
};

const rowSelected = () => {};
const showOp=(row)=> {
  tableData.value.list.forEach((element)=>{
    element.showOp =false;
  });
  row.showOp = true;
};

const cancelShowOp=(row)=> {
  row.showOp = false;
}
</script>

<style lang="scss" scoped>
@import "@/assets/file.list.scss";
</style>
