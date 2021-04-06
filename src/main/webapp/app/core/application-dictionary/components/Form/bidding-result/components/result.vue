<template>
  <div class="app-container bidding-result-form">
    <el-row
      class="action-toolbar"
      :gutter="columnSpacing"
    >
      <el-col :span="24">
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
        >
          Submit
        </el-button>
        <el-button
          size="mini"
          type="primary"
          @click="showGeneratePoDialog = true"
        >
          <svg-icon name="ecommerce/016-bill"></svg-icon> Generate PO
        </el-button>
        <el-button
          size="mini"
        >
          <svg-icon name="icomoo/085-printer"></svg-icon> Print
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
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item
            label="Cost Evaluation No."
            prop="documentNo"
          >
            <el-input
              v-model="mainForm.documentNo"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Biding No.">
            <el-input
              v-model="mainForm.biddingNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Biding Evaluation No.">
            <el-input
              v-model="mainForm.biddingEvaluationNo"
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
        </el-col>
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Bidding Type">
            <el-input
              v-model="mainForm.biddingType"
              disabled
            ></el-input>
          </el-form-item>
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
          <el-form-item label="Reference">
            <el-input
              v-model="mainForm.requisitionName"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row
        :gutter="columnSpacing"
        style="margin-top: 16px"
      >
        <el-col
          :xs="24"
          :sm="24"
          :lg="18"
          :xl="16"
        >
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
            <el-table-column
              label="Vendor"
              prop="vendorName"
              min-width="150"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="Proposed Price"
              min-width="200"
            >
              <template slot-scope="{ row }">
                {{ row.proposedPrice | formatCurrency }}
              </template>
            </el-table-column>
            <el-table-column
              label="Total Score"
              prop="totalScore"
              min-width="100"
            ></el-table-column>
            <el-table-column>
              <template slot="header">
                &nbsp;
              </template>
              <el-button
                icon="el-icon-search"
                size="mini"
                title="View"
                type="primary"
                @click="showScoreDetail = true"
              >
                Detail
              </el-button>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      
      <el-row style="margin-top: 16px">
        <el-col
          :xs="24"
          :sm="8"
        >
          <el-form-item
            v-if="manual"
            label="Select Vendor(s)"
            style="margin-bottom: .5rem"
          >
            <el-checkbox-group v-model="mainForm.selectedVendors">
              <el-checkbox label="INGRAM MICRO INDONESIA" value="1000" name="selectedVendors"></el-checkbox>
              <el-checkbox label="SISTECH KHARISMA" value="2000" name="selectedVendors"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>
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
    <el-dialog
      width="30%"
      :visible.sync="showScoreDetail"
      title="Score Detail"
    >
      <score-detail></score-detail>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="showScoreDetail = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
      </div>
    </el-dialog>
    <el-dialog
      width="30%"
      :visible.sync="showGeneratePoDialog"
      title="Generate PO Confirmation"
    >
      <p>Do you want to generate purchase orders?</p>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          style="margin-left: 0px;"
          size="mini"
          @click="showGeneratePoDialog = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="showGeneratePoDialog = false"
        >
          Generate
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./result.component.ts"></script>

<style lang="scss">
.compact .bidding-result-form .el-table--mini td {
  height: 35px;
}
</style>

<style lang="scss" scoped>
.action-toolbar {
  padding: 4px;
}
</style>
