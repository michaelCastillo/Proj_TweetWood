import Vue from 'vue'
import Router from 'vue-router'
import AppMovies from './components/Movies/Movies.vue'
import AppMovie from './components/Movies/Movie.vue'
import AppHome from './components/Home/Home.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'app-home',
            component: AppHome
        },
        {
            path: '/films',
            name: 'app-movies',
            component: AppMovies
        },
        {
            path: '/films/:id',
            name: 'film',
            component: AppMovie
        }
    ]
})