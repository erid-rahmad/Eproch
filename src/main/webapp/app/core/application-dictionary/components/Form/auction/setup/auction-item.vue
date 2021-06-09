<template>
  <div class="auction-item">
    <el-form
      disabled
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="auction"
      :size="formSettings.size"
    >
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Auction No.">
            <el-input
              v-model="auction.documentNo"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Title">
            <el-input
              v-model="auction.name"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      v-loading="loadingItems"
      ref="itemGrid"
      border
      :data="items"
      highlight-current-row
      size="mini"
      stripe
      @current-change="onCurrentRowChanged"
    >
      <el-table-column width="50" label="No">
        <template slot-scope="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>

      <el-table-column
        label="Code"
        width="130"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.productIdMessage"
            :disabled="!row.productIdError"
          >
            <el-select
              v-model="row.productId"
              :class="{ 'is-error': row.productIdError }"
              clearable
              filterable
              remote
              :remote-method="retrieveProducts"
              placeholder="Select Product"
              size="mini"
              @clear="retrieveProducts"
            >
              <el-option
                v-for="product in products"
                :key="product.id"
                :label="product.code"
                :value="product.id"
              >
                <span style="float: left">{{ product.code }}</span>
                <span style="float: right; color: #8492a6; font-size: 12px">{{ product.name }}</span>
              </el-option>
            </el-select>
          </el-tooltip>
          <template v-else>
            {{ row.productCode }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        label="Name"
        min-width="200"
        show-overflow-tooltip
        sortable
      >
        <template slot-scope="{ row }">
          <template v-if="row.editing">
            {{ printProductName(row.productId) }}
          </template>
          <template v-else>
            {{ row.productName }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        label="Qty"
        width="150"
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.quantityMessage"
            :disabled="!row.quantityError"
          >
            <el-input-number
              v-model="row.quantity"
              :class="{ 'is-error': row.quantityError }"
              controls-position="right"
              :min="0"
              size="mini"
              @change="value => onQuantityChange(row, value)"
            ></el-input-number>
          </el-tooltip>
          <template v-else>
            {{ row.quantity }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        label="UoM"
        width="100"
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.uomIdMessage"
            :disabled="!row.uomIdError"
          >
            <el-select
              v-model="row.uomId"
              :class="{ 'is-error': row.uomIdError }"
              clearable
              filterable
              remote
              :remote-method="retrieveUoms"
              placeholder="Select UoM"
              size="mini"
              @clear="retrieveUoms"
            >
              <el-option
                v-for="uom in uoms"
                :key="uom.id"
                :label="uom.code"
                :value="uom.id"
              >
                <span style="float: left">{{ uom.code }}</span>
                <span style="float: right; color: #8492a6; font-size: 12px">{{ uom.name }}</span>
              </el-option>
            </el-select>
          </el-tooltip>
          <template v-else>
            {{ row.uomName }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        align="right"
        label="Price"
        width="200"
        sortable
      >
        <template slot-scope="{ row }">
          <el-tooltip
            v-if="row.editing"
            :content="row.ceilingPriceMessage"
            :disabled="!row.ceilingPriceError"
          >
            <el-input
              v-model="row.ceilingPrice"
              v-inputmask="{ alias: 'currency' }"
              :class="{ 'is-error': row.ceilingPriceError }"
              size="mini"
              @change="value => onCeilingPriceChange(row, value)"
            ></el-input>
          </el-tooltip>
          <template v-else>
            {{ row.ceilingPrice | formatCurrency('id') }}
          </template>
        </template>
      </el-table-column>

      <el-table-column
        align="right"
        label="Total"
        width="200"
        sortable
      >
        <template slot-scope="{ row }">
          {{ row.amount | formatCurrency('id') }}
        </template>
      </el-table-column>

      <el-table-column
        v-if="!readOnly"
        align="right"
        fixed="right"
        width="200"
      >
        <template slot="header">
          <el-button
            v-show="!editMode"
            icon="el-icon-plus"
            size="mini"
            title="Add an Item"
            type="primary"
            @click="onAddClicked"
          ></el-button>
        </template>
        <template slot-scope="{ row, $index }">
          <el-button
            v-if="row.editing"
            icon="el-icon-close"
            size="mini"
            type="danger"
            @click="onEditCanceled($index)"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            v-else
            :disabled="editMode"
            icon="el-icon-edit"
            size="mini"
            type="primary"
            @click="onEditClicked(row)"
          >
            {{ $t('entity.action.edit') }}
          </el-button>

          <el-button
            v-if="row.editing"
            icon="el-icon-check"
            size="mini"
            type="primary"
            @click="onSaveClicked(row)"
          ></el-button>
          <el-button
            v-else
            :disabled="editMode"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="onDeleteClicked(row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-form
      :disabled="!selectedRow.editing"
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="selectedRow"
      :size="formSettings.size"
    >
      <el-divider content-position="left">
        <h4>Bidding Rules</h4>
      </el-divider>
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Improve Bid by">
            <strong>{{ printBidImprovementUnit(auction.ruleBidImprovementUnit) }}</strong>
          </el-form-item>
          <el-form-item label="Bid Decrement">
            <el-input-number
              v-model="selectedRow.bidDecrement"
              controls-position="right"
              :min="0"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="Leading Bid Front Buffer">
            <el-input-number
              v-model="selectedRow.protectFrontBuffer"
              controls-position="right"
              :min="0"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="Leading Bid Back Buffer">
            <el-input-number
              v-model="selectedRow.protectBackBuffer"
              controls-position="right"
              :min="0"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="Tie Bids Rule">
            <strong>{{ printTieBidsRule(auction.ruleTieBidsRule) }}</strong>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-dialog
      width="30%"
      :visible.sync="deleteConfirmationVisible"
      :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the selected record?</span>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="deleteConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="deleteRecord"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./auction-item.component.ts"></script>
