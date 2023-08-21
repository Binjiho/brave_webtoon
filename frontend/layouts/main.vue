<script setup lang="ts">
import layout from "./default.vue";
</script>

<template>
  <layout>
    <template #header>
      <UiHeaderMain></UiHeaderMain>
    </template>
    <slot></slot>
  </layout>
</template>
