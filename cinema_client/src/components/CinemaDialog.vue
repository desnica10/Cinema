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
                    <v-col cols="6">
                      <v-text-field
                        v-model="newCinema.name"
                        :rules="form.rules.name"
                        label="Name"
                        type="text"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field
                        v-model="newCinema.address"
                        :rules="form.rules.address"
                        label="Address"
                        type="text"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="6">
                      <v-text-field
                        v-model="newCinema.phone"
                        :rules="form.rules.phone"
                        label="Phone"
                        type="text"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field
                        v-model="newCinema.email"
                        :rules="form.rules.email"
                        label="E-mail"
                        type="text"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <v-select
                        v-model="newCinema.managers"
                        :rules="form.rules.managers"
                        :items="managers"
                        label="Managers"
                        :item-text="displayManager"
                        multiple
                        chips
                        hint="Selet managers of cinema"
                        persistent-hint
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-form>
              </template>
              <template v-else>
                <p>
                  Are you sure, you want to delete:
                  <strong>"{{cinema.name}}"</strong>
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

export default {
  props: ["dialog", "cinema", "type"],
  data() {
    return {
      newCinema: {
        id: null,
        name: null,
        address: null,
        phone: null,
        email: null,
        managers: [],
      },
      managers: [],
      title: "",
      width: "55em",
      form: {
        valid: false,
        rules: {
          name: [(v) => !!v || "Name is required"],
          address: [(v) => !!v || "Address is required"],
          phone: [(v) => !!v || "Phone is required"],
          email: [(v) => !!v || "Email is required"],
          managers: [
            (v) => this.newCinema.managers.length > 0 || "Manager is required",
          ],
        },
      },
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close-dialog");
    },
    doType() {
      if (this.type !== "DELETE") {
        let managers = [];
        this.newCinema.managers.forEach((selectedManager) => {
          this.managers.forEach((manager) => {
            if (
              selectedManager ===
              manager.firstName +
                " " +
                manager.lastName +
                " (" +
                manager.username +
                ")"
            ) {
              managers.push(manager);
              return;
            }
          });
        });
        this.newCinema.managers = managers;
      }
      switch (this.type) {
        case "ADD":
          if (this.form.valid) {
            this.$emit("add-cinema", this.newCinema);
          }
          break;
        case "EDIT":
          if (this.form.valid) {
            this.$emit("edit-cinema", this.newCinema);
          }
          break;
        case "DELETE":
          this.$emit("delete-cinema", this.cinema);
          break;
        default:
          break;
      }
    },
    displayManager(manager) {
      return (
        manager.firstName +
        " " +
        manager.lastName +
        " (" +
        manager.username +
        ")"
      );
    },
  },
  mounted() {
    switch (this.type) {
      case "ADD":
        this.title = "Add";
        break;
      case "EDIT":
        this.title = "Edit";
        this.newCinema = Object.assign({}, this.cinema);
        break;
      case "DELETE":
        this.title = "Delete";
        this.width = "25em";
        break;
      default:
        break;
    }

    axios.get("/user/manager").then((response) => {
      this.managers = response.data;
    });

    this.title += " Cinema";
  },
};
</script>

<style>
</style>