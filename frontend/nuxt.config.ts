import vuetify from "vite-plugin-vuetify";
// https://nuxt.com/docs/api/configuration/nuxt-config
import svgLoader from "vite-svg-loader";

export default defineNuxtConfig({
  head: {
    title: "frontend",
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      { hid: "description", name: "description", content: "" },
      { name: "title", content: "웹툰 가상 캐스팅" },
      {
        name: "keywords",
        content: "웹툰,캐스팅,가상캐스팅,닮은연예인,웹툰가상캐스팅",
      },
      { name: "format-detection", content: "telephone=no" },
    ],
    link: [{ rel: "icon", type: "image/x-icon", href: "public/favicon.ico" }],
  },
  devServer: {
    port: 9001,
  },
  ssr: false,
  target: "static",
  css: ["@/static/fonts/font.css", "@/assets/scss/index.scss"],
  build: { transpile: ["vuetify"] },
  modules: [
    async (options, nuxt) => {
      nuxt.hooks.hook("vite:extendConfig", (config) =>
        // @ts-ignore
        config.plugins.push(vuetify())
      );
    },
    "@pinia/nuxt",
  ],
  imports: {
    dirs: ["./store"],
  },
  vite: {
    plugins: [svgLoader()],
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: '@import "@/assets/scss/vuetify/variables.scss";',
        },
      },
    },
    server: {
      proxy: {
        "/api": {
          target: "http://localhost:9002/",
          changeOrigin: true,
        },
      },
    },
  },
});
