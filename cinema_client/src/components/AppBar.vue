<template>
  <v-app-bar dark flat app>
    <v-toolbar-title>
      <router-link to="/" tag="h3">Cinemas</router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <template v-if="user === null || user.type !== 'ADMIN'">
      <v-select
        v-model="$store.state.cinema.cinema"
        :hint="`${$store.state.cinema.cinema.name}, ${$store.state.cinema.cinema.address}`"
        :items="cinemas"
        item-text="name"
        item-value="name"
        label="Select"
        append-outer-icon="mdi-theater"
        persistent-hint
        return-object
        single-line
      ></v-select>

      <v-spacer></v-spacer>

      <v-btn style="margin-right: 3%;" icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
    </template>
    <template v-if="user == null">
      <v-btn to="/login" text>
        Login
        <v-icon style="margin-left: 8%;">mdi-account</v-icon>
      </v-btn>
      <v-btn to="/register" text>
        Register
        <v-icon style="margin-left: 8%;">mdi-account-plus</v-icon>
      </v-btn>
    </template>
  </v-app-bar>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import axios from "axios";

export default {
  data() {
    return {};
  },
  computed: { ...mapGetters(["user", "cinemas"]) },
  methods: { ...mapActions(["setCinemasAction"]) },
  mounted() {
    this.setCinemasAction(this.user);
  },
};
</script>

<style scoped>
a:hover {
  text-decoration: none;
}

h3:hover {
  cursor: pointer;
}
</style>