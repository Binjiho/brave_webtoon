<script lang="ts">
export default {
  props: {
    img: {
      type: String,
      default: "",
    },
  },
};
</script>

<template>
  <div class="webtoon-character">
    <img :src="img" alt="" />
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/components/webtoon/character";
</style>
