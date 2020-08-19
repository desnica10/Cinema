<template>
  <div>
      <cinemas v-if="user.type === 'ADMIN'" />
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import Cinemas from './admin/Cinemas.vue'

export default {
    components: {
        Cinemas
    },
    computed: {
        ...mapGetters(['user'])
    }
}
</script>

<style>

</style>