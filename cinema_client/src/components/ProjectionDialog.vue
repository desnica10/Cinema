<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent :width="width">
      <v-card>
        <v-card-title>
          <span class="headline">{{title}}</span>
        </v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12">
              <template v-if="type !== 'DELETE'">
                <v-form v-model="form.valid">
                  <v-row>
                    <v-col>
                      <v-select
                        v-model="newProjection.movie"
                        :rules="form.rules.movie"
                        item-text="name"
                        :items="movies"
                        label="Movies"
                        dense
                      ></v-select>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-select
                        v-model="newProjection.halls"
                        :rules="form.rules.halls"
                        item-text="name"
                        :items="halls"
                        label="Halls"
                        dense
                        multiple
                        chips
                        hint="Selet halls for projection"
                      ></v-select>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <v-text-field
                        v-model="newProjection.price"
                        :rules="form.rules.price"
                        label="Price"
                        type="number"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col>
                      <VueCtkDateTimePicker
                        v-model="newProjection.date"
                        :rules="form.rules.date"
                        position="bottom"
                        :right="true"
                        :inline="true"
                        format="YYYY-MM-DD hh:mm"
                        no-header
                      />
                    </v-col>
                  </v-row>
                </v-form>
              </template>
              <template v-else>
                <p>
                  Are you sure, you want to delete:
                  <strong>"{{projection.movie.name}}"</strong>
                </p>
              </template>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="closeDialog">Close</v-btn>
          <v-btn
            :color="(type === 'DELETE')? 'red darken-1':'green darken-1'"
            text
            @click="doType"
          >{{type}}</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from "axios";

import { mapGetters } from "vuex";

import VueCtkDateTimePicker from "vue-ctk-date-time-picker";
import "vue-ctk-date-time-picker/dist/vue-ctk-date-time-picker.css";

export default {
  props: ["dialog", "projection", "type"],
  components: {
    VueCtkDateTimePicker,
  },
  data() {
    return {
      newProjection: {
        id: null,
        date: null,
        price: null,
        movie: null,
        cinema: null,
        halls: [],
      },
      halls: [],
      movies: [],
      title: "",
      width: "55em",
      form: {
        valid: false,
        rules: {
          date: [(v) => !!v || "Date is required"],
          price: [(v) => !!v || "Price is required"],
          movie: [
            (v) => this.newProjection.movie !== null || "Movie is required",
          ],
          halls: [
            (v) => this.newProjection.halls.length > 0 || "Halls is required",
          ],
        },
      },
    };
  },
  computed: { ...mapGetters(["cinema"]) },
  methods: {
    closeDialog() {
      this.$emit("close-dialog");
    },
    doType() {
      if (this.type !== "DELETE" && this.form.valid) {
        // this.newProjection.halls = this.halls.filter((x) =>
        //   this.newProjection.halls?.includes(x.name)
        // );

        // this.newProjection.movie = this.movies.filter((x) =>
        //   this.newProjection.movie?.includes(x.name)
        // )[0];

        let halls = [];
        this.newProjection.halls.forEach((selectedHall) => {
          this.halls.forEach((hall) => {
            if (selectedHall === hall.name) {
              halls.push(hall);
              return;
            }
          });
        });
        this.newProjection.halls = halls;

        this.movies.forEach((movie) => {
          if (this.newProjection.movie === movie.name) {
            this.newProjection.movie = movie;
            return;
          }
        });

        if (this.newProjection.date.split(" ")[1] !== undefined) {
          this.newProjection.date =
            this.newProjection.date.split(" ")[0] +
            "T" +
            this.newProjection.date.split(" ")[1];
        }
      }
      switch (this.type) {
        case "ADD":
          if (this.form.valid) {
            this.newProjection.cinema = this.cinema;
            this.$emit("add-projection", this.newProjection);
          }
          break;
        case "EDIT":
          if (this.form.valid) {
            this.$emit("edit-projection", this.newProjection);
          }
          break;
        case "DELETE":
          this.$emit("delete-projection", this.projection);
          break;
        default:
          break;
      }
    },
  },
  mounted() {
    axios.get(`/cinema/${this.cinema.id}/hall`).then((response) => {
      this.halls = response.data;
    });

    axios.get(`/movie`).then((response) => {
      this.movies = response.data;
    });

    switch (this.type) {
      case "ADD":
        this.title = "Add";
        break;
      case "EDIT":
        this.title = "Edit";
        this.newProjection = Object.assign({}, this.projection);
        break;
      case "DELETE":
        this.title = "Delete";
        this.width = "25em";
        break;
      default:
        break;
    }

    this.title += " Projection";
  },
};
</script>

<style>
</style>