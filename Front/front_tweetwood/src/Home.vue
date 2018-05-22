<template>
  <div>
    <div v-if="films!=null">
      <li v-for="film in films">
        <strong>{{film.title}}</strong>
        <router-link :to="{ name: 'film', params: {id: film.id} }">Ver</router-link>
      </li>
    </div>
    <span v-else>Cargando...</span>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'home',
  mounted(){
    this.getFilms();
  },
  data () {
    return {
      msg: 'Home',
      films: null
    }
  },
  methods:{
    getFilms(){
      axios.get('https://api.themoviedb.org/3/movie/now_playing?api_key=7917990738a6b09dbb79384b066eca6b')
        .then((films)=>{
          this.films=films.data.results;
        });
    }
  }
}
</script>
