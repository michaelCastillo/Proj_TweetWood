<template>
    <div id="movie">
        <v-card width="300px" class="movie-card">
            <v-card-media src="https://images-na.ssl-images-amazon.com/images/I/912RMzf4gZL._SY445_.jpg" height="200px">
            </v-card-media>
            <v-card-title primary-title>
                <div>
                    <h3 class="headline mb-0">título de la peli</h3>
                    <div>Located two hours south of Sydney in the Southern Highlands of New South Wales, ...</div>
                </div>
            </v-card-title>
            <v-card-actions class="movie-card-actions">
                <v-btn flat color="orange">Explorar</v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'movie',
        mounted() {
            this.id = this.$route.params.id;
        },
        data: function () {
            return {
                id: null,
                film: null
            }
        },
        methods: {
            getFilm() {
                axios.get('https://api.themoviedb.org/3/movie/' + this.id + '?api_key=7917990738a6b09dbb79384b066eca6b')
                    .then((film) => {
                        this.film = film.data;
                    });
            }
        }
    }
</script>