<script lang="ts">
import UIConstants from "@/constants/UIConstants";

export default {
  data() {
    return {
      rules: {
        ...UIConstants.inputRules,
      },
      isValidLogin: false,
    };
  },
  methods: {
    userLogin() {},
  },
};
</script>

<template>
  <ui-header-prev-title></ui-header-prev-title>
  <div class="account-layout">
    <v-form v-model="isValidLogin">
      <h2 class="account-layout__title logo">
        <VBtn variant="text" :ripple="false" :active="false" to="/">
          <VIcon icon="custom:colorLogo"></VIcon>
        </VBtn>
      </h2>
      <ul class="login-form">
        <li>
          <v-text-field
            placeholder="아이디"
            class="height-50"
            :rules="rules.input"
          ></v-text-field>
        </li>
        <li>
          <ui-input-password
            placeholder="비밀번호"
            :rules="rules.password"
          ></ui-input-password>
        </li>
      </ul>
      <v-btn
        block
        class="main-btn-50"
        :disabled="!isValidLogin"
        @click="userLogin()"
        >로그인</v-btn
      >
    </v-form>
    <div class="no-account">
      <p>계정이 없으세요?</p>
      <v-btn to="/account/join">회원가입하기</v-btn>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "assets/scss/page/account/login/index";
</style>
