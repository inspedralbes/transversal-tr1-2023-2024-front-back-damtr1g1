<script setup>
import DeleteDialog from "./DeleteDialog.vue";
</script>

<script>
const env = import.meta.env.VITE_NODE_ROUTE;

export default {
  props: ["data"],
  methods: {
    deleteProduct() {
      this.$emit("deleteProduct");
    },
  },
};
</script>
<template>
  <v-card elevation="4" rounded="xl">
    <v-card-title>
      <div class="font-weight-bold ms-1 mb-2 text-truncate">
        {{ data.nom }}
      </div>
    </v-card-title>
    <v-img
      height="200"
      :src="`${env}getImage/${data.imatgeNom}`"
      style="border-top: black"
    >
    </v-img>
    <v-container>
      <v-row no-gutters>
        <v-col>
          <v-sheet
            class="mx-3 pa-1"
            style="border: 1px solid black; border-radius: 5px"
            >{{ data.preu.toFixed(2) }}
            <v-icon size="x-small" class="mb-1">mdi-currency-eur</v-icon>
          </v-sheet>
        </v-col>
        <v-col>
          <v-sheet
            class="mx-3 pa-1"
            style="border: 1px solid black; border-radius: 5px"
            >{{ data.quantitat }} unitat/s</v-sheet
          >
        </v-col>
      </v-row>
      <v-divider class="border-opacity-25 my-4"></v-divider>
      <v-row no-gutters>
        <v-col>
          <v-sheet class="mx-2">
            <v-btn
              block
              rounded="xl"
              class="bg-light-blue-lighten-3"
              @click="$router.push({ path: '/editar-producte/' + data.id })"
              >Editar</v-btn
            >
          </v-sheet>
        </v-col>
        <v-col>
          <v-sheet class="mx-2">
            <DeleteDialog @deleteProduct="deleteProduct()" :data="data" />
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>
