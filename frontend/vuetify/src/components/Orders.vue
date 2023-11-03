<script setup>
import OrdersToDo from "./OrdersToDo.vue";
</script>

<script>
export default {
  data: () => ({
    tab: null,
  }),
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
    <h1 class="text-center text-h2 my-10 mb-16 font-weight-bold">Comandes</h1>
    <v-container>
      <v-card
        style="background: linear-gradient(#9094e9, #b0b8f1)"
        class="text-center"
        elevation="4"
        rounded="xl"
      >
        <v-tabs v-model="tab" fixed-tabs bg-color="primary">
          <v-tab value="one" style="max-width: 100%">Comandes a fer</v-tab>
          <v-tab value="two" style="max-width: 100%">Comandes fetes</v-tab>
        </v-tabs>

        <v-card-text class="pa-0">
          <v-window v-model="tab">
            <v-window-item value="one"><OrdersToDo /></v-window-item>

            <v-window-item value="two"> Two </v-window-item>
          </v-window>
        </v-card-text>
      </v-card>
    </v-container>
  </v-container>
</template>
