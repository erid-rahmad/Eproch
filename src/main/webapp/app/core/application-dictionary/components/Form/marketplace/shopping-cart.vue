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
            :src="getImg(item.product.cGallery)"
            @click="getDetail(item.product)"
          >
            <div slot="error" class="image-slot">
              <em class="el-icon-picture-outline"></em>
            </div>
          </el-image>
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
        <el-col :span="10">
          <el-button
            icon="el-icon-goods"
            size="mini"
            @click="continueShopping"
          >
            Continue Shopping
          </el-button>
        </el-col>

        <el-col :span="14" align="end">
          <el-row>
            <el-col :span="14">
              <strong>Total</strong> : <strong style="color: #409eff">Rp {{ subtotal | formatCurrency('id') }}</strong>
            </el-col>
            <el-col :span="10">
              <el-button
                icon="el-icon-shopping-cart-full"
                size="mini"
                type="primary"
                @click="createPrConfirmationVisible = true"
              >
                Purchase Requisition
              </el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog
      width="30%"
      :visible.sync="createPrConfirmationVisible"
      title="Create Purchase Request"
    >
      <template>
        <p>Do you want to create purchase request from the selected item(s)?</p>
        <el-form
          ref="purchaseRequisitionForm"
          :model="purchaseRequisitionForm"
          label-position="left"
          label-width="128px"
          size="mini"
        >
          <el-form-item
            class="field-cost-center"
            label="Cost Center"
            prop="costCenterId"
            required
            title="Select Cost Center"
          >
            <el-select
              v-model="purchaseRequisitionForm.costCenterId"
              clearable
              filterable
              placeholder="Select Cost Center"
            >
              <el-option
                v-for="item in costCenterOptions"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            class="field-warehouse"
            label="Warehouse"
            prop="warehouseId"
            required
            title="Select Warehouse"
          >
            <el-select
              v-model="purchaseRequisitionForm.warehouseId"
              clearable
              filterable
              placeholder="Select Warehouse"
            >
              <el-option
                v-for="item in warehouseOptions"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            class="field-currency"
            label="Currency"
            prop="currencyId"
            required
            title="Select Currency"
          >
            <el-select
              v-model="purchaseRequisitionForm.currencyId"
              clearable
              filterable
              placeholder="Select Currency"
            >
              <el-option
                v-for="item in currencyOptions"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-check"
            type="primary"
            @click="createPurchaseRequisition"
          >
            Create
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="createPrConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
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
