<script lang="ts">
import api from "@/mixin/api";
import UIHelpers from "@/mixin/UIHelpers";

export default {
  mixins: [api, UIHelpers],
  data() {
    return {
      webtoonInfo: {
        id: this.$route.params.id,
        title: "",
        img: "",
      },
      pageData: {
        page: 1,
        itemsPerPage: 10,
        hasNext: true,
      },
      characterList: [],
    };
  },
  methods: {
    async getWebtoonCharacter() {
      const filter = {
        id: this.webtoonInfo.id,
        pageSize: 100,
        offset: 0,
      };

      return new Promise((resolve, reject) => {
        this.sendAnonymousGet(
          "/api/webtoon/webtoonRoleList",
          this.urlParamsFormatter(filter, this.pageData),
          (response) => {
            resolve(response);
          }
        );
      });
    },
    async load({ side, done }) {
      if (!this.pageData.hasNext) return done("error");
      // Perform API call
      const res = await this.getWebtoonCharacter();
      this.pageData.page += 1;

      this.webtoonInfo = {
        ...this.webtoonInfo,
        title: res.data[0].title,
        img: res.data[0].uploadPath,
      };

      let list = res.data[0].webtoonRoleEntityList;
      this.pageData.hasNext = res?.data[0]?.hasNext;

      if (!list?.length) return done("error");
      if (list[0]?.name === this.characterList[0]?.name) return done("error");

      this.characterList.push(...list);
      done("ok");
    },
    goVotePage(id) {
      this.$router.push("/webtoon/vote/" + id);
    },
  },
};
</script>

<template>
  <ui-header-share-title
    title="캐릭터 선택"
    isTransparent
  ></ui-header-share-title>
  <div class="back-bg">
    <div
      class="back-bg__img"
      :style="{ 'background-image': `url(${webtoonInfo.img})` }"
    ></div>
  </div>
  <div class="webtoon-info">
    <webtoon-character class="webtoon-info__img" :img="webtoonInfo.img">
    </webtoon-character>
    <ui-label-play-type class="webtoon-info__label"> </ui-label-play-type>
    <h3 class="webtoon-info__title">{{ webtoonInfo.title }}</h3>
    <p class="webtoon-info__text">하단의 캐릭터를 선택하여<br />투표해주세요</p>
  </div>
  <v-infinite-scroll :items="characterList" @load="load" class="character-list">
    <template
      v-for="(item, index) in characterList"
      :key="item.i"
      @click="goVotePage(item.id)"
    >
      <div class="character-list__item">
        <webtoon-character :img="item.uploadPath"></webtoon-character>
        <p>{{ item.name }}</p>
      </div>
    </template>
  </v-infinite-scroll>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/id";
</style>
