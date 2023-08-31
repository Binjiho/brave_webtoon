<script>
import router from "#app/plugins/router";

export default {
  props: {
    isUnderLine: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isUser: true,
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
        <v-btn variant="text" to="/admin" color="#9747fe" v-if="isUser"
          >관리자 페이지 이동</v-btn
        >
        <v-btn variant="text" to="/account/login" v-if="!isUser">로그인</v-btn>
        <v-btn variant="text" class="user-info" v-if="isUser">
          <v-icon icon="custom:profileFill"></v-icon>
          홍길동</v-btn
        >
      </div>
    </VContainer>
  </header>
</template>

<style scoped lang="scss">
@import "assets/scss/components/ui/header";
</style>
