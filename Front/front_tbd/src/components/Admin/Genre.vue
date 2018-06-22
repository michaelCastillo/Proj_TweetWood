<template>
  <div id="app-home">
    <v-container>
      <h1>Géneros</h1>
      <v-card width="300px" class="movie-card" hover :to="{name: 'new-genre'}">
        <v-card-title primary-title>
            <div>
                <h3 class="headline mb-0">Nuevo género</h3>
            </div>
        </v-card-title>
      </v-card>
      <br>
      <hr>
      <h3>Géneros existentes</h3>
      <v-flex v-for="genre in genres">
        <v-card width="300px" class="movie-card" hover :to="{name: 'new-genre', params:{ id: genre.id }}">
            <v-card-title primary-title>
                <div>
                    <h3 class="headline mb-0">{{genre.nombre}}</h3>
                </div>
            </v-card-title>
        </v-card>
        <v-btn flat color="red">Eliminar</v-btn>
      </v-flex>
    </v-container>
  </div>
</template>

<script>
  import axios from 'axios';
  export default
  {
    name: 'genre',
    mounted()
      {
        this.getGenres();
      },
    data: function(){
      return{
        genres: null
      }
    },
    methods:
    {
      onNewGenre() {
        this.$router.push({name: 'new-genre'});
      },
      getGenres() {
        axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos')
        .then((genres)=>{
          this.genres = genres.data;
          });
      }
    }
  }
</script>
