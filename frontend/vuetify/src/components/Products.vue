<script setup>
import Product from "./Product.vue";
</script>

<script>
export default {
  data: () => ({
    productes: "",
    productes_originals: "",
    busqueda: "",
    carregant: false,
    addProductText: window.innerWidth < 1100 ? "+" : "NOU PRODUCTE",
  }),
  mounted() {
    this.carregant = true;
    window.addEventListener("resize", () => {
      this.onResize()
    });
    fetch("http://localhost:3001/api/getProducts")
      .then((response) => response.json())
      .then((data) => {
        this.productes_originals = data;
        this.productes = data;
        this.carregant = false;
        console.log(data);
      });
  },
  methods: {
    onResize() {
      if (window.innerWidth < 1100) {
        this.addProductText = "+";
      } else {
        this.addProductText = "NOU PRODUCTE";
      }
    },
  },
  watch: {
    busqueda: function () {
      this.productes = this.productes_originals.filter((productes) => {
        return (productes = productes.nom
          .toLowerCase()
          .match(this.busqueda.toLowerCase()));
      });
    },
  },
};
</script>

<template>
  <v-container class="order-container">
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">Productes</h1>
    <v-row>
      <v-col cols="2">
        <v-sheet style="background-color: transparent">
          <v-btn
            class="bg-light-green-lighten-2 text-h6"
            height="63px"
            rounded="xl"
            v-text="addProductText"
            block
            @click="$router.push('/afegir-producte')"
          ></v-btn>
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet style="background-color: transparent">
          <v-toolbar
            dense
            floating
            style="background: #ffaa9f"
            class="mb-3"
            rounded="xl"
          >
            <v-text-field
              class="mt-1 ml-5"
              variant="plain"
              style="font-weight: 700"
              placeholder="Busca..."
              v-model="busqueda"
            ></v-text-field>
            <v-btn icon>
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </v-toolbar>
        </v-sheet>
      </v-col>
    </v-row>

    <v-card
      style="background: linear-gradient(#9094e9, #b0b8f1)"
      class="text-center"
      elevation="4"
      rounded="xl"
    >
      <v-sheet style="background-color: transparent">
        <v-row class="mx-2 my-2">
          <h1 v-if="carregant == true" class="py-16 text-center w-100">
            Carregant productes...
          </h1>
          <div
            v-if="productes.length == 0 && carregant == false"
            class="py-16 text-center w-100"
          >
            <h1>No hi han productes amb aquest nom</h1>
          </div>
          <v-col
            v-for="producte in productes"
            :key="producte.id"
            sm="6"
            md="4"
            lg="3"
            cols="12"
          >
            <Product :data="producte" />
          </v-col>
        </v-row>
      </v-sheet>
    </v-card>
  </v-container>
</template>
