import Cookies from 'js-cookie'

// 全局函数 - 公共操作相关
const commonFunction = {
    /**
     * 设置 Cookies
     * @param {string} name 名称
     * @param {string} value 值
     * @param {object} others 域名、路径、有效期等，封装到对象中
     */
    setCookies(name, value, others = null) {
        Cookies.set(name, value, {...others })
    },
    /**
     * 获取 Cookies
     * @param {string} name 名称
     * @param {object} others 域名、路径等，封装到对象中
     * @returns {string} Cookies 值
     */
    getCookies(name, others = null) {
        return Cookies.get(name, {...others })
    },
    /**
     * 移除 Cookies
     * @param {string} name 名称
     * @param {object} others 域名、路径等，封装到对象中
     */
    removeCookies(name, others = null) {
        Cookies.remove(name, {...others })
    }
}
export default commonFunction
