<script lang="ts">
import api from "~/mixin/api";
import UIHelpers from "~/mixin/UIHelpers";

export default {
  mixins: [api, UIHelpers],
  data() {
    return {
      characterInfo: {
        id: this.$route.params.id,
        name: "",
        img: "",
      },
      webtoonInfo: {
        id: "",
        title: "",
      },
      voteList: null,
      newCelebrity: null,
      activeCelebrity: "",
      celebrityName: "",
    };
  },
  methods: {
    getWebtoonCharacter() {
      this.sendAnonymousGet(
        `/api/webtoonVote/${this.characterInfo.id}`,
        "",
        (response) => {
          let result = response.data[0];
          this.characterInfo = {
            ...this.characterInfo,
            name: result.name,
            img: result.uploadPath,
          };

          this.webtoonInfo = {
            id: result.webtoonId,
            title: result.title,
          };

          this.voteList = result.voteEntityList;
        }
      );
    },
    searchCelebrity() {
      this.sendAnonymousGet(
        `/api/person/${this.celebrityName}`,
        "",
        (response) => {
          let result = response.data.items[0];
          this.newCelebrity = {
            personUrl: result.thumbnail,
            personName: this.celebrityName,
          };
        }
      );
    },
    postVote() {
      if (typeof this.activeCelebrity === "boolean") {
        this.newVote();
        return;
      }

      let findCelebrity = this.voteList.find(
        (v) => v.id === this.activeCelebrity
      );

      const data = {
        webtoonId: this.webtoonInfo.id,
        webtoonRoleId: this.characterInfo.id,
        personName: findCelebrity.personName,
        personUrl: findCelebrity.personUrl,
      };

      this.sendAnonymousPost(`/api/webtoonVote`, data, (response) => {
        this.$root.vtoast.show({ message: "투표가 완료되었습니다" });
        this.$router.replace(
          `/webtoon/vote/result?character=${this.characterInfo.id}&webtoon=${this.webtoonInfo.id}`
        );
      });
    },
    newVote() {
      const data = {
        webtoonId: this.webtoonInfo.id,
        webtoonRoleId: this.characterInfo.id,
        personName: this.newCelebrity.personName,
        personUrl: this.newCelebrity.personUrl,
      };

      this.sendAnonymousPost(`/api/webtoonVote`, data, (response) => {
        this.$root.vtoast.show({ message: "투표가 완료되었습니다" });
        this.$router.replace(
          `/webtoon/vote/result?character=${this.characterInfo.id}&webtoon=${this.webtoonInfo.id}`
        );
      });
    },
  },
  created() {
    this.getWebtoonCharacter();
  },
};
</script>

<template>
  <ui-header-share-title title="투표하기"></ui-header-share-title>
  <div class="webtoon-info">
    <webtoon-character
      class="webtoon-info__img"
      :img="characterInfo.img"
    ></webtoon-character>
    <span class="webtoon-info__title">
      {{ webtoonInfo.title }}
    </span>
    <h3 class="webtoon-info__character">
      {{ characterInfo.name }}
    </h3>
    <p class="webtoon-info__text">
      아래 이미지에서 가장 잘 어울리는 사람<br />
      1명을 투표해주세요
    </p>
  </div>
  <hr class="divider" />
  <div class="celebrity-wrap">
    <v-radio-group
      v-model="activeCelebrity"
      v-if="voteList?.[0].id"
      hide-details
    >
      <div class="celebrity-list">
        <webtoon-person-select
          v-for="item in voteList"
          :item="item"
        ></webtoon-person-select>
      </div>
    </v-radio-group>
    <div class="celebrity-search">
      <p class="celebrity-search__title" v-if="voteList?.[0].id">
        찾는 연예인이 없다면?
      </p>
      <p class="celebrity-search__title" v-else>연예인 이름을 검색해주세요</p>
      <ui-input-search
        placeholder="연예인 이름 입력"
        @search="searchCelebrity"
        v-model="celebrityName"
      ></ui-input-search>
    </div>
    <div class="celebrity-list"></div>
    <v-radio-group v-model="activeCelebrity">
      <webtoon-person-select
        :item="newCelebrity"
        v-if="newCelebrity"
      ></webtoon-person-select>
    </v-radio-group>
  </div>
  <div class="bottom-fixed">
    <v-btn
      @click="postVote()"
      block
      class="main-btn-50"
      :disabled="!activeCelebrity"
      >투표완료</v-btn
    >
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/webtoon/vote/id";
</style>
