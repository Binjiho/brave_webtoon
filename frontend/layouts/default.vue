<template>
  <VApp>
    <slot name="header"></slot>
    <VMain>
      <VContainer>
        <slot />
      </VContainer>
    </VMain>
  </VApp>
</template>

<style scoped>
.v-main {
  padding-top: 60px;
}
</style>
