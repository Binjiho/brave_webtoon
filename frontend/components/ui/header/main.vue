<script>
import router from "#app/plugins/router";

export default {
  props: {
    isUnderLine: {
      type: Boolean,
      default: false,
    },
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
    </VContainer>
  </header>
</template>

<style scoped lang="scss">
@import "assets/scss/components/ui/header";
</style>
