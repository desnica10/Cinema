<template>
  <div>
      <cinema-projections v-if="user !== null && user.type === 'MANAGER'" />
      <cinemas v-if="user !== null && user.type === 'ADMIN'" />
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Cinemas from './admin/Cinemas.vue'
import CinemaProjections from './manager/CinemaProjections.vue'

export default {
    components: {
        Cinemas,
        CinemaProjections,
    },
    computed: {
        ...mapGetters(['user'])
    }
}
</script>

<style>

</style>