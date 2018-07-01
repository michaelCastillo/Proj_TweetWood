<template>
    <v-app dark id="app-home">
        <v-container>
            <h1> Página principal </h1>
            <p> Aqui deberiamos explicar en que consiste la página con unos dibujos, quizás hasta el diagrama de contexto</p>
            <v-flex xl4 lg4 md4 sm12 xs12 elevation-12>
            </v-flex>
            <v-flex xl8 lg8 md8 sm12 xs12 elevation-12>
              <BarChart :chart-data="datacollectionBar"></BarChart>
            </v-flex>
        </v-container>
    </v-app>
</template>

<script>
    import BarChart from './BarChart.js';
    export default {
      components:{BarChart},
      name: 'app-home',
    mounted(){
      this.getFilms();

    },
    data: function () {
        return {
            films: null,
            poster: 'https://image.tmdb.org/t/p/w342/',
            datacollectionBar: null
        }
    },
    getFilms() {
        axios.get('http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/peliculas/mostValue')
            .then((film) => {
                this.films = film.data;
                let tam = this.films.length-1;
                for(var i=0; i<tam; i++){
                  this.films[i].value = this.redondeo2decimales(this.films[i].value);
                }
                let desaprobacion=100-this.films.value;
                desaprobacion = this.redondeo2decimales(desaprobacion);
                this.datacollectionBar= {
                  labels: ['January', 'February'],
                  datasets: [
                    {
                      label: 'Data One',
                      backgroundColor: '#f87979',
                      data: [40, 20]
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
</script>
