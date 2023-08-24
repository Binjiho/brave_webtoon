<script lang="ts">
export default {
  props: {
    percent: {
      type: Number,
      default: 0,
    },
    label: {
      type: String,
      default: null,
    },
  },
};
</script>

<template>
  <div class="progress-round">
    <div
      class="progress-round__bar"
      :style="{ transform: `scaleX(${percent}%)` }"
    ></div>
    <p class="progress-round__text">
      {{ label }} <span>({{ percent }}%)</span>
    </p>
  </div>
</template>

<style scoped lang="scss">
@import "assets/scss/components/chart/progress/round";
</style>
