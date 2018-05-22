<template>
  <div>
    <h1>Detalle de la Película</h1>
    <hr>
    <div v-if="film != null">
      <!-- image.tmdb.org/t/p/w185/ || imageLink+film.poster_path -->
      <h1>{{film.original_title}}</h1>
      <img :src= "imageLink+film.poster_path" />
      <p>{{film.overview}}</p>
    </div>
    <span v-else>Cargando...</span>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'film',
  mounted(){
    this.id = this.$route.params.id;
    this.getFilm();
  },
  data () {
    return {
      id: null,
      film: null,
      imageLink: 'https://image.tmdb.org/t/p/w185/',
    }
  },
  methods:{
    getFilm(){
      axios.get('https://api.themoviedb.org/3/movie/'+this.id+'?api_key=7917990738a6b09dbb79384b066eca6b')
        .then((film)=>{
          this.film=film.data;
        });
    }
  }
}
</script>
