<template>
  <div>
    <v-form v-if="film!=null" ref="form" v-model="valid" lazy-validation>
      <v-text-field
        v-model="title"
        label="Título"
        required
      ></v-text-field>
      <v-text-field
        v-model="genre"
        label="Género"
        required
      ></v-text-field>
      <v-text-field
        v-model="newWord"
        label="Palabra clave"
        required
      ></v-text-field>
      <v-btn @click="add">Agregar Palabra</v-btn>
      <v-flex xl2 lg3 md6 sm12 xs12 class="words-box"v-for="(keyWord, index) in keyWords">
        <v-card width="150px" class="movie-card">
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
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data: () => ({
      valid: true,
      title: '',
      genre: '',
      newWord: null,
      keyWords: ["palabras", "de", "ejemplo"],
      film: null
    }),
    mounted(){
      this.id = this.$route.params.id;
      if(this.id!=-1)
        this.getFilm();
      else
        this.film = -1;
    },
    methods: {
      submit () {
        if (this.$refs.form.validate()) {
          let local_url = `http://localhost:1310/peliculas/crear`;
          let keywordsList = [];
          let keywords = this.keyWords.map(keyword =>{
            let keyJson = {palabra:keyword};
            keywordsList.push(keyJson);
          });
          let objPost = {
            nombre: this.title,
            restriccion:"+15",
            //genre: this.genre,
            keywords: keywordsList
          };
            console.log(keywordsList);
            console.log(objPost);
          axios.post(local_url,objPost)
          .then(response =>{
            console.log(response);
          }).catch(error => {
            console.log(error);
          })
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
          axios.get('https://api.themoviedb.org/3/movie/' + this.id + '?api_key=7917990738a6b09dbb79384b066eca6b')
              .then((film) => {
                  this.film = film.data;
                  this.title = this.film.title;
                  this.genre = this.film.genres[0].name;
              });
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
