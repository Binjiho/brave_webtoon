<script lang="ts">
export default {
  props: {
    item: {
      type: Object,
      default: null,
    },
  },
};
</script>

<template>
  <div class="person-select">
    <v-radio
      class="person-select__check-box"
      :value="item.id"
      trueIcon="custom:checkOn"
      falseIcon="custom:checkOff"
    ></v-radio>
    <img :src="item.personUrl" class="person-select__img" />
    <v-icon icon="custom:personFill" class="person-select__icon"></v-icon>
    <div class="person-select__name">
      <p>{{ item.personName }}</p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.person-select {
  width: 100%;
  height: 80px;
  position: relative;
  overflow: hidden;
  border-radius: 3px;
  max-width: 80px;

  &__check-box {
    position: absolute;
    left: 0;
    top: 0;
    background: #fff;

    &:not(.v-selection-control--dirty) {
      border: 1px solid #e8e8e8;
    }
  }

  &__img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  &__icon {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    bottom: -5px;
    z-index: -1;
    color: #999;
  }

  &__name {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    text-align: center;
    background: rgba(0, 0, 0, 0.3);
    height: 14px;
    color: #fff;
    font-size: 12px;
    line-height: 12px;
  }
}
</style>
