<script lang="ts">
export default {
  mounted() {
    this.$root.vtoast = this.$refs.vtoast;
  },
};
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
  <ui-snackbar-info ref="vtoast"></ui-snackbar-info>
</template>

<style lang="scss">
@import "@/assets/scss/vuetify/reset.scss";
</style>
