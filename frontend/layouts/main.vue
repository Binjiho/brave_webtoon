<script setup lang="ts">
import layout from "./default.vue";
</script>

<template>
  <layout>
    <template #header>
      <ui-header-main></ui-header-main>
    </template>
    <slot></slot>
  </layout>
</template>
