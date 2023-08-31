<script lang="ts">
import UIConstants from "@/constants/UIConstants";

export default {
  data() {
    return {
      rules: {
        ...UIConstants.inputRules,
      },
      user: {
        id: "",
        password: "",
        name: "",
        phoneNumber: "",
      },
      isValidId: false,
      isValidJoin: false,
      isOnlyId: false,
    };
  },
  methods: {
    checkValidId() {
      this.isOnlyId = true;
      this.isValidId = false;
      this.$root.vtoast.show({ message: "아이디가 중복 확인 되었습니다." });
    },
    createAccount() {
      this.$root.vtoast.show({
        message: "회원가입이 완료되었습니다<br>로그인을 해주세요",
      });
      this.$router.push("/account/login");
    },
  },
};
</script>
<template>
  <ui-header-prev-title></ui-header-prev-title>
  <div class="account-layout">
    <v-form v-model="isValidJoin">
      <h2 class="account-layout__title">회원가입</h2>
      <ul class="join-form">
        <li>
          <label class="label required">아이디</label>
          <v-form v-model="isValidId" @submit.prevent="checkValidId">
            <div class="btn-wrap">
              <v-text-field
                placeholder="아이디"
                class="height-50"
                :rules="rules.id"
                v-model="user.id"
                :disabled="isOnlyId"
              ></v-text-field>
              <v-btn
                class="main-btn-50"
                width="100"
                :disabled="!isValidId"
                @click="checkValidId()"
                >{{ isOnlyId ? "확인완료" : "중복확인" }}</v-btn
              >
            </div>
          </v-form>
        </li>
        <li>
          <label class="label required">비밀번호</label>
          <ui-input-password
            placeholder="영어,숫자,특수문자 포함 10자 이상"
            :rules="rules.password"
            v-model="user.password"
          ></ui-input-password>
        </li>
        <li>
          <label class="label required">이름</label>
          <v-text-field
            v-model="user.name"
            placeholder="성함 입력"
            class="height-50"
            :rules="rules.input"
          ></v-text-field>
        </li>
        <li>
          <label class="label required">휴대폰번호</label>
          <ui-input-phone
            v-model="user.phoneNumber"
            placeholder="(예시) 010-1234-5678"
            :rules="rules.phoneFormat"
          >
          </ui-input-phone>
        </li>
      </ul>
      <div class="bottom-fixed">
        <v-btn
          class="main-btn-50"
          block
          :disabled="!isValidJoin"
          @click="createAccount()"
          >가입하기</v-btn
        >
      </div>
    </v-form>
  </div>
</template>

<style lang="scss" scoped>
@import "assets/scss/page/account/join/index";
</style>
