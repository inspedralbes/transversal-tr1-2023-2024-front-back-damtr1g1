<script setup></script>

<script>
export default {
  props: ["title", "img", "id"],
  methods: {
    animation() {
      document.getElementById(this.id).className = "control-panel__animation";
    },
  },
};
</script>

<template>
  <div>
    <v-row class="pl-3 pt-2">
      <v-col
        cols="2"
        align="center"
        class="pa-2"
        style="
          background-color: #b0b8f1;
          max-height: 500px;
          max-width: 120px;
          overflow-y: auto;
          border-right: solid 1px rgba(90, 90, 90, 0.466);
        "
      >
        <v-list-item
          v-for="i in 5"
          class="mb-2"
          style="background-color: #7875df; width: 100px; height: 100px"
          rounded="xl"
          link
        >
          <v-row align="center">
            <v-col cols="4">
              {{ i }}
            </v-col>
            <v-divider vertical class="border-opacity-100"></v-divider>
            <v-col>
              <div>
                <p>
                  3
                  <v-icon size="x-small">mdi-food-drumstick</v-icon>
                </p>
              </div>
              <p>13:55</p>
            </v-col>
          </v-row>
        </v-list-item>
      </v-col>
      <v-col cols="16">
        <v-switch v-model="showMessages" label="Show messages"></v-switch>
      </v-col>
    </v-row>
  </div>
</template>
