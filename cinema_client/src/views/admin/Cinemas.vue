<template>
  <v-card class="home" height="53em">
    <v-snackbar
      v-model="alert.show"
      :timeout="alert.timeout"
      :color="alert.color"
      top
      right
      style="margin-top: 2%;"
    >{{ alert.text }}</v-snackbar>
    <v-card-title>
      <v-row>
        <v-col align="center" justify="center">
          <v-card color="white">
            <v-card-title class="text-center justify-center">
              <h1 class="display-1 lack--text">Cinemas</h1>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>
      <v-data-table
        :headers="headers"
        :items="cinemas"
        :expanded.sync="menagers"
        :search="search"
        single-expand
        item-key="id"
        show-expand
        hide-default-footer
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template v-slot:item.actions="{ item }">
          <v-btn icon>
            <v-icon color="yellow darken-2" @click="editCinemaDialog(item)">mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon color="red darken-2" @click="deleteCinemaDialog(item)">mdi-delete</v-icon>
          </v-btn>
        </template>
        <template v-slot:expanded-item="{ headers, item }">
          <td :colspan="headers.length">
            <ul>
              <li
                v-for="manager in item.managers"
                :key="manager.id"
              >{{manager.firstName + " " + manager.lastName + " (" + manager.username + ")"}}</li>
            </ul>
          </td>
        </template>
      </v-data-table>
    </v-card-text>
    <v-row style="margin-right: 1em;">
      <v-col align="end" justify="center">
        <v-fab-transition>
          <v-btn color="green" fab large dark class="v-btn--example" @click="addCinemaDialog">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-fab-transition>
      </v-col>
    </v-row>
    <cinema-dialog
      :dialog="dialog"
      :cinema="cinema"
      :type="type"
      @close-dialog="closeDialog"
      @add-cinema="addCinema"
      @edit-cinema="editCinema"
      @delete-cinema="deleteCinema"
      v-if="dialog"
    />
  </v-card>
</template>

<script>
import axios from "axios";

import CinemaDialog from "../../components/CinemaDialog.vue";

export default {
  components: {
    CinemaDialog,
  },
  data() {
    return {
      search:"",
      dialog: false,
      cinema: null,
      type: null,
      headers: [
        { text: "ID", value: "id" },
        { text: "Name", value: "name" },
        { text: "Address", value: "address" },
        { text: "E-mail", value: "email" },
        { text: "Phone", value: "phone" },
        { text: "Menagers", value: "data-table-expand", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      menagerHeader: [
        { text: "ID", value: "id" },
        { text: "Username", value: "username" },
        { text: "First name", value: "firstName" },
        { text: "Last name", value: "lastName" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      cinemas: [],
      menagers: [],
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  methods: {
    addCinema(cinema) {
      axios
        .post(`/cinema`, cinema)
        .then((response) => {
          this.cinemas = response.data;
          this.closeDialog();
          this.alert.text = "Successfully done";
          this.alert.color = "green";
          this.alert.show = true;
        })
        .catch((error) => {
          this.alert.text = error.response.data;
          this.alert.color = "red";
          this.alert.show = true;
        });
    },
    editCinema(cinema) {
      axios
        .put(`/cinema`, cinema)
        .then((response) => {
          this.cinemas = response.data;
          this.closeDialog();
          this.alert.text = "Successfully done";
          this.alert.color = "green";
          this.alert.show = true;
        })
        .catch((error) => {
          this.alert.text = error.response.data;
          this.alert.color = "red";
          this.alert.show = true;
        });
    },
    deleteCinema(cinema) {
      axios
        .delete(`/cinema/${cinema.id}`)
        .then((response) => {
          this.cinemas = response.data;
          this.closeDialog();
          this.alert.text = "Successfully done";
          this.alert.color = "green";
          this.alert.show = true;
        })
        .catch((error) => {
          this.alert.text = error.response.data;
          this.alert.color = "red";
          this.alert.show = true;
        });
    },
    addCinemaDialog(cinema) {
      this.cinema = cinema;
      this.type = "ADD";
      this.dialog = true;
    },
    editCinemaDialog(cinema) {
      this.cinema = cinema;
      this.type = "EDIT";
      this.dialog = true;
    },
    deleteCinemaDialog(cinema) {
      this.cinema = cinema;
      this.type = "DELETE";
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
  },
  mounted() {
    axios.get("/cinema").then((response) => {
      this.cinemas = response.data;
    });
  },
};
</script>

<style scoped>
.home {
  box-shadow: 0px 0px 1px -2px rgba(0, 0, 0, 0.2),
    0px 0px 0px 0px rgba(0, 0, 0, 0.14), 0px 0px 0px 0px rgba(0, 0, 0, 0.12) !important;
}
</style>