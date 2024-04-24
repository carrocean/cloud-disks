const regs= {
    number:/^([0]|[1-9][0-9]*)$/,
    password:/^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*_]{8,}$/, 
}
const verify = (rule,value,reg,callback)=> {
    if(value){
        if(reg.test(value)) {
            callback()
        }else {
            callback(new Error(rule.message))
        }
    }else {
        callback()
    }
}

export default {
    
    number:(rule,value,callback)=> {
        return verify(rule,value,regs.number,callback)
    },
    password:(rule,value,callback)=> {
        return verify(rule,value,regs.password,callback)
    }

}