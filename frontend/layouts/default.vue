<template>
  <VApp id="scroll-target">
    <slot name="header"></slot>
    <VMain>
      <VContainer>
        <slot />
      </VContainer>
    </VMain>
  </VApp>
</template>

<style scoped lang="scss">
.v-container {
  min-height: calc(100vh - $header-height);
  padding-top: $header-height + 20px !important;
  padding-bottom: 100px !important;
}
</style>
