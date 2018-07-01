<template>
    <div>
        <h1> soy un tweet </h1>
        
        <div  v-for="film in films">
            <Tweet :id="film.fiveTweets[0]"></Tweet>
            <h2>{{film.nombre}}</h2>
        </div>
    </div>

</template>

<script>
import { Tweet, Moment, Timeline } from 'vue-tweet-embed';
import axios from 'axios';
export default {
    name: 'app-showTweets',
    components:{
        Tweet
    },
    films:null,
    mounted() {
            
            axios.get(`http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas`).then((films) => {
                this.films = films.data;
                this.films.map((film) => {
                    film.fiveTweets = film.fiveTweets.split("|");
                });
            });

            
        }
}
</script>

<style scoped>

</style>


