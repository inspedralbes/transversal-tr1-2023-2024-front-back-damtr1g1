<script setup>
</script>

<script>


export default {
  data() {
    return {
      fotopath: 'http://localhost:3001/api/getImatgeEstadistiques/producteCantidad'
    };
  },
  methods: {
    executeStatistics() {
      fetch('http://localhost:3001/api/executeStatistics')
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          // Handle a successful response here
          var rand = Math.random(100000);
          console.log('Response from /api/executeStatistics:', data);
        
          this.fotopath = 'http://localhost:3001/api/getImatgeEstadistiques/producteCantidad?'+rand;
         //location.reload()
          // You can update your UI or perform other actions with the response data.
        })
        .catch(error => {
          // Handle errors here
          console.error('Error executing statistics:', error);
          // You can show an error message or perform error handling.
        });
    },
  },
};
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
            Estadístiques
          </div>
          <v-img
            height="600"
            :src="this.fotopath"
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
                @click="executeStatistics"
                >Actualitza
              </v-btn>
            </v-sheet>
          </v-container>
        </v-card-title>
      </v-card>
    </v-container>
  </v-container>
</template>
