import Vue from 'vue'
import Vuex from 'vuex'

import createPersistedState from 'vuex-persistedstate'

import user from './module/user.js';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
  },
  plugins: [createPersistedState({
    paths: ['user']
  })]
})