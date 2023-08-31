<script lang="ts">
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
  },
  data() {
    return {
      visible: false,
      password: "",
    };
  },
};
</script>

<template>
  <v-text-field
    autocomplete="off"
    v-model="password"
    :append-inner-icon="
      password ? (visible ? 'custom:show' : 'custom:hide') : ''
    "
    density="compact"
    :type="visible ? 'text' : 'password'"
    @click:append-inner="visible = !visible"
    :placeholder="placeholder"
    clearable
    :rules="rules"
    class="height-50"
  ></v-text-field>
</template>

<style scoped lang="scss"></style>
