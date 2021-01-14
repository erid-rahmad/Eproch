<template>
  <div class="app-container shopping-cart">
    <item-detail
      ref="itemDetail"
      v-show="showDetail"
      :data="selectedItem"
      origin="cart"
    />
    <div v-show="!showDetail">
      <h4>Shopping cart</h4>

      <el-row>
        <el-col :span="12">
          <el-checkbox v-model="checked">Select All</el-checkbox>
        </el-col>

        <el-col :span="12" align="end">
          <el-button
            type="danger"
            icon="el-icon-delete"
            plain
          >
            Remove All
          </el-button>
        </el-col>
      </el-row>

      <el-row
        v-for="itemCart in cart"
        :key="itemCart.vendorName"
        class="cart-item-group"
      >
        <el-col
          class="set-border"
          :span="24"
        >
          <el-row>
            <el-col :span="24">
              <el-checkbox v-model="checked">
                <em class="el-icon-s-shop"></em> {{ itemCart.vendorName }}
              </el-checkbox>
            </el-col>
          </el-row>

          <el-row
            v-for="(item, i) in itemCart.items"
            :key="i"
            class="cart-item"
          >
            <el-col :span="1">
              <el-checkbox v-model="checked" />
            </el-col>
            <el-col :span="2">
              <el-image
                class="image-thumbnail link"
                fit="contain"
                :src="item.product.cGallery.cGalleryItems[0].cAttachment.imageMedium"
                @click="getDetail(item.product)"
              />
            </el-col>

            <el-col :span="15">
              <el-row>
                <el-col :span="24">
                  <strong
                    class="link"
                    @click="getDetail(item.product)"
                  >
                    {{ item.product.name }}
                  </strong>
                </el-col>
                <el-col
                  class="currency"
                  :span="24"
                >
                  {{ item.product.price | formatCurrency('id') }}
                </el-col>
                <el-col :span="20">
                  <el-input
                    v-model="item.note"
                    size="mini"
                    placeholder="Notes"
                  />
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="1">
              <el-button
                icon="el-icon-delete"
                plain
                type="danger"
              />
            </el-col>
            <el-col :span="3">
              <el-input-number
                v-model="item.quantity"
                :min="1"
                size="mini"
                @change="handleChangeQty"
              />
            </el-col>
            <el-col :span="2">
              <strong style="color: #409eff">
                Rp. {{ (item.quantity * item.product.price) | formatCurrency('id') }}
              </strong>
            </el-col>
          </el-row>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-button
            icon="el-icon-goods"
            plain
            type="primary"
            @click="continueShopping"
          >
            Continue Shopping
          </el-button>
        </el-col>

        <el-col :span="12" align="end">
          <el-row>
            <el-col :span="17">
              <strong>Total</strong> : <strong style="color: #409eff">{{ grandTotal }}</strong>
            </el-col>
            <el-col :span="7">
              <el-button
                type="success"
                icon="el-icon-shopping-cart-full"
                plain
              >
                Purchase Requisition
              </el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script lang="ts" src="./shopping-cart.component.ts"></script>
<style lang="scss" scoped>
.shopping-cart {
  .currency {
    text-align: right;
  }

  .cart-item-group {
    padding: 0px 10px 10px 10px;

    .set-border {
      border: 1px dashed #d9d9d9;
      padding: 5px;
    }

    .cart-item {
      border-bottom: 1px dashed #d9d9d9;

      &:last-child {
        border-bottom: 0px;
      }

      .image-thumbnail {
        padding: 5px;
        height: 96px;
        display: block;
        margin-left: auto;
        margin-right: auto;
      }

      .link {
        cursor: pointer;
      }
    }
  }

  .el-row {
    margin-bottom: 16px;
  }
}
</style>
