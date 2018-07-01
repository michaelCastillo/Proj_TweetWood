<template>
  <v-app dark id="app-home">
    <v-container>
      <h1>Géneros</h1>
      <v-card color = "green accent-4" width="300px" class="movie-card" hover :to="{name: 'new-genre', params:{ id: -1}}">
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
        <v-card v-if="genre.disponible == true" width="300px" class="movie-card">
            <v-card-title primary-title>
                <div>
                    <h3 class="headline mb-0">{{genre.nombre}}</h3>
                </div>
            </v-card-title>
            <v-card-actions>
              <v-btn flat color="orange" @click="goTo(genre.id)">Editar</v-btn>
              <v-btn flat color="red" @click="del(genre.id)">Desabilitar</v-btn>
            </v-card-actions>
        </v-card>
        <v-card v-else color = "red lighten-4" width="300px" class="movie-card">
          <v-card-title primary-title>
              <div>
                  <h3 class="headline mb-0">{{genre.nombre}}</h3>
              </div>
          </v-card-title>
          <v-card-actions>
            <v-btn flat color="orange" @click="goTo(genre.id)">Editar</v-btn>
            <v-btn flat color="green" @click="able(genre.id)">Habilitar</v-btn>
          </v-card-actions>
        </v-card>
        <br>
      </v-flex>
    </v-container>
  </v-app>
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
      },
      able(id){
        axios.put('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos/setDisponible', {id_genero:id})
        .then(response =>{
          console.log(response);
          alert("Habilitaste el género con ID: "+ id);
          this.getGenres();
        }).catch(error => {
          console.log(error);
        });
      },
      del(id){
        axios.delete('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos', {data:{id_genero:id}})
        .then(response =>{
          console.log(response);
          alert("Desabilitaste el género con ID: "+id);
          this.getGenres();
        }).catch(error => {
          console.log(error);
        });
      },
      goTo(id){
        this.$router.push({name: 'new-genre', params:{ id: id}});
      }
    }
  }
</script>
