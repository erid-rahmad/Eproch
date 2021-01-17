<template>
  <div class="app-container marketplace">
    <marketplace-catalog
      v-show="isProductCatalogView"
      @item-selected="showProductDetail"
    />
    <product-detail
      ref="itemDetail"
      v-show="isProductDetailView"
      :data="selectedItem"
      :origin="origin"
      @closed="onProductDetailClosed"
    />
    <shopping-cart
      ref="shoppingCart"
      v-show="isShoppingCart"
      @item-selected="showProductDetail"
      @closed="onShoppingCartClosed"
    />
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
