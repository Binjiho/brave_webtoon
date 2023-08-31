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
.v-application {
  background: #f2f5f7;
  height: 100vh;
}

.v-main {
  position: relative;
  z-index: 1;
  max-width: $max-layout;
  background: #fff;
  margin: 0 auto !important;
  width: 100%;
  height: 100%;
}

.v-container {
  padding: 20px !important;
  margin: 0 auto !important;
  max-width: $max-layout;
  min-width: $min-layout;
  min-height: 100%;
  padding-top: $header-height !important;
  box-sizing: border-box;
}
</style>
