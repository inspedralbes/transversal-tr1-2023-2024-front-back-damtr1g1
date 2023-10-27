<script setup>
import Product from "./Product.vue";

</script>

<script>
export default {
  data: () => ({
    productes: "",
  }),
  mounted() {
    fetch("http://localhost:3001/api/selectProducte")
      .then((response) => response.json())
      .then((data) => {
        this.productes = data;
        console.log(data);
      });
  },
};
</script>

<template>
  <v-container class="order-container">
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">Productes</h1>
    <v-container>
      <v-card
        style="background: linear-gradient(#9094e9, #b0b8f1)"
        class="text-center"
        elevation="4"
        rounded="xl"
      >
        <v-sheet class="d-flex flex-wrap ml-10">
          <v-sheet v-for="producte in productes" class="ma-5">
            <v-row justify="space-around">
              <Product :data="producte" />
            </v-row>
          </v-sheet>
        </v-sheet>
      </v-card>
    </v-container>
  </v-container>
</template>
