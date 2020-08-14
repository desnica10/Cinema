<template>
  <v-row>
    <v-col align="center" justify="center">
      <v-snackbar
        v-model="alert.show"
        :timeout="alert.timeout"
        :color="alert.color"
        top
        right
        style="margin-top: 2%;"
      >{{ alert.text }}</v-snackbar>
      <v-form v-model="form.valid">
        <v-card style="width: 40%; margin-top: 2.5%">
          <v-card-title justify="center">
            <v-col>
              <h4>Register</h4>
            </v-col>
          </v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="user.username"
                  :rules="form.rules.username"
                  label="Username"
                  type="text"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user.password"
                  :rules="form.rules.password"
                  label="Password"
                  type="password"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user.firstName"
                  :rules="form.rules.firstName"
                  label="First Name"
                  type="text"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user.lastName"
                  :rules="form.rules.lastName"
                  label="Last Name"
                  type="text"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user.phone"
                  :rules="form.rules.phone"
                  label="Phone"
                  type="text"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user.email"
                  :rules="form.rules.email"
                  label="E-mail"
                  type="text"
                ></v-text-field>
              </v-col>
              <v-col>
                <date-picker v-model="user.birth" format="DD-MM-YYYY" placeholder="Birth" style="width: 100%;"></date-picker>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                Want to
                <a href="/login">login</a>?
              </v-col>
              <v-spacer />
              <v-col>
                <v-btn color="green white--text" @click="register">Register</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-form>
    </v-col>
  </v-row>
</template>

<script>
import axios from "axios";

import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";

export default {
  components: {
    DatePicker,
  },
  data() {
    return {
      user: {
        username: null,
        password: null,
        firstName: null,
        lastName: null,
        phone: null,
        email: null,
        birth: null,
      },
      form: {
        valid: false,
        rules: {
          username: [(v) => !!v || "Username is required"],
          password: [(v) => !!v || "Password is required"],
          firstName: [(v) => !!v || "Name is required"],
          lastName: [(v) => !!v || "Surname is required"],
          phone: [(v) => !!v || "Phone is required"],
          email: [(v) => !!v || "E-mail is required"],
        },
      },
      alert: {
        show: false,
        text: "Error",
        timeout: 2000,
        color: "red",
      },
    };
  },
  methods: {
    register() {
      if (this.form.valid) {
        if (this.user.birth === null) {
          this.alert.color = "red";
          this.alert.text = "Birth field is required.";
          this.alert.show = true;
          return;
        }
        axios
          .post("/register", this.user)
          .then(() => {
            this.$router.push("/login");
          })
          .catch((error) => {
            this.alert.text = error.response.data;
            this.alert.color = "red";
            this.alert.show = true;
          });
      } else {
        this.alert.color = "red";
        this.alert.text = "All fields are required.";
        this.alert.show = true;
      }
    },
  },
};
</script>

<style>
</style>