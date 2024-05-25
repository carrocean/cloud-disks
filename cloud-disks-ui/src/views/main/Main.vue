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
        <el-button @click="newFolder">
          <span class="iconfont icon-folder-add"></span>
          新建文件夹
        </el-button>
        <el-button >
          <span class="iconfont icon-del"></span>
          批量删除
        </el-button>
        <el-button >
          <span class="iconfont icon-move"></span>
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
        :initFetch="true"
        :options="tableOptions"
        @rowSelected="rowSelected"
      >
        
            <template #fileName="{index, row} ">
              <div 
               class="file-item"
               @mouseenter="showOp(row)" 
               @mouseleave="cancelShowOp(row)"
               >
                <template v-if="(row.fileType ==3 || row.fileType==1)&&row.status ==2">
                  <Icon :cover="row.fileCover" :width="32"></Icon>            
                </template>
                <template v-else>
                  <Icon v-if="row.folderType == 0" :fileType="row.fileType"></Icon>
                  <Icon v-if="row.folderType == 1" :fileType="0"></Icon>
                </template>
            <span class="file-name" v-if="!row.showEdit" :title="row.fileName">
            <span>{{ row.fileName}}</span>
            <span v-if="row.status==0" class="transfer-status">转码中</span>
            <span v-if="row.status==1" class="transfer-status transfer-fail">转码失败</span>
            </span>

            <div class="edit-panel" v-if="row.showEdit">
              <el-input 
              v-model.trim="row.fileNameReal"
              ref="editNameRef"
              :maxLength="190"
              @keyup.enter="saveNameEdit(index)"
              >
              <template #suffix>{{row.fileSuffix }}</template>
              </el-input>
              <span :class="['iconfont icon-right1',row.fileNameReal?'':'not-allow',]" @click="saveNameEdit(index)"></span>
              <span class="iconfont icon-error" @click="canceNameEdit"></span>
            </div>
            <span class="op">
              <template v-if="row.showOp  && row.status==2">
                <span class="iconfont icon-share1">分享</span>
                <span class="iconfont icon-download" v-if="row.folderType==0">下载</span>
                <span class="iconfont icon-del">删除</span>
                <span class="iconfont icon-edit" @click="editFileName(index)">重命名</span>
                <span class="iconfont icon-move">移动</span>
              </template>
            </span>
          </div>
        </template>
        <template #fileSize="{index , row}">
        <span v-if="row && row.fileSize">{{proxy.Utils.size2Str(row.fileSize) }}</span> 
        </template> 
    </Table>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick ,onMounted} from "vue";
const { proxy } = getCurrentInstance();
const emit = defineEmits(["addFile"]);
const addFile=(fileData)=> {
  emit("addFile",{file:fileData.file, filePid:currentFolder.value.fileId});
};
const currentFolder=ref({fileId:0})

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

const tableData = ref({
  pageNo: 1,
  pageSize: 10,
  list: [
    // 这里初始化 tableData.list 为一个具有初始内容的数组
    { 
      fileName: '文件1.vue', 
      fileSize: 1024, 
      fileType: 0, 
      folderType: 0, 
      lastUpdateTime: '2024-05-01', 
      status: 1, 
      fileId:1,
      
    },
    { 
      fileName: '文件2.pdf', 
      fileSize: 2048, 
      fileType: 1, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 0, 
      fileId:2,
    },{ 
      fileName: '文件3.pdf', 
      fileSize: 2048, 
      fileType: 2, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 1, 
      fileId:3,
    },
    { 
      fileName: '文件4.pdf', 
      fileSize: 2048, 
      fileType: 3, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 1, 
      fileId:4,
    },
    { 
      fileName: '文件5.pdf', 
      fileSize: 2048, 
      fileType: 4, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 1, 
      fileId:5,
    },
    { 
      fileName: '文件6.pdf', 
      fileSize: 2048, 
      fileType: 5, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 2, 
      fileId:6,
    },
    { 
      fileName: '文件7.pdf', 
      fileSize: 2048, 
      fileType: 6, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 2, 
      fileId:7,
    },
    { 
      fileName: '文件8.pdf', 
      fileSize: 2048, 
      fileType: 7, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 2, 
      fileId:8,
    },
    { 
      fileName: '文件9.pdf', 
      fileSize: 102555555, 
      fileType: 8, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 2, 
      fileId:9,
    },
    { 
      fileName: '文件10.pdf', 
      fileSize: 2048, 
      fileType: 9, 
      folderType: 0, 
      lastUpdateTime: '2024-05-02', 
      status: 2, 
      fileId:10,
    },
    // 可以继续添加更多初始行数据...
  ]
});
const tableOptions = ref({
  extHeight: 50,
  selectType: "checkbox",
});

const fileNameFuzzy = ref();
const categroy = ref();

const loadDataList = async () => {
  let params = {
    pageNo: tableData.value.pageNo,
    pageSize:tableData.value.pageSize,
    fileNameFuzzy:fileNameFuzzy.value,
    filePid:0,
  };
  if(params.categroy!=="all"){
    delete params.filePid;
  }
  const result =await proxy.Request({
    url:api.loadDataList,
    params:params
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
//编辑行
const editing=ref(false);
const editNameRef=ref();
//新建文件夹
const newFolder=()=> {
  if(editing.value){
    return;
  }
    tableData.value.list.forEach((element)=> {
    element.showEdit= false;
  });
  editing.value=true;
  tableData.value.list.unshift({
    showEdit:true,
    fileType:0,
    fileId:"",
    filePid:0,
  });
  nextTick(()=>{
    editNameRef.value.focus();
  });
};
//取消新建
const canceNameEdit = (index) => {
      if (tableData.value.list[index].fileId) {
        // 如果 fileId 存在，则仅设置 showEdit 为 false
        tableData.value.list[index].showEdit = false;
      } else {
        // 如果 fileId 不存在，则从列表中移除该项
        
      }
      tableData.value.list.splice(index, 1);
  // 重置编辑状态
  editing.value = false;
};
// 确认新建
const saveNameEdit=async(index)=> {
  const{fileId,filePid,fileNameReal}=tableData.value.list[index];
  if(fileNameReal==""||fileNameReal.indexOf("/")!=-1) {
    proxy.Message.warning("文件名不能为空并且不能有斜杠");
    return;
  }
  let url=api.rename;
  if(fileId == "" ) {
    url=api.newFoloder;
  }
  let result=await proxy.Request({
    url:url,
    params:{
      fileId:fileId,
      filePid:filePid,
      fileName:fileNameReal,
    },
  });
  if(!result) {
    return;
  }
  tableData.value.list[index]=result.data;
  editing.value=false;
}
//重命名
const editFileName=(index)=> {
  if(tableData.value.list[0].fileId == "") {
    tableData.value.list.splice(0,1);
    index=index-1;
  }
  tableData.value.list.forEach((element)=>{
    element.showEdit=false;
  });
  let currentData=tableData.value.list[index];
  currentData.showEdit=true;
  //编辑文件
  if(currentData.folderType==0) {
    currentData.fileNameReal=currentData.fileName.substring(0,currentData.fileName.indexOf("."));
    currentData.fileSuffix = currentData.fileName.substring(currentData.fileName.lastIndexOf("."));
  }
  else{
    currentData.fileNameReal=currentData.fileName;
    currentData.fileSuffix="";
  }
  editing.value=true;
  nextTick(()=>{
    editNameRef.value.focus();
  });
}
</script>

<style lang="scss" scoped>
@import "@/assets/file.list.scss";
</style>