import { useUserStore } from "~/store/auth";

export default defineNuxtRouteMiddleware((to, from) => {
  const store = useUserStore();
  const { getUser } = store;

  if (getUser) {
    if (getUser.role !== "ADMIN") {
      alert("권한이 없습니다");
      return navigateTo("/");
    }
  } else {
    alert("권한이 없습니다");
    return navigateTo("/");
  }
});
