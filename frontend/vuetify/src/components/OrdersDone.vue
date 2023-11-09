<script setup></script>

<script>
export default {
  props: ["title", "img", "id"],
  data: () => ({
    jsonComandes: {},
  }),
  mounted() {
    fetch(import.meta.env.VITE_NODE_ROUTE + "getComandesFinalitzades")
      .then((response) => response.json())
      .then((data) => {
        this.jsonComandes = data;
        console.log(this.jsonComandes);
      });
  },
};
</script>

<template>
  <div>
    <v-table
      fixed-header
      height=""
      style="background-color: transparent; color: black; font-weight: bold"
    >
      <thead class="text-h4">
        <tr>
          <th class="text-center">Id</th>
          <th class="text-center">Nom d'usuari</th>
          <th class="text-center">Hora d'encarrec</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="comanda in jsonComandes" :key="comanda" class="text-h6">
          <td align="center">
            <div
              class="text-center"
              style="
                background-color: #7472de;
                border: 1px solid #3d3976;
                width: 35px;
                height: 35px;
                border-radius: 20px;
              "
            >
              {{ comanda.id }}
            </div>
          </td>
          <td>{{ comanda.usuari }}</td>
          <td>{{ comanda.data_comanda }}</td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>
