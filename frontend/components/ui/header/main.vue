<script>
import router from "#app/plugins/router";
import { useCounterStore } from "~/store/auth";

export default {
  setup() {
    const store = useCounterStore();
    const access_token = computed(() => store.access_token);
    const user = computed(() => store.user);
    const { logoutUser } = store;
    return { access_token, user, logoutUser };
  },
  props: {
    isUnderLine: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isUser: false,
    };
  },
};
</script>

<template>
  <header class="header" :class="{ underline: isUnderLine }">
    <VContainer class="header__inner">
      <h1 class="logo">
        <VBtn
          variant="text"
          width="77"
          height="20"
          :ripple="false"
          to="/"
          :active="false"
        >
          <VIcon icon="custom:colorLogo"></VIcon>
        </VBtn>
      </h1>
      <div class="account-wrap">
        <v-btn variant="text" to="/account/login" v-if="!access_token"
          >로그인</v-btn
        >
        <v-menu v-if="access_token" open-on-hover offset="5">
          <template v-slot:activator="{ props }">
            <v-btn variant="text" class="user-info" v-bind="props">
              <v-icon icon="custom:profileFill"></v-icon>
              {{ user.name }}</v-btn
            >
          </template>
          <v-list class="auth-list">
            <v-list-item to="/admin" v-if="user.role === 'ADMIN'">
              관리자 페이지 이동
            </v-list-item>
            <v-list-item @click="logoutUser"> 로그아웃 </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </VContainer>
  </header>
</template>

<style scoped lang="scss">
@import "assets/scss/components/ui/header";
</style>
