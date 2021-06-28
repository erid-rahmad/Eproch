<template>
  <div class="vendor-confirmation-detail">
    <el-scrollbar class="form-wrapper">
      <el-form
        ref="mainForm"
        :label-position="formSettings.labelPosition"
        :label-width="formSettings.labelWidth"
        :model="mainForm"
        :size="formSettings.size"
      >
        <el-row
          :gutter="columnSpacing"
          style="margin-top: 16px"
        >
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item
              label="Bidding No."
            >
              <el-input
                v-model="mainForm.biddingNo"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="Bidding Type"
            >
              <el-input
                v-model="mainForm.biddingTypeName"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="Currency"
            >
              <el-input
                v-model="mainForm.currencyName"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Department">
              <el-input
                v-model="mainForm.costCenterName"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="PiC">
              <el-input
                v-model="mainForm.picName"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Amount">
              <el-input
                v-model="mainForm.amount"
                v-inputmask="{'alias': 'currency'}"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
            <el-table
              border
              :data="confirmations"
              highlight-current-row
              :max-height="256"
              size="mini"
              stripe
              style="margin-top: 16px; width: 100%"
            >
              <el-table-column
                label="No"
                width="50"
              >
                <template slot-scope="{ $index }">
                  {{ $index + 1 }}
                </template>
              </el-table-column>
              <el-table-column
                label="Vendor"
                prop="vendorName"
                min-width="200"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                label="Contract Amount"
                min-width="150"
              >
                <template slot-scope="{ row }">
                  {{ row.negoAmount | formatCurrency }}
                </template>
              </el-table-column>
              <el-table-column
                label="Status"
                min-width="100"
              ><template slot-scope="{ row }">{{formatConfirmationStatus(row.status)}}</template></el-table-column>
              <el-table-column
                label="View Detail"
                width="140"
              >
                <template slot-scope="{ row }">
                  <el-button
                    class="button"
                    icon="el-icon-more"
                    size="mini"
                    style="width: 100%"
                    type="primary"
                    :loading="loading"
                    @click="viewDetail(row)"
                  ></el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="View History"
                width="140"
              >
                <template slot-scope="{ row }">
                  <el-button
                    class="button"
                    icon="el-icon-more"
                    size="mini"
                    style="width: 100%"
                    type="primary"
                    @click="viewHistory(row)"
                  ></el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="Action"
                width="140"
              >
                <template slot-scope="{ row }">
                  <el-button
                    v-if="row.status !== 'P' && row.status !== 'A'"
                    class="button"
                    icon="el-icon-document-checked"
                    size="mini"
                    style="width: 100%"
                    type="primary"
                    @click="openConfirmationForm(row)"
                  >
                    Action
                  </el-button>
                  <el-button
                    v-if="row.status==='A'"
                    class="button"
                    icon="el-icon-document-checked"
                    size="mini"
                    style="width: 100%"
                    type="primary"
                    @click="generatePo(row)"
                  >
                    Generate PO
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="Generate Contract?"
                width="140"
              >
                <template slot-scope="{ row }">
                  <el-button
                    v-if="row.status!=='A'"
                    class="button"
                    icon="el-icon-document-checked"
                    size="mini"
                    type="primary"
                    @click="openContractParameter(row)"
                  >
                    Generate Contract
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
      </el-form>
    </el-scrollbar>

    <el-dialog
      width="50%"
      :visible.sync="showPoForm"
      title="Generate PO"
    >
      PO has been generated. Your PO Number is {{poNumber}}.
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showPoForm = false"
        >
          Close
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="50%"
      :visible.sync="confirmPublish"
      title="Confirm Publish"
    >
      Confirm publish contract?
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="confirmPublish = false"
        >
          No
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="publish"
        >
          Yes
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="30%"
      :visible.sync="contractParameterFormVisible"
      title="Generate Contract"
    >
      <el-form
        v-loading="generatingContract"
        ref="mainForm"
        :label-position="formSettings.labelPosition"
        :label-width="formSettings.labelWidth"
        :model="contractParameter"
        :size="formSettings.size"
      >
        <el-form-item label="Start Date">
          <el-date-picker
            v-model="contractParameter.startDate"
            :format="dateDisplayFormat"
            size="mini"
            type="date"
            :value-format="dateValueFormat"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="Expiration Date">
          <el-date-picker
            v-model="contractParameter.expirationDate"
            :format="dateDisplayFormat"
            size="mini"
            type="date"
            :value-format="dateValueFormat"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="Evaluation Type">
          <ad-input-lookup
            v-model="contractParameter.vendorEvaluationId"
            placeholder="Select Evaluation Type"
            table-name="c_vendor_evaluation"
          ></ad-input-lookup>
        </el-form-item>
        <el-form-item label="Evaluation Period">
          <ad-input-list
            v-model="contractParameter.evaluationPeriod"
            placeholder="Select Evaluation Period"
            reference-key="vendorEvaluationPeriod"
          ></ad-input-list>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="contractParameterFormVisible = false"
        >
          Cancel
        </el-button>
        <el-button
          icon="el-icon-check"
          :loading="generatingContract"
          size="mini"
          type="primary"
          @click="generateContract"
        >
          Generate
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="50%"
      :visible.sync="showDetail"
      title="Detail"
    >
      <el-form
        ref="mainForm"
        label-position="left"
        label-width="200px"
        :model="selectedConfirmation"
        size="mini"
      >
        <el-row :gutter="columnSpacing">
          <el-col
            :xs="24"
            :sm="24"
            :lg="18"
            :xl="12"
          >
            <el-form-item label="Amount Total">
              <el-input
                v-model="selectedConfirmation.amount"
                v-inputmask="{'alias': 'currency'}"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Quantity Total">
              <el-input-number
                v-model="selectedConfirmation.quantity"
                controls-position="right"
                disabled
                size="mini"
              ></el-input-number>
            </el-form-item>
            <el-form-item label="Contract No.">
              <el-input
                v-model="selectedConfirmation.contractNo"
                disabled
                size="mini"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-table
          border
          :data="selectedConfirmation.lines"
          highlight-current-row
          :max-height="256"
          size="mini"
          stripe
          style="margin-top: 16px; width: 100%"
        >
          <el-table-column
            label="No."
            width="50"
          >
            <template slot-scope="{ $index }">
              {{ $index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            label="Item"
            prop="productName"
            min-width="200"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="Quantity"
            prop="quantity"
            min-width="100"
          ></el-table-column>
          <el-table-column
            label="Unit Price"
            min-width="150"
          >
            <template slot-scope="{ row }">
              {{ row.priceNegotiation | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Total"
            min-width="150"
          >
            <template slot-scope="{ row }">
              {{ row.totalNegotiationPrice | formatCurrency }}
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showDetail = false"
        >
          Close
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="50%"
      :visible.sync="showHistory"
      title="History"
    >
      <el-table
        border
        :data="history"
        highlight-current-row
        :max-height="256"
        size="mini"
        stripe
        style="margin-top: 16px; width: 100%"
      >
        <el-table-column
          label="No."
          width="50"
        >
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          label="Confirmation No."
          prop="confirmationNo"
          min-width="100"
        ></el-table-column>
        <el-table-column
          label="Modified Date"
          min-width="100"
        > <template slot-scope="{ row }">
          {{row.lastModifiedDate | formatDate}}
          </template>
        </el-table-column>
        <el-table-column
          label="Status"
          min-width="100"
        ><template slot-scope="{ row }">
          {{row.accept ? "Accept" : "Need Revision"}}
          </template></el-table-column>
        <el-table-column
          label="Reason"
          min-width="200"
        ><template slot-scope="{ row }">
          {{row.accept ? row.accept : row.needRevision}}
          </template></el-table-column>
      </el-table>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showHistory = false"
        >
          Close
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="50%"
      :visible.sync="showConfirmationForm"
      title="Vendor Confirmation Form"
    >
      <el-form
        ref="contractForm"
        :rules="contractFormValidationSchema"
        label-position="left"
        label-width="200px"
        :model="contract"
        size="mini"
      >
        <el-row :gutter="columnSpacing">
          <el-col
            :xs="24"
            :sm="24"
            :lg="18"
            :xl="12"
          >
            <el-form-item
              label="Confirmation No."
              prop="confirmationNo"
            >
              <el-input
                v-model="contract.confirmationNo"
              ></el-input>
            </el-form-item>
            <el-form-item label="Confirmation Start Date">
              <el-date-picker
                v-model="contract.contractStartDate"
                v-loading="contractLoading"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="Confirmation End Date">
              <el-date-picker
                v-model="contract.contractEndDate"
                v-loading="contractLoading"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="24"
            :lg="18"
            :xl="12"
          >
            <el-form-item label="Confirmation Attachment" prop="attachment">
              <el-upload
                ref="contractFile"
                v-model="file"
                v-loading="contractLoading"
                :action="action"
                class="upload-demo"
                :limit="limit"
                :multiple="false"
                :accept="accept"
                :file-list="fileList"
                :before-upload="handleBeforeUpload"
                :on-change="onUploadChange"
                :on-preview="handlePreview"
                :on-exceed="handleExceed"
                :on-remove="handleRemove"
                :on-error="onUploadError"
                :on-success="onUploadSuccess"
              >
                <el-button
                  size="mini"
                  type="primary"
                >
                  Select Document
                </el-button>
                <div
                  slot="tip"
                  class="el-upload__tip"
                >
                  pdf/jpg/png files with a size less than 2mb
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="Confirmation Detail" prop="contractDetail">
          <el-input
            v-model="contract.contractDetail"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="resetForm"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="saveAsDraft"
        >
          {{ $t('entity.action.save') }}
        </el-button>
        <el-button
          icon="el-icon-s-promotion"
          size="mini"
          type="primary"
          @click="showConfirmPublish"
        >
          Publish
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./detail.component.ts"></script>

<style lang="scss">
.compact {
  .vendor-confirmation-detail {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }
  }
}

.vendor-confirmation-detail {
  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }
}
</style>

<style lang="scss" scoped>
/* .vendor-confirmation-detail {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px;
  }
} */
</style>
