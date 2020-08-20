<template>
  <div>
      <cinema-projections v-if="user !== null && user.type === 'MANAGER'" />
      <cinemas v-else-if="user !== null && user.type === 'ADMIN'" />
      <films v-else />
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Cinemas from './admin/Cinemas.vue'
import CinemaProjections from './manager/CinemaProjections.vue'
import Films from './customer/Films.vue'

export default {
    components: {
        Cinemas,
        CinemaProjections,
        Films,
    },
    computed: {
        ...mapGetters(['user'])
    }
}
</script>

<style>

</style>