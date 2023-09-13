import axios from "axios";

export default defineNuxtPlugin((nuxtApp) => {
const defaultUrl = "http://127.0.0.1:9001";

  let api = axios.create({
//     baseURL: defaultUrl,
    headers: {},
  });
  return {
    provide: {
      api: api,
    },
  };
});
