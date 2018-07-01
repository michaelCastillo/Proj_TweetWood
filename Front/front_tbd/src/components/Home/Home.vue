<template>
    <v-app dark id="app-home">
        <v-container>
            <h1> Página principal </h1>
            <p> Aqui deberiamos explicar en que consiste la página con unos dibujos, quizás hasta el diagrama de contexto</p>
            <v-container fluid>
              <v-layout row wrap>
                <v-flex xl6 lg6 md6 sm12 xs12 elevation-12>
                  <BarChart :chart-data="datacollectionBar2">
                  </BarChart>
                </v-flex>
                <v-flex xl6 lg6 md6 sm12 xs12 elevation-12>
                  <BarChart :chart-data="datacollectionBar">
                </BarChart>
                </v-flex>
              </v-layout>
            </v-container>
        </v-container>
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
            datacollectionBar2: null
        }
    },
    methods:{
      getFilms() {
          axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/mostValuated')
              .then((film) => {
                  this.films = film.data;
                  let tam = this.films.length-1;
                  for(var i=0; i<tam; i++){
                    this.films[i].value = this.redondeo2decimales(this.films[i].value);
                  }
                  let desaprobacion=100-this.films.value;
                  desaprobacion = this.redondeo2decimales(desaprobacion);
                  let labelList=[];
                  let dataList=[];
                  for(var i=0; i<this.films.length; i++){
                    labelList.push(this.films[i].nombre);
                    dataList.push(this.films[i].value);
                  }
                  this.datacollectionBar= {
                    labels: labelList,
                    datasets: [
                      {
                        label: 'Data One',
                        backgroundColor: '#f87979',
                        data: dataList
                      }
                    ]
                  }
                });
              },
              getFilms2() {
                axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/mostTweeted')
                .then((film) => {
                  this.films2 = film.data;
                  let labelList2=[];
                  let dataList2=[];
                  for(var i=0; i<this.films.length; i++){
                    labelList2.push(this.films2[i].nombre);
                    dataList2.push(this.films2[i].numTweets);
                  }
                  this.datacollectionBar2= {
                  labels: labelList2,
                  datasets: [
                    {
                      label: 'Data Two',
                      backgroundColor: '#f87979',
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
                  }
    }
    }
</script>
<style>
  .small {
    max-width: 600px;
    margin:  150px auto;
  }
</style>
