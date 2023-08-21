<script lang="ts">
export default {
  props: {
    placeholder: {
      type: String,
      default: "입력",
    },
  },
  data() {
    return {
      isFocused: false,
    };
  },
};
</script>

<template>
  <v-text-field
    class="search-btn-input"
    :placeholder="placeholder"
    @update:focused="isFocused = !isFocused"
    :class="{ focus: isFocused }"
  >
    <template #append>
      <v-btn icon="custom:search" variant="text"></v-btn>
    </template>
  </v-text-field>
</template>

<style lang="scss">
@import "@/assets/scss/components/ui/input/search";
</style>
