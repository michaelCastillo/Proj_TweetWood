<template>
    <v-parallax src="https://themes.muffingroup.com/be/parallax/wp-content/uploads/2015/01/home-parallax-section-works.jpg" height=650>
    <v-layout
      align-center
      column
      justify-center>
      <h1 class="display-4 font-weight-thin mb-3">TweetWood</h1>
      <h4 class="title">Análisis de las últimas películas comentadas en Twitter!</h4>
      <br><br>
        <div class="text-xs-center">
            <v-tooltip bottom>
                <v-btn @click="goToFilms" slot="activator" color="purple" fab large dark>
                    <v-icon dark :size="icon_size">local_movies</v-icon>
                </v-btn>
                <span>Peliculas</span>
            </v-tooltip>
            <v-tooltip bottom>
                <v-btn @click="goToGraph" slot="activator" fab dark large color="purple">
                    <v-icon dark :size="icon_size">share</v-icon>
                </v-btn>
                <span>Grafo</span>
            </v-tooltip>
            <v-tooltip bottom>
                <v-btn @click="goToMap" slot="activator" fab dark large color="purple">
                    <v-icon dark :size="icon_size">map</v-icon>
                </v-btn>
                <span>Mapa</span>
            </v-tooltip>
            <v-tooltip bottom>
                <v-btn @click="goToCharts" slot="activator" fab dark large color="purple">
                    <v-icon dark :size="icon_size">insert_chart</v-icon>
                </v-btn>
                <span>Graficos</span>
            </v-tooltip>
        </div>
    </v-layout>
    </v-parallax>
</template>
<script>
export default {
    data: ()=> ({
        hidden: false,
        icon_size: "50px"
    }),
    methods:
    {
        goToFilms()
        {
            this.$router.push({name: 'app-movies'});
        },
        goToGraph()
        {
            this.$router.push({name: 'grafo'});
        },
        goToMap()
        {
            this.$router.push({name: 'map'});
        },
        goToCharts()
        {
            this.$router.push({name: 'app-home'});
        }
    }
}
</script>

<style scoped>
h4{
    margin: 25px 5px;
}
</style>