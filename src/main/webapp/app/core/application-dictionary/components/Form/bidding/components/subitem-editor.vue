<template>
  <div class="subitem-editor">
    <el-form
      ref="mainForm"
      label-width="150px"
      :model="mainForm"
      :disabled="readOnly"
      :rules="validationSchema"
      size="mini"
    >
      <el-col
        :lg="12"
        :xs="24"
      >
        <el-form-item label="Product">
          <el-input
            v-model="itemDetail.productName"
            class="form-input"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item
          label="Sub Item"
          prop="productId"
        >
          <el-select
            ref="subItemProduct"
            v-model="mainForm.productId"
            clearable
            filterable
            :loading="loadingProducts"
            placeholder="Select a Product"
            remote
            :remote-method="searchProduct"
            style="width: 100%"
            tabindex="1"
          >
            <el-option
              v-for="item in productOptions"
              :key="item.id"
              :label="`${item.code} - ${item.name}`"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col
        :lg="12"
        :xs="24"
      >
        <el-form-item label="Sub Item">
          <el-input
            v-model="mainForm.totalAmount"
            v-inputmask="{'alias': 'currency'}"
            disabled
          ></el-input>
        </el-form-item>
      </el-col>
    </el-form>
    <el-table
      v-loading="loadingLines"
      border
      :empty-text="gridSchema.emptyText"
      :data="mainForm.mBiddingSubItemLines"
      :max-height="gridSchema.maxHeight"
      size="mini"
    >
      <el-table-column
        label="No"
        min-width="50"
      >
        <template slot-scope="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        label="Product"
        min-width="200"
      >
        <template slot-scope="{ row }">
          <el-select
            v-model="row.productId"
            clearable
            filterable
            placeholder="Select a Product"
            remote
            :remote-method="searchProduct"
            size="mini"
          >
            <el-option
              v-for="item in productOptions"
              :key="item.id"
              :label="`${item.code} - ${item.name}`"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        label="Quantity"
        min-width="150"
      >
        <template slot-scope="{ row }">
          <el-input-number
            v-model="row.quantity"
            clearable
            controls-position="right"
            :min="0"
            size="mini"
            @change="value => onQuantityChange(row, value)"
          ></el-input-number>
        </template>
      </el-table-column>
      <el-table-column
        label="UoM"
        min-width="100"
      >
        <template slot-scope="{ row }">
          <el-select
            v-model="row.uomId"
            clearable
            filterable
            size="mini"
          >
            <el-option
              v-for="item in uomOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        label="Price"
        min-width="150"
      >
        <template slot-scope="{ row }">
          <el-input
            v-model="row.price"
            v-inputmask="{'alias': 'currency'}"
            size="mini"
            @change="value => onPriceChange(row, value)"
          ></el-input>
        </template>
      </el-table-column>
      <el-table-column
        align="right"
        label="Amount"
        min-width="100"
      >
        <template slot-scope="{ row }">
          {{ row.amount | formatCurrency }}
        </template>
      </el-table-column>
      <el-table-column
        align="right"
        fixed="right"
        min-width="100"
      >
        <template slot="header">
          <el-button
            :disabled="readOnly"
            icon="el-icon-plus"
            size="mini"
            type="primary"
            @click="addSubItem"
          ></el-button>
        </template>
        <template slot-scope="{ $index }">
          <el-button
            v-if="!readOnly"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="removeSubItem($index)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" src="./subitem-editor.component.ts"></script>
<style lang="scss">
.subitem-editor {
  .el-table .el-button--mini {
    margin: 4px 0;
  }
}
</style>
