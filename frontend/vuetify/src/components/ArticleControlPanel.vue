<script setup></script>

<script>
export default {
  props: ["title", "img", "path"],
};
</script>

<template>
  <v-col sm="6" md="4" lg="3" cols="12">
    <v-card
      style="background: linear-gradient(#9094e9, #b0b8f1)"
      class="text-center"
      elevation="4"
      rounded="xl"
    >
      <v-card-title>
        <div
          class="font-weight-bold text-center text-h3 py-6 text-truncate"
          style="color: #3d3976"
        >
          {{ title }}
        </div>
        <v-img height="200" :src="`/img/${ img }`"> </v-img>
        <v-container align="center" justify="center">
          <v-sheet class="ma-2" style="background: transparent">
            <transition>
              <v-btn
                class="my-4"
                height="50px"
                width="170px"
                rounded
                @click="$router.push({ path: path })"
                style="
                  background: linear-gradient(to left, #e8321a, #ff7a68);
                  color: white;
                "
                >Entra
              </v-btn>
            </transition>
          </v-sheet>
        </v-container>
      </v-card-title>
    </v-card>
  </v-col>
</template>
