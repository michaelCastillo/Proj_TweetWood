<template>
    <div id="app-nav">
        <v-navigation-drawer v-model="drawer" app temporary >
            <v-list dense>
                <v-subheader>TweetWood</v-subheader>
                <v-list-tile to="/">
                    <v-list-tile-action><v-icon>home</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title >Home</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile to="/films">
                    <v-list-tile-action><v-icon>local_movies</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Films</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-divider></v-divider>
                <v-list-tile to="/graph">
                    <v-list-tile-action><v-icon>share</v-icon></v-list-tile-action>
                    <v-list-tile-content><v-list-tile-title>Graph</v-list-tile-title></v-list-tile-content>
                </v-list-tile>
                <v-divider></v-divider>
                <v-subheader>Administrator</v-subheader>
                <v-list-tile to="/admin">
                        <v-list-tile-action><v-icon>settings</v-icon></v-list-tile-action>
                        <v-list-tile-content><v-list-tile-title>Administrator</v-list-tile-title></v-list-tile-content>
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
    .app-title {
        
    }
</style>
