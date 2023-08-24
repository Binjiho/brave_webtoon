<script>
export default {
  name: "vtoast",
  data() {
    return {
      showSnackbar: false,
      message: "",
      color: "",
      icon: "mdi-check",
      timer: 1000,
    };
  },
  methods: {
    show(data) {
      this.message = data.message || "메시지를 입력하세요";
      this.color = data.color;
      this.timer = data.timer || 1500;
      this.icon = data.icon;
      this.showSnackbar = true;
    },
  },
};
</script>

<template>
  <v-snackbar
    :color="color"
    :timeout="timer"
    v-model="showSnackbar"
    bottom
    right
  >
    <v-icon left>{{ icon }}</v-icon
    >{{ message }}
  </v-snackbar>
</template>

<style scoped lang="scss"></style>
