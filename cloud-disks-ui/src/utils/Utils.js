export default {
    size2Str:(limit) => {
        var size = "";
        if(limit<0.1*1024){
            size=limit.toFixed(2)+'B'
        }
        else if(limit<0.1*1024*1024){
            size=(limit/1024).toFixed(2)+'KB'
        }
        else if(limit<0.1*1024*1024*1024){
            size=(limit/(1024*1024)).toFixed(2)+'MB'
        }
        else{
            size=(limit/1024*1024*1024).toFixed(2)+'GB'
        }
        var sizeStr = size+"";
        var index=sizeStr.indexOf(".");
        var dou = sizeStr.substr(index+1,2)
        if(dou=="00"){
            return sizeStr.substring(0,index)+sizeStr.substr(index+3,2)
        }
        return size;
    },
}

export function parseTime(time, pattern) {
    if (arguments.length === 0 || !time) {
        return null
    }
    const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
    let date
    if (typeof time === 'object') {
        date = time
    } else {
        if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
            time = parseInt(time)
        } else if (typeof time === 'string') {
            time = time.replace(new RegExp(/-/gm), '/').replace('T', ' ').replace(new RegExp(/\.[\d]{3}/gm), '');
        }
        if ((typeof time === 'number') && (time.toString().length === 10)) {
            time = time * 1000
        }
        date = new Date(time)
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    }
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key]
        // Note: getDay() returns 0 on Sunday
        if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
        if (result.length > 0 && value < 10) {
            value = '0' + value
        }
        return value || 0
    })
    return time_str
}