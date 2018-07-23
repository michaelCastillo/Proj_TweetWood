<template>
  <div id="app">
    <v-app>
        <app-nav></app-nav>
        <v-content>
            <router-view></router-view>
        </v-content>
        <app-footer></app-footer>
    </v-app>
  </div>
</template>

<script>
import AppNav from './components/Layout/Nav.vue'
import AppFooter from './components/Layout/Footer.vue'

export default {
  name: 'app',
  components: {
    AppNav, AppFooter
  }
}
</script>

<style scoped>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    /* -webkit-font-smoothing: antialiased; */
    /* -moz-osx-font-smoothing: grayscale; */
    /* text-align: center; */
    /* color: #2c3e50; */
    /* padding: 0px 0px; */

    background-color: red;
}
</style>
