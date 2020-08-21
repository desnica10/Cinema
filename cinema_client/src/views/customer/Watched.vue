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
              <h1 class="display-1 lack--text">Watched Movies</h1>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-tabs v-model="tab" grow>
        <v-tab>Watched</v-tab>
        <v-tab>Already Rated</v-tab>
        <v-tab>Rate</v-tab>
      </v-tabs>
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
          <!-- <v-rating style="display: inline;" :readonly="tab != 2" dense half-increments length="10" v-model="item.averageGrade"></v-rating> -->
          <template v-if="tab == 0">
              <v-rating style="display: inline;" readonly dense half-increments length="10" v-model="item.averageGrade"></v-rating>
              <v-chip small class="ma-2" color="orange" text-color="white">{{item.averageGrade}}</v-chip>
          </template>
          <template v-else-if="tab == 1">
              <v-rating style="display: inline;" readonly dense half-increments length="10" :value="myRate(item)"></v-rating>
              <v-chip small class="ma-2" color="orange" text-color="white">{{myRate(item)}}</v-chip>
          </template>
          <template v-else>
              <v-rating style="display: inline;" :hover="true" dense half-increments length="10" v-model="grade.grade" @input="rateMovie(item)"></v-rating>
              <v-chip small class="ma-2" color="orange" text-color="white">{{grade.grade}}</v-chip>
          </template>
        </template>
      </v-data-table>
    </v-card-text>
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
      tab: 0,
      movie: null,
      grade: {
          grade: null,
          movie: null,
          customer: null,
      },
      headers: [
        { text: "Name", value: "name" },
        { text: "Description", value: "description" },
        { text: "Grade", value: "averageGrade" },
        { text: "Duration", value: "duration" },
        { text: "Genre", value: "type" },
      ],
      movies: [],
      grades: [],
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
      let rated = [];
      let notRated = [];
      let flag = false;
      if (this.tab != 0) {
        this.movies.forEach((movie) => {
          flag = false;
          this.grades.forEach((grade) => {
            if (grade.movie.name === movie.name) {
              rated.push(movie);
              flag = true;
            }
          });

          if (!flag) {
            notRated.push(movie);
          }
        });
      }
      switch (this.tab) {
        case 0:
          return this.movies;
          break;
        case 1:
          return rated;
          break;
        case 2:
          return notRated;
          break;
      }
    },
  },
  watch: {
      movies: function (newVal) {
          axios.get(`movie/grade/${this.user.id}`).then((response) => {
      this.grades = response.data;
    });
      }
  },
  methods: {
    rateMovie(movie) {
        this.grade.customer = this.user;
        this.grade.movie = movie;
      axios.put(`movie/watched`, this.grade).then((response) => {
        this.movies = response.data;
        this.grade = {
          grade: null,
          movie: null,
          customer: null,
      }
      });
    },
    myRate(movie){
        let myGrade;
        this.grades.forEach(grade => {
            if (grade.movie.name === movie.name) {
                myGrade = grade.grade;
                return;
            }
        });

        return myGrade;
    }
  },
  mounted() {
    axios.get(`movie/watched/${this.user.id}`).then((response) => {
      this.movies = response.data;
    });

    axios.get(`movie/grade/${this.user.id}`).then((response) => {
      this.grades = response.data;
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