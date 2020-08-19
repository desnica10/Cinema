import Vue from 'vue'
import Vuex from 'vuex'

import createPersistedState from 'vuex-persistedstate'

import user from './module/user.js';
import cinema from './module/cinema.js';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
    cinema,
  },
  plugins: [createPersistedState({
    paths: ['user','cinema']
  })]
})