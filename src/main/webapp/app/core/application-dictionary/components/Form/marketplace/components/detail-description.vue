<template>
  <div class="product-additional-info">
    <div
      v-if="content && contentType === 'html'"
      v-html="content"
    ></div>
    <div v-else-if="content">{{ content }}</div>
    <div v-else>{{ noContentMessage }}</div>
  </div>
</template>

<script lang="ts" src="./detail-description.component.ts"></script>
