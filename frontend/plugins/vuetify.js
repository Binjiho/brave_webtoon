import { createVuetify } from "vuetify";
import { mdi } from "vuetify/iconsets/mdi";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

export default defineNuxtPlugin((app) => {
  const vuetify = createVuetify({
    ssr: true,
    components,
    directives,
    icons: {
      defaultSet: "mdi",
      sets: {
        mdi,
      },
    },
    defaults: {
      VBtn: {
        variant: "flat",
        color: "blue-darken-2",
      },
    },
  });

  app.vueApp.use(vuetify);
});
