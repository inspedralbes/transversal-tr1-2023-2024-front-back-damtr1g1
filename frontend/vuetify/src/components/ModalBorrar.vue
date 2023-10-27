<script>
export default {
  props: ["data"],
  methods: {
    deleteQuestion: function (id) {
      fetch(`http://localhost:4000/deletePregunta?id=${id}`, {
        method: "POST",
        mode: "cors",
      })
        .then((response) => response.json())
        .then((data) => {
          window.location.reload();
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
        Esborrar
      </v-btn>
    </template>

    <template v-slot:default="{ isActive }">
      <v-card>
        <v-card-title class="text-center text-h5 font-weight-bold">
          Estàs segur que vols esborrar {{ data.title }} de la llista de
          pel·lícules?
        </v-card-title>
        <v-card-text>
          Atenció! Si elimines aquesta pel·lícula, perdràs l'oportunitat
          d'endevinar l'any en el proper concurs de cinèfils."
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
                  text="Esborrar"
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
