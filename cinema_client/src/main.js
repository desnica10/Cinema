import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false

axios.defaults.baseURL = "http://localhost:8080"

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
