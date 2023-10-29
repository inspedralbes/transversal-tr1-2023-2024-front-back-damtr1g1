<script setup>
import ModalBorrar from "./ModalBorrar.vue";
</script>

<script>
export default {
  props: ["data"],
  methods: {
    editQuestion: function (id) {
      fetch("http://localhost:4000/updatePreguntes", {
        method: "POST",
        mode: "cors",
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
        });
    },
  }
  //http://localhost:3001/api/image/peraConferencia.jpg
};
</script>
<template>
  <v-card
    width="400"
    elevation="4"
    align="center"
    justify="center"
    id="data.id"
  >
    <v-card-title>
      <div class="font-weight-bold ms-1 mb-2 text-truncate">
        {{ data.producteNom }}
      </div>
    </v-card-title>
    <v-img
      height="200"
      :src="'http://localhost:3001/api/image/' + data.imatgeNom"
      style="border-top: black"
    >
    </v-img>
    <v-container>
      <v-row no-gutters>
        <v-col>
          <v-sheet class="ma-2">
            <v-btn
              block
              class="bg-light-blue-lighten-3"
              @click="$router.push({ path: '/editar-pregunta/' + data.id })"
              >Editar</v-btn
            >
          </v-sheet>
        </v-col>
        <v-col>
          <v-sheet class="ma-2">
            <ModalBorrar :data="data" />
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>
