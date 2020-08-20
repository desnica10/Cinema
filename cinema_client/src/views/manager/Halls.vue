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
              <h1 class="display-1 lack--text">{{cinema.name}} Halls</h1>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="halls"
        :search="search"
        item-key="id"
        hide-default-footer
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template v-slot:item.actions="{ item }">
          <v-btn icon>
            <v-icon color="yellow darken-2" @click="editHallDialog(item)">mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon color="red darken-2" @click="deleteHallDialog(item)">mdi-delete</v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card-text>
    <v-row style="margin-right: 1em;">
      <v-col align="end" justify="center">
        <v-fab-transition>
          <v-btn color="green" fab large dark class="v-btn--example" @click="addHallDialog(null)">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-fab-transition>
      </v-col>
    </v-row>
    <hall-dialog
      :dialog="dialog"
      :hall="hall"
      :type="type"
      @close-dialog="closeDialog"
      @add-hall="addHall"
      @edit-hall="editHall"
      @delete-hall="deleteHall"
      v-if="dialog"
    />
  </v-card>
</template>

<script>
import axios from "axios";

import { mapGetters } from "vuex";

import HallDialog from "../../components/HallDialog.vue";

export default {
  components: {
    HallDialog,
  },
  data() {
    return {
      search: "",
      dialog: false,
      type: null,
      headers: [
        { text: "ID", value: "id" },
        { text: "Name", value: "name" },
        { text: "Capacity", value: "capacity" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      halls: [],
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  computed: { ...mapGetters(["cinema"]) },
  watch: {
    cinema: function (newCinema) {
      axios.get(`/cinema/${newCinema.id}/hall`).then((response) => {
      this.halls = response.data;
    });
    },
  },
  methods: {
    addHall(hall) {
      axios
        .post(`/cinema/${this.cinema.id}/hall`, hall)
        .then((response) => {
          this.halls = response.data;
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
    editHall(hall) {
      axios
        .put(`/cinema/${this.cinema.id}/hall`, hall)
        .then((response) => {
          this.halls = response.data;
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
    deleteHall(hall) {
      axios
        .delete(`/cinema/${this.cinema.id}/hall/${hall.id}`)
        .then((response) => {
          this.halls = response.data;
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
    addHallDialog(hall) {
      this.hall = hall;
      this.type = "ADD";
      this.dialog = true;
    },
    editHallDialog(hall) {
      this.hall = hall;
      this.type = "EDIT";
      this.dialog = true;
    },
    deleteHallDialog(hall) {
      this.hall = hall;
      this.type = "DELETE";
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
  },
  mounted() {
    axios.get(`/cinema/${this.cinema.id}/hall`).then((response) => {
      this.halls = response.data;
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