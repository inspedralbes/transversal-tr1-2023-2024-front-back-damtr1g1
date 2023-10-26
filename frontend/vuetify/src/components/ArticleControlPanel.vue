<script setup>
</script>

<script>
</script>

<template>
  <v-container>
    <v-row justify="center">
      <v-col v-for="item in 3" :key="item" sm="6" md="4" lg="3" cols="12">
        <v-card
          style="background: linear-gradient(#9094e9, #b0b8f1)"
          class="text-center"
          elevation="4"
          rounded="xl"
        >
          <v-card-title>
            <div
              class="font-weight-bold text-center text-h3 py-6 text-truncate"
              style="color:#3D3976"
            >
              Comandes
            </div>
            <v-img
              height="200"
              src="../assets/img/order_icon.png"
              style="border-top: black"
            >
            </v-img>
            <v-btn class="my-4" height="50px" width="170px" rounded style="background: linear-gradient(to left, #E8321A, #FF7A68); color:white"
              >Entra
            </v-btn>
          </v-card-title>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
