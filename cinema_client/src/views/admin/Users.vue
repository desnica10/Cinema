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
              <h1 class="display-1 lack--text">Users</h1>
            </v-card-title>
            <v-tabs v-model="tab" grow>
              <v-tab>Customers</v-tab>
              <v-tab>Managers</v-tab>
              <v-tab>Admins</v-tab>
            </v-tabs>
          </v-card>
        </v-col>
      </v-row>
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="8">
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-col>
        <v-spacer />
        <v-col cols="3">
          <v-radio-group v-model="activity" row>
            <v-radio color="green darken--2" label="Active" value="ACTIVE"></v-radio>
            <v-radio color="red darken--2" label="Diactive" value="DIACTIVE"></v-radio>
          </v-radio-group>
        </v-col>
      </v-row>
      <v-data-table
        :headers="headers"
        :items="filteredUsers"
        :search="search"
        item-key="id"
        hide-default-footer
        class="elevation-1"
        height="43em"
        style="overflow: auto;"
      >
        <template
          v-slot:item.birth="{ item }"
        >{{item.birth.split("T")[0].split("-")[2]+"/"+ item.birth.split("T")[0].split("-")[1] +"/"+ item.birth.split("T")[0].split("-")[0]}}</template>
        <template v-slot:item.actions="{ item }">
          <v-btn icon>
            <v-icon color="yellow darken-2" @click="openDialog(item)">mdi-account-convert</v-icon>
          </v-btn>
          <v-btn icon :disabled="item.active">
            <v-icon color="green darken-2" @click="changeActivity(item,true)">mdi-account-check</v-icon>
          </v-btn>
          <v-btn icon :disabled="!item.active">
            <v-icon color="red darken-2" @click="changeActivity(item,false)">mdi-account-cancel</v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </v-card-text>
    <v-dialog v-model="dialog" persistent width="30em">
      <v-card>
        <v-card-title class="headline">Change User Type</v-card-title>
        <v-card-text>
          <v-select v-model="user.type" :items="types"></v-select>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red darken-1" text @click="dialog = false">Cancel</v-btn>
          <v-btn color="green darken-1" text @click="changeType">Change</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      search: "",
      activity: "ACTIVE",
      tab: 0,
      dialog: false,
      headers: [
        { text: "ID", value: "id" },
        { text: "Username", value: "username" },
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "E-mail", value: "email" },
        { text: "Phone", value: "phone" },
        { text: "Birth", value: "birth" },
        { text: "Type", value: "type" },
        { text: "Actions", value: "actions", sortable: false },
      ],
      users: [],
      types: [],
      user: {
        type: null,
      },
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  computed: {
    filteredUsers() {
      let users = [];
      let active = this.activity === "ACTIVE" ? true : false;
      let type = "";

      switch (this.tab) {
        case 0:
          type = "CUSTOMER";
          break;
        case 1:
          type = "MANAGER";
          break;
        case 2:
          type = "ADMIN";
          break;
      }

      this.users.forEach((user) => {
        if (user.active === active && user.type === type) {
          users.push(user);
        }
      });

      return users;
    },
  },
  methods: {
    changeActivity(user, activity) {
      axios.put(`/user/${user.id}/${activity}`).then((response) => {
        console.log(response.data);
        this.users = response.data;
      });
    },
    openDialog(user) {
      this.dialog = true;
      this.user = Object.assign({}, user);
    },
    changeType() {
      axios.put(`/user/type/${this.user.id}/${this.user.type}`).then((response) => {
        this.users = response.data;
        this.dialog = false;
      });
    },
  },
  mounted() {
    axios.get("/user").then((response) => {
      this.users = response.data;
    });

    axios.get("/user/types").then((response) => {
      this.types = response.data;
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