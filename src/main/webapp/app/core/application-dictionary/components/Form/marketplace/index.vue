<template>
  <div class="app-container marketplace">
    <marketplace-catalog
      v-show="isProductCatalogView"
      @item-selected="showProductDetail"
    ></marketplace-catalog>
    <product-detail
      ref="itemDetail"
      v-show="isProductDetailView"
      :data="selectedItem"
      :origin="origin"
      @closed="onProductDetailClosed"
      @compared="onProductCompared"
    ></product-detail>
    <shopping-cart
      ref="shoppingCart"
      v-show="isShoppingCart"
      @item-selected="showProductDetail"
      @closed="onShoppingCartClosed"
    ></shopping-cart>
    <el-dialog
      :visible.sync="showComparisonWindow"
      title="Compare Products"
      width="80%"
    >
      <div slot="title">
        <span>Compare Product </span>
        <el-button
          size="mini"
          style="margin-left: 8px"
          type="primary"
          @click="clearComparisonList"
        >
          Clear
        </el-button>
      </div>
      <comparison-window/>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
