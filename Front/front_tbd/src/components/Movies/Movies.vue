<template>
    <v-app dark id="app-movies">
        <v-layout row wrap>
            <v-flex xl2 lg3 md6 sm12 xs12 class="movie-box" v-for="film in films">
                <v-card width="300px" class="movie-card" hover :to="{name: 'film', params:{ id: film.id }}">
                    <v-card-media :src="img+film.img" height="185px">
                    </v-card-media>
                    <v-card-title primary-title>
                        <div>
                            <h3 class="headline mb-0">{{ film.nombre}}</h3>
                        </div>
                    </v-card-title>
                </v-card>
            </v-flex>
        </v-layout>
    </v-app>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'app-movies',
        mounted()
        {
            this.getFilms();
        },
        data: function () {
            return {
                films: null,
                img: 'https://image.tmdb.org/t/p/w185/'
            }
        },
        methods: {
            getFilms() {
                axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/disponibles')
                    .then((films)=>{
                        this.films=films.data;
                    });
            }

        }
    }
</script>

<style scoped>
    .movie-card {
        margin: 5% 5%;
    }
    .movie-card-actions {
        margin: 1% 1%;
        padding: 0px 90px;
    }
</style>
