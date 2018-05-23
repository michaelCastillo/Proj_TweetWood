<template>
    <div id="app-home">
        <v-container>
            <h1> Administrador </h1>
            <h2>Lista de películas:</h2>
            <v-card width="300px" class="movie-card" hover :to="{name: 'new-film', params:{id: this.def}}">
              <v-card-title primary-title>
                  <div>
                      <h3 class="headline mb-0">Nueva Película</h3>
                  </div>
              </v-card-title>
            </v-card>
            <v-flex v-for="film in films">
              <v-card width="300px" class="movie-card" hover :to="{name: 'new-film', params:{ id: film.id }}">
                  <v-card-title primary-title>
                      <div>
                          <h3 class="headline mb-0">{{film.title}}</h3>
                      </div>
                  </v-card-title>
              </v-card>
            </v-flex>
        </v-container>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'admin',
        mounted()
        {
            this.getFilms();
        },
        data: function () {
            return {
                films: null,
                def: -1
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
