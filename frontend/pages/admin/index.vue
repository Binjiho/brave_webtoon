<script lang="ts">
import Main from "@/pages/admin/webtoon/index.vue";

export default {
  setup() {
    definePageMeta({
      layout: "admin",
      middleware: "is-admin",
    });
  },
  components: {
    Main,
  },
};
</script>

<template>
  <Main></Main>
</template>

<style scoped></style>
