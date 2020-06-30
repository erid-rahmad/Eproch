<template>
  <component :is="component" v-bind="{baseApiUrl, model, list, listKey}"/>
</template>
<script lang="ts" src="./tree-item.component.ts"></script>
