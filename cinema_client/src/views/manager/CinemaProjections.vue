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
              <h1 class="display-1 lack--text">{{cinema.name}} Projections</h1>
            </v-card-title>
            <v-card-actions>
              <v-select
                v-model="selectedMovie"
                item-text="name"
                :items="movies"
                label="Movie"
                dense
              ></v-select>
            </v-card-actions>
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
        :items="filteredProjections"
        :search="search"
        single-expand
        item-key="id"
        show-expand
        hide-default-footer
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template v-slot:item.date="{ item }">{{item.date | dateFilter}}</template>
        <template v-slot:item.actions="{ item }">
          <v-btn icon>
            <v-icon color="yellow darken-2" @click="editProjectionDialog(item)">mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon color="red darken-2" @click="deleteProjectionDialog(item)">mdi-delete</v-icon>
          </v-btn>
        </template>
        <template v-slot:expanded-item="{ headers, item }">
          <td :colspan="headers.length">
            <ul>
              <li v-for="hall in item.halls" :key="hall.id">{{hall.name}} ({{hall.capacity}})</li>
            </ul>
          </td>
        </template>
      </v-data-table>
    </v-card-text>
    <v-row style="margin-right: 1em;">
      <v-col align="end" justify="center">
        <v-fab-transition>
          <v-btn
            color="green"
            fab
            large
            dark
            class="v-btn--example"
            @click="addProjectionDialog(null)"
          >
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-fab-transition>
      </v-col>
    </v-row>
    <projection-dialog
      :dialog="dialog"
      :projection="projection"
      :type="type"
      @close-dialog="closeDialog"
      @add-projection="addProjection"
      @edit-projection="editProjection"
      @delete-projection="deleteProjection"
      v-if="dialog"
    />
  </v-card>
</template>

<script>
import { mapGetters } from "vuex";

import axios from "axios";

import ProjectionDialog from "../../components/ProjectionDialog.vue";

export default {
  components: {
    ProjectionDialog,
  },
  data() {
    return {
      search: "",
      dialog: false,
      projection: null,
      type: null,
      headers: [
        { text: "ID", value: "id" },
        { text: "Movie", value: "movie.name" },
        { text: "Time - Date", value: "date" },
        { text: "Price (din)", value: "price" },
        { text: "Halls", value: "data-table-expand", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      projections: [],
      movies: [],
      filteredProjections: [],
      selectedMovie: null,
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  computed: {
    ...mapGetters(["cinema"]),
  },
  watch: {
    cinema: function (newCinema) {
      axios.get(`/projection/${newCinema.id}`).then((response) => {
        this.projections = response.data;
      });
    },
    selectedMovie: function (newSelectedMovie) {
      this.filteredProjections = [];
      this.projections.forEach((projection) => {
        if (projection.movie.name === newSelectedMovie) {
          this.filteredProjections.push(projection);
        }
      });
    },
  },
  methods: {
    addProjection(projection) {
      axios
        .post(`/projection/${this.cinema.id}`, projection)
        .then((response) => {
          this.projections = response.data;
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
    editProjection(projection) {
      axios
        .put(`/projection/${this.cinema.id}`, projection)
        .then((response) => {
          this.projections = response.data;
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
    deleteProjection(projection) {
      axios
        .delete(`/projection/${projection.id}`)
        .then((response) => {
          this.projections = response.data;
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
    addProjectionDialog(projection) {
      this.projection = projection;
      this.type = "ADD";
      this.dialog = true;
    },
    editProjectionDialog(projection) {
      this.projection = projection;
      this.type = "EDIT";
      this.dialog = true;
    },
    deleteProjectionDialog(projection) {
      this.projection = projection;
      this.type = "DELETE";
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
  },
  filters: {
    dateFilter(date) {
      let components = date.split("T");
      let ddMMyyyy =
        components[0].split("-")[2] +
        "/" +
        components[0].split("-")[1] +
        "/" +
        components[0].split("-")[0];
      let time = components[1].split(".")[0];
      return time + " - " + ddMMyyyy;
    },
  },
  mounted() {
    axios.get(`/projection/${this.cinema.id}`).then((response) => {
      this.projections = response.data;
    });

    axios.get(`/movie/${this.cinema.id}`).then((response) => {
      this.movies = response.data;
      if (response.data.length > 0) {
        // this.selectedMovie = this.movies[0].name;
      }
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