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
          <ArticleControlPanel :title="'Comandes'" :img="'order_icon.png'" :path="'/comandes'"/>
          <ArticleControlPanel :title="'Productes'" :img="'product_icon.png'" :path="'/productes'"/>
          <ArticleControlPanel :title="'Estadístiques'" :img="'stats_icon.png'" :path="'/estadistiques'"/>
      </v-row>
    </v-container>
  </v-container>
</template>
