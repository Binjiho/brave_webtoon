<script lang="ts">
export default {
  data() {
    return {
      characterInfo: {
        id: Number(this.$route.query.character),
        name: "",
      },
      webtoonInfo: {
        id: Number(this.$route.query.webtoon),
        title: "",
      },
      mostVote: {
        cnt: 0,
      },
      restVoteList: [],
      isAllVote: false,
      characterList: [],
    };
  },
  computed: {
    votePagingList() {
      if (this.isAllVote) {
        return this.restVoteList;
      }
      return this.restVoteList.slice(0, 4);
    },
  },
  methods: {
    async getWebtoonResult() {
      const response = await this.$api
        .get("/api/webtoonVote/result/" + this.characterInfo.id)
        .then((response) => {
          let result = response.data[0];
          let { title, uploadPath, name, webtoonId: id } = result;
          this.characterInfo = { ...this.characterInfo, name, uploadPath };
          this.webtoonInfo = { ...this.webtoonInfo, title, id };

          let allCount = result.voteResultListDtoList.reduce((acc, cur) => {
            return acc + cur.cnt;
          }, 0);

          let [first, ...rest] = result.voteResultListDtoList
            .sort((a, b) => b.cnt - a.cnt)
            .map((v) => {
              return {
                ...v,
                percent: Math.floor((v.cnt / allCount) * 100),
              };
            });

          this.mostVote = first;
          this.restVoteList = rest;
          return result;
        });
    },
    async getWebtoonCharacter() {
      const response = await this.$api
        .get("/api/webtoonRoleList", {
          params: {
            id: this.webtoonInfo.id,
            pageSize: 100,
            offset: 0,
          },
        })
        .then((response) => {
          let result = response.data[0];
          let { title, uploadPath: img } = result;
          this.webtoonInfo = { ...this.webtoonInfo, title, img };
          this.characterList = result.webtoonRoleEntityList;
          return result;
        });

      return response;
    },
    goVotePage(id) {
      this.$router.push("/webtoon/vote/" + id);
    },
    changeCharacterId() {
      this.$router.replace(
        `/webtoon/vote/result?character=${this.characterInfo.id}&webtoon=${this.webtoonInfo.id}`
      );
      this.getWebtoonResult();
    },
  },
  created() {
    this.getWebtoonCharacter().then((response) => {
      if (!this.characterInfo.id) {
        this.characterInfo.id = response.webtoonRoleEntityList[0].id;
      }
      this.getWebtoonResult();
    });
  },
};
</script>

<template>
  <ui-header-share-title isTransparent title="랭킹보기">
  </ui-header-share-title>
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
    <p class="webtoon-info__text">하단의 캐릭터를 선택하여<br />확인해주세요</p>
  </div>
  <!--1. start 캐릭터 리스트-->
  <div>
    <v-tabs
      v-model="characterInfo.id"
      class="block-tab"
      @update:modelValue="changeCharacterId"
    >
      <v-tab v-for="item in characterList" :key="item.i" :value="item.id">
        {{ item.name }}
      </v-tab>
    </v-tabs>
  </div>
  <!--1. end 캐릭터 리스트-->
  <!--2. start 가상캐스팅 투표 1위-->
  <div class="vote-result" v-if="mostVote.cnt">
    <strong class="vote-result__percent">{{ mostVote.percent || 0 }}%</strong>
    <p class="vote-result__text">
      <b>{{ this.webtoonInfo.title }}</b> 가상캐스팅 투표 1위
    </p>
    <ul class="most-vote">
      <li>
        <img :src="characterInfo.uploadPath" alt="" />
        <div class="text">
          <p>{{ characterInfo.name }}</p>
        </div>
      </li>
      <li>
        <img :src="mostVote.personUrl" alt="" />
        <div class="text">
          <p>{{ mostVote.personName }} ({{ mostVote.cnt }}표)</p>
        </div>
      </li>
    </ul>
    <ul class="vote-list" v-if="votePagingList.length">
      <li
        v-for="(item, index) in votePagingList"
        :key="item.i"
        class="vote-item"
      >
        <p class="vote-item__ranking">{{ index + 2 }}위</p>
        <div class="vote-item__percent-box">
          <div class="vote-item__percent-box__img-box">
            <img :src="item.personUrl" alt="" />
          </div>
          <chart-progress-round
            :percent="item.percent"
            :label="item.personName"
          ></chart-progress-round>
        </div>
      </li>
    </ul>
    <v-btn
      variant="text"
      class="all-vote-btn"
      height="14"
      @click="isAllVote = !isAllVote"
      v-if="restVoteList.length > 4"
      >전체 순위 {{ isAllVote ? "닫기" : "확인하기" }}
      <v-icon
        :icon="isAllVote ? 'custom:arrowUpSLine' : 'custom:arrowDownSLine'"
        size="12"
      ></v-icon>
    </v-btn>
  </div>
  <!--2. end 가상캐스팅 투표 1위-->
  <!--3. start 가상캐스팅 투표 없음-->
  <ui-info-no-area
    v-else
    message="투표 내용이 없습니다"
    btnText="투표하러 가기"
    @btn-click="goVotePage(characterInfo.id)"
  ></ui-info-no-area>
  <!--3. end 가상캐스팅 투표 없음-->
  <!--4. start 다른 캐릭터 투표-->
  <div class="character-vote">
    <p class="character-vote__title">
      ‘{{ webtoonInfo.title }}’의<br />다른 캐릭터를 투표해보세요
    </p>
    <ul class="character-list">
      <li v-for="item in characterList" @click="goVotePage(item.id)">
        <webtoon-character :img="item.uploadPath"></webtoon-character>
        <p>{{ item.name }}</p>
      </li>
    </ul>
  </div>
  <!--4. end 다른 캐릭터 투표-->
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/webtoon/vote/result";
</style>
