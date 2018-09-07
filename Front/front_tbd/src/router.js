import Vue from 'vue'
import Router from 'vue-router'
import AppMovies from './components/Movies/Movies.vue'
import AppMovie from './components/Movies/Movie.vue'
import AppHome from './components/Home/Home.vue'
import HomePage from './components/Home/HomePage.vue'
import Admin from './components/Admin/Admin.vue'
import NewFilm from './components/Admin/NewFilm.vue'
import Genre from './components/Admin/Genre.vue'
import Films from './components/Admin/Films.vue'
import NewGenre from './components/Admin/NewGenre.vue'
import NeoGraph from './components/Movies/Grafo.vue'
import Login from './components/Login/Login.vue'
import MapGraph from './components/Movies/MapGraph.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'home-page',
            component: HomePage
        },
        {
            path: '/charts',
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
        },
        {
            path: '/new-film/:id',
            name: 'new-film',
            component: NewFilm
        },
        {
            path: '/genre',
            name: 'genre',
            component: Genre
        },
        {
            path: '/new-genre',
            name: 'new-genre',
            component: NewGenre
        },
        {
            path: '/films-admin',
            name: 'films-admin',
            component: Films
        },
        {
            path: '/graph',
            name: 'grafo',
            component: NeoGraph
        },
        {
            path: '/admin',
            name: 'admin',
            component: Admin
            // meta: {requiresAuth: true}
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/map',
            name: 'map',
            component: MapGraph
        }
    ]
})
