<template>
    <div class="no-data">
    <div>
        <img src="../assets/云.svg" :width="120" fit="fill"> 
    </div>
    <div class="msg">{{ msg }}</div>
    </div>
</template>
<script setup>
const props = defineProps({
    msg: {
    type: String,
    },
});
</script>
<style lang="scss"scoped>
.no-data {
    text-align: center;
    padding: 10px Opx;
    icon-empty {
    font-size: 50px;
    color:#bbb;
    }
    .msg {
        margin-top: 10px;
        color: 门#909399;
        font-size: 14px;
    }
}
</style>