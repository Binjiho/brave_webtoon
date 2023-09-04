<script lang="ts">
export default {
  props: {
    modelValue: {
      type: String,
    },
    list: {
      type: Array,
    },
  },
  methods: {
    selectId(id) {
      this.$emit("update:modelValue", id);
    },
  },
};
</script>

<template>
  <ul class="ui-tab-sort">
    <li
      class="ui-tab-sort__item"
      v-for="item in list"
      :key="item.i"
      @click="selectId(item.id)"
      :class="{ active: item.id === modelValue }"
    >
      {{ item.text }}
    </li>
  </ul>
</template>

<style scoped lang="scss">
.ui-tab-sort {
  display: flex;
  padding-left: 0;
  align-items: center;

  &__item {
    font-weight: 400;
    font-size: 14px;
    line-height: 22px;
    color: #666666;
    display: flex;
    align-items: center;
    cursor: pointer;

    &:after {
      content: "";
      @include line(#dddddd, 15px, 14px, 1px);
    }

    &.active {
      font-weight: 700;
      color: #000000;
    }

    &:last-child {
      &::after {
        display: none;
      }
    }
  }
}
</style>
