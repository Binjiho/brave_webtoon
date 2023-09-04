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
        itemsPerPage: 20,
        length: 1,
      },
    };
  },
  methods: {
    getWebtoonRoleList() {
      this.sendGet(
        `/api/admin/webtoonList/${this.webtoonId}`,
        null,
        (response) => {
          console.log(response);
          this.webtoonRoleList = response.data;
        }
      );
    },
  },
  mounted() {
    this.getWebtoonRoleList();
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
          <tr v-for="item in webtoonRoleList" :key="item.id">
            <td>{{ item.id }}</td>
            <td>
              <v-switch
                class="big"
                :model-value="!item.deleteYn"
                @update:modelValue="changeWebtoonShow(item)"
              ></v-switch>
            </td>
            <td class="text-left">
              <p class="text-overflow-1">{{ item.name }}</p>
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
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/admin/webtoon/id.scss";
</style>
