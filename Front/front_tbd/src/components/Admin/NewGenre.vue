<template>
  <div id="app-home">
    <v-container>
      <h1>Nuevo Género</h1>
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field v-model="title" label="Nombre del Género" required></v-text-field>
        <v-btn :disabled="!valid" @click="submit">Enviar </v-btn>
      </v-form>
    </v-container>
  </div>
</template>
<script>
  import axios from 'axios'
  export default {
    data: () => ({
      valid: true,
      genre: null,
      title: '',
      id: null,
      url: 'http://167.99.155.164:8080'
    }),
    mounted(){
      this.id = this.$route.params.id;
      if(this.id!=-1)
        this.getGenre();
    },
    methods: {
      submit () {
        if (this.$refs.form.validate()) {
          let global_url = this.url+`/tweetwood_back-0.0.1-SNAPSHOT/generos`;
          if(this.id==-1){
            let objPost = {
              nombre: this.title,
              valorizacion:0
            };
            axios.post(global_url+'/create',objPost)
            .then(response =>{
              console.log(response);
              this.$router.push('/genre');
              alert(this.title+" agregado corectamente.");
            }).catch(error => {
              console.log(error);
            })
          }
          else{
            let objPost = {
              id: this.id,
              nombre: this.title
            };
            axios.put(global_url+'/'+this.id,objPost)
            .then(response =>{
              console.log(response);
              this.$router.push('/genre');
              alert(this.title+" modificado corectamente.");
            }).catch(error => {
              console.log(error);
            })
          }
        }
      },
      getGenre() {
          axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/generos/' + this.id)
              .then((genres) => {
                  this.genre = genres.data;
                  this.title = this.genre.nombre;
              });
      }
    }
  }
</script>
