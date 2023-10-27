// Components
import { createApp } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import LandingPage from "@/components/LandingPage.vue";
import Orders from "@/components/Orders.vue";
import Products from "@/components/Products.vue";
import App from "./App.vue";

// Plugins
import { registerPlugins } from "@/plugins";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: LandingPage },
    { path: "/comandes", component: Orders },
    { path: "/productes", component: Products },
  ],
});

const app = createApp(App);

app.use(router);

registerPlugins(app);

app.mount("#app");
