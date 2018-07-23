<template>
    <v-app dark id="app-home">
        <v-container>
            <h2>Películas</h2>
            <br>
            <hr>
            <h3>Agregar película</h3>
            <v-card color = "green accent-4" width="300px" class="movie-card" hover :to="{name: 'new-film', params:{id: this.def}}">
              <v-card-title primary-title>
                  <div>
                      <h3 class="headline mb-0">Nueva Película</h3>
                  </div>
              </v-card-title>
            </v-card>
            <br>
            <v-btn color="orange" @click="anadirApi()">Añadir películas API</v-btn>
            <v-btn color="red" @click="delAll()">Eliminar Todo</v-btn>
            <v-btn color="blue" @click="startLucene()">Start Lucene</v-btn>
            <br>
            <hr>
            <h3>Películas existentes</h3>
            <v-flex v-for="film in films" :key="film.id">
              <v-card v-if = "film.disponible==true" width="300px" class="movie-card" @click="anadirApi()">
                  <v-card-title primary-title>
                      <div>
                          <h3 class="headline mb-0">{{film.nombre}}</h3>
                      </div>
                  </v-card-title>
                  <v-card-actions>
                    <v-btn flat color="orange" @click="goTo(film.id)">Editar</v-btn>
                    <v-btn flat color="red" @click="del(film.id)">Desabilitar</v-btn>
                  </v-card-actions>
              </v-card>
              <v-card v-else color = "red lighten-4" width="300px" class="movie-card">
                <v-card-title primary-title>
                    <div>
                        <h3 class="headline mb-0">{{film.nombre}}</h3>
                    </div>
                </v-card-title>
                <v-card-actions>
                  <v-btn flat color="orange" @click="goTo(film.id)">Editar</v-btn>
                  <v-btn flat color="green" @click="able(film.id)">Habilitar</v-btn>
                </v-card-actions>
              </v-card>
              <br>
            </v-flex>
        </v-container>
    </v-app>
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
        axios.delete('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas',{data:{id_pelicula:id}})
        .then(response =>{
          console.log(response);
          this.getFilms();
        }).catch(error => {
          console.log(error);
        });
      },
      goTo(id){
        this.$router.push({name: 'new-film', params:{ id: id}});
      },
      able(id){
        axios.put('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/disponible',{id_pelicula:id})
        .then(response =>{
          console.log(response);
          this.getFilms();
        }).catch(error => {
          console.log(error);
        });
      },
      anadirApi(){
        axios.get('https://api.themoviedb.org/3/movie/now_playing?api_key=7917990738a6b09dbb79384b066eca6b')
            .then((pelis)=>{
              pelis.data.results.map(peli =>{
                let obj={
                  nombre: peli.title,
                  restriccion:"",
                  idApi: peli.id,
                  img: peli.poster_path,
                  fiveTweets: "",
                  generos: [],
                  keywords: [{palabra:peli.title}]
                }
                axios.post('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/crear',obj)
                // .then(response =>{
                //   console.log(obj);
                // })
                .catch(error => {
                  console.log(error);
                });
              });
              this.getFilms();
            });
      },
      delAll(){
        axios.delete('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/keywords/deleteAll')
            .then(()=>{
              axios.delete('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/deleteAll')
                  .then(()=>{
                    this.getFilms();
                  }).catch(error => {
                    console.log(error);
                  });
            }).catch(error => {
              console.log(error);
            });
      },
      startLucene(){
        axios.post('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/lucene/start')
        .then(response=>{
          console.log(response);
        });
      }
    }
  }
</script>
