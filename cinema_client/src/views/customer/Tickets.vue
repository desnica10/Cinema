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
              <h1 class="display-1 lack--text">Tickets</h1>
            </v-card-title>
          </v-card>
        </v-col>
      </v-row>
    </v-card-title>
    <v-row>
      <v-col cols="8"></v-col>
      <v-col cols="4">
        <v-radio-group v-model="status" dense row>
          <v-radio color="yellow darken-2" label="Reserved" value="RESERVED"></v-radio>
          <v-radio color="green darken-2" label="Paid" value="PAID"></v-radio>
          <v-radio color="red darken-2" label="Canceled" value="CANCELED"></v-radio>
        </v-radio-group>
      </v-col>
    </v-row>
    <v-card-text>
      <v-data-table
        :headers="headers"
        :items="filteredTickets"
        item-key="id"
        hide-default-footer
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template v-slot:item.reservationDate="{ item }">{{item.reservationDate | dateFilter}}</template>
        <template v-slot:item.actions="{ item }">
          <v-btn icon v-if="item.status === 'RESERVED'" @click="paiTicket(item)">
            <v-icon color="green darken-2">mdi-cash-multiple</v-icon>
          </v-btn>
          <v-btn icon v-if="item.status === 'RESERVED'" @click="cancelTicket(item)">
            <v-icon color="red darken-2">mdi-cancel</v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card-text>
  </v-card>
</template>

<script>
import axios from "axios";

import { mapGetters } from "vuex";

export default {
  data() {
    return {
      status: "RESERVED",
      headers: [
        { text: "ID", value: "id" },
        { text: "Movie", value: "projection.movie.name" },
        { text: "Seats", value: "numberOfSeats" },
        { text: "Date", value: "reservationDate" },
        { text: "Hall", value: "hall.name" },
        { text: "Status", value: "status" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      tickets: [],
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
    filteredTickets() {
      let tickets = [];
      this.tickets.forEach((ticket) => {
        if (ticket.status === this.status) {
          tickets.push(ticket);
        }
      });

      return tickets;
    },
  },
  watch: {
    cinema: function (newCinema) {
      axios
        .get(`/ticket/${this.user.id}/cinema/${this.cinema.id}`)
        .then((response) => {
          this.tickets = response.data;
        });
    },
  },
  methods: {
    paiTicket(ticket) {
      axios
        .put(`/ticket/pai/${ticket.id}`)
        .then((response) => {
          this.tickets = response.data;
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
    cancelTicket(ticket) {
      axios
        .put(`/ticket/cancel/${ticket.id}`)
        .then((response) => {
          this.tickets = response.data;
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
    axios
      .get(`/ticket/${this.user.id}/cinema/${this.cinema.id}`)
      .then((response) => {
        this.tickets = response.data;
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