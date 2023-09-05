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
    <img :src="img" alt="" v-if="img" />
    <v-icon icon="custom:personFill" v-else></v-icon>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/components/webtoon/character";
</style>
