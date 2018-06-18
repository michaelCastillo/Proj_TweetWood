<template>
  <div>
    <v-form v-if="film!=null" ref="form" v-model="valid" lazy-validation>
      <v-text-field
        v-model="title"
        label="Título"
        required
      ></v-text-field>
      <v-text-field
        v-model="restriction"
        label="Restricción"
        required
      ></v-text-field>
      <v-text-field
        v-model="url_img"
        label="Url Imagen"
        required
      ></v-text-field>
      <v-text-field
        v-model="genre"
        label="Género"
        required
      ></v-text-field>
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
      restriction: '',
      url_img: '',
      newWord: null,
      keyWords: [],
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
          let global_url = `http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/crear`;
          let keywordsList = [];
          let keywords = this.keyWords.map(keyword =>{
            let keyJson = {palabra:keyword};
            keywordsList.push(keyJson);
          });
          let objPost = {
            nombre: this.title,
            restriccion:this.restriction,
            //genre: this.genre,
            keywords: keywordsList
          };
          axios.post(global_url,objPost)
          .then(response =>{
            console.log(response);
            this.$router.push('/films-admin');
            alert(this.title+" agregada corectamente.");
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
          axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/' + this.id + '/get')
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
