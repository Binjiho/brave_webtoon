<script lang="ts">
export default {
  data() {
    return {
      characterInfo: {
        id: this.$route.query.character,
        name: "",
      },
      webtoonInfo: {
        id: this.$route.query.webtoon,
        title: "",
      },
      mostVote: {},
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
    getWebtoonResult() {
      this.$api
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
        })
        .then((response) => {
          this.getWebtoonCharacter();
        });
    },
    getWebtoonCharacter() {
      this.$api
        .get("/api/webtoonRoleList", {
          params: {
            id: this.webtoonInfo.id,
            pageSize: 20,
            offset: 0,
          },
        })
        .then((response) => {
          let result = response.data[0];
          this.characterList = result.webtoonRoleEntityList;
        });
    },
    goVotePage(id) {
      this.$router.push("/webtoon/vote/" + id);
    },
  },
  created() {
    this.getWebtoonResult();
  },
};
</script>

<template>
  <ui-header-prev-title title="투표결과">
    <VBtn class="share-btn"></VBtn>
  </ui-header-prev-title>
  <div v-if="webtoonInfo.id"></div>
  <!--2. start 가상캐스팅 투표 1위-->
  <div class="vote-result">
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
      v-if="votePagingList.length"
      >전체 순위 {{ isAllVote ? "닫기" : "확인하기" }}
      <v-icon
        :icon="isAllVote ? 'custom:arrowUpSLine' : 'custom:arrowDownSLine'"
        size="12"
      ></v-icon>
    </v-btn>
  </div>
  <!--2. end 가상캐스팅 투표 1위-->
  <!--3. start 다른 캐릭터 투표-->
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
</template>

<style scoped lang="scss">
@import "assets/scss/page/webtoon/result";
</style>
