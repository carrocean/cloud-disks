<template>
    <div class="framework">
        <div class="header">
            <div class="logo">
                <span class="iconfont icon-pan"></span>
                <div class="name">606网盘</div>
            </div>
            <div class="right-panel">
                <el-popover :width="800" trigger="click" :v-model:visible="true" :offset="20" transition="none"
                    :hide-after="0" :popper-style="{ padding: '0px' }">
                    <template #reference>
                        <span class="iconfont icon-transfer"></span>
                    </template>
                    <template #default>
                        这里是上传界面区域
                    </template>
                </el-popover>

                <el-dropdown>
                    <div class="user-info">
                        <div class="avatar"></div>
                        <span class="nick-name">{{ userInfo.nickName }}</span>
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>修改头像</el-dropdown-item>
                            <el-dropdown-item>修改密码</el-dropdown-item>
                            <el-dropdown-item @click="loginout">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
        <div class="body">
            <div class="left-sider">
                <div class="menu-list">
                    <div :class="['menu-item',item.menuCode==currentMenu.menuCode?'active':'']" v-for="item in menus" @click="jump(item)">
                        <div :class="['iconfont', 'icon-' + item.icon]"></div>
                        <div class="text">{{ item.name }}</div>
                    </div>
                </div>
                <div class="menu-sub-list">
                    <div class="body-content"></div>
                    <div @click="jump(sub)" 
                    :class="['menu-item-sub',currentPath==sub.path?'active':'']" 
                    v-for="sub in currentMenu.children"
                    >
                        <span class="['iconfont', 'icon-' + sub.icon]" v-if="sub.icon">
                        </span>
                        <span class="text">{{ sub.name }}</span>
                    </div>
                    <div class="tips" v-if="currentMenu && currentMenu.tips">
                        {{ currentMenu.tips }}
                    </div>
                    <div class="space-info">
                        <div>空间使用</div>
                        <div class="percent"></div>
                    </div>
                </div>
            </div>
            <div class="body-content">
                <router-view v-slot="{Component}">
                    <component :is="Component">

                    </component>
                </router-view>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick, watch } from "vue";
import { useRouter,useRoute } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();

const userInfo = ref({
    nickName: "张三",
});

const menus = [
    {
        icon: "cloude",
        name: "首页",
        menuCode: "main",
        path: "/main/all",
        allShow: true,
        children: [
            {
                icon: "all",
                name: "全部",
                category: "all",
                path: "/main/all",
            },
            {
                icon: "video",
                name: "视频",
                category: "video",
                path: "/main/video",
            },
            {
                icon: "music",
                name: "音频",
                category: "music",
                path: "/main/music",
            },
            {
                icon: "image",
                name: "图片",
                category: "image",
                path: "/main/image",
            },
            
            {
                icon: "doc",
                name: "文档",
                category: "doc",
                path: "/main/doc",
            },
            {
                icon: "more",
                name: "其他",
                category: "others",
                path: "/main/others",
            },
        ],
    },
    {
        path: "/myshare",
        icon: "share",
        name: "分享",
        menuCode: "share",
        allShow: true,
        children: [
            {
                name: "分享记录",
                path: "/myshare",
            },
        ],
    },
    {
        path: "/recycle",
        icon: "del",
        name: "回收站",
        menuCode: "recycle",
        tips: "回收站为你保存10天内删除的文件",
        allShow: true,
        children: [
            {
                name: "删除的文件",
                path: "/recycle",
            },
        ],
    },
    {
        path: "/settings/fileList",
        icon: "settings",
        name: "设置",
        menuCode: "settings",
        allShow: false,
        children: [
            {
                name: "用户文件",
                path: "/settings/fileList",
            },
            {
                name: "用户管理",
                path: "/settings/userList",
            },
            {
                name: "系统设置",
                path: "/settings/sysSetting",
            },
        ],
    },
];

const currentMenu = ref({});
const currentPath = ref();

const loginout = () => {
    router.push("./Login")
}

const jump = (data)=>{
    if(!data.path||data.menuCode == currentMenu.value.menuCode){
        return;
    }
    router.push(data.path);
};

const setMenu = (menuCode,path) => {
    const menu = menus.find(item=>{
        return item.menuCode === menuCode;
    })
    currentMenu.value=menu;
    currentPath.value=path;
};


watch(()=>route,(newVal,oldVal)=>{
    if(newVal.meta.menuCode){
        setMenu(newVal.meta.menuCode,newVal.path);
        }
    },
    { immediate: true, deep: true}
);

</script>

<style lang="scss" scoped>
.header {
    background-color: #05a1f5;
    box-shadow: 0 3px 3px 0 #44b4f1;
    height: 56px;
    padding-left: 24px;
    padding-right: 24px;
    position: relative;
    z-index: 200;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .logo {
        display: flex;
        align-items: center;

        .icon-pan {
            font-size: 40px;
            color: #ffffff;
        }

        .name {
            font-weight: bold;
            margin-left: 5px;
            font-size: 25px;
            color: #ffffff;
        }
    }

    .right-panel {
        display: flex;
        align-items: center;

        .icon-transfer {
            cursor: pointer;
        }

        .user-info {
            margin-right: 10px;
            display: flex;
            align-items: center;
            cursor: pointer;

            .avatar {
                margin: 0px 5px 0px 15px;
            }

            .nick-name {
                color: #ffffff;
            }
        }
    }
}

.body {
    display: flex;

    .left-sider {
        background-color:rgb(5, 161, 245);
        border-right: 1px solid #f1f2f4;
        display: flex;

        .menu-list {
            height: calc(100vh - 56px);
            width: 80px;
            box-shadow: 0 3px 10px 0 rgba(249, 246, 246, 0.945);
            border-right: 1px solid #f1f3f4;

            .menu-item {
                text-align: center;
                font-size: 14px;
                font-weight: bold;
                padding: 20px 0px;
                cursor: pointer;
                color: #eef9fe;
                &:hover {
                    background: rgb(3, 144, 219);
                }

                .iconfont {
                    font-weight: normal;
                    font-size: 28px;
                    color: #eef9fe;
                    
                }
                
            }

            .active {
                
                .iconfont {
                    color: #fdfdfd;

                }

                .text {
                    color: #ffffff;
                }
            }
        }

        .menu-sub-list {
            width: 200px;
            padding: 20px 10px 0px;
            position: relative;
            background-color: #f1f2f4;

            .menu-item-sub {
                text-align: center;
                line-height: 40px;
                border-radius: 5px;
                cursor: pointer;
                

                &:hover {
                    background: #f3f3f3;
                }

                .iconfont {
                    font-size: 14px;
                    margin-right: 20px;
                    
                }

                .text {
                    font-size: 13px;
                }
            }

            .active {
                background: #eef9fe;

                .iconfont {
                    color: #05a1f5;
                }

                .text {
                    color: #05a1f5;
                }
            }

            .tips {
                margin-top: 10px;
                color: #888888;
                font-size: 13px;
            }

            .space-info {
                position: absolute;
                bottom: 10px;
                width: 100%;
                padding: 0px 5px;

                .percent {
                    padding-right: 10px;
                }

                .space-use {
                    margin-top: 5px;
                    color: #7e7e7e;
                    display: flex;
                    justify-content: space-around;

                    .use {
                        flex: 1;
                    }

                    .iconfont {
                        cursor: pointer;
                        margin-right: 20px;
                        color: #05a1f5;
                    }
                }
            }
        }
    }

    .body-content {
        flex: 1;
        width: 0;
        padding-right: 20px;
    }
}
</style>