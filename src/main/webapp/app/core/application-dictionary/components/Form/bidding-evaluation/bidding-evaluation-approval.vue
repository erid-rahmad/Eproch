<template>
  <div class="app-container bidding-evaluation-approval">
    <div class="toolbar">
      <el-col :span="24">
        <document-action-button
          :approved="false"
          :document-type-id="bidding.documentTypeId"
          :next-action="defaultDocumentAction"
          size="mini"
          window-type="TRANSACTION"
          @change="onDocumentActionChanged"
        ></document-action-button>
      </el-col>
    </div>
    <el-scrollbar class="form-wrapper">
      <el-form
        ref="mainForm"
        label-position="left"
        label-width="150px"
        :model="bidding"
        size="mini"
      >
        <el-row :gutter="24">
          <el-col
            :xs="24"
            :sm="12"
            :lg="8"
          >
            <el-form-item
              label="Bidding No."
              prop="biddingNo"
              required
            >
              <el-input
                v-model="bidding.documentNo"
                class="form-input"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Title">
              <el-input
                v-model="bidding.name"
                class="form-input"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Bidding Type">
              <el-input
                v-model="bidding.biddingTypeName"
                class="form-input"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :lg="8"
          >
            <el-form-item label="PiC">
              <el-input
                v-model="bidding.adUserUserName"
                class="form-input"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Department">
              <el-input
                v-model="bidding.costCenterName"
                class="form-input"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">
          <h4>Vendor Scoring</h4>
        </el-divider>
        <el-row>
          <el-col
            :xs="24"
            :sm="18"
            :xl="16"
          >
            <el-table
              border
              class="vendor-scoring"
              :data="vendorScorings"
              highlight-current-row
              size="mini"
              stripe
              style="width: 100%; height: 100%"
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
                label="Evaluation Event"
                prop="evaluationEvent"
                sortable
                min-width="180"
              ></el-table-column>
              <el-table-column
                label="Criteria"
                prop="criteria"
                sortable
                min-width="100"
              ></el-table-column>
              <el-table-column
                label="Sub-Criteria"
                prop="subCriteria"
                sortable
                min-width="100"
              ></el-table-column>
              <el-table-column
                label="Percentage (%)"
                prop="percentage"
                sortable
                min-width="100"
              ></el-table-column>
              <el-table-column
                label="PiC"
                prop="picUserName"
                sortable
                min-width="100"
              ></el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-divider content-position="left">
          <h4>Supporting Document</h4>
        </el-divider>
        <el-form
          label-position="left"
          label-width="150px"
          :model="supportingDocument"
          size="mini"
        >
          <el-row :gutter="24">
            <el-col
              :xs="24"
              :sm="12"
              :lg="8"
            >
              <el-form-item label="PiC">
                  {{ supportingDocument.picUserName }}
              </el-form-item>
              <el-form-item label="Document">
                <el-button
                  class="btn-attachment"
                  icon="el-icon-download"
                  size="mini"
                  type="primary"
                >
                  {{ supportingDocument.fileName }}
                </el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-divider content-position="left">
          <h4>Biding Evaluation Result</h4>
        </el-divider>
        <el-table
          border
          :data="evaluationResult"
          highlight-current-row
          size="mini"
          stripe
        >
          <el-table-column
            label="No."
            width="50"
          >
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            label="Vendor Name"
            prop="vendorName"
            show-overflow-tooltip
            min-width="200"
          ></el-table-column>
          <el-table-column
            label="Proposed Price"
            width="200"
          >
            <template slot-scope="{ row }">
              {{ row.proposedPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Price Ranking"
            prop="priceRanking"
            width="150"
          ></el-table-column>
          <el-table-column 
            class-name="document-submission"
            label="Document Submission"
            width="250"
          >
            <template slot-scope="{ row }">
              <el-row
                v-for="item in row.attachments"
                :key="item.id"
              >
                <el-col
                  class="border"
                  :span="12"
                >
                  {{ item.documentName }}
                </el-col>
                <el-col
                  class="border"
                  :span="12"
                >
                  <el-button
                    class="btn-attachment"
                    icon="el-icon-download"
                    size="mini"
                    type="primary"
                  >
                    Download
                  </el-button>
                </el-col>
              </el-row>
            </template>
          </el-table-column>
          <el-table-column label="Scoring Criteria 1 Name">
            <el-table-column label="Quality">
              <el-table-column
                label="Admin Quality"
                prop="qualityScore"
                width="150"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="Cost">
              <el-table-column
                label="Admin Cost"
                prop="costScore"
                width="150"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="Timeline">
              <el-table-column
                label="Admin Timeline"
                prop="timelineScore"
                width="150"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="Packaging">
              <el-table-column
                label="Admin Packaging"
                prop="packagingScore"
                width="150"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="Morale">
              <el-table-column
                label="Admin Morale"
                prop="moraleScore"
                width="150"
              ></el-table-column>
            </el-table-column>     
          </el-table-column>
          <el-table-column
            label="Total Score"
            prop="totalScore"
            width="120"
          ></el-table-column>
          <el-table-column
            label="Score Ranking"
            prop="scoreRanking"
            width="120"
          ></el-table-column>
        </el-table>
        <el-divider content-position="left">
          <h4>Select Vendor to the Next Phase</h4>
        </el-divider>
        <el-row :gutter="24">
          <el-col
            :xs="24"
            :sm="18"
            :lg="12"
          >
            <el-table
              border
              :data="evaluationResult"
              highlight-current-row
              size="mini"
              stripe
            >
              <el-table-column
                type="selection"
                width="56"
              ></el-table-column>
              <el-table-column
                label="Vendor"
                prop="vendorName"
                min-width="200"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                label="Total Score"
                prop="totalScore"
                min-width="120"
              ></el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-row style="margin-top: 16px">
          <el-col
            :xs="24"
            :sm="12"
            :lg="8"
          >
            <el-form-item
              label="Remark"
              prop="remark"
              style="margin-bottom: .5rem"
            >
              <el-input
                v-model="bidding.remark"
                :rows="3"
                type="textarea"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-scrollbar>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="bidding"
      :visible.sync="showDocumentActionConfirm"
    ></document-action-confirm>
  </div>
</template>
<script lang="ts" src="./bidding-evaluation-approval.component.ts"></script>
<style lang="scss">
.bidding-evaluation-approval {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;
  
  td.document-submission .cell {
    padding: 0;

    .el-row .el-col.border {
      border-bottom: none !important;
      border-left: none !important;
      padding: 4px 10px;

      &:last-child {
        border-right: none !important;
      }
    }

    .el-row:first-child .el-col.border {
      border-top: none !important;
    }
  }

  .el-divider--horizontal {
    margin-top: 32px;
    margin-bottom: 16px;
  }

  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }


  .toolbar {
    padding: 4px;
  }

  .vendor-scoring tbody td {
    height: 35px;
  }
}
.el-tabs__header {
  margin: 0px;
}

.el-table__fixed {
  box-shadow: none;
}

.main {
  padding: 0px;
}

.form-input {
  width: 100%;
}
</style>
