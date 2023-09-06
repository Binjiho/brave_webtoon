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
      searchValue: "",
      sortList: [
        { id: "최신순", text: "최신순" },
        { id: "오래된순", text: "오래된순" },
        { id: "투표많은순", text: "투표많은순" },
        { id: "투표낮은순", text: "투표낮은순" },
      ],
      sortNumber: "최신순",
      webtoonList: [],
      pageData: {
        page: 1,
        itemsPerPage: 15,
        length: 1,
      },
    };
  },
  methods: {
    search() {
      this.pageData.page = 1;
      this.getWebtoonList();
    },
    getWebtoonList() {
      const filter = {
        order: this.sortNumber,
        search: this.searchValue,
      };

      this.sendGet(
        "/api/admin/webtoonList",
        this.urlParamsPageFormatter(filter, this.pageData),
        (response) => {
          this.webtoonList = response.data[0].webtoonEntityList;
          this.pageData.length = response.data[0].endPage;
        }
      );
    },
    changeWebtoonShow(item) {
      let filter = new FormData();
      filter.append("id", item.id);
      filter.append("deleteYn", item.deleteYn === 1 ? 0 : 1);
      filter.append("deleteYN", item.deleteYn === 1 ? 0 : 1);

      this.sendPost(
        "/api/admin/webtoonList",
        filter,
        (response) => {
          this.getWebtoonList();
        },
        () => {},
        "multipart/form-data"
      );
    },
    changeSort() {
      this.pageData.page = 1;
      this.getWebtoonList();
    },
  },
  mounted() {
    this.getWebtoonList();
  },
};
</script>

<template>
  <admin-ui-title title="웹툰 리스트">
    <v-btn class="main-btn-40" width="140" to="/admin/webtoon/create"
      >웹툰 생성</v-btn
    >
  </admin-ui-title>
  <div class="filter">
    <v-text-field
      placeholder="웹툰명 및 캐릭터 입력"
      v-model="searchValue"
      @keyup.enter="search"
      hide-details
      class="height-40"
    >
      <template #append-inner>
        <v-btn icon="custom:search" variant="text" @click="search"></v-btn>
      </template>
    </v-text-field>
    <ui-tab-sort
      :list="sortList"
      v-model="sortNumber"
      @change="changeSort"
    ></ui-tab-sort>
  </div>
  <v-table class="table">
    <thead>
      <tr>
        <th width="80">No.</th>
        <th width="80">노출</th>
        <th class="text-left">웹툰명</th>
        <th width="120">생성일</th>
        <th width="120">총 투표 횟수</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in webtoonList" :key="item.id">
        <td>{{ item.id }}</td>
        <td>
          <v-switch
            class="big"
            :model-value="!item.deleteYn"
            @update:modelValue="changeWebtoonShow(item)"
            color="#000"
          ></v-switch>
        </td>
        <td class="text-left">
          <router-link
            :to="`/admin/webtoon/${item.id}`"
            class="text-overflow-1 link"
            >{{ item.title }}</router-link
          >
        </td>
        <td>{{ formattedDate(item.createdDate) }}</td>
        <td>{{ item.hit }}회</td>
      </tr>
    </tbody>
  </v-table>
  <v-pagination
    v-model="pageData.page"
    :length="pageData.length"
    @update:modelValue="getWebtoonList()"
  ></v-pagination>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/admin/webtoon/index.scss";
</style>
