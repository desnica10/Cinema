<template>
  <v-app-bar dark flat app>
    <v-toolbar-title>
      <router-link to="/" tag="h3">Cinemas</router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <template  v-if="user === null || user.type !== 'ADMIN'">
    <v-select
      v-model="select"
      :hint="`${select.name}, ${select.address}`"
      :items="items"
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
import { mapGetters } from "vuex";
import axios from 'axios'

export default {
  data() {
    return {
      select: { name: "Cinaplexxx", address: "Bulevar Oslobođenja 119, 21000 Novi Sad" },
      items: [
        { name: "Cinaplexxx", address: "Bulevar Oslobođenja 119, 21000 Novi Sad" },
        { name: "Arena", address: "Bulevar Oslobođenja 119, 21000 Novi Sad" },
      ],
    };
  },
  computed: { ...mapGetters(["user"]) },
  mounted(){
    axios.get("/cinema").then((response) => {
      this.items = response.data;
      this.select = this.items[0];
    });
  }
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