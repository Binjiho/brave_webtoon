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
      searchValue: "",
    };
  },
  methods: {
    search() {
      this.$emit("search", this.searchValue);
    },
  },
};
</script>

<template>
  <v-text-field
    class="search-btn-input"
    :placeholder="placeholder"
    @update:focused="isFocused = !isFocused"
    :class="{ focus: isFocused }"
    v-model="searchValue"
    @keyup.enter="search"
    hide-details
  >
    <template #append>
      <v-btn icon="custom:search" variant="text" @click="search"></v-btn>
    </template>
  </v-text-field>
</template>

<style lang="scss">
@import "@/assets/scss/components/ui/input/search";
</style>
