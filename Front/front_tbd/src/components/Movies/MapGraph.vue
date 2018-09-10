<template>
    <v-container fluid grid-list-xs>
        <v-flex xs8 offset-xs2> 
            <h2 class="map-title">Mapa cloroplético de América Latina de aprobación/rechazo de un género.</h2>
            <div v-if="generos !== null">
                <v-select dark :items="generos" item-text="nombre" item-value="id" @change="loadMaps" placeholder="Selecciona un género aquí" label="Géneros" prepend-icon="movie"></v-select>
            </div>
        </v-flex>
        <div v-if="dataAprobacion !== null">
            <v-layout>
                <v-flex xs4 offset-xs2>
                    <div id="mapa1"></div>
                </v-flex>
                <v-flex xs6>
                    <div id="mapa2"></div>
                </v-flex>
            </v-layout>
        </div>
        <div v-else>
            <h2 class="map-title">Selecciona una categoría!</h2>
        </div>
    </v-container>
</template>

<script>
    import * as d3plus from 'd3plus';
    //import la from './data/world.json';
    import la from './data/la.json';
    import axios from 'axios';

    export default {
        name: 'mapGraph',
        data(){
            return{
                /*
               popData: [
                    {id: "AFG", data: 4830620}, 
                    {id: "AL", data: 733375},
                    {id: "BRA", data: 8000000},
                    {id: "USA", data: 10000000}
                ]
                */
                url: 'http://167.99.155.164:8080',
                chart: null,
                chart2: null,
                generos: [],
                dataAprobacion: null,
                dataRechazo: null
            }
        },
        beforeMount(){
            this.load();
        },
        updated(){
            this.loadMapAprobacion();
            this.loadMapRechazo();
        },
        mounted(){

            this.getGeneros();
            
        },
        methods:{
            getGeneros(){
                axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/generos')
                    .then((genres)=>{
                        var data = genres.data;
                        for(var i = 0; i < data.length; i++)
                        {
                            var genre = {
                                id: data[i].id,
                                nombre: data[i].nombre
                            }

                            this.generos.push(genre);
                        }
                      });
            },
            getData(value){
                axios.get(this.url+'/tweetwood_back-0.0.1-SNAPSHOT/generos/' + value + '/getHeat')
                    .then((heat)=>{
                        this.popData = [];
                        var newData = [];
                        var newData1 = [];
                        var data = heat.data;
                        for(var i = 0; i < data.length; i++)
                        {
                            var pais = {
                                id: data[i].id_str,
                                data: data[i].data * 0.7
                            }

                            var pais1 = {
                                id: data[i].id_str,
                                data: data[i].data * 0.3
                            }

                            newData.push(pais);
                            newData1.push(pais1);

                        }
                        this.dataAprobacion = newData;
                        this.dataRechazo = newData1;
                      });

                // this.chart.data(this.dataAprobacion);
                // this.chart2.data(this.dataRechazo);
            },
            loadMapAprobacion(){
                this.chart
                    .data(this.dataAprobacion)
                    .groupBy("id")
                    .select("#mapa1")
                    .colorScale("data")
                    .colorScalePosition("right");

                this.chart
                    .colorScaleConfig({color: ["#98e08d", "#71c164", "#4c9b3f", "#246619", "#124409"]})
                    .topojson(la)

                this.chart.render();
            },
            loadMapRechazo(){
                this.chart2
                    .data(this.dataRechazo)
                    .groupBy("id")
                    .select("#mapa2")
                    .colorScale("data")
                    .colorScalePosition("right");

                this.chart2
                    .colorScaleConfig({color: ["#630e0e", "#721e1e", "#993b3b", "#c66161", "#db8181"]})
                    .topojson(la)

                this.chart2.render();
            },
            load(){
                this.chart = new d3plus.Geomap();
                this.chart2 = new d3plus.Geomap();
            },
            loadMaps(value){
                this.getData(value);
            }
        }
    }
</script>

<style>
.map-title{
    text-align: center;
    color: white;
}



#mapa1{
    width: 500px;
    height: 520px;
    background-color: white;
}
#mapa2{
    width: 500px;
    height: 520px;
    background-color: white;
}
</style>