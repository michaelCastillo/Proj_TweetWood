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
import AppHome from './components/Home/Home.vue'
import AppFooter from './components/Layout/Footer.vue'

export default {
  name: 'app',
  components: {
    AppNav, AppHome, AppFooter
  }
}
</script>

<style>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
    background-color: #ededed;
}
</style>
