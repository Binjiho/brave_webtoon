<script setup>
const props = defineProps({
  error: Object,
});

const handleError = () => clearError({ redirect: "/" });
</script>

<template>
  <VContainer class="container">
    <h2 class="title">{{ error.statusCode }}</h2>
    <p class="message">{{ error.message }}</p>
    <div class="control-box">
      <VBtn @click="$router.back()">이전 페이지</VBtn>
      <VBtn @click="handleError()" variant="outlined">메인으로</VBtn>
    </div>
  </VContainer>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/page/error";
</style>
