<template>
  <v-app>
    <AppBar />

    <navigation-drawer :user="user" v-if="user != null" />

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { mapGetters } from "vuex";

import AppBar from "./components/AppBar";
import NavigationDrawer from "./components/NavigationDrawer";

export default {
  name: "App",
  components: {
    AppBar,
    NavigationDrawer,
  },
  computed: { ...mapGetters(["user"]) },
};
</script>
