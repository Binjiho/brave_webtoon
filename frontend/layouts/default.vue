<template>
  <VApp>
    <UiHeader></UiHeader>
    <VMain>
      <VContainer>
        <slot />
      </VContainer>
    </VMain>
  </VApp>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>
