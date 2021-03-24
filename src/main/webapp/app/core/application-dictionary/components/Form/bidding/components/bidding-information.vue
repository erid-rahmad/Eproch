<template>
  <div class="bidding-information">
    <el-divider content-position="left"><h4>Bidding Information</h4></el-divider>

    <el-form
      ref="biddingInformation"
      label-position="left"
      label-width="150px"
      :model="bidding"
      :rules="rules"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :span="8">
          <el-form-item
            label="Title"
            prop="name"
            required
          >
            <el-input
              v-model="bidding.name"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Bidding No"
            prop="biddingNo"
          >
            <el-input
              v-model="bidding.documentNo"
              class="form-input"
              clearable
              :disabled="editMode"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Reference No"
            prop="referenceNo"
            required
          >
            <el-input
              ref="requisitionNo"
              v-model="bidding.referenceNo"
              v-loading="loadingReferenceNo"
              clearable
              :disabled="editMode"
              placeholder="Please Enter Reference No"
              @change="retrieveReferencedData"
            >
              <el-button
                v-if="!editMode"
                :loading="loadingReferenceNo"
                icon="el-icon-search"
                slot="append"
                @click="retrieveReferencedData(bidding.referenceNo)"
              >
                Search
              </el-button>
            </el-input>
          </el-form-item>

          <el-form-item
            label="Reference Type"
            prop="referenceTypeName"
            required
          >
            <el-input
              v-model="bidding.referenceTypeName"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Department" prop="costCenterId" required>
            <el-select
              v-model="bidding.costCenterId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Department"
              style="width: 100%"
            >
              <el-option
                v-for="item in costCenterOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="Currency"
            prop="currencyName"
            required
          >
            <el-input
              v-model="bidding.currencyName"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="Bidding Type"
            prop="biddingTypeId"
            required
          >
            <el-select
              v-model="bidding.biddingTypeId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Bidding Type"
              style="width: 100%"
              @change="retrieveEventTypes"
            >
              <el-option
                v-for="item in biddingTypeOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="Vendor Selection Method"
            prop="vendorSelection"
            required
          >
            <el-select
              v-model="bidding.vendorSelection"
              class="form-input"
              clearable
              filterable
              placeholder="Select Selection Method"
              style="width: 100%"
            >
              <el-option
                v-for="item in vendorSelectionOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="Ceiling Price"
            prop="ceilingPrice"
            required
          >
            <el-input
              v-model="bidding.ceilingPrice"
              v-inputmask="{'alias': 'currency'}"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Estimated Price"
            prop="estimatedPrice"
            required
          >
            <el-input
              v-model="bidding.estimatedPrice"
              v-inputmask="{'alias': 'currency'}"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="PiC"
            prop="adUserUserId"
            required>
            <el-select
              style="width: 100%"
              v-model="bidding.adUserUserId"
              class="form-input"
              clearable
              filterable
              placeholder="Select PiC"
            >
              <el-option
                v-for="item in picOptions"
                :key="item.userId"
                :label="item.userLogin"
                :value="item.userId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Event Type" prop="eventTypeId" required>
            <el-select
              v-model="bidding.eventTypeId"
              class="form-input"
              clearable
              :disabled="!bidding.biddingTypeId"
              filterable
              placeholder="Select Event Type"
              style="width: 100%"
            >
              <el-option
                v-for="item in eventTypeOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-divider content-position="left">
      <h4>Requirement</h4>
    </el-divider>

    <el-row>
      <el-col :span="24">
        <el-table
          v-loading="loadingLines"
          ref="biddingLines"
          border
          class="bidding-info-table"
          :data="bidding.biddingLines"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          :height="gridSchema.height"
          highlight-current-row
          :max-height="gridSchema.maxHeight"
          size="mini"
          stripe
          style="width: 100%"
        >
          <el-table-column min-width="50" label="No">
            <template slot-scope="{ $index }">
              {{ $index + 1 }}
            </template>
          </el-table-column>

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
                  size="mini"
                  title="Add sub item"
                  type="primary"
                  @click="editSubItem(row, $index)"
                >Sub-item</el-button>
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
            <template
              slot-scope="{ row }"
            >
              <el-input
                v-if="!row.subItem"
                v-model="row.ceilingPrice"
                v-inputmask="{'alias': 'currency'}"
                size="mini"
                @change="value => onCeilingPriceChange(row, value)"
              ></el-input>
              <span v-else>{{ row.ceilingPrice | formatCurrency }}</span>
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
            prop="deliveryDate"
          >
            <template slot-scope="{ row }">
              <el-date-picker
                v-model="row.deliveryDate"
                class="form-input"
                clearable
                :format="dateDisplayFormat"
                placeholder="Pick a date"
                size="mini"
                style="width: 100%"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </template>
          </el-table-column>

          <el-table-column
            label="Remark"
            min-width="200"
          >
            <template slot-scope="{ row }">
              <el-input
                v-model="row.remark"
                clearable
                :maxlength="255"
                size="mini"
              ></el-input>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-divider content-position="left"><h4>Project Information</h4></el-divider>
    <el-row>
      <el-col :span="12">
        <el-table
          v-loading="loadingProjectInfo"
          ref="projectInformations"
          highlight-current-row
          border
          stripe
          size="mini"
          style="height: 100%"
          :height="gridSchema.height"
          :max-height="gridSchema.maxHeight"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          :data="bidding.projectInformations"
        >
          <el-table-column min-width="30" label="No">
            <template slot-scope="{ $index }">
              {{ $index + 1 }}
            </template>
          </el-table-column>

          <el-table-column
            label="Information"
            min-width="100"
            prop="information"
          ></el-table-column>

          <el-table-column
            label="Attachment"
            min-width="80"
          >
            <template slot-scope="{ row }">
              <el-button
                class="btn-attachment"
                icon="el-icon-download"
                size="mini"
                type="primary"
                @click="downloadAttachment(row)"
              >
                    {{ printFileName(row.attachment) }}
              </el-button>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            min-width="50"
          >
            <template slot="header">
              <el-button
                icon="el-icon-plus"
                size="mini"
                type="primary"
                @click="projectFormVisible = true"
              ></el-button>
            </template>
            <template slot-scope="{ $index }">
              <el-button
                icon="el-icon-delete"
                size="mini"
                type="danger"
                @click="removeProject($index)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      title="Edit Sub Item"
      :visible.sync="subItemEditorVisible"
      @closed="onSubItemClosed"
    >
      <subitem-editor
        ref="subitemEditor"
        :item-index="selectedItemIndex"
        :item-detail="selectedItem"
        @saved="onSubItemSaved"
        @error="onSubItemError"
      ></subitem-editor>
      <div slot="footer">
        <el-button
          :disabled="savingSubitem"
          icon="el-icon-close"
          size="mini"
          style="margin-left: 0px;"
          @click="closeSubitemEditor"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
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

    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      title="Add Project"
      :visible.sync="projectFormVisible"
    >
      <template>
        <el-form
          ref="projectInformation"
          label-position="left"
          label-width="150px"
          :model="projectInformation"
          size="mini"
        >
          <el-form-item
            label="Information"
            prop="name"
            required
          >
            <el-input
              v-model="projectInformation.name"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Attachment"
            prop="attachment"
            required
          >
            <el-upload
              ref="upload"
              v-model="projectInformation.attachment"
              :accept="accept"
              :action="action"
              auto-upload
              :limit="limit"
              :before-upload="handleBeforeUpload"
              :on-preview="handlePreview"
              :on-exceed="handleExceed"
              :on-remove="handleRemove"
              :on-error="onUploadError"
              :on-success="onUploadSuccess"
            >
              <el-button
                icon="el-icon-search"
                slot="trigger"
                type="primary"
              >
                Select File
              </el-button>
              <span
                class="el-upload__tip"
                style="margin-left: 10px"
                slot="tip"
              >
                Files with a size less than 5Mb
              </span>
            </el-upload>
          </el-form-item>
        </el-form>

        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="projectFormVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            style="margin-left: 0px;"
            type="primary"
            @click="saveProject"
          >
            {{ $t('entity.action.save') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./bidding-information.component.ts"></script>

<style lang="scss" scoped>
.bidding-info-table .el-badge {
  margin: 4px 0;
}
</style>
