<script lang="ts">
import UIHelpers from "@/mixin/UIHelpers";
import api from "@/mixin/api";

export default {
  mixins: [api, UIHelpers],
  data() {
    return {
      webtoonList: [],
      pageData: {
        page: 1,
        itemsPerPage: 10,
        hasNext: true,
      },
      webtoonName: "",
    };
  },
  methods: {
    async getWebtoonList() {
      const filter = {
        title: this.webtoonName,
      };

      return new Promise((resolve, reject) => {
        this.sendAnonymousGet(
          "/api/webtoon/webtoonList",
          this.urlParamsFormatter(filter, this.pageData),
          (response) => {
            resolve(response);
          }
        );
      });
    },
    async load({ side, done }) {
      if (!window.scrollY && this.webtoonName) return done("error");
      if (!this.pageData.hasNext) return done("error");
      // Perform API call
      const res = await this.getWebtoonList();
      this.pageData.page += 1;

      let list = res?.data[0]?.mainResponseDtoList;
      this.pageData.hasNext = res?.data[0]?.hasNext;

      if (!list?.length) return done("error");
      if (list[0]?.title === this.webtoonList[0]?.title) return done("error");

      this.webtoonList.push(...list);
      done("ok");
    },
    searchWebtoon() {
      this.pageData.page = 1;
      const filter = {
        title: this.webtoonName,
      };

      this.sendAnonymousGet(
        "/api/webtoon/webtoonList",
        this.urlParamsFormatter(filter, this.pageData),
        (response) => {
          this.webtoonList = response.data[0].mainResponseDtoList;
        }
      );
    },
  },
};
</script>

<template>
  <div class="search-wrap">
    <v-form action="" @submit.prevent="">
      <ui-input-search
        placeholder="웹툰명 입력"
        @search="searchWebtoon"
        v-model="webtoonName"
      ></ui-input-search>
    </v-form>
  </div>
  <v-infinite-scroll :items="webtoonList" @load="load" class="list">
    <template v-for="(item, index) in webtoonList" :key="item.i">
      <webtoon-item :item="item"></webtoon-item>
    </template>
  </v-infinite-scroll>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/index";
</style>
