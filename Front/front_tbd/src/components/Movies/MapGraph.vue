<template>
    <v-container fluid grid-list-xs>
        <h2 class="map-title">Mapa de calor de Santiago de Chile.</h2>
        <v-layout row wrap justify-center>
            <v-flex xs8 offset-xs-4 >
                <div id="mapa"></div>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import * as d3plus from 'd3plus';
    import la from './data/la.json';

    export default {
        name: 'mapGraph',
        data(){
            return{
               
            }
        },
        mounted(){
            this.loadMap();
        },
        methods:{
            loadMap(){
                var chart = new d3plus.Geomap()
                  
                  .groupBy("id")
                  .select("#mapa")
                  .colorScale("Aprobación");

                chart
                  .topojson(la);

                chart.render();
            }
        }
    }
</script>

<style>
#mapa{
    width: 1000px;
    height: 600px;
}
</style>