import Vue from 'vue'
import Router from 'vue-router'
import AppMovies from './components/Movies/Movies.vue'
import AppMovie from './components/Movies/Movie.vue'
import AppHome from './components/Home/Home.vue'
import Admin from './components/Admin/Admin.vue'
import NewFilm from './components/Admin/NewFilm.vue'
import Genre from './components/Admin/Genre.vue'
import Films from './components/Admin/Films.vue'
import NewGenre from './components/Admin/NewGenre.vue'
import NeoGraph from './components/Movies/Grafo.vue'

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
        },
        {
            path: '/admin',
            name: 'admin',
            component: Admin
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
        }
    ]
})
