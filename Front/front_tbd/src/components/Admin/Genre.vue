<template>
  <div id="app-home">
    <v-container>
      <h1>Géneros</h1>
      <v-card width="300px" class="movie-card" hover :to="{name: 'new-genre'}">
        <v-card-title primary-title>
            <div>
                <h3 class="headline mb-0">Nuevo género</h3>
            </div>
        </v-card-title>
      </v-card>
    </v-container>
  </div>
</template>

<script>
  export default
  {
    name: 'genre',
    data: function()
    {
      return
      {
        genres: null
      }
    },
    methods:
    {
      onNewGenre() {
        this.$router.push({name: 'new-genre'});
      }
    }
  }
</script>
