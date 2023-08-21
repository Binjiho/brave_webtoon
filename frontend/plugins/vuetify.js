import { createVuetify } from "vuetify";
import { mdi } from "vuetify/iconsets/mdi";
import { customSVGs } from "@/assets/iconsets/custom";
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
        custom: customSVGs,
      },
    },
    defaults: {},
  });

  app.vueApp.use(vuetify);
});
