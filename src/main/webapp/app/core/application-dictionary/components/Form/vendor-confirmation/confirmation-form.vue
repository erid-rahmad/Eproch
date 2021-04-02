<template>
  <div class="confirmation-form">
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
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Contract No.">
              <el-input
                v-model="mainForm.contractNo"
              ></el-input>
            </el-form-item>
            <el-form-item label="Contract Start Date">
              <el-input
                v-model="mainForm.startDate"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Contract End Date">
              <el-input
                v-model="mainForm.endDate"
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
            <el-form-item label="Contract Attachment">
              <el-button
                class="btn-attachment"
                icon="el-icon-download"
                size="mini"
                type="primary"
              >
                Download
              </el-button>
            </el-form-item>
            <el-form-item label="Contract Detail">
              <el-input
                v-model="mainForm.remark"
                :autosize="{minRows: 2, maxRows: 7}"
                disabled
                style="margin-bottom: 5px"
                type="textarea"
              ></el-input>
            </el-form-item>
            <el-form-item label="">
              <el-button
                size="mini"
                @click="showHistory = true"
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
          label="Contract No."
          prop="contractNo"
          min-width="100"
        ></el-table-column>
        <el-table-column
          label="Modified Date"
          prop="lastModifiedDate"
          min-width="100"
        ></el-table-column>
        <el-table-column
          label="Status"
          prop="status"
          min-width="100"
        ></el-table-column>
        <el-table-column
          label="Reason"
          prop="reason"
          min-width="200"
        ></el-table-column>
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
