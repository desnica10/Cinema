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
              <h1 class="display-1 lack--text">{{cinema.name}} Movies</h1>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text>
      <v-data-table
        :headers="headers"
        :items="filteredFilms"
        item-key="id"
        hide-default-footer
        multi-sort
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template v-slot:item.averageGrade="{ item }">
          <v-rating style="display: inline;" readonly dense half-increments length="10" v-model="item.averageGrade"></v-rating>
          <v-chip small class="ma-2" color="orange" text-color="white">{{item.averageGrade}}</v-chip>
        </template>
        <template v-slot:item.actions="{ item }">
          <v-btn icon @click="openDialog(item)">
            <v-icon color="yellow darken-2">mdi-ticket</v-icon>
          </v-btn>
        </template>
      </v-data-table>
      <v-row>
        <v-col cols="6">
          <v-text-field
            v-model="search.name"
            label="Name"
            dense
            single-line
            hide-details
            prepend-icon="mdi-magnify"
          ></v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="search.description"
            label="Description"
            dense
            hide-details
            append-icon="mdi-magnify"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row style="margin-bottom: 2%">
        <v-col cols="6">
          <v-select
            v-model="search.type"
            item-text="name"
            :items="types"
            label="Genre"
            dense
            prepend-icon="mdi-magnify"
          ></v-select>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="search.averageGrade"
            label="Average Grade"
            dense
            type="number"
            hide-details
            append-icon="mdi-magnify"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-card-text>
    <ticket-dialog
      :dialog="dialog"
      :movie="movie"
      @close-dialog="closeDialog"
      @add-ticket="addTicket"
      v-if="dialog"
    />
  </v-card>
</template>

<script>
import axios from "axios";

import { mapGetters } from "vuex";

import TicketDialog from "../../components/TicketDialog.vue";

export default {
  components: {
    TicketDialog,
  },
  data() {
    return {
      search: {
        name: "",
        description: "",
        averageGrade: "",
        type: "ALL",
      },
      dialog: false,
      movie: null,
      headers: [
        { text: "Name", value: "name" },
        { text: "Description", value: "description" },
        { text: "Grade", value: "averageGrade" },
        { text: "Duration", value: "duration" },
        { text: "Genre", value: "type" },
        { text: "Get Ticket", value: "actions", sortable: false },
      ],
      movies: [],
      types: [],
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  computed: {
    ...mapGetters(["user", "cinema"]),
    filteredFilms() {
      return this.movies.filter((x) =>
        x.name.includes(this.search.name) &&
        x.description.includes(this.search.description) &&
        this.search.type === "ALL"
          ? true
          : x.type.includes(this.search.type) && this.search.averageGrade == ""
          ? true
          : x.averageGrade == this.search.averageGrade
      );
    },
  },
  watch: {
    cinema: function (newCinema) {
      axios.get(`/movie/${this.cinema.id}`).then((response) => {
        this.movies = response.data;
      });
    },
  },
  methods: {
    addTicket(ticket) {
      axios
        .post(`/ticket`, ticket)
        .then((response) => {
          console.log(response.data);
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
    openDialog(movie) {
      this.movie = movie;
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
  },
  mounted() {
    axios.get(`/movie/${this.cinema.id}`).then((response) => {
      this.movies = response.data;
    });

    axios.get(`/movie/type`).then((response) => {
      this.types = response.data;
      this.types.unshift("ALL");
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