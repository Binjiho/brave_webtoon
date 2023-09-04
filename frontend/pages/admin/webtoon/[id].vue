<script lang="ts">
import api from "~/mixin/api";
import UIHelpers from "~/mixin/UIHelpers";
import value from "~/mixin/value";

export default {
  setup() {
    definePageMeta({
      layout: "admin",
      middleware: "is-admin",
    });
  },
  mixins: [api, UIHelpers, value],
  data() {
    return {
      webtoonId: this.$route.params.id,
      searchValue: "",
      sortList: [
        { id: "최신순", text: "최신순" },
        { id: "오래된순", text: "오래된순" },
        { id: "투표많은순", text: "투표많은순" },
        { id: "투표낮은순", text: "투표낮은순" },
      ],
      sortNumber: "최신순",
      webtoonRoleList: [],
      pageData: {
        page: 1,
        itemsPerPage: 10,
        length: 1,
      },
      webtoonRoleId: null,
      webtoonVoteList: [],
    };
  },
  methods: {
    async getWebtoonRoleList() {
      return new Promise((resolve, reject) => {
        this.sendGet(
          `/api/admin/webtoonList/${this.webtoonId}`,
          this.urlParamsPageFormatter(null, this.pageData),
          (response) => {
            this.webtoonRoleList = response.data[0].webtoonRoleEntityList;
            this.webtoonRoleId = response.data[0].webtoonRoleEntityList[0].id;
            resolve(response);
          }
        );
      });
    },
    changeWebtoonRoleShow(item) {
      let filter = new FormData();
      filter.append("roleId", item.id);
      filter.append("deleteYn", item.deleteYn === 1 ? 0 : 1);

      this.sendPost(
        `/api/admin/webtoonList/${this.webtoonId}`,
        filter,
        (response) => {
          this.getWebtoonRoleList();
        },
        () => {},
        "multipart/form-data"
      );
    },
    getWebtoonVoteList() {
      const filter = {
        roleId: this.webtoonRoleId,
      };
      this.sendGet("/api/admin/voteList", filter, (response) => {
        this.webtoonVoteList = response.data;
      });
    },
    changeWebtoonVote(id) {
      this.webtoonRoleId = id;
      this.getWebtoonVoteList();
    },
    savePersonImage(item) {
      let filter = new FormData();
      filter.append("voteId", item.id);
      filter.append("personUrl", item.personUrl);

      this.sendPost(
        "/api/admin/voteList/",
        filter,
        (response) => {
          this.getWebtoonVoteList();
        },
        () => {},
        "multipart/form-data"
      );
    },
  },
  mounted() {
    this.getWebtoonRoleList().then(() => {
      this.getWebtoonVoteList();
    });
  },
};
</script>

<template>
  <admin-ui-title title="웹툰 리스트 상세" isPrev></admin-ui-title>
  <div class="layout">
    <div>
      <div class="webtoon-info">
        <h3 class="webtoon-info__title">웹툰 정보</h3>
        <div class="webtoon-item"></div>
      </div>
      <v-table class="table">
        <thead>
          <tr>
            <th width="60">No.</th>
            <th width="70">노출</th>
            <th class="text-left">캐릭터</th>
            <th width="100">총 투표 횟수</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="item in webtoonRoleList"
            :key="item.id"
            :class="{ active: item.id === webtoonRoleId }"
          >
            <td>{{ item.id }}</td>
            <td>
              <v-switch
                class="big"
                :model-value="!item.deleteYn"
                @update:modelValue="changeWebtoonRoleShow(item)"
              ></v-switch>
            </td>
            <td class="text-left">
              <p
                class="text-overflow-1 link"
                @click="changeWebtoonVote(item.id)"
              >
                {{ item.name }}
              </p>
            </td>
            <td>{{ item.hit }}회</td>
          </tr>
        </tbody>
      </v-table>
      <v-pagination
        v-model="pageData.page"
        :length="pageData.length"
        @update:modelValue="getWebtoonRoleList()"
      ></v-pagination>
    </div>
    <div class="vote-info">
      <v-table class="table">
        <thead>
          <tr>
            <th width="60">순위</th>
            <th class="text-left">연예인 정보</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, i) in webtoonVoteList" :key="item.id">
            <td>{{ i + 1 }}</td>
            <td class="text-left">
              <div class="celebrity-info">
                <webtoon-character :img="item.personUrl"> </webtoon-character>
                <ul class="celebrity-info__list">
                  <li>
                    <h5>이름</h5>
                    <p>{{ item.personName }}</p>
                  </li>
                  <li>
                    <h5>투표 횟수</h5>
                    <p>{{ item.cnt }}회</p>
                  </li>
                  <li>
                    <h5>이미지 링크</h5>
                    <div class="btn-text-field">
                      <v-text-field
                        v-model="item.personUrl"
                        class="height-small"
                        hide-details
                      >
                      </v-text-field>
                      <v-btn @click="savePersonImage(item)">저장</v-btn>
                    </div>
                  </li>
                </ul>
              </div>
            </td>
          </tr>
        </tbody>
      </v-table>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/admin/webtoon/id.scss";
</style>
