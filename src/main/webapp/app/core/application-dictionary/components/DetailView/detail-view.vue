<template>
  <div
    class="detail-view"
  >
    <vue-form-json-schema
      class="main-form"
      v-if="this.loaded"
      v-model="model"
      :schema="schema"
      :ui-schema="uiSchema"
      :options="options"
      v-on:state-change="onChangeState"
      v-on:validated="onValidated"
    />
  </div>
</template>
<script lang="ts" src="./detail-view.component.ts"></script>
