<template>
  <div class="price-proposal">
    <h3 style="margin-top: 0">Price Proposal</h3>

    <el-form
      v-loading="loading"
      ref="mainForm"
      label-position="left"
      label-width="200px"
      :model="mainForm"
      :rules="validationRulesHeader"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="18"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Title">
            <el-input
              v-model="mainForm.biddingName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding No">
            <el-input
              v-model="mainForm.biddingNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Currency">
            <el-input
              v-model="mainForm.currencyName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Proposed Price">
            <el-input
              v-model="mainForm.proposedPrice"
              v-inputmask="{'alias': 'currency'}"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Remaining Time">
              <span>{{ timeRemaining }}</span>
            </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="18"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Bidding Type">
            <el-input
              v-model="mainForm.biddingTypeName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Vendor Selection Method">
            <el-select
              v-model="mainForm.vendorSelection"
              class="form-input"
              disabled
            >
              <el-option
                v-for="item in vendorSelectionOptions"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Ceiling Price">
            <el-input
              v-model="mainForm.ceilingPrice"
              v-inputmask="{'alias': 'currency'}"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Event Type">
            <el-input
              v-model="mainForm.eventTypeName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Submision Deadline">
            <el-date-picker
              v-model="schedule.actualEndDate"
              disabled
              :format="dateDisplayFormat"
              size="mini"
              type="datetime"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="18"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Biding Status">
            <el-select
              v-model="mainForm.biddingStatus"
              disabled
            >
              <el-option
                v-for="item in biddingStatuses"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            v-if="!isVendor"
            label="Vendor"
          >
            <el-input
              v-model="mainForm.vendorName"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      v-loading="loadingLines"
      ref="proposalPriceLines"
      border
      class="proposal-price-table"
      :data="mainForm.proposalPriceLines"
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      highlight-current-row
      size="mini"
      stripe
      style="width: 100%"
    >
      <el-table-column
        fixed
        min-width="50"
        label="No"
      >
        <template slot-scope="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="Requirement">
        <el-table-column
          label="Product"
          min-width="200"
          prop="productName"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column
          label="Sub Item"
          width="110"
          >
          <template slot-scope="{ row, $index }">
            <el-badge :is-dot="!!row.subItem">
              <el-button
                class="share-button"
                :disabled="!row.subItem"
                size="mini"
                title="Add sub item"
                type="primary"
                @click="editSubItem(row, $index)"
              >
                Sub-item
              </el-button>
            </el-badge>
          </template>
        </el-table-column>

        <el-table-column
          label="Quantity"
          min-width="150"
          prop="quantity"
        ></el-table-column>

        <el-table-column
          label="UoM"
          min-width="50"
          prop="uomName"
        ></el-table-column>

        <el-table-column
          align="right"
          label="Ceiling Price/Unit"
          min-width="150"
        >
          <template slot-scope="{ row }">
            {{ row.ceilingPrice | formatCurrency }}
          </template>
        </el-table-column>

        <el-table-column
          align="right"
          label="Total Ceiling Price"
          min-width="150"
        >
          <template slot-scope="{ row }">
            {{ row.totalCeilingPrice | formatCurrency }}
          </template>
        </el-table-column>

        <el-table-column
          label="Delivery Date"
          min-width="150"
        >
          <template slot-scope="{ row }">
            {{ row.reqDeliveryDate | formatDate(true) }}
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column label="Submission">
        <el-table-column
          align="right"
          label="Price Submission/Unit"
          min-width="150"
        >
          <template slot-scope="{ row, $index }">
            <el-tooltip
              v-if="!row.subItem"
              :disabled="!row.proposedPriceError"
              :content="row.proposedPriceMessage"
            >
              <el-input
                v-model="row.proposedPrice"
                v-inputmask="{ alias: 'currency' }"
                :class="{ 'is-error': row.proposedPriceError }"
                :disabled="disabled"
                size="mini"
                @change="value => onProposedPriceChange(row, $index, value)"
              ></el-input>
            </el-tooltip>
            <span v-else>{{ row.proposedPrice | formatCurrency }}</span>
          </template>
        </el-table-column>

        <el-table-column
          align="right"
          label="Total Price Submission"
          min-width="150"
        >
          <template slot-scope="{ row }">
            {{ row.totalPriceSubmission | formatCurrency }}
          </template>
        </el-table-column>

        <el-table-column
          label="Delivery Date"
          min-width="150"
        >
          <template slot-scope="{ row, $index }">
            <el-tooltip
              :disabled="!row.deliveryDateError"
              :content="row.deliveryDateMessage"
            >
              <el-date-picker
                v-model="row.deliveryDate"
                :ref="`deliveryDate${$index}`"
                class="form-input"
                :class="{ 'is-error': row.deliveryDateError }"
                clearable
                :disabled="disabled"
                :format="dateDisplayFormat"
                placeholder="Pick a date"
                size="mini"
                style="width: 100%"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column
        label="Remark"
        min-width="200"
        prop="remark"
      ></el-table-column>
    </el-table>

    <el-dialog :show-close="false" title="Edit Sub Item" :visible.sync="subItemEditorVisible" @closed="onSubItemClosed">
      <subitem-editor
        ref="subitemEditor"
        :read-only="disabled"
        :item-index="selectedItemIndex"
        :item-detail="selectedItem"
        @saved="onSubItemSaved"
        @error="onSubItemError"
      >
      </subitem-editor>
      <div slot="footer">
        <el-button :disabled="savingSubitem" icon="el-icon-close" size="mini" style="margin-left: 0px;" @click="closeSubitemEditor">
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          v-if="!disabled"
          :loading="savingSubitem"
          icon="el-icon-check"
          size="mini"
          style="margin-left: 0px;"
          type="primary"
          @click="saveSubitemEditor"
        >
          {{ $t('entity.action.save') }}
        </el-button>
      </div>
    </el-dialog>
    
  </div>
</template>

<script lang="ts" src="./price-proposal.component.ts"></script>
