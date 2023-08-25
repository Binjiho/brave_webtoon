<script lang="ts">
export default {
  props: {
    message: {
      type: String,
      default: "내용이 없습니다",
    },
    height: {
      type: Number,
      default: 150,
    },
    btnText: {
      type: String,
      default: "",
    },
  },
};
</script>

<template>
  <div class="no-area" :style="{ height: height + 'px' }">
    <v-icon icon="custom:infoLine"></v-icon>
    <p v-text="message"></p>
    <v-btn v-if="btnText" @click="$emit('btn-click')">{{ btnText }}</v-btn>
  </div>
</template>

<style scoped lang="scss">
.no-area {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  color: #999;
  padding-top: 20px;

  p {
    font-size: 14px;
    font-weight: 400;
    line-height: 12px;
    margin-top: 8px;
  }

  .v-btn {
    background: #fff;
    color: #999;
    box-shadow: none;
    border: 1px solid #999;
    margin-top: 14px;
    font-size: 14px;
    padding: 0 10px;
    height: 32px;
  }
}
</style>
