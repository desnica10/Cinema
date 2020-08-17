<template>
  <div>
    <v-row>
      <v-col align="center" justify="center">
        <v-snackbar
          v-model="alert.show"
          :timeout="alert.timeout"
          :color="alert.color"
          top
          right
        >{{ alert.text }}</v-snackbar>
        <v-form v-model="form.valid">
          <v-card style="width: 40%; margin-top: 2.5%">
            <v-card-title justify="center">
              <v-col>
                <h5>Profile</h5>
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
                  <date-picker
                    v-model="user.birth"
                    format="DD-MM-YYYY"
                    placeholder="Birth"
                    style="width: 100%;"
                  ></date-picker>
                </v-col>
              </v-row>
              <v-row>
                <v-col align="left">
                  <v-btn color="red white--text" @click="cancel">Cancel</v-btn>
                </v-col>
                <v-col align="end">
                  <v-btn color="green white--text" @click="save">Save</v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-form>
      </v-col>
    </v-row>
    <v-row>
      <v-col align="center" justify="center">
        <v-form ref="passwordForm" v-model="form.validPassword">
          <v-card style="width: 40%; margin-top: 2.5%">
            <v-card-title justify="center">
              <v-col>
                <h5>Password</h5>
              </v-col>
            </v-card-title>
            <v-card-text>
              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="oldPassword"
                    :rules="form.rules.password"
                    label="Old password"
                    type="password"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="newPassword"
                    :rules="form.rules.newPassword"
                    label="New password"
                    type="password"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    v-model="confirmPassword"
                    :rules="form.rules.confirmPassword"
                    label="Confirm password"
                    type="password"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col align="left">
                  <v-btn color="red white--text" @click="cancelPassword">Cancel</v-btn>
                </v-col>
                <v-col align="end">
                  <v-btn color="green white--text" @click="restartPassword">Restart</v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-form>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
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
      oldPassword: null,
      newPassword: null,
      confirmPassword: null,
      form: {
        valid: false,
        validPassword: false,
        rules: {
          username: [(v) => !!v || "Username is required"],
          password: [
            (v) => !!v || "Password is required",
            (v) => v === this.user.password || "Password does not match",
          ],
          newPassword: [(v) => !!v || "Password is required"],
          confirmPassword: [
            (v) => !!v || "Password is required",
            (v) => v === this.newPassword || "Password does not match",
          ],
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
    ...mapActions(["setUser"]),
    save() {
      if (this.form.valid) {
        if (this.user.birth === null) {
          this.alert.color = "red";
          this.alert.text = "Birth field is required.";
          this.alert.show = true;
          return;
        }
        axios
          .put(`/user/${this.$store.state.user.user.id}`, this.user)
          .then((response) => {
            this.setUser(response.data);
            this.user = response.data;
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
    cancel() {
      axios
        .get(`/user/${this.$store.state.user.user.id}`)
        .then((response) => {
          this.user = response.data;
        })
        .catch((error) => {
          this.alert.text = error.response.data;
          this.alert.color = "red";
          this.alert.show = true;
        });
    },
    cancelPassword(event) {
      this.$refs["passwordForm"].reset();
    },
    restartPassword(event) {
      if (this.form.validPassword) {
        this.user.password = this.newPassword;
        axios
          .post(`/user/${this.$store.state.user.user.id}`, this.user)
          .then((response) => {
            this.$refs["passwordForm"].reset();
            this.alert.text = response.data;
            this.alert.color = "green";
            this.alert.show = true;
          })
          .catch((error) => {
            this.alert.text = error.response.data;
            this.alert.color = "red";
            this.alert.show = true;
          });
      }
    },
  },
  mounted() {
    axios
      .get(`/user/${this.$store.state.user.user.id}`)
      .then((response) => {
        this.user = response.data;
      })
      .catch((error) => {
        this.alert.text = error.response.data;
        this.alert.color = "red";
        this.alert.show = true;
      });
  },
};
</script>

<style>
</style>