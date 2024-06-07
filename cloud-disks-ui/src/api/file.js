
//导入创建好的axios实例
import { request } from '@/utils/Request.js';

// 接口配置项
var prefix = '/api/cloud/disks/file/';
var managerUrl = {
    fileList: prefix + "fileList",
    showUpload: prefix + 'showUpload',
    uploadFile: prefix + 'uploadFile',
    makeFolder: prefix + "makeFolder",
    deleteFileOrFolder: prefix + "deleteFileOrFolder",
    renameFileOrFolder: prefix + "renameFileOrFolder",
    showTree: prefix + "showTree",
    tree: prefix + "tree",
    copyOrMoveFile: prefix + "copyOrMoveFile",
    downloadFile: prefix + "downloadFile",
    viewFile: prefix + "viewFile",
}

// 列出文件列表
export function fileList(data) {
    const { dir = '/', originalDir = '/', parentid = 0 } = data;
    return request({
        url: managerUrl.fileList,
        method: 'get',
        params: {
            dir,
            originalDir,
            parentid
        }
    });
}

// 显示上传文件页面弹窗
export function showUpload(data) {
    const { dir, originalDir, parentid } = data;
    return request({
        url: managerUrl.showUpload,
        method: 'get',
        params: {
            dir,
            originalDir,
            parentid
        }
    });
}

// 上传文件
export function uploadFile(data) {
    const { dir, originalDir, parentid } = data;
    return request({
        url: managerUrl.uploadFile,
        method: 'post',
        data: {
            dir,
            originalDir,
            parentid
        }
    });
}

// 创建文件夹
export function makeFolder(data) {
    const { dirName, originalDir, mkdir, parentid } = data;
    return request({
        url: managerUrl.makeFolder,
        method: 'post',
        data: {
            dirName,
            originalDir,
            mkdir,
            parentid
        }
    });
}

// 删除文件或者文件夹
export function deleteFileOrFolder(data) {
    const { ids, parentid } = data;
    return request({
        url: managerUrl.deleteFileOrFolder,
        method: 'post',
        data: {
            ids,
            parentid
        }
    });
}

// 重命名文件或文件夹
export function renameFileOrFolder(data) {
    const { id, name, newname, type, parentid } = data;
    return request({
        url: managerUrl.renameFileOrFolder,
        method: 'post',
        data: {
            id,
            name,
            newname,
            type,
            parentid
        }
    });
}

// 显示目录树
export function showTree(data) {
    const { ids, flag, parentid } = data;
    return request({
        url: managerUrl.showTree,
        method: 'get',
        params: {
            ids,
            flag,
            parentid
        }
    });
}

// 获得目录树
export function tree(data) {
    const { id = 0 } = data;
    return request({
        url: managerUrl.tree,
        method: 'get',
        params: {
            id
        }
    });
}

// 复制或者移动文件与目录
export function copyOrMoveFile(data) {
    const { ids, parentid, destid, flag } = data;
    return request({
        url: managerUrl.copyOrMoveFile,
        method: 'post',
        data: {
            ids,
            parentid,
            destid,
            flag
        }
    });
}

// 下载文件
export function downloadFile(data) {
    const { name, originalName, path } = data;
    return request({
        url: managerUrl.downloadFile,
        method: 'get',
        params: {
            name,
            originalName,
            path
        }
    });
}

// 在线查看文件
export function viewFile(data) {
    const {name, originalName, path} = data;
    return request({
        url: managerUrl.viewFile,
        method: 'get',
        params: {
            name,
            originalName,
            path
        }
    });
}