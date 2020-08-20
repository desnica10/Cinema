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
                        v-model="newHall.name"
                        :rules="form.rules.name"
                        label="Name"
                        type="text"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6">
                      <v-text-field
                        v-model="newHall.capacity"
                        :rules="form.rules.capacity"
                        label="Capacity"
                        type="number"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-form>
              </template>
              <template v-else>
                <p>
                  Are you sure, you want to delete:
                  <strong>"{{hall.name}}"</strong>
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
  props: ["dialog", "hall", "type"],
  data() {
    return {
      newHall: {
        id: null,
        name: null,
        capacity: null,
      },
      title: "",
      width: "55em",
      form: {
        valid: false,
        rules: {
          name: [(v) => !!v || "Name is required"],
          capacity: [(v) => !!v || "Capacity is required"],
        },
      },
    };
  },
  methods: {
    closeDialog() {
      this.$emit("close-dialog");
    },
    doType() {
      switch (this.type) {
        case "ADD":
          if (this.form.valid) {
            this.$emit("add-hall", this.newHall);
          }
          break;
        case "EDIT":
          if (this.form.valid) {
            this.$emit("edit-hall", this.newHall);
          }
          break;
        case "DELETE":
          this.$emit("delete-hall", this.hall);
          break;
        default:
          break;
      }
    },
  },
  mounted() {
    switch (this.type) {
      case "ADD":
        this.title = "Add";
        break;
      case "EDIT":
        this.title = "Edit";
        this.newHall = Object.assign({}, this.hall);
        break;
      case "DELETE":
        this.title = "Delete";
        this.width = "25em";
        break;
      default:
        break;
    }

    this.title += " Hall";
  },
};
</script>

<style>
</style>