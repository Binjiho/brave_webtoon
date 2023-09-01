<script lang="ts">
export default {
  data() {
    return {
      navList: [
        {
          title: "웹툰 리스트",
          icon: "custom:movieLine",
          value: 1,
          link: "/admin",
        },
      ],
    };
  },
};
</script>

<template>
  <v-navigation-drawer permanent absolute>
    <v-list>
      <v-list-item
        v-for="item in navList"
        :key="item.i"
        :value="item"
        :to="item.link"
        variant="plain"
      >
        <template v-slot:prepend>
          <v-icon :icon="item.icon"></v-icon>
        </template>
        <v-list-item-title v-text="item.title"></v-list-item-title>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<style scoped lang="scss">
.v-navigation-drawer {
  top: $header-height !important;
  height: calc(100% - $header-height) !important;
}

.v-list {
  padding-top: 40px !important;
}

.v-list-item {
  color: #666;
  opacity: 1;
  padding-left: 30px !important;

  &.v-list-item--active {
    color: #000;
  }

  .v-list-item-title {
    font-size: 14px;
  }

  .v-icon {
    width: 24px;
    height: 24px;
    margin-right: 18px;
  }
}
</style>
