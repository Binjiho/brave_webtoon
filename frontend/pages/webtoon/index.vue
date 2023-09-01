<script lang="ts">
import UIHelpers from "@/mixin/UIHelpers";

export default {
  mixins: [UIHelpers],
  data() {
    return {
      webtoonList: [],
      pageData: {
        page: 1,
        itemsPerPage: 10,
        hasNext: true,
      },
    };
  },
  methods: {
    async getWebtoonList() {
      const result = await this.$api.get("/api/webtoonList", {
        params: { ...this.urlParamsFormatter(null, this.pageData) },
      });
      return result;
    },
    async load({ done }) {
      if (!this.pageData.hasNext) return done("error");
      // Perform API call
      const res = await this.getWebtoonList();
      this.pageData.page += 1;

      let list = res.data[0].mainResponseDtoList;
      this.pageData.hasNext = res.data[0].hasNext;

      if (!list.length) return done("error");

      this.webtoonList.push(...list);
      done("ok");
    },
  },
};
</script>

<template>
  <div class="search-wrap">
    <ui-input-search placeholder="웹툰명 입력"></ui-input-search>
  </div>
  <v-infinite-scroll :items="webtoonList" :onLoad="load" class="list">
    <template v-for="(item, index) in webtoonList" :key="item.i">
      <webtoon-item :item="item"></webtoon-item>
    </template>
  </v-infinite-scroll>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/index";
</style>
