import { h } from "vue";
import type { IconSet, IconProps } from "vuetify";

//logo
import colorLogo from "assets/iconsets/icons/logo/colorLogo.vue";

//system
import arrowDownSLine from "assets/iconsets/icons/system/arrow-down-s-line.vue";
import arrowLeftSLine from "assets/iconsets/icons/system/arrow-left-s-line.vue";
import arrowRightSLine from "assets/iconsets/icons/system/arrow-right-s-line.vue";
import arrowUpSLine from "assets/iconsets/icons/system/arrow-up-s-line.vue";
import arrowLeftLine from "assets/iconsets/icons/system/arrow-left-line.vue";
import infoLine from "assets/iconsets/icons/system/info-line.vue";
import shareLine from "assets/iconsets/icons/system/share-line.vue";
import search from "assets/iconsets/icons/system/search.vue";
import checkLine from "assets/iconsets/icons/system/check-line.vue";
import rankingLine from "assets/iconsets/icons/system/ranking-line.vue";

const customSvgNameToComponent: any = {
  colorLogo,
  arrowDownSLine,
  arrowLeftSLine,
  arrowRightSLine,
  arrowUpSLine,
  arrowLeftLine,
  infoLine,
  shareLine,
  search,
  checkLine,
  rankingLine,
};

const customSVGs: IconSet = {
  component: (props: IconProps) => {
    return h(customSvgNameToComponent[props.icon]);
  },
};

export { customSVGs };
