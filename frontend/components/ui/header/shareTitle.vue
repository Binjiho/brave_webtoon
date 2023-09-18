<script lang="ts">
export default {
  props: {
    isTransparent: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "타이틀",
    },
    isUnderLine: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    sharePage() {
      const shareObject = {
        title: this.title,
        url: window.location.href,
      };

      if (!navigator.share) {
        // navigator를 지원하지 않는 경우
        this.$root.vtoast.show({ message: "페이지 공유를 지원하지 않습니다." });
        return;
      }

      navigator.share(shareObject).catch((error) => {
        this.$root.vtoast.show({ message: "공유할 수 없는 페이지입니다." });
      });
    },
  },
};
</script>

<template>
  <ui-header-prev-title
    :isTransparent="isTransparent"
    :title="title"
    :isUnderLine="isUnderLine"
  >
    <VBtn class="share-btn" @click="sharePage()"></VBtn>
  </ui-header-prev-title>
</template>

<style scoped lang="scss"></style>
