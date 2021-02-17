<template>
  <el-popover
    v-if="hasAccess"
    v-model="visible"
    class="quick-cart"
    width="480"
  >
    <div
      v-if="cartItems.length"
      class="content"
    >
      <el-row
        v-for="item in cartItems"
        :key="item.id"
        :gutter="8"
      >
        <el-col :span="6">
          <el-image
            class="image-thumbnail link"
            fit="contain"
            :src="item.product.cGallery.cGalleryItems[0].cAttachment.imageSmall"
          />
        </el-col>
        <el-col :span="14">
          <div class="product-name">{{ item.product.name }}</div>
          <div class="product-price">Rp {{ item.product.price | formatCurrency('id') }}</div>
        </el-col>
        <el-col :span="4">
          <div class="item-quantity">Qty: {{ item.quantity }}</div>
          <el-button
            icon="el-icon-delete"
            plain
            size="mini"
            type="danger"
            @click="removeItem(item)"
          />
        </el-col>
      </el-row>
      <el-row
        class="summary"
        :gutter="8"
      >
        <el-col :span="12">
          Total ({{ cartItems.length }}): <span style="color: #409eff; font-weight: 600">Rp {{ subtotal | formatCurrency('id') }}</span>
        </el-col>
        <el-col
          :span="12"
          style="text-align: right"
        >
          <el-button
            size="mini"
            type="primary"
            @click="showCart"
          >
            Show Cart
          </el-button>
        </el-col>
      </el-row>
    </div>
    <div
      v-else
      class="empty-cart"
    >
      <span>There is no item yet</span>
    </div>
    <el-badge
      slot="reference"
      :hidden="cartItems.length === 0"
      :value="cartItems.length"
      class="item-quantity-badge"
    >
      <svg-icon name="shopping"/>
    </el-badge>
  </el-popover>
</template>
<script lang="ts" src="./quick-cart.component.ts"></script>
<style lang="scss" scoped>
.content {
  .item-quantity {
    margin: 3px 0;
  }

  .product-name {
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    height: 1.8em;
    line-height: 1.8em;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
  }

  .product-price {
    font-weight: 600;
  }

  .summary {
    padding-top: 10px;
    border-top: 1px solid #dedede;
  }
}
.empty-cart {
  text-align: center;
}
</style>
<style lang="scss">
.item-quantity-badge .el-badge__content {
  margin-top: 12px;
}
</style>
