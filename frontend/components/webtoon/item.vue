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
      return "/webtoon/" + this.item.id;
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
            <img src="https://via.placeholder.com/225x350" alt="" />
          </div>
          <p>청명</p>
        </li>
        <li class="character">
          <div class="character__img-wrap">
            <img src="https://via.placeholder.com/225x350" alt="" />
          </div>
          <p>이도현</p>
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
        <v-btn prepend-icon="custom:rankingLine">랭킹보기</v-btn>
      </li>
      <li>
        <v-btn prepend-icon="custom:shareLine">공유하기</v-btn>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/components/webtoon/item";
</style>
