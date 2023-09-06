<script setup lang="ts">
import layout from "./default.vue";
</script>

<template>
  <layout isSidebar>
    <template #header>
      <ui-header-main isUnderLine></ui-header-main>
    </template>
    <admin-side-bar class="side-bar"></admin-side-bar>
    <div class="admin-wrap admin">
      <slot></slot>
    </div>
  </layout>
</template>

<style lang="scss">
.admin {
  @import "@/assets/scss/page/admin/common.scss";
}
</style>
<style lang="scss" scoped>
.admin-wrap {
  background: #fafafa;
  min-height: calc(100vh - $header-height);
  margin-top: $header-height;
  padding: 44px 40px;
  min-width: 900px;
}

.side-bar {
  position: absolute;
  left: 0;
  height: calc(100% - $header-height);
}
</style>
