<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px" style="float:right;">
      <el-form-item label="文件名称" prop="title">
        <el-input
            v-model="fileName"
            placeholder="请输入文件名称"
            clearable
            style="width: 240px;"
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb10">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="RefreshLeft"
            :disabled="multiple"
            @click="handleRecover"
        >恢复
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
        >彻底删除
        </el-button>
      </el-col>
    </el-row>

    <el-table ref="operlogRef" v-loading="loading" :data="operlogList" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column width="60">
        <template #default="scope">
          <img
              :src="getAssetsFile(scope.row.type)"
              style="width: 30px; max-height: 30px;"/>
        </template>
      </el-table-column>
      <el-table-column label="文件名称" align="left" prop="name">
        <template #default="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件类型" align="left" prop="type">
        <template #default="scope">
          <span>{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="大小" align="left" prop="size">
        <template #default="scope">
          <span>{{ scope.row.size }} B</span>
        </template>
      </el-table-column>
      <el-table-column label="添加日期" align="center" prop="date" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.date) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              type="text"
              icon="RefreshLeft"
              @click="handleRecover(scope.row)"
          >恢复
          </el-button>
          <el-button
              type="text"
              icon="Delete"
              @click="handleDelete(scope.row)"
          >彻底删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script setup>
import {recycleDelete, recycleList, recycleRecover} from "@/api/recycle.js";
import {getCurrentInstance, reactive, ref, toRefs} from "vue";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {parseTime} from '@/utils/Utils.js'


const {proxy} = getCurrentInstance();

const router = useRouter();
const operlogList = ref([]);
const loading = ref(false);
const showSearch = ref(true);
const ids = ref([]);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);
const defaultSort = ref({prop: "operTime", order: "descending"});

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: undefined,
    operName: undefined,
    businessType: undefined,
    status: undefined
  }
});

const {queryParams} = toRefs(data);
const fileName = ref('')

function getAssetsFile(type) {
  return new URL('/images/file_'+type+'.png', import.meta.url).href
}


/** 查询文件 */
function getList() {
  loading.value = true;
  let query = {'fileName': fileName.value}
  recycleList(query).then(response => {
    operlogList.value = response.data;
    total.value = response.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  proxy.$refs["operlogRef"].sort(defaultSort.value.prop, defaultSort.value.order);
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  multiple.value = !selection.length;
}

/** 排序触发事件 */
function handleSortChange(column, prop, order) {
  queryParams.value.orderByColumn = column.prop;
  queryParams.value.isAsc = column.order;
  getList();
}

/** 删除按钮操作 */
function handleDelete(row) {
  loading.value = true;
  recycleDelete(row).then(response => {
    ElMessage.success("删除成功")
    getList();
    loading.value = false;
  });
}

/** 恢复按钮操作 */
async function handleRecover(row) {
  loading.value = true;
  recycleRecover(row).then(response => {
    ElMessage.success("恢复成功")
    getList();
    loading.value = false;
  });
}

getList();
</script>

<style>

.mb10 {
  margin-bottom: 10px;
}

.app-container {
  margin-top: 12px;
}

</style>