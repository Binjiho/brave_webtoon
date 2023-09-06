<script lang="ts">
import api from "~/mixin/api";
import UIHelpers from "~/mixin/UIHelpers";
import value from "~/mixin/value";
import UIConstants from "~/constants/UIConstants";

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
      rules: {
        ...UIConstants.inputRules,
      },
      isValidForm: false,
      webtoonUrl: "",
    };
  },
  methods: {
    createWebtoon() {
      if (!this.isValidForm) return;
    },
  },
  mounted() {},
};
</script>

<template>
  <v-form class="create-layout" v-model="isValidForm">
    <admin-ui-title title="웹툰 생성" isPrev></admin-ui-title>
    <div class="create-layout__sub-title">
      <h3 class="create-layout__sub-title__text">크롤링</h3>
      <v-btn
        class="right-arrow-btn"
        href="https://www.chuing.net/db/webtoon/menu/rank.php?sel_type=1"
        target="_blank"
        >사이트 바로가기</v-btn
      >
    </div>
    <ul class="form-list">
      <li>
        <h4 class="label">url 주소</h4>
        <v-text-field
          class="height-40"
          placeholder="url 주소를 적어주세요"
          :rules="rules.input"
          v-model="webtoonUrl"
          @keyup.enter="createWebtoon"
          clearable
        ></v-text-field>
      </li>
    </ul>
    <div class="create-layout__btns">
      <v-btn class="main-btn-40" :disabled="!isValidForm" @click="createWebtoon"
        >생성하기</v-btn
      >
    </div>
  </v-form>
</template>

<style scoped lang="scss">
@import "@/assets/scss/page/admin/webtoon/create.scss";
</style>
