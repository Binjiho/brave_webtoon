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

<style lang="scss">
.list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  flex-wrap: wrap;
  padding-top: 20px;
  gap: 22px 10px;
}

@media all and (min-width: 500px) {
  .list {
    grid-template-columns: 1fr 1fr 1fr;
  }
}
</style>
