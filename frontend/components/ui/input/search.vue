<script lang="ts">
import validation from "~/constants/validation";

export default {
  props: {
    placeholder: {
      type: String,
      default: "입력",
    },
    modelValue: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      isFocused: false,
    };
  },
  computed: {
    value: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
  },
  methods: {
    search() {
      this.$emit("search", this.value);
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
    v-model="value"
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
