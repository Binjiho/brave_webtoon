<script lang="ts">
import router from "#app/plugins/router";

export default {
  methods: { router },
  props: {
    isTransparent: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "타이틀",
    },
  },
};
</script>

<template>
  <header class="header" :class="{ transparent: isTransparent }">
    <VContainer class="header__inner">
      <div class="header__left-area">
        <VBtn class="prev-btn" @click="$router.back()"></VBtn>
      </div>
      <div class="header__center-area">
        <h2 class="title">{{ title }}</h2>
      </div>
      <div class="header__right-area">
        <slot></slot>
      </div>
    </VContainer>
  </header>
</template>

<style scoped lang="scss">
@import "assets/scss/components/ui/header";
</style>
