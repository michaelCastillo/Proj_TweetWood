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
      id: null
    }),
    mounted(){
      this.id = this.$route.params.id;
      if(this.id!=-1)
        this.getGenre();
    },
    methods: {
      submit () {
        if (this.$refs.form.validate()) {
          let global_url = `http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos`;
          if(this.id==-1){
            let objPost = {
              nombre: this.title
            };
            axios.post(global_url,objPost)
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
            axios.put(global_url,objPost)
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
          axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/generos/' + this.id)
              .then((genres) => {
                  this.genre = genres.data;
                  this.title = this.genre.nombre;
              });
      }
    }
  }
</script>
