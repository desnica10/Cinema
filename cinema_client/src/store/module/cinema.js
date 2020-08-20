import axios from 'axios';

const state = {
    cinemas: [],
    cinema: {
        name: '',
        address: '',
    },
};

const getters = {
    cinemas: state => state.cinemas,
    cinema: state => state.cinema,
};

const actions = {
    async setCinemasAction({ commit }, user) {
        if (user === null || user.type === 'CUSTOMER') {
            axios.get("/cinema").then((response) => {
                commit('setCinemas', response.data);
                commit('setCinema', response.data[0]);
            });
        } else if (user.type === 'MANAGER') {
            axios.get(`/cinema/manager/${user.id}`).then((response) => {
                if (response.data.length === 0) {
                    commit('setCinemas', []);
                    commit('setCinema', {
                        name: '',
                        address: ''
                    });
                }else {
                    commit('setCinema', response.data[0]);
                }
            });
        }
    },
    setCinemaAction({ commit }, cinema) {
        commit('setCinema', cinema);
    },
};

const mutations = {
    setCinemas: (state, cinemas) => {
        state.cinemas = cinemas;
    },

    setCinema: (state, cinema) => {
        state.cinema = cinema;
    },
};

export default {
    state,
    getters,
    actions,
    mutations
}