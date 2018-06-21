<template>
    <div id="app-home">
        <v-container>
            <h2>Películas</h2>
            <br>
            <hr>
            <h3>Agregar película</h3>
            <v-card width="300px" class="movie-card" hover :to="{name: 'new-film', params:{id: this.def}}">
              <v-card-title primary-title>
                  <div>
                      <h3 class="headline mb-0">Nueva Película</h3>
                  </div>
              </v-card-title>
            </v-card>
            <br>
            <hr>
            <h3>Películas existentes</h3>
            <v-flex v-for="film in films">
              <v-card width="300px" class="movie-card" hover :to="{name: 'new-film', params:{ id: film.id }}">
                  <v-card-title primary-title>
                      <div>
                          <h3 class="headline mb-0">{{film.nombre}}</h3>
                      </div>
                  </v-card-title>
              </v-card>
              <v-btn flat color="red" @click="del(film.id)">Eliminar</v-btn>
            </v-flex>
        </v-container>
    </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'films',
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
    methods:
    {
      getFilms() {
        //https://api.themoviedb.org/3/movie/now_playing?api_key=7917990738a6b09dbb79384b066eca6b
        axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas')
        .then((films)=>{
          this.films=films.data;
          });
      },
      del(id){
        axios.delete('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas', {data:{id_pelicula:id}})
        .then(response =>{
          console.log(response);
          this.getFilms();
        }).catch(error => {
          console.log(error);
        });
      }
    }
  }
</script>
