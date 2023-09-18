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
      { property: "og:title", content: "match" },
      { property: "og:image", content: "./assets/image/match_share_img.png" },
      { property: "og:url", content: "https://www.matchwt.com" },
    ],
    link: [{ rel: "icon", type: "image/x-icon", href: "public/favicon.ico" }],
  },
  devServer: {
    port: 9001,
    open: true, // opens browser window automatically
    hmr: {
      clientPort: 24600,
      Port: 24600,
      protocol: 'ws',
      host: 'localhost',
    },
  },
  axios: {
      baseURL: 'http://127.0.0.1:9001',
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
  pinia: {
    autoImports: ["defineStore", "acceptHMRUpdate"],
  },
  vite: {
    server: {
      watch: { usePolling: true },
    },
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
          target: process.env.API_URL,
          // target: "http://host.docker.internal:9002/",
          changeOrigin: true,
          secure: false,
        },
      },
//       hmr: {
//         clientPort: 24600,
//         Port: 24600,
//         protocol: 'ws',
//         host: process.env.NUXT_URL,
//       }
    },
  },
});
