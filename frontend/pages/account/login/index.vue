<script lang="ts">
import UIConstants from "@/constants/UIConstants";
import api from "~/mixin/api";
import UIHelpers from "~/mixin/UIHelpers";
import { useUserStore } from "~/store/auth";

export default {
  mixins: [api, UIHelpers],
  setup() {
    const store = useUserStore();
    const { loginUserSuccess } = store;
    return { loginUserSuccess, store };
  },
  data() {
    return {
      rules: {
        ...UIConstants.inputRules,
      },
      isValidLogin: false,
      user: {
        id: "",
        pw: "",
      },
    };
  },
  methods: {
    userLogin() {
      const data = {
        userId: this.user.id,
        userPw: this.user.pw,
      };

      this.sendAnonymousPost(
        `/api/account/signIn`,
        data,
        (response) => {
          const token = response.data.token;

          if (!token) {
            this.$root.vtoast.show({ message: response.data.failMsg });
            return;
          }

          this.loginUserSuccess(response);
          this.$router.replace("/");
        },
        () => {}
      );
    },
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
            v-model="user.id"
          ></v-text-field>
        </li>
        <li>
          <ui-input-password
            placeholder="비밀번호"
            :rules="rules.password"
            v-model="user.pw"
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
