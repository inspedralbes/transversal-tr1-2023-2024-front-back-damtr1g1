<script>
export default {
  props: ["data"],
  methods: {
    deleteQuestion: function (id) {
      fetch(`http://localhost:3001/api/deleteProduct?idproducte=${id}`, {
        method: "POST",
        mode: "cors",
      })
        .then((response) => response.json())
        .then(() => {
          
        });
    },
  },
};
</script>

<template>
  <v-dialog width="1000">
    <template v-slot:activator="{ props }">
      <v-btn
        v-bind="props"
        class="v-btn v-btn--block v-btn--elevated v-theme--light v-btn--density-default v-btn--size-default v-btn--variant-elevated bg-red-lighten-2"
        text="Open Dialog"
      >
        eliminar
      </v-btn>
    </template>

    <template v-slot:default="{ isActive }">
      <v-card>
        <v-card-title class="text-center text-h5 font-weight-bold" style="white-space: break-spaces;">
          Estàs segur que vols eliminar "{{ data.nom }}" de la llista de
          productes?
        </v-card-title>
        <v-card-text>
          Recorda, un cop cliquis a 'Eliminar', aquest producte desapareixerà més ràpid que una pizza en una festa d'aniversari. La decisió és irreversible, així que assegura't que aquest producte hagi tingut una vida plena.
        </v-card-text>

        <v-spacer></v-spacer>
        <v-container align="center" justify="center">
          <v-row no-gutters>
            <v-col>
              <v-sheet class="ma-2">
                <v-btn
                  text="cancelar"
                  class=""
                  block
                  @click="isActive.value = false"
                ></v-btn>
              </v-sheet>
            </v-col>
            <v-col>
              <v-sheet class="ma-2">
                <v-btn
                  class="px-5 bg-red"
                  text="Eliminar"
                  block
                  @click="
                    isActive.value = false;
                    deleteQuestion(data.id);
                  "
                ></v-btn>
              </v-sheet>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
    </template>
  </v-dialog>
</template>
