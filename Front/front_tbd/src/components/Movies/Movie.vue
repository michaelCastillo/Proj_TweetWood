<template>
    <v-app dark id="movie">
        <v-container fluid>
            <v-layout v-if="this.film != null" row wrap class="movie">
                <v-flex xl3 lg3 md3 sm12 xs12 elevation-12 class="movie-picture">
                    <div class="movie-title">
                        <h1>{{film.nombre}}</h1>
                    </div>
                    <div v-if="this.apiData.poster_path != null">
                        <img :src="poster+this.apiData.poster_path"/>
                    </div>
                    <div v-else>
                        <h3>Imagen no se encuentra disponible.</h3>
                    </div>
                    <div class="movie-sinopsis">
                        <h3>Reseña</h3>
                        <div v-if="this.apiData.overview != null">
                            <p>{{apiData.overview}}</p>
                        </div>
                        <div v-else>
                            <p>Lamentablemente la reseña no se encuentra disponible.</p>
                        </div>
                    </div>
                </v-flex>

                <v-flex xl7 offset-xl1 lg7 offset-lg1 md7 offset-md1 sm12 xs12 elevation-12 class="movie-info">
                    <h3>Géneros</h3>
                    <br>
                    <div v-for="genre in film.generos" :key="genre.id">
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
                              <div align="center" v-for="tweet in tweetList" :key="tweet.id">
                                  <Tweet :id="tweet" error-message="" error-message-class="tweet--not-found"></Tweet>
                              </div>
                              <br>
                              <hr>
                              <br>
                              <h3>Tweets totales sobre {{film.nombre}}: {{film.numTweets}}</h3>
                              <br>
                            </div>
                            <div v-if="n==2" class="size-chart">
                              <CommitChart
                                :chart-data="datacollectionPie"
                              >
                              </CommitChart>
                            </div>
                            <div v-if="n==3" class="size-chart">
                              <LineChart :chart-data="datacollectionLine"></LineChart>
                            </div>
                            <div v-if="n==4" class="size-chart">
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

                <!-- <v-flex xl12 lg12 md12 sm12 xs12 class="graph" elevation-12>
                    <p>Reseña</p>

                </v-flex> -->
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
            //this.updateChartFill();
        },
        updated(){
            this.getApiData(this.film.idApi);
        },
        data: function () {
            return {
                id: null,
                film: null,
                poster: 'https://image.tmdb.org/t/p/w342/',
                api_key: "?api_key=946fc3c5365c912765fffe16866d65ed",
                apiData: null,
                datacollectionPie: null,
                datacollectionLine: null,
                datacollectionLine2: null,
                tweetList: [],
                maxTweet: -1,
                dateMaxTweet: null,
                url: 'http://167.99.155.164:8080'
            }
        },
        methods: {
          getFilm() {
              axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/peliculas/' + this.id)
                  .then((film) => {
                      this.film = film.data;
                      this.splitTweet();
                      let tam = this.film.estadisticas.length-1;
                      this.film.estadisticas[tam].aprobacion = this.redondeo2decimales(this.film.estadisticas[tam].aprobacion);
                      let desaprobacion=100-this.film.estadisticas[tam].aprobacion;
                      desaprobacion = this.redondeo2decimales(desaprobacion);
                      this.datacollectionPie = {
                        labels: ['Aprobación (' + this.film.estadisticas[tam].aprobacion+'%)', 'Desaprobación (' + desaprobacion + '%)'],
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
                            label: 'Aprobación histórica',
                            backgroundColor: '#2ae03c',
                            data: dataList,
                            fill: "false",
                            borderColor: '#2ae03c'
                          }
                        ]
                      }
                      this.datacollectionLine2 = {
                        labels: labelList2,
                        datasets: [
                          {
                            label: 'Tweets',
                            backgroundColor: '#2ac2e0',
                            data: dataList2,
                            fill: "false",
                            borderColor: '#2ac2e0'
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
          },
          getApiData(id){
              axios.get('https://api.themoviedb.org/3/movie/' + id + this.api_key)
                   .then((api_data) => {
                       this.apiData = api_data.data;
                    })
                    .catch((err) => console.error(err));
          }

        }
    }
</script>

<style scoped>
    #movie {
        text-align: center;
    }
    .movie-picture {
        /*margin: 10px 10px;*/
        border: 1px solid #303030;
        border-radius: 5px;
        background-color: #252525;
    }

    .movie-info {
        padding-top: 10px;
        border: 1px solid #303030;
        background-color: #252525;
        border-radius: 5px;
    }

    .movie-title {
        margin: 2% 2%;
    }
    .movie-overview {
        margin: 1% 5%;
        text-align: justify;
    }

    .movie-sinopsis {
        text-align: center;
    }

    .movie-sinopsis h3 {
        padding: 10px 0px;
    }

    .movie-sinopsis p {
        padding: 10px 15px;
    }

    .movie-overview h4 {
        text-align: center;
    }

    .graph {

        border: 1px solid #efefef;
        border-radius: 10px;
    }

    .size-chart {
        margin: 5px auto;
        width: 550px;
        /* color: white; */
    }
</style>
