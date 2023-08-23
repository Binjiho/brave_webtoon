<script lang="ts">
export default {
  data() {
    return {
      webtoonList: [],
    };
  },
  methods: {
    getWebtoonList() {
      this.$api
        .get("/api/webtoonList")
        .then((response) => (this.webtoonList = response.data));
    },
  },
  created() {
    this.getWebtoonList();
  },
};
</script>

<template>
  <ui-input-search placeholder="웹툰명 입력"></ui-input-search>
  <div class="list">
    <webtoon-item
      v-for="item in webtoonList"
      :key="item.i"
      :item="item"
    ></webtoon-item>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/index";
</style>
