<script lang="ts">
export default {
  props: {
    item: {
      type: Object,
      default: null,
    },
  },
  computed: {
    votePageUrl() {
      return "/webtoon/" + this.item.webtoonId;
    },
    rankPageUrl() {
      return `/webtoon/vote/result?webtoon=${this.item.webtoonId}`;
    },
  },
  methods: {
    sharePage() {
      const shareObject = {
        title: this.item.title,
        text: "webtoon.com",
        url: window.location.href + "webtoon/" + this.item.webtoonId,
      };

      if (!navigator.share) {
        // navigator를 지원하지 않는 경우
        this.$root.vtoast.show({ message: "페이지 공유를 지원하지 않습니다." });
        return;
      }

      navigator.share(shareObject).catch((error) => {
        this.$root.vtoast.show({ message: "공유할 수 없는 페이지입니다." });
      });
    },
  },
};
</script>

<template>
  <div class="webtoon-item">
    <router-link :to="votePageUrl" class="webtoon-item__link">
      <ul class="webtoon-item__thumbnail">
        <li class="character">
          <div class="character__img-wrap">
            <webtoon-character :img="item.uploadPath"> </webtoon-character>
          </div>
          <p>{{ item.name }}</p>
        </li>
        <li class="character">
          <div class="character__img-wrap">
            <webtoon-character :img="item.personUrl"> </webtoon-character>
          </div>
          <p>{{ item.personName || "?" }}</p>
        </li>
      </ul>
      <h2 class="webtoon-item__title">{{ item.title }}</h2>
    </router-link>
    <ul class="btn-box">
      <li>
        <v-btn prepend-icon="custom:checkLine" :to="votePageUrl"
          >투표하기</v-btn
        >
      </li>
      <li>
        <v-btn prepend-icon="custom:rankingLine" :to="rankPageUrl"
          >랭킹보기</v-btn
        >
      </li>
      <li>
        <v-btn prepend-icon="custom:shareLine" @click="sharePage()"
          >공유하기</v-btn
        >
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/components/webtoon/item";
</style>
