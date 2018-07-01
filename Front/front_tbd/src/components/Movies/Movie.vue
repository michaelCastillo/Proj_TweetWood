<template>
    <v-app dark id="movie">
        <v-container fluid>
            <v-layout v-if="this.film != null" row wrap class="movie">
                <v-flex xl4 lg4 md4 sm12 xs12 elevation-12 class="movie-picture">
                    <div class="movie-title">
                        <h1>{{film.nombre}}</h1>
                    </div>
                    <div v-if="this.film.img != null">
                        <img :src="poster+this.film.img"/>
                    </div>
                </v-flex>

                <v-flex xl8 lg8 md8 sm12 xs12 elevation-12 class="movie-info">
                    <br>
                    <h3>{{film.nombre}} géneros:</h3>
                    <br>
                    <hr>
                    <br>
                    <div v-for="genre in film.generos">
                      {{genre.nombre}}: {{genre.valorizacion}}% de valoración en Twitter.
                      <br>
                    </div>
                    <br>
                    <hr>
                    <v-divider></v-divider>
                    <v-tabs dark slider-color="red">
                        <v-tab v-for="n in 4" :key="n" ripple>
                            <div v-if="n==1">
                                Tweets
                            </div>
                            <div v-if="n==2">
                                Valoración
                            </div>
                            <div v-if="n==3">
                                Valoración histórica
                            </div>
                            <div v-if="n==4">
                              Tweets en el tiempo
                            </div>
                        </v-tab>
                        <v-tab-item v-for="n in 4" :key="n">
                            <div v-if="n==1">
                              <div align="center" v-for="tweet in tweetList">
                                <Tweet :id="tweet"></Tweet>
                              </div>
                              <br>
                              <hr>
                              <br>
                              <h3>Tweets Totales sobre {{film.nombre}}: {{film.numTweets}}</h3>
                              <br>
                            </div>
                            <div v-if="n==2">
                              <CommitChart
                              :chart-data="datacollectionPie">
                              </CommitChart>
                            </div>
                            <div v-if="n==3">
                              <LineChart :chart-data="datacollectionLine"></LineChart>
                            </div>
                            <div v-if="n==4">
                              <LineChart :chart-data="datacollectionLine2"></LineChart>
                              <br>
                              <hr>
                              <br>
                              <h3>Pick Tweets : {{maxTweet}}  </h3>
                              <h3>Fecha: {{dateMaxTweet}}</h3>
                              <br>

                            </div>
                        </v-tab-item>
                    </v-tabs>
                </v-flex>

                <v-flex xl12 lg12 md12 sm12 xs12 class="graph" elevation-12>
                    <p>Reseña</p>

                </v-flex>
            </v-layout>
            <span v-else>Cargando...</span>
        </v-container>

    </v-app>
</template>

<script>
    import axios from 'axios';
    import CommitChart from './CommitChart'
    import LineChart from './LineChart.js'
    import { Tweet } from 'vue-tweet-embed'

    export default {
        components: { CommitChart, LineChart, Tweet },
        name: 'film',
        mounted() {
            this.id = this.$route.params.id;
            this.getFilm();
        },
        data: function () {
            return {
                id: null,
                film: null,
                poster: 'https://image.tmdb.org/t/p/w342/',
                datacollectionPie: null,
                datacollectionLine: null,
                datacollectionLine2: null,
                tweetList: [],
                maxTweet: -1,
                dateMaxTweet: null
            }
        },
        methods: {
          getFilm() {
              axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/' + this.id)
                  .then((film) => {
                      this.film = film.data;
                      this.splitTweet();
                      let tam = this.film.estadisticas.length-1;
                      this.film.estadisticas[tam].aprobacion = this.redondeo2decimales(this.film.estadisticas[tam].aprobacion);
                      let desaprobacion=100-this.film.estadisticas[tam].aprobacion;
                      desaprobacion = this.redondeo2decimales(desaprobacion);
                      this.datacollectionPie = {
                        labels: ['Aprobación', 'Desaprobación'],
                        datasets: [
                          {
                            label: 'Data One',
                            backgroundColor: ['#66BB6A','#C62828'],
                            data: [this.film.estadisticas[tam].aprobacion, desaprobacion]
                          }
                        ]
                      }
                      let labelList=[];
                      let dataList=[];
                      let labelList2=[];
                      let dataList2=[];
                      let date=null;
                      for(var i=0; i<this.film.estadisticas.length; i++){
                        date=this.film.estadisticas[i].fecha.split("T",1)[0];
                        labelList.push(date);
                        dataList.push(this.film.estadisticas[i].aprobacion);
                        labelList2.push(date);
                        dataList2.push(this.film.estadisticas[i].numTweets);
                        if(this.maxTweet < this.film.estadisticas[i].numTweets){
                          this.maxTweet=this.film.estadisticas[i].numTweets;
                          this.dateMaxTweet=date;
                        }
                      }
                      this.datacollectionLine = {
                        labels: labelList,
                        datasets: [
                          {
                            label: 'Aprovación histórica',
                            backgroundColor: '#f87979',
                            data: dataList
                          }
                        ]
                      }
                      this.datacollectionLine2 = {
                        labels: labelList2,
                        datasets: [
                          {
                            label: 'Otro',
                            backgroundColor: '#900C3F',
                            data: dataList2
                          }
                        ]
                      }
                  });
          },
          redondeo2decimales(numero){
            var flotante = parseFloat(numero);
            var resultado = Math.round(flotante*100)/100;
            return resultado;
          },
          splitTweet(){
            this.tweetList=this.film.fiveTweets.split("|");
          }

        }
    }
</script>

<style scoped>
    .movie {

    }
    .movie-picture {
        /*margin: 10px 10px;*/
        border: 1px solid #efefef;
        border-radius: 10px;
    }

    .movie-info {
        /*margin: 10px 10px;*/
        border: 1px solid #efefef;
        border-radius: 10px;
    }

    .movie-title {
        margin: 2% 2%;
    }
    .movie-overview {
        margin: 1% 5%;
        text-align: justify;
    }

    .movie-overview h4 {
        text-align: center;
    }

    .graph {

        border: 1px solid #efefef;
        border-radius: 10px;
    }
</style>
