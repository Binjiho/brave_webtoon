<script setup></script>

<template>
  <header class="header">
    <VContainer class="header__inner">
      <h1 class="logo">
        <VBtn variant="text" height="20" :ripple="false" to="/" :active="false">
          <VIcon icon="custom:colorLogo"></VIcon>
        </VBtn>
      </h1>
    </VContainer>
  </header>
</template>

<style scoped lang="scss">
@import "@/assets/scss/components/ui/header/main";
</style>
