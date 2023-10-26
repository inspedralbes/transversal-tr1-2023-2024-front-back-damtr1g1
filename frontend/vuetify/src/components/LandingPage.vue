<script setup>
import ArticleControlPanel from "./ArticleControlPanel.vue";
</script>

<script>
</script>

<template>
  <v-container>
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">
      Panell de Control
    </h1>
    <v-container>
      <v-row justify="center">
          <ArticleControlPanel :title="'Comandes'" :img="'order_icon.png'" :id="1"/>
          <ArticleControlPanel :title="'Productes'" :img="'product_icon.png'" :id="2"/>
          <ArticleControlPanel :title="'Estadístiques'" :img="'stats_icon.png'" :id="3"/>
      </v-row>
    </v-container>
  </v-container>
</template>
