<script lang="ts">
import validation from "@/constants/validation";

export default {
  props: {
    placeholder: {
      type: String,
      default: "",
    },
    rules: {
      type: Array,
      default: [(v) => v],
    },
    modelValue: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      formatNumber: "",
    };
  },
  computed: {
    value: {
      get() {
        return this.formatNumber;
      },
      set(value) {
        let n = validation.returnOnlyNumbers(value);
        const number = validation.formatPhoneNumber(n);
        this.$emit("update:modelValue", n);
        this.formatNumber = number;
      },
    },
  },
};
</script>

<template>
  <v-text-field
    :placeholder="placeholder"
    v-model="value"
    :rules="rules"
    class="height-50"
  ></v-text-field>
</template>
