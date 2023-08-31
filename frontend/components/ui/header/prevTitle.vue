<script lang="ts">
export default {
  props: {
    isTransparent: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "",
    },
    isUnderLine: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      windowScrollTop: 0,
    };
  },
  methods: {
    onScroll(e) {
      this.windowScrollTop = e.target.scrollingElement.scrollTop;
    },
  },
};
</script>

<template>
  <header
    class="header"
    :class="{
      transparent: isTransparent,
      underline: isUnderLine,
      'is-scroll': windowScrollTop > 50,
    }"
    v-scroll="onScroll"
  >
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
