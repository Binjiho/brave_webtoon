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
        itemsPerPage: 100,
      },
      characterList: [],
    };
  },
  methods: {
    getWebtoonCharacter() {
      const filter = {
        id: this.webtoonInfo.id,
        pageSize: 100,
        offset: 0,
      };

      this.sendAnonymousGet(
        "/api/webtoonRoleList",
        this.urlParamsFormatter(filter, this.pageData),
        (response) => {
          let result = response.data[0];

          this.webtoonInfo = {
            ...this.webtoonInfo,
            title: result.title,
            img: result.uploadPath,
          };

          this.characterList = result.webtoonRoleEntityList;
        }
      );
    },
    goVotePage(id) {
      this.$router.push("/webtoon/vote/" + id);
    },
  },
  created() {
    this.getWebtoonCharacter();
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
  <ul class="character-list">
    <li v-for="item in characterList" @click="goVotePage(item.id)">
      <webtoon-character :img="item.uploadPath"></webtoon-character>
      <p>{{ item.name }}</p>
    </li>
  </ul>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/id";
</style>
