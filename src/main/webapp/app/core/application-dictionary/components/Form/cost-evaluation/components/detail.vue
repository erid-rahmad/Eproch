<template>
  <div class="app-container">
    <el-row
      v-if="approval"
      class="action-toolbar"
      :gutter="columnSpacing"
    >
      <el-col :span="24">
        <el-button
          size="mini"
        >
          <svg-icon name="icomoo/104-undo2"></svg-icon> Revise
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
        >
          Approve
        </el-button>
        <el-button
          size="mini"
          type="danger"
        >
          <svg-icon name="icomoo/271-blocked"></svg-icon> Reject
        </el-button>
      </el-col>
    </el-row>
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
            label="Cost Evaluation No."
            prop="documentNo"
          >
            <el-input
              v-model="mainForm.documentNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Bidding Title"
            prop="biddingTitle"
          >
            <el-input
              v-model="mainForm.biddingTitle"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Biding No."
            prop="biddingNo"
          >
            <el-input
              v-model="mainForm.biddingNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Currency"
            prop="currencyName"
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
          <el-form-item
            label="PIC"
            prop="picName"
          >
            <el-input
              v-model="mainForm.picName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Depertement"
            prop="costCenterName"
          >
            <el-input
              v-model="mainForm.costCenterName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Reference"
            prop="requisitionNo"
          >
            <el-input
              v-model="mainForm.requisitionNo"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-table
        border
        :data="tableData"
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
        <el-table-column label="Detail">
          <el-table-column
            label="Product"
            prop="productName"
            min-width="150"
          ></el-table-column>
          <el-table-column
            label="Qty"
            prop="quantity"
            min-width="100"
          ></el-table-column>
          <el-table-column
            label="UoM"
            prop="uomName"
            min-width="50"
          ></el-table-column>
          <el-table-column
            label="Mandays"
            prop="mandays"
            min-width="75"
          ></el-table-column>
        </el-table-column>
        <el-table-column label="Evaluation">
          <el-table-column
            label="Price"
            min-width="200"
          >
            <template slot-scope="{ row }">
              {{ row.evaluationPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Sub Total"
            width="150"
          >
            <template slot-scope="{ row }">
              {{ row.evaluationSubTotal | formatCurrency }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="Perum Nurdiyanti Megantara">
          <el-table-column
            label="Price"
            min-width="200"
          >
            <template slot-scope="{ row }">
              {{ row.vendor1Price | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Sub Total"
            width="150"
          >
            <template slot-scope="{ row }">
              {{ row.vendor1SubTotal | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Gap"
            width="200"
          >
            <template slot-scope="{ row }">
              {{ row.evaluationSubTotal - row.vendor1SubTotal | formatCurrency }}
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column label="PT Hidayanto (Persero) Tbk">
          <el-table-column
            label="Price"
            min-width="200"
          >
            <template slot-scope="{ row }">
              {{ row.vendor2Price | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Sub Total"
            width="150"
          >
            <template slot-scope="{ row }">
              {{ row.vendor2SubTotal | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Gap"
            width="200"
          >
            <template slot-scope="{ row }">
              {{ row.evaluationSubTotal - row.vendor2SubTotal | formatCurrency }}
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
      <el-row style="margin-top: 16px">
        <el-col
          :xs="24"
          :sm="8"
        >
          <el-form-item
            label="Note"
            prop="remark"
            style="margin-bottom: .5rem"
          >
            <el-input
              v-model="mainForm.remark"
              :rows="3"
              type="textarea"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Supporting Documents"
            prop="galleryId"
          >
            <el-upload
              action="https://jsonplaceholder.typicode.com/posts/"
              class="upload-demo"
              :file-list="uploadedDocuments"
              :limit="5"
              multiple
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
    </el-form>
  </div>
</template>

<script lang="ts" src="./detail.component.ts"></script>

<style lang="scss" scoped>
.action-toolbar {
  padding: 4px;
}
</style>
