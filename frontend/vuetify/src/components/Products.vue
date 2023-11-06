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
    addProductText: window.innerWidth < 1550 ? "+" : "NOU PRODUCTE",
  }),
  mounted() {
    window.onscroll = function () {
      if (window.scrollY > 327) {
        document.getElementById("search-bar").style.backgroundColor = "#9094e9";
      } else {
        try {
          document.getElementById("search-bar").style.backgroundColor =
            "transparent";
        } catch (e) {}
      }
    };
    this.carregant = true;
    window.addEventListener("resize", () => {
      this.onResize();
    });
    this.getProductes();
  },
  methods: {
    getProductes() {
      
      fetch(import.meta.env.VITE_NODE_ROUTE + "getProducts")
        .then((response) => response.json())
        .then((data) => {
          this.productes_originals = data;
          this.productes = data;
          this.carregant = false;
          console.log(data);
        });
    },
    deleteProduct() {
      this.getProductes()
    },
    onResize() {
      if (window.innerWidth < 1550) {
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
    <v-btn
      variant="tonal"
      icon="mdi-arrow-left"
      class="mt-5"
      @click="$router.push('/')"
    ></v-btn>
    <h1 class="text-center text-h2 mt-10 font-weight-bold">Productes</h1>
    <v-row
      id="search-bar"
      class="mt-16 mx-0"
      style="position: sticky; top: 0; z-index: 100; border-radius: 20px"
    >
      <v-col cols="2">
        <v-sheet style="background-color: transparent">
          <v-btn
            class="bg-light-green-lighten-2 text-h6"
            height="65px"
            rounded="xl"
            block
            @click="$router.push('/afegir-producte')"
            >{{ addProductText }}</v-btn
          >
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet style="background-color: transparent">
          <v-toolbar
            dense
            floating
            style="
              background: #ffaa9f;
              border: 1px solid rgba(112, 112, 112, 0.5);
            "
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
            <Product v-on:deleteProduct="deleteProduct" :data="producte" />
          </v-col>
        </v-row>
      </v-sheet>
    </v-card>
  </v-container>
</template>
