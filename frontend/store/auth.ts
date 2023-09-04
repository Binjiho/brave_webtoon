import { defineStore } from "pinia";

export const useUserStore = defineStore("counter", {
  state: () => ({
    access_token: sessionStorage.getItem("accessToken"),
    user: JSON.parse(sessionStorage.getItem("user")),
  }),
  getters: {
    getAccessToken: (state) => {
      return state.access_token;
    },
    getUser: (state) => {
      return state.user;
    },
  },
  actions: {
    loginUserSuccess(successData) {
      this.access_token = successData.data.token;
      this.user = successData.data.userInfo;

      sessionStorage.setItem("user", JSON.stringify(successData.data.userInfo));
      sessionStorage.setItem(
        "accessToken",
        JSON.stringify(successData.data.token)
      );
    },
    logoutUser(successData) {
      sessionStorage.removeItem("user");
      sessionStorage.removeItem("accessToken");
      location.reload();
    },
  },
});
