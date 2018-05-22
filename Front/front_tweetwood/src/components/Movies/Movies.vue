<template>
    <div id="app-movies">
        <v-spacer>
            <v-container>
                <v-layout row wrap>
                    <v-flex xl4 lg4 md6 sm12 xs12 class="movie-box" v-for="film in films">
                        <v-card width="300px" class="movie-card">
                            <v-card-media :src="img+film.poster_path" height="200px">
                            </v-card-media>
                            <v-card-title primary-title>
                                <div>
                                    <h3 class="headline mb-0">{{ film.title}}</h3>
                                    <div>Popularity: {{film.popularity}}</div>
                                </div>
                            </v-card-title>
                            <v-card-actions class="movie-card-actions">
                                <v-btn flat color="orange">Explorar</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-flex>

                    <!-- No borrar esto porfa (Hector)
                    <v-flex xl4 lg4 md6 sm12 xs12>-->
                        <!--<v-card width="300px" class="movie-card">-->
                            <!--<v-card-media src="https://images-na.ssl-images-amazon.com/images/I/912RMzf4gZL._SY445_.jpg" height="200px">-->
                            <!--</v-card-media>-->
                            <!--<v-card-title primary-title>-->
                                <!--<div>-->
                                    <!--<h3 class="headline mb-0">título de la peli</h3>-->
                                    <!--<div>Located two hours south of Sydney in the Southern Highlands of New South Wales, ...</div>-->
                                <!--</div>-->
                            <!--</v-card-title>-->
                            <!--<v-card-actions class="movie-card-actions">-->
                                <!--<v-btn flat color="orange">Explorar</v-btn>-->
                            <!--</v-card-actions>-->
                        <!--</v-card>-->
                    <!--</v-flex>-->
                </v-layout>
            </v-container>
        </v-spacer>
    </div>
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
                axios.get('https://api.themoviedb.org/3/movie/now_playing?api_key=7917990738a6b09dbb79384b066eca6b')
                    .then((films)=>{
                        this.films=films.data.results;
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