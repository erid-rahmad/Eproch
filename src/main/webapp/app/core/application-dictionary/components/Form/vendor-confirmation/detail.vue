<template>
  <div class="vendor-confirmation-detail">
    <el-scrollbar class="form-wrapper">
      <el-form
        ref="mainForm"
        label-position="left"
        label-width="200px"
        :model="mainForm"
        size="mini"
      >
        <el-row
          :gutter="columnSpacing"
          style="margin-top: 16px"
        >
          <el-col
            :xs="24"
            :sm="8"
          >
            <el-form-item
              label="Biding No."
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
            :sm="8"
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
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col
            :xs="24"
            :sm="20"
            :lg="18"
          >
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
                  {{ row.amount | formatCurrency }}
                </template>
              </el-table-column>
              <el-table-column
                label="Status"
                prop="documentStatus"
                min-width="100"
              ></el-table-column>
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
                    @click="viewDetail(row)"
                  ></el-button>
                </template>
              </el-table-column>
              <el-table-column
                label="Action"
                width="140"
              >
                <template slot-scope="{ row }">
                  <el-button
                    class="button"
                    icon="el-icon-document-checked"
                    size="mini"
                    style="width: 100%"
                    type="primary"
                    @click="openConfirmationForm(row)"
                  >
                    Action
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
    </el-scrollbar>
    
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
            :sm="12"
          >
            <el-form-item label="Amount Total">
              <el-input
                v-model="selectedConfirmation.amount"
                v-inputmask="{'alias': 'currency'}"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Quantity Total">
              <el-input
                v-model="selectedConfirmation.quantity"
                disabled
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
            prop="item"
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
              {{ row.unitPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Total"
            min-width="150"
          >
            <template slot-scope="{ row }">
              {{ row.totalLine | formatCurrency }}
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
      :visible.sync="showConfirmationForm"
      title="Vendor Confirmation Form"
    >
      <el-form
        ref="contractForm"
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
            <el-form-item label="Contract No.">
              <el-input
                v-model="contract.contractNo"
              ></el-input>
            </el-form-item>
            <el-form-item label="Contract Start Date">
              <el-input
                v-model="contract.startDate"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Contract End Date">
              <el-input
                v-model="contract.endDate"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="24"
            :lg="18"
            :xl="12"
          >
            <el-form-item label="Contract Attachment">
              <el-upload
                action="https://jsonplaceholder.typicode.com/posts/"
                class="upload-demo"
                :limit="1"
                :multiple="false"
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
        <el-form-item label="Contract Detail">
          <el-input
            v-model="contract.remark"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showConfirmationForm = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="showConfirmationForm = false"
        >
          {{ $t('entity.action.save') }}
        </el-button>
        <el-button
          icon="el-icon-s-promotion"
          size="mini"
          type="primary"
          @click="showConfirmationForm = false"
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
