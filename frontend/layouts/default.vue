<template>
  <v-app>
    <Header></Header>
    <v-main>
      <v-container>
        <Nuxt />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Header from "~/components/Header.vue";
export default {
  name: "DefaultLayout",
  components: {
    Header,
  },
  data() {
    return {};
  },
};
</script>
