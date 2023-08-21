import { h } from "vue";
import type { IconSet, IconProps } from "vuetify";

//logo
import colorLogo from "assets/iconsets/icons/logo/colorLogo.vue";

const customSvgNameToComponent: any = {
  colorLogo,
};

const customSVGs: IconSet = {
  component: (props: IconProps) => {
    return h(customSvgNameToComponent[props.icon]);
  },
};

export { customSVGs };
