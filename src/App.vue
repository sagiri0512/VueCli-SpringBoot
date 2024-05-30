<template>
  <TopNavigationVue/>
  <router-view></router-view>
</template>

<script>
import TopNavigationVue from './components/TopNavigation.vue'
export default {
  name: 'App',
  components: {
    TopNavigationVue,
  }
}
</script>