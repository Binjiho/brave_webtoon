<script lang="ts">
export default {};
</script>

<template>
  <ui-header-prev-title isTransparent title="캐릭터 선택">
    <VBtn class="share-btn"></VBtn>
  </ui-header-prev-title>
  <div class="back-img"></div>
  <div class="webtoon-info">
    <webtoon-character class="webtoon-info__img"> </webtoon-character>
    <ui-label-play-type class="webtoon-info__label"> </ui-label-play-type>
    <h3 class="webtoon-info__title">나를 바꿔줘</h3>
    <p class="webtoon-info__text">하단의 캐릭터를 선택하여<br />투표해주세요</p>
  </div>
  <ul class="character-list">
    <li v-for="item in 10">
      <webtoon-character> </webtoon-character>
      <p>로이드프론테라로이드프론테라</p>
    </li>
  </ul>
</template>

<style lang="scss">
.back-img {
  background: gray;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 218px;
  z-index: -1;
}

.webtoon-info {
  display: grid;
  grid-template-columns: auto 1fr;
  column-gap: 12px;
  margin-bottom: 21px;
  grid-template-areas:
    "a b"
    "a c"
    "a ."
    "a d";

  &__img {
    grid-area: a;
  }

  &__label {
    grid-area: b;
  }

  &__title {
    grid-area: c;
    color: #fff;
    font-size: 16px;
  }

  &__text {
    grid-area: d;
    color: #fff;
    font-size: 12px;
  }
}

.character-list {
  display: flex;
  padding-top: 20px;
  flex-wrap: wrap;
  gap: 16px;

  li {
    font-size: 12px;
    width: min-content;
    cursor: pointer;

    &:hover {
      p {
        font-weight: bold;
      }
    }

    p {
      margin-top: 2px;
      @include textOverflow(100%, 1);
    }
  }
}
</style>
