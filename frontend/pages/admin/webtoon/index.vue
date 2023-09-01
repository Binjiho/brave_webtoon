<script lang="ts">
definePageMeta({
  layout: "admin",
});

export default {
  data() {
    return {
      searchValue: "",
    };
  },
  methods: {
    search() {},
  },
};
</script>

<template>
  <admin-ui-title title="웹툰 리스트"></admin-ui-title>
  <div class="filter">
    <v-text-field
      placeholder="입력"
      v-model="searchValue"
      @keyup.enter="search"
      hide-details
    >
      <template #append-inner>
        <v-btn icon="custom:search" variant="text" @click="search"></v-btn>
      </template>
    </v-text-field>
  </div>
</template>

<style scoped>
@import "@/assets/scss/page/admin/common.scss";
</style>
