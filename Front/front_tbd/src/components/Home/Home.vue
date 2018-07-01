<template>
    <div id="app-home">
        <v-container>
            <h1> Página principal </h1>
            <p> Aqui deberiamos explicar en que consiste la página con unos dibujos, quizás hasta el diagrama de contexto</p>
            <AppShowTweets> </AppShowTweets>
        </v-container>
        
    </div>
    
</template>

<script>
    import AppShowTweets from '../ShowTweets/ShowTweet.vue'
    

    export default {
        name: 'app-home',
        components:{
            AppShowTweets
        }
    }
</script>