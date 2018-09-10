<template>
    <div id="app-nav">
        <v-navigation-drawer dark v-model="drawer" app temporary >
            <v-list dense>
                <v-subheader>TweetWood</v-subheader>
                <v-list-tile to="/">
                    <v-list-tile-action><v-icon>home</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title >Página Principal</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-list-tile to="/films">
                    <v-list-tile-action><v-icon>local_movies</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Películas</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-list-tile to="/charts">
                    <v-list-tile-action><v-icon>far fa-chart-bar</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Gráficos</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-list-tile to="/graph">
                    <v-list-tile-action><v-icon>share</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Grafo</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                 <v-list-tile to="/map">
                    <v-list-tile-action><v-icon>map</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Mapa</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-divider></v-divider>
                <v-subheader>Administrator</v-subheader>
                <v-list-tile to="/login">
                        <v-list-tile-action><v-icon>settings</v-icon></v-list-tile-action>
                        <v-list-tile-content><v-list-tile-title>Administrador</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-navigation-drawer>
        <v-toolbar app dark>
            <v-toolbar-side-icon v-on:click="drawer=!drawer" ></v-toolbar-side-icon>
            <v-toolbar-title class="app-title">TweetWood</v-toolbar-title>
            <v-spacer></v-spacer>
        </v-toolbar>
    </div>
</template>

<script>
    export default {
        name: 'app-nav',
        data: () => ({
            drawer: false
        }),
        props: {
            source: String
        }
    }
</script>

<style>
</style>
