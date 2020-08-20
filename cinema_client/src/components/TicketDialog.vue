<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent :width="width">
      <v-card>
        <v-card-title>
          <span class="headline">Tickets "{{movie.name}}"</span>
        </v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
              ></v-text-field>
              <v-data-table
                :headers="headers"
                :items="projections"
                :search="search"
                item-key="id"
                hide-default-footer
                class="elevation-1"
                height="43em"
                style="overflow: auto;"
              >
                <template v-slot:item.date="{ item }">{{item.date | dateFilter}}</template>
                <template v-slot:item.hall="{ item }">
                  <v-form v-model="form.valid">
                    <v-select
                      v-model="ticket.hall"
                      item-text="name"
                      :items="item.halls"
                      :rules="form.rules.hall"
                      label="Halls"
                      dense
                    ></v-select>
                  </v-form>
                </template>
                <template v-slot:item.seats="{ item }">
                  <v-form v-model="form.valid">
                    <v-text-field
                      v-model="ticket.numberOfSeats"
                      append-icon="mdi-seat"
                      label="Number of Seats"
                      :rules="form.rules.seats"
                      single-line
                      dense
                    ></v-text-field>
                  </v-form>
                </template>
                <template v-slot:item.actions="{ item }">
                  <v-btn icon @click="reserveTicket(item)" :disabled="user === null">
                    <v-icon color="green darken-2">mdi-ticket</v-icon>
                  </v-btn>
                </template>
              </v-data-table>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red darken-1" text @click="closeDialog">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from "axios";

import { mapGetters } from "vuex";

export default {
  props: ["dialog", "movie"],
  data() {
    return {
      ticket: {
        id: null,
        status: null,
        numberOfSeats: null,
        reservationDate: null,
        hall: null,
        customer: null,
        projection: null,
      },
      projections: [],
      headers: [
        { text: "Date", value: "date" },
        { text: "Price", value: "price" },
        { text: "Hall", value: "hall", sortable: false },
        { text: "Number of Seats", value: "seats", sortable: false },
        { text: "Get Ticket", value: "actions", sortable: false },
      ],
      form: {
        valid: false,
        rules: {
          seats: [
            (v) =>
              this.ticket.numberOfSeats > 0 || "Number of seats is required",
          ],
          hall: [(v) => this.ticket.hall != null || "Hall is required"],
        },
      },
      search: "",
      title: "",
      width: "55em",
    };
  },
  computed: { ...mapGetters(["cinema", "user"]) },
  methods: {
    reserveTicket(projection) {
      if (this.form.valid && this.ticket.numberOfSeats > 0 && this.ticket.hall != null) {
        this.ticket.hall = projection.halls.filter((x) => x.name === this.ticket.hall)[0];
        this.ticket.customer = this.user;
        this.ticket.projection = projection;
        this.$emit("add-ticket", this.ticket);
      }
    },
    closeDialog() {
      this.$emit("close-dialog");
    },
    doType() {},
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
      return ddMMyyyy + " - " + time;
    },
  },
  mounted() {
    axios
      .get(`/projection/${this.cinema.id}/movie/${this.movie.id}`)
      .then((response) => {
        this.projections = response.data;
      });
  },
};
</script>

<style>
</style>