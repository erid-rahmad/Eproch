<template>
  <div class="app-container marketplace">
    <marketplace-catalog
      v-show="browseCatalog"
      @item-selected="showProductDetail"
    />
    <item-detail
      ref="itemDetail"
      v-show="!browseCatalog"
      :data="selectedItem"
      origin="catalog"
    />
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
