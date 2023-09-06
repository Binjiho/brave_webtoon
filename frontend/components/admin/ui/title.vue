<script lang="ts">
export default {
  props: {
    title: {
      type: String,
      default: "",
    },
    isPrev: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<template>
  <div class="title-wrap">
    <h3 class="main-title">
      <v-icon
        v-if="isPrev"
        icon="custom:arrowLeftMLine"
        @click="$router.back()"
      ></v-icon>
      <span>{{ title }}</span>
    </h3>
    <slot></slot>
  </div>
</template>

<style scoped lang="scss">
.title-wrap {
  display: flex;
  justify-content: space-between;
  min-height: 40px;
  align-items: center;
}

.main-title {
  font-size: 20px;
  font-weight: 700;
  color: #000;

  .v-icon {
    width: 24px;
    height: 24px;
    margin-right: 18px;
  }
}
</style>
