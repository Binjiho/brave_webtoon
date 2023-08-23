<script lang="ts">
export default {
  data() {
    return {
      characterInfo: {
        id: this.$route.params.id,
      },
      mostVote: {},
      restVoteList: [],
    };
  },
  methods: {
    getWebtoonResult() {
      this.$api
        .get("/api/webtoonVote/result/" + this.characterInfo.id)
        .then((response) => {
          let result = response.data[0];
          let { title, uploadPath } = result;
          this.characterInfo = { title, uploadPath };

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
        });
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
  <div class="vote-result">
    <strong class="vote-result__percent">{{ mostVote.percent }}%</strong>
    <p class="vote-result__text"><b>나를 바꿔줘</b> 가상캐스팅 투표 1위</p>
    <ul class="most-vote">
      <li>
        <img :src="characterInfo.uploadPath" alt="" />
        <div class="text">
          <p>{{ characterInfo.title }}</p>
        </div>
      </li>
      <li>
        <img :src="mostVote.personUrl" alt="" />
        <div class="text">
          <p>{{ mostVote.personName }} ({{ mostVote.cnt }}표)</p>
        </div>
      </li>
    </ul>
    <ul>
      <li>
        <p></p>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/webtoon/result/id";
</style>
