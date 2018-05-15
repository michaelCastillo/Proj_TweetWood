<template>
  <div>
    <div v-if="film != null">
      <h1>{{film.original_title}}</h1>
      <p>{{film.overview}}</p>
    </div>
    <span v-else>Loading...</span>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'film',
  mounted(){
    this.getFilm();
  },
  data () {
    return {
      film: null
    }
  },
  methods:{
    getFilm(){
      axios.get('https://api.themoviedb.org/3/movie/550?api_key=7917990738a6b09dbb79384b066eca6b')
        .then((film)=>{
          this.film=film.data;
        });
    }
  }
}
</script>
