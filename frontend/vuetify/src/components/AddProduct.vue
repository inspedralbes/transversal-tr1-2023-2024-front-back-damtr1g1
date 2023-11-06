<script setup></script>

<script>
export default {
  data: function () {
    return {
      llistat_categories: [],
      ids_categories: [],
      dialog: false,
      imgPreview: null,
      data: {
        categoria: null,
        nom: "",
        definicio: "",
        preu: "",
        quantitat: "",
        img: null,
      },
    };
  },
  mounted() {
    fetch(import.meta.env.VITE_NODE_ROUTE + "getCategoria")
      .then((response) => response.json())
      .then((data) => {
        for (let i = 0; i < data.length; i++) {
          this.llistat_categories.push(data[i].nom);
          this.ids_categories.push(data[i].id);
        }
      });
  },
  methods: {
    afegirPregunta() {
      if (
        this.data.categoria !== null &&
        this.data.nom !== "" &&
        this.data.definicio !== "" &&
        this.data.preu !== "" &&
        this.data.quantitat !== "" &&
        this.data.img !== null
      ) {
        for (let i = 0; i < this.llistat_categories.length; i++) {
          if (this.llistat_categories[i] == this.data.categoria) {
            this.data.categoria = this.ids_categories[i];
          }
        }

        let formData = new FormData();
        formData.append("img", this.data.img);
        fetch(
          import.meta.env.VITE_NODE_ROUTE + `addProduct?imatgeNom=${
            this.data.img.name
          }&categoria=${this.data.categoria}&definicio=${
            this.data.definicio
          }&nom=${this.data.nom}&preu=${this.data.preu}&quantitat=${
            this.data.quantitat
          }`,
          {
            method: "POST",
            mode: "cors",
            body: formData,
          }
        )
          .then((response) => response.json())
          .then((data) => {
            this.$router.push("/productes");
          });
      } else {
        console.log(this.data);
        this.dialog = true;
      }
    },
    onFileChange(e) {
      const file = e.target.files[0];
      if (file != undefined) {
        console.log(file);
        this.data.img = file;
        this.imgPreview = URL.createObjectURL(file);
      } else {
        this.imgPreview = null;
        this.data.img = null;
      }
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
      @click="$router.push('/productes')"
    ></v-btn>
    <h1 class="text-center text-h2 mb-16 mt-8 font-weight-bold">
      Afegir Producte
    </h1>
    <v-sheet class="grid" align="center" style="background-color: transparent">
      <v-card
        style="background-color: #ced4f7"
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
            class="font-weight-bold text-center agregarNomProducte"
            label="Nom del producte"
            v-model="data.nom"
            :rules="[() => !!data.nom || 'Camp obligatori!']"
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
          <v-img :width="400" :src="imgPreview">
            <input
              @change="onFileChange"
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
            label="Descipció del producte"
            v-model="data.definicio"
            :rules="[() => !!data.definicio || 'Camp obligatori!']"
            auto-grow
          />
          <v-row gutters>
            <v-col cols="4">
              <v-text-field
                label="Preu"
                suffix="€"
                variant="outlined"
                type="number"
                width="100px"
                class="font-weight-bold text-center agregarNomProducte"
                v-model="data.preu"
                placeholder="10,00"
                :rules="[() => !!data.preu || 'Camp obligatori!']"
              />
            </v-col>

            <v-col cols="4">
              <v-text-field
                label="Quantitat"
                suffix="unitats"
                variant="outlined"
                type="number"
                width="100px"
                class="font-weight-bold text-center agregarNomProducte"
                :rules="[() => !!data.quantitat || 'Camp obligatori!']"
                placeholder="20"
                v-model="data.quantitat"
              />
            </v-col>
            <v-col cols="4">
              <v-autocomplete
                variant="outlined"
                v-model="data.categoria"
                :rules="[() => !!data.categoria || 'Camp obligatori!']"
                :items="this.llistat_categories"
                label="Categoria"
                placeholder="Escull..."
              ></v-autocomplete>
            </v-col>
          </v-row>
        </v-container>
      </v-card>
      <v-sheet width="500" style="background-color: transparent">
        <v-row no-gutters>
          <v-col>
            <v-sheet class="mr-2 mt-4" style="background-color: transparent">
              <v-btn
                block
                class="bg-white"
                height="60"
                @click="this.$router.push('/productes')"
                >Cancelar</v-btn
              >
            </v-sheet>
          </v-col>
          <v-col>
            <v-sheet class="ml-2 mt-4" style="background-color: transparent">
              <v-btn
                block
                class="bg-light-green-lighten-2"
                height="60"
                @click="afegirPregunta()"
                >Afegir producte</v-btn
              >
            </v-sheet>
          </v-col>
        </v-row>
      </v-sheet>

      <v-dialog persistent v-model="dialog" width="auto">
        <v-card>
          <v-card-text> Tots els camps son obligatoris! </v-card-text>
          <v-card-actions>
            <v-btn color="red" block @click="dialog = false">Tanca</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-sheet>
  </v-container>
</template>
