<template>
  <div id="app-home">
    <v-container>
      <v-form v-if="film!=null" ref="form" v-model="valid" lazy-validation>
        <v-text-field
          v-model="title"
          label="Título"
          required
        ></v-text-field>
        <h3>Restricción</h3>
        <v-radio-group v-model="row" row>
          <v-radio label="Libre" color="green darken-1" value="Libre" ></v-radio>
          <v-radio label="+10" color="light-blue darken-1" value="+10"></v-radio>
          <v-radio label="+12" color="yellow darken-2" value="+12" ></v-radio>
          <v-radio label="+14" color="orange darken-1" value="+14"></v-radio>
          <v-radio label="+16" color="red darken-1" value="+16" ></v-radio>
          <v-radio label="+18"  color="grey darken-4" value="+18"></v-radio>
        </v-radio-group>
        <v-text-field
          v-model="url_img"
          label="Url Imagen"
          required
        ></v-text-field>
        <h3>Géneros de la película</h3>
        <v-container fluid>
          <v-flex v-for="genre in genres">
            <v-checkbox v-model="selected" :label=genre.nombre :value = genre.id></v-checkbox>
          </v-flex>
        </v-container>
        <br>
        <h3>Agregar Keywords</h3>
        <br>
        <hr>
        <br>
        <v-text-field
          v-model="newWord"
          label="Keyword"
          required
        ></v-text-field>
        <v-btn @click="add">Agregar Palabra</v-btn>
        <v-flex xl2 lg3 md6 sm12 xs12 class="words-box"v-for="(keyWord, index) in keyWords">
          <v-card width="300px" class="movie-card">
              <v-card-title secondary-title>
                  <div>
                      <h3 class="headline mb-0">{{keyWord}}</h3>
                  </div>
              </v-card-title>
              <v-card-actions>
            <v-btn flat color="red" @click="pop(index)">Eliminar</v-btn>
          </v-card-actions>
          </v-card>
        </v-flex>
        <br>
        <v-btn
          :disabled="!valid"
          @click="submit"
        >
          Enviar
        </v-btn>
        <v-btn @click="clear">Limpiar</v-btn>
      </v-form>
      <span v-else>Cargando...</span>
    </v-container>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data: () => ({
      valid: true,
      title: '',
      genre: '',
      url_img: '',
      newWord: null,
      keyWords: [],
      film: null,
      checkbox: false,
      genres: null,
      selected: [],
      row: null
    }),
    mounted(){
      this.id = this.$route.params.id;
      this.genres = this.getGenres();
      if(this.id!=-1){
        this.getFilm();
      }
      else
        this.film = -1;
    },
    methods: {
      submit () {
        if (this.$refs.form.validate()) {
          let global_url = `http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/crear`;
          let put_url = `http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas`;
          let keywordsList = [];
          let genresList = [];
          this.keyWords.map(keyword =>{
            let keyJson = {palabra:keyword};
            keywordsList.push(keyJson);
          });
          this.selected.map(id_genre =>{
            let genre = {id:id_genre}
            genresList.push(genre);
          });
          console.log(genresList);
          if(this.id==-1){
            let objPost = {
              nombre: this.title,
              restriccion:this.row,
              generos: genresList,
              keywords: keywordsList
            };
            axios.post(global_url,objPost)
            .then(response =>{
              console.log(response);
              console.log("OBJPOST");
              console.log(objPost);
              this.$router.push('/films-admin');
              alert(this.title+" agregada corectamente.");
            }).catch(error => {
              console.log(error);
            })
          }
          else{
            let objPost = {
              id: this.id,
              nombre: this.title,
              restriccion:this.row,
              generos: genresList,
              keywords: keywordsList
            };
            axios.put(put_url,objPost)
            .then(response =>{
              console.log(response);
              console.log("OBJPOST");
              console.log(objPost);
              this.$router.push('/films-admin');
              alert(this.title+" modificada corectamente.");
            }).catch(error => {
              console.log(error);
            })
          }
        }
      },
      clear () {
        this.$refs.form.reset();
        this.keyWords = [];
      },
      add(){
        this.keyWords.unshift(this.newWord);
        this.newWord = null;
      },
      pop(index){
        this.keyWords.splice(index,1);
      },
      getFilm() {
          axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/' + this.id)
              .then((film) => {
                  this.film = film.data;
                  this.title = this.film.nombre;
                  this.restriction = this.film.restriccion;
                  this.film.keywords.map(keyword =>{
                    this.keyWords.push(keyword.palabra);
                  });
                  //this.selected = this.film.generos;
                  this.loadCheckbox();
                  this.row = this.film.restriccion;
              });
      },
      getGenres() {
        axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos/disponibles')
        .then((genres)=>{
          this.genres = genres.data;
          });
      },
      loadCheckbox(){
        if(this.film != null){
          this.film.generos.map(genre =>{
            this.selected.unshift(genre.id);
          });
        }
      }
    }
  }
</script>

<style scoped>
    .movie-card {
        margin: 1% 1%;
    }
    .movie-card-actions {
        margin: 1% 1%;
        padding: 0px 30px;
    }
</style>
