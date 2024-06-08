<template>
	<div class="file-list-wrapper">
		<!-- 操作按钮 -->
<!--		<el-header height="auto">-->
<!--			<OperationMenu-->
<!--				:fileType="fileType"-->
<!--				:filePath="filePath"-->
<!--				@getSearchFileList="getSearchFileList"-->
<!--				@getTableDataByType="getTableDataByType"-->
<!--			></OperationMenu>-->
<!--		</el-header>-->

    <el-upload
        :action=uploadUrl
        :limit="1"
        :headers="headers"
    >
      <template #trigger>
        <el-button type="primary">选择文件</el-button>
      </template>
    </el-upload>

<!--		<div class="middle-wrapper">-->
<!--			&lt;!&ndash; 面包屑导航栏 &ndash;&gt;-->
<!--			<BreadCrumb-->
<!--				class="breadcrumb"-->
<!--				:fileType="fileType"-->
<!--				:filePath="filePath"-->
<!--				@getTableDataByType="getTableDataByType"-->
<!--			></BreadCrumb>-->
<!--		</div>-->

<!--		 文件列表-表格模式 -->
<!--		<FileTable-->
<!--			:fileType="fileType"-->
<!--			:filePath="filePath"-->
<!--			:fileList="fileList"-->
<!--			:loading.sync="loading"-->
<!--			v-if="fileModel === 0"-->
<!--			@getTableDataByType="getTableDataByType"-->
<!--			@click.native.right="handleClickRight"-->
<!--		></FileTable>-->

		<!-- 图片-时间线模式 -->
<!--		<FileTimeLine-->
<!--			class="image-model"-->
<!--			:fileList="fileList"-->
<!--			:loading.sync="loading"-->
<!--			v-if="fileModel === 2 && fileType === 1"-->
<!--			@getTableDataByType="getTableDataByType"-->
<!--			@click.native.right="handleClickRight"-->
<!--		></FileTimeLine>-->

<!--		<div class="pagination-wrapper">-->
<!--			<div class="current-page-count">当前页{{ fileList.length }}条</div>-->
<!--			&lt;!&ndash; 回收站不展示分页组件 &ndash;&gt;-->
<!--			<el-pagination-->
<!--				:current-page="pageData.currentPage"-->
<!--				:page-size="pageData.pageCount"-->
<!--				:total="pageData.total"-->
<!--				:page-sizes="[10, 50, 100, 200]"-->
<!--				:layout="-->
<!--					screenWidth <= 768-->
<!--						? 'total, prev, next, jumper'-->
<!--						: 'sizes, total, prev, pager, next'-->
<!--				"-->
<!--				@current-change="handleCurrentChange"-->
<!--				@size-change="handleSizeChange"-->
<!--				v-if="fileType !== 6"-->
<!--			>-->
<!--			</el-pagination>-->
<!--		</div>-->
	</div>
</template>

<script setup>
import OperationMenu from '@/components/file/components/OperationMenu.vue'
import {computed, ref} from "vue";
import {useRoute} from "vue-router";
import common from "@/libs/globalFunction/common.js";
import globalConfig from "@/config/index.js";
// import BreadCrumb from '_c/common/BreadCrumb.vue'
// import FileTable from '_c/common/FileTable.vue'
// import FileGrid from './components/FileGrid.vue'
// import FileTimeLine from './components/FileTimeLine.vue'

// import {
// 	getFileListByPath,
// 	getFileListByType,
// 	getRecoveryFile,
// 	getMyShareFileList,
// 	searchFile
// } from '@/api/file.js'

const token = common.getCookies(globalConfig.tokenKeyName)
const headers = ref({
  'token': token
})
const uploadUrl = ref('http://localhost:30001/api/cloud/disks/file/upload')


// const route = useRoute()
// const fileType = computed(() => route.query.fileType ? Number(route.query.fileType) : 0)
// const filePath = computed(() => route.query.filePath ? route.query.filePath : '/')


// /**
//  * 获取搜索文件结果列表
//  * @param {string} fileName 文件名称
//  */
// function getSearchFileList(fileName) {
//   this.loading = true
//   searchFile({
//     currentPage: this.pageData.currentPage,
//     pageCount: this.pageData.pageCount,
//     fileName: fileName
//   }).then((res) => {
//     this.loading = false
//     if (res.success) {
//       this.fileList = res.dataList.map((item) => {
//         return {
//           ...item,
//           highlightFields: item.highLight.fileName[0]
//         }
//       })
//       this.pageData.total = res.data.totalHits
//     } else {
//       this.$message.error(res.message)
//     }
//   })
// }
//
// /**
//  * 表格数据获取相关事件 | 获取文件列表数据
//  */
// function getTableDataByType() {
//   this.loading = true
//   // 分类型
//   if (Number(this.fileType)) {
//     switch (Number(this.fileType)) {
//       case 6: {
//         this.showFileRecovery() //  回收站
//         break
//       }
//       case 8: {
//         this.showMyShareFile() //  我的分享
//         break
//       }
//       default: {
//         this.showFileList()
//         break
//       }
//     }
//   } else {
//     // 全部文件
//     this.showFileList()
//   }
//   this.$store.dispatch('showStorage')
// }

</script>

<style lang="stylus" scoped>

.file-list-wrapper {
  >>> .el-header {
    padding: 0;
  }

  .middle-wrapper {
    margin-bottom: 8px;
  }

  .pagination-wrapper {
    position: relative;
    border-top: 1px solid $BorderBase;
    height: 44px;
    line-height: 44px;
    text-align: center;

    .current-page-count {
      position: absolute;
      left: 16px;
      height: 32px;
      line-height: 32px;
      font-size: 13px;
      color: $RegularText;
    }
  }
}
</style>
