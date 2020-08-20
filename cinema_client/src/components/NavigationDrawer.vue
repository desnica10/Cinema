<template>
  <v-navigation-drawer app :mini-variant="mini" permanent>
    <v-list>
      <v-list-item link class="px-2" align="center" v-if="user !== null" to="/profile">
        <v-list-item-avatar color="grey">
          <span class="white--text headline">{{user.firstName[0]}}{{user.lastName[0]}}</span>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title class="title">{{user.firstName}} {{user.lastName}}</v-list-item-title>
          <v-list-item-subtitle>({{user.username}})</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <template v-if="user.type === 'CUSTOMER'">
      <v-list nav>
        <v-list-item link to="/">
          <v-list-item-icon>
            <v-icon>mdi-video-vintage</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Movies</v-list-item-title>
        </v-list-item>
      </v-list>
      <v-divider />
      <v-list nav>
        <v-list-item link to="/tickets">
          <v-list-item-icon>
            <v-icon>mdi-ticket</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Tickets</v-list-item-title>
        </v-list-item>
      </v-list>
      <v-divider />
      <v-list nav>
        <v-list-item link to="/movies">
          <v-list-item-icon>
            <v-icon>mdi-movie-open</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Whached Movies</v-list-item-title>
        </v-list-item>
      </v-list>
    </template>

    <template v-if="user.type === 'MANAGER'">
      <v-list nav>
        <v-list-item link to="/">
          <v-list-item-icon>
            <v-icon>mdi-video-vintage</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Projection</v-list-item-title>
        </v-list-item>
      </v-list>
      <v-divider />

      <v-list nav>
        <v-list-item link to="/halls">
          <v-list-item-icon>
            <v-icon>mdi-seat</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Halls</v-list-item-title>
        </v-list-item>
      </v-list>
    </template>

    <template v-if="user.type === 'ADMIN'">
      <v-list nav>
        <v-list-item link to="/">
          <v-list-item-icon>
            <v-icon>mdi-theater</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Cinemas</v-list-item-title>
        </v-list-item>
      </v-list>
      <v-divider />

      <v-list nav>
        <v-list-item link to="/users">
          <v-list-item-icon>
            <v-icon>mdi-account-group</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Users</v-list-item-title>
        </v-list-item>
      </v-list>
      <v-divider />
    </template>

    <template v-slot:append>
      <v-list>
        <v-list-item link @click="logout">
          <v-list-item-icon>
            <v-icon color="red">mdi-exit-to-app</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
        <v-divider />
        <v-list-item link @click.stop="mini = !mini">
          <v-list-item-icon>
            <v-icon v-if="mini">mdi-arrow-expand-right</v-icon>
            <v-icon v-else>mdi-arrow-expand-left</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Colapse</v-list-item-title>
        </v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>
</template>

<script>
import { mapActions } from "vuex";

export default {
  props: ["user"],
  data() {
    return {
      mini: true,
    };
  },
  methods: {
    ...mapActions(["removeUser", "setCinemasAction"]),
    logout() {
      this.removeUser();
      this.setCinemasAction(null);
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
a:hover {
  text-decoration: none;
}
</style>