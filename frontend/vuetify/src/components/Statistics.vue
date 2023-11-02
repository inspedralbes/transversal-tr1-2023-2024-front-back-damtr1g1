<script setup>
</script>

<script>
</script>

<template>
  <v-container class="order-container">
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">
      Estadístiques
    </h1>
    <v-container>
      <v-card
        style="
          background: linear-gradient(#9094e9, #b0b8f1);
          
        "
        class="text-center"
        elevation="4"
        rounded="xl"
      >
        <v-card-title>
          <div
            class="font-weight-bold text-center text-h3 py-6 text-truncate"
            style="color: #3d3976"
          >
            Les comandes
          </div>
          <v-img
            height="200"
            src="/src/assets/img/order_icon.png"
          >
          </v-img>
          <v-container align="center" justify="center">
            <v-sheet class="ma-2" style="background: transparent">
              <v-btn
                class="my-4"
                height="50px"
                width="170px"
                rounded
                style="
                  background: linear-gradient(to left, #e8321a, #ff7a68);
                  color: white;
                "
                >Entra
              </v-btn>
            </v-sheet>
          </v-container>
        </v-card-title>
      </v-card>
    </v-container>
  </v-container>
</template>
