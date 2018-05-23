<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-text-field
      v-model="name"
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
    <div v-for="(keyWord, index) in keyWords">
      {{keyWord}}
      <button @click="pop(index)">Borrar</button>
    </div>
    <br>
    <v-btn
      :disabled="!valid"
      @click="submit"
    >
      Enviar
    </v-btn>
    <v-btn @click="clear">Limpiar</v-btn>
  </v-form>
</template>

<script>
  import axios from 'axios'
  export default {
    data: () => ({
      valid: true,
      title: '',
      genre: '',
      newWord: null,
      keyWords: []
    }),
    methods: {
      submit () {
        if (this.$refs.form.validate()) {
          axios.post('/api/submit', {
            title: this.name,
            genre: this.email,
            keyWords: this.keyWords,
          })
        }
      },
      clear () {
        this.$refs.form.reset()
      },
      add(){
        this.keyWords.unshift(this.newWord);
        this.newWord = null;
      },
      pop(index){
        this.keyWords.splice(index,1);
      }
    }
  }
</script>
