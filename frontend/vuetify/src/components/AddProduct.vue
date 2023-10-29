<script setup></script>

<script>
export default {
  data: function () {
    return {
      data: {
        producte_Categoria: "",
        producte_Nom: "",
        producte_Definicio: "",
        producte_Preu: 0,
        producte_Quantitat: 0,
        imageNom: "",
      },
    };
  },
  methods: {
    cancelar() {
      this.$router.push("/productes");
    },
    afegirPregunta() {
      fetch(
        `http://localhost:3001/addProduct?imatge=${
          this.data.imageNom
        }&producteCategoria=${this.data.poster}&producteDefinicio=${
          this.data.answers
        }&producteNom=${this.data.correctIndex - 1}&productePreu=${
          this.data.producte_Preu
        }&producteQuantitat=${this.data.producte_Quantitat}`,
        {
          method: "POST",
          mode: "cors",
        }
      )
        .then((response) => response.json())
        .then((data) => {
          this.$router.push("/");
        });
    },
  },
};
</script>

<template>
  <div>
    <h1 class="text-center text-h2 my-16 pt-10 font-weight-bold">
      Agregar producte
    </h1>
    <v-sheet class="grid" align="center" style="background-color: transparent">
      <v-card
        class="bg-grey-lighten-2"
        width="500"
        height="600"
        elevation="6"
        align="center"
        justify="center"
      >
        <v-container width="400">
          <v-text-field
            variant="outlined"
            type="text"
            width="100px"
            class="font-weight-bold text-center"
            placeholder="Nom del producte"
            v-model="data.title"
          />
        </v-container>
        <v-card
          class="mx-4"
          height="300"
          elevation="4"
          align="center"
          justify="center"
          rounded="l"
        >
          <v-img :width="400" aspect-ratio="1/1" cover :src="data.poster">
            <input
              type="file"
              class="mt-10"
              placeholder="Afegir enllaç de la imatge"
            />
          </v-img>
        </v-card>
        <v-container width="400">
          <v-textarea
            rows="2"
            variant="outlined"
            placeholder="Descipció del producte"
            v-model="data.correctIndex"
            auto-grow
          />
        </v-container>
      </v-card>
      <v-sheet width="500" style="background-color: transparent">
        <v-row no-gutters>
          <v-col>
            <v-sheet class="mr-2 mt-4" style="background-color: transparent">
              <v-btn block class="bg-white" height="60" @click="cancelar()"
                >Cancelar</v-btn
              >
            </v-sheet>
          </v-col>
          <v-col>
            <v-sheet class="ml-2 mt-4" style="background-color: transparent">
              <v-btn
                block
                class="bg-black"
                height="60"
                @click="afegirPregunta()"
                >Afegir pregunta</v-btn
              >
            </v-sheet>
          </v-col>
        </v-row>
      </v-sheet>
    </v-sheet>
  </div>
</template>
