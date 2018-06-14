<template>
    <div id="app-home">
        <v-container>
            <h1> Administrador </h1>
            <v-card width="300px" class="movie-card" hover :to="{name: 'films-admin'}">
              <v-card-title primary-title>
                  <div>
                      <h3 class="headline mb-0">Películas</h3>
                  </div>
              </v-card-title>
            </v-card>
            <v-card width="300px" class="movie-card" hover :to="{name: 'genre'}">
              <v-card-title primary-title>
                  <div>
                      <h3 class="headline mb-0">Géneros</h3>
                  </div>
              </v-card-title>
            </v-card>
        </v-container>
    </div>
</template>

<script>
    export default {
        name: 'admin'
    }
</script>
