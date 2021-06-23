<template>
  <div class="confirmation-form">
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
            <el-form-item label="Confirmation No.">
              <el-input
                v-model="mainForm.confirmationNo"
              ></el-input>
            </el-form-item>
            <el-form-item label="Contract Start Date">
              <el-date-picker
                v-model="mainForm.contractStartDate"
                :disabled="isVendor"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="Contract End Date">
              <el-date-picker
                v-model="mainForm.contractEndDate"
                :disabled="isVendor"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Contract Attachment">
              <el-button
                class="btn-attachment"
                icon="el-icon-download"
                size="mini"
                type="primary"
                @click="downloadAttachment"
              >
                Download
              </el-button>
            </el-form-item>
            <el-form-item label="Contract Detail">
              <el-input
                v-model="mainForm.contractDetail"
                :autosize="{minRows: 2, maxRows: 7}"
                disabled
                style="margin-bottom: 5px"
                type="textarea"
              ></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-button
                size="mini"
                @click="viewHistory"
              >
                <svg-icon name="icomoo/078-history"></svg-icon> History
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-scrollbar>
    
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
  </div>
</template>

<script lang="ts" src="./confirmation-form.component.ts"></script>

<style lang="scss">
.compact {
  .confirmation-form {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }
  }
}

.confirmation-form {
  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }
}
</style>

<style lang="scss" scoped>
/* .confirmation-form {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px;
  }
} */
</style>
