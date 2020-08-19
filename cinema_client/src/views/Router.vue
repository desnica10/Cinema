<template>
  <router-view />
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "Home",
  computed: { ...mapGetters(["user"]) },
};
</script>
