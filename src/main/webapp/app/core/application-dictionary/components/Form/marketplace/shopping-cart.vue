<template>
  <div class="shopping-cart">
    <h4>Shopping cart</h4>
    <el-card>
      <el-row>
        <el-col :span="12">
          <el-checkbox v-model="checked">Select All</el-checkbox>
        </el-col>
        <el-col :span="12" align="end">
          <el-button
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="removeAll"
          >
            Remove All
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card
      v-for="itemCart in cart"
      :key="itemCart.vendorName"
    >
      <div slot="header">
        <el-checkbox v-model="checked">
          <em class="el-icon-s-shop"></em> {{ itemCart.vendorName }}
        </el-checkbox>
      </div>
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
            :src="item.product.cGallery.cGalleryItems[0].cAttachment.imageSmall"
            @click="getDetail(item.product)"
          />
        </el-col>

        <el-col :span="12">
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
              <strong>Rp {{ item.product.price | formatCurrency('id') }}</strong>
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
        <el-col :span="2">
          <el-button
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="removeItem(item)"
          />
        </el-col>
        <el-col :span="4">
          <el-input-number
            v-model="item.quantity"
            :min="1"
            size="mini"
            @change="handleChangeQty"
          />
        </el-col>
        <el-col :span="3">
          <strong style="color: #409eff">
            Rp. {{ (item.quantity * item.product.price) | formatCurrency('id') }}
          </strong>
        </el-col>
      </el-row>
    </el-card>

    <el-card>
      <el-row>
        <el-col :span="12">
          <el-button
            icon="el-icon-goods"
            size="mini"
            @click="continueShopping"
          >
            Continue Shopping
          </el-button>
        </el-col>

        <el-col :span="12" align="end">
          <el-row>
            <el-col :span="17">
              <strong>Total</strong> : <strong style="color: #409eff">Rp {{ subtotal | formatCurrency('id') }}</strong>
            </el-col>
            <el-col :span="7">
              <el-button
                icon="el-icon-shopping-cart-full"
                size="mini"
                type="primary"
              >
                Purchase Requisition
              </el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script lang="ts" src="./shopping-cart.component.ts"></script>
<style lang="scss" scoped>
.shopping-cart {
  padding: 24px;

  .el-card {
    margin-bottom: 16px;

    .cart-item {
      margin-bottom: 16px;

      .currency {
        color: #409eff;
      }

      .link {
        cursor: pointer;
      }
    }
  }

  .subtotal {
    text-align: right;
  }
}
</style>
