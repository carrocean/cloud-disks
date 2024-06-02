<template>
  <div class="top-navigation">
    <template v-if="folderList.length>0">
    <span class="back link" @click="backParent">返回上一级</span>
    <el-divider direction="vertical"></el-divider>
    </template>
    <span v-if="folderList.length==0" class="all-file">全部文件</span>
    <span v-if="folderList.length>0" class="link" @click="setCurrentFolder(-1)">全部文件</span>
    <template v-for="(item,index) in folderList">
    <span class="iconfont icon-right"></span>
    <span
        class="link"
        v-if="index<folderList.length-1"
        @click="setCurrentFolder(index)">
        {{ item.fileName }}
    </span>
    <span v-if="index == folderList.length-1" class="text">{{ item.fileName }}</span>
</template>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick ,watch} from "vue"
const { proxy } = getCurrentInstance(); 
import { useRouter,useRoute } from "vue-router";
const router = useRouter();
const route=useRoute();

const props=defineProps({
    watchPath :{
        type:Boolean,
        default:true,
    },
    shareId:{
        type:String,
    },
    adminShow: {
        type:Boolean,
        default:false,
    },
});
const api = {

}
//分类
const categroy=ref();

//目录合集
const folderList = ref([]);

//当前目录
const currentFolder = ref({fileId:"0"});

const init = () => {
    folderList.value=[];
    currentFolder.value={fileId:"0"};
    doCallback();
}
const openFolder= (data)=>{
    const { fileId,fileName }=data;
    const folder = {
        fileName:fileName,
        fileId:fileId,
    };
    folderList.value.push(folder);
    currentFolder.value=folder;
    setPath();
};
defineExpose({ openFolder });

//返回上一级
const backParent=() => {
    let currentIIndex = null;
    for(let i =0;i<folderList.value.length;i++) {
        if(folderList.value[i].fileId==currentFolder.value.fileId){
            currentIIndex = i;
            break;
        }
    }
    setCurrentFolder(currentIIndex-1);
}
//点击导航设置当前目录
const setCurrentFolder=(index)=> {
    if(index==-1) {
        //返回全部
        currentFolder.value={ fileId:"0"};
        folderList.value=[];
    }
    else{
        currentFolder.value=folderList.value[index];
        folderList.value.splice(index+1,folderList.value.length);
    }
    setPath();
}
const setPath=()=>{
    if(props.watchPath) {
        //设置不监听路由回掉方法
        doCallback();
        return;
    }
    let pathArray = [];
    folderList.value.forEach(item=>{
        pathArray.push(item.fileId);
    });
    router.push({
        path:route.path,
        query:pathArray.length == 0?"" : { path:pathArray.join("/")},
    });
};

//获取当前目录
const getNavigationFolder =async(path)=>{
    let url ="";
    if(proxy.shareId) {
        url = "";
    }
    if(props.adminShow) {
        url="";
    }
    let result = await proxy.Request({
        url:url,
        showLoading:false,
        params:{
            path:path,
            shareId:props.shareId,
        },
    });
    if(!result) {
        return;
    }
    folderList.value= result.data;
};

const emit=defineEmits(["navChange"]);
const doCallback=() => {
    emit("navChange",{
        categroyId:categroy.value,
        curFolder:currentFolder.value,
    });
};

watch(() =>route, 
    (newVal, oldVal) => {
    if(!props.watchPath) {
        return;
  }
  if(newVal.path.indexOf("/main")===-1){
    return;
  }
  const path=newVal.query.path;
  categroy.value=newVal.params.categroy;
  if(path==undefined) {
    init();
  }else {
    getNavigationFolder(path);
    let pathArray=path.split("/");
    currentFolder.value = {
        fileId:pathArray[pathArray.length-1],
    };
    doCallback();
  }

},
{ immediate: true, deep: true });



</script>

<style lang="scss" scoped>
.top-navigation {
    font-size: 13px;
    display: flex;
    align-items: center;
    line-height: 40px;

    .all-file {
        font-weight: bold;
        padding-left: 20px;
    }
    .link {
        color: #06a7ff;
        padding: 0px 5px;
        font-size: 13px;
    }
}
</style>
