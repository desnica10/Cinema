const state = {
    user: null,
};

const getters = {
    user: state => state.user,
};

const actions = {
    setUser({ commit }, user) {
        commit('setUser', user);
    },
    removeUser({ commit }) {
        commit('removeUser');
    },
};

const mutations = {
    setUser: (state, user) => {
        state.user = user;
    },
    removeUser: (state) => {
        state.user = null;
    },
};

export default {
    state,
    getters,
    actions,
    mutations
}