<template>
    <v-app id="app-home">
        <!-- <v-container class="app-home-container"> -->
            <v-container fluid>
              <v-layout row>
                <v-flex xl6  lg6  md6 sm12 xs12 elevation-6>
                  <!-- <div class="bar-chart"> -->
                    <BarChart 
                      class="bar-chart"
                      :chart-data="datacollectionBar2"
                    >
                    </BarChart>
                </v-flex>
                <!-- <v-flex xl5 offset-xl1 lg5 offset-lg1 md5 offset-md1 sm12 xs12 elevation-6> -->
                <v-flex xl6 lg6 md6 sm12 xs12 elevation-6>
                  <!-- <div class="bar-chart"> -->
                    <BarChart 
                      class="bar-chart"
                      :chart-data="datacollectionBar"
                      
                    >
                    </BarChart>
                  <!-- </div> -->
                </v-flex>
              </v-layout>
            </v-container>
        <!-- </v-container> -->
    </v-app>
</template>

<script>
    import axios from 'axios';
    import BarChart from './BarChart.js';

    export default {
      components:{BarChart},
      name: 'app-home',
    mounted(){
      this.getFilms();
      this.getFilms2();
    },
    data: function () {
        return {
            films: null,
            poster: 'https://image.tmdb.org/t/p/w342/',
            datacollectionBar: null,
            films2: null,
            datacollectionBar2: null,
            url: 'http://167.99.155.164:8080'
        }
    },
    methods:{
      getFilms() {
          axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/peliculas/mostValuated')
              .then((film) => {
                  this.films = film.data;

                  let tam = this.films.length-1;
                  for(let i=0; i<tam; i++){
                    this.films[i].value = this.redondeo2decimales(this.films[i].value);
                  }
                  // let desaprobacion=100-this.films.value;
                  // desaprobacion = this.redondeo2decimales(desaprobacion);
                  let labelList=[];
                  let dataList=[];
                  for(let i=0; i<this.films.length; i++){
                    labelList.push(this.films[i].nombre);
                    dataList.push(Math.log10(this.films[i].value));
                  }
                  this.datacollectionBar= {
                    labels: labelList,
                    datasets: [
                      {
                        label: 'Películas mejor valoradas',
                        backgroundColor: '#f87979',
                        data: dataList
                      }
                    ]
                  }
                }).catch((err) => console.error(err));
              },
              getFilms2() {
                axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/peliculas/mostTweeted')
                .then((film) => {
                  this.films2 = film.data;
                  let labelList2=[];
                  let dataList2=[];
                  for(let i=0; i<this.films2.length; i++){
                    labelList2.push(this.films2[i].nombre);
                    dataList2.push(Math.log10(this.films2[i].numTweets));
                  }
                  this.datacollectionBar2= {
                  labels: labelList2,
                  datasets: [
                    {
                      label: 'Películas más tweeteadas',
                      backgroundColor: '#79b0f8',
                      data: dataList2,
                    }
                  ]
                  }
                }).catch((err) => console.error(err));
              },
              redondeo2decimales(numero){
                    var flotante = parseFloat(numero);
                    var resultado = Math.round(flotante*100)/100;
                    return resultado;
                  }
    }
    }
</script>
<style scoped>
  .bar-chart {
    background-color: dark;
  }

  .app-home-container {
    /* background-color: #303030; */
    /* width: 100%; */
    /* margin: 0% 10%; */
  }

  #app-home {
    background-color: #303030;
    /* margin: 0 0; */
  }
</style>
