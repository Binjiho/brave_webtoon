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
    defaults: {
      VRadioGroup: {
        trueIcon: "custom:radioOn",
        falseIcon: "custom:radioOff",
        color: "main-color",
      },
      VRadio: {
        trueIcon: "custom:radioOn",
        falseIcon: "custom:radioOff",
        color: "main-color",
      },
      VCheckbox: {
        trueIcon: "custom:checkOn",
        falseIcon: "custom:checkOff",
        color: "main-color",
      },
      VSwitch: {
        color: "main-color",
        inset: true,
      },
      VRating: {
        fullIcon: "custom:ratingOn",
        emptyIcon: "custom:ratingOff",
        color: "main-color",
      },
      VSelect: {
        menuIcon: "custom:arrowDownSLine",
        variant: "outlined",
        density: "comfortable",
        noDataText: "비어있음",
        placeholder: "선택",
        menuProps: {
          contentClass: "select-menu",
          offset: "10px",
        },
      },
    },
  });

  app.vueApp.use(vuetify);
});
