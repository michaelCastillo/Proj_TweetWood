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
    import la from './data/world.json';

    export default {
        name: 'mapGraph',
        data(){
            return{
               popData: [
                    {id: "AFG", population: 4830620}, 
                    {id: "AL", population: 733375},
                    {id: "BRA", population: 8000000}
                ]
            }
        },
        mounted(){
            this.loadMap();
        },
        methods:{
            loadMap(){
                var chart = new d3plus.Geomap()
                    .data(this.popData)
                    .groupBy("id")
                    .select("#mapa")
                    .colorScale("population");

                chart
                  .topojson(la)
                  .fitFilter(function(d) {
                        return ["AFG", "AL", "BR"].indexOf(d.id) < 0;
                    });

                chart.render();
            }
        }
    }
</script>

<style>
#mapa{
    width: 1000px;
    height: 600px;
    background-color: white;
}
</style>