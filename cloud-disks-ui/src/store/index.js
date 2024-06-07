import Vuex from 'vuex'

import user from './module/user' //  用户模块
import common from './module/common' //  公共模块

export default new Vuex.Store({
	state: {
		//
	},
	getters: {
		// 登录状态
		isLogin: (state) => state.user.isLogin,
		// 用户姓名
		userName: (state) => state.user.userInfoObj.userName,
		// 用户ID
		userId: (state) => state.user.userInfoObj.userId,
	},
	mutations: {
		//
	},
	actions: {
		//
	},
	modules: {
		user,
		common,
	}
})
