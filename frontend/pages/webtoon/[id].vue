<script lang="ts">
export default {
  data() {
    return {
      webtoonId: this.$route.params.id,
      webtoonTitle: "",
      webToonImg: "",
      characterList: [],
    };
  },
  methods: {
    getWebtoonCharacter() {
      this.$api
        .get("/api/webtoonRoleList", {
          params: {
            id: this.webtoonId,
            pageSize: 20,
            offset: 0,
          },
        })
        .then((response) => {
          let result = response.data[0];
          this.webtoonTitle = result.title;
          this.webToonImg = result.uploadPath;
          this.characterList = result.webtoonRoleEntityList;
        });
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
  <ui-header-prev-title isTransparent title="캐릭터 선택">
    <VBtn class="share-btn"></VBtn>
  </ui-header-prev-title>
  <div class="back-img"></div>
  <div class="webtoon-info">
    <webtoon-character class="webtoon-info__img" :img="webToonImg">
    </webtoon-character>
    <ui-label-play-type class="webtoon-info__label"> </ui-label-play-type>
    <h3 class="webtoon-info__title">{{ webtoonTitle }}</h3>
    <p class="webtoon-info__text">하단의 캐릭터를 선택하여<br />투표해주세요</p>
  </div>
  <ul class="character-list">
    <li v-for="item in characterList" @click="goVotePage(item.id)">
      <webtoon-character
        :img="item.uploadPath + item.title + '.jpg'"
      ></webtoon-character>
      <p>{{ item.title }}</p>
    </li>
  </ul>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/webtoon/id";
</style>
