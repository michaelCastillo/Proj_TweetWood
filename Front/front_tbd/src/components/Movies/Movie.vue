<template>
    <div id="movie">
        <v-container fluid>
            <v-layout row wrap class="movie">
                <v-flex xl6 lg6 md4 sm12 xs12 elevation-12 class="movie-picture">
                    <div>
                        <img :src="poster+film.poster_path"/>
                    </div>
                </v-flex>

                <v-flex xl6 lg6 md4 sm12 xs12 elevation-12 class="movie-info">
                    <div class="movie-title">
                        <h1>{{film.title}}</h1>
                    </div>
                    <v-divider></v-divider>
                    <div class="movie-overview">
                        <h4> Overview </h4>
                        <p>{{film.overview}}</p>
                    </div>
                </v-flex>

                <v-flex xl12 lg12 md12 sm12 xs12 class="graph" elevation-12>
                    <v-tabs v-model="active" dark slider-color="red">
                        <v-tab v-for="n in 2" :key="n" ripple>
                            <div v-if="n==1">
                                Linear Chart
                            </div>
                            <div v-if="n==2">
                                Pie Chart
                            </div>

                        </v-tab>
                        <v-tab-item v-for="n in 3" :key="n">
                            <div v-if="n==1">
                                <p>hola en linear</p>
                            </div>
                            <div v-if="n==2">
                                <p>hola en torta</p>
                            </div>
                        </v-tab-item>
                    </v-tabs>

                </v-flex>
            </v-layout>
        </v-container>

    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'film',
        mounted() {
            this.id = this.$route.params.id;
            this.getFilms();
        },
        data: function () {
            return {
                id: null,
                film: null,
                poster: 'https://image.tmdb.org/t/p/w342/'
            }
        },
        methods: {
            getFilms() {
                axios.get('https://api.themoviedb.org/3/movie/' + this.id + '?api_key=7917990738a6b09dbb79384b066eca6b')
                    .then((film) => {
                        this.film = film.data;
                    });
            }
        }
    }
</script>

<style scoped>
    .movie {

    }
    .movie-picture {
        /*margin: 10px 10px;*/
        border: 1px solid #efefef;
        border-radius: 10px;
    }

    .movie-info {
        /*margin: 10px 10px;*/
        border: 1px solid #efefef;
        border-radius: 10px;
    }

    .movie-title {
        margin: 2% 2%;
    }
    .movie-overview {
        margin: 1% 5%;
        text-align: justify;
    }

    .movie-overview h4 {
        text-align: center;
    }

    .graph {

        border: 1px solid #efefef;
        border-radius: 10px;
    }
</style>