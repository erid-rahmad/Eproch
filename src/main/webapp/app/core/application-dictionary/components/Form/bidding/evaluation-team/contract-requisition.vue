<template>
  <div class="contract-requisition">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="mainForm"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Requisition No.">
            <el-input
              v-model="mainForm.requisitionNo"
              class="form-input"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding No.">
            <el-input
              v-model="mainForm.biddingNo"
              class="form-input"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Title">
            <el-input
              v-model="mainForm.biddingTitle"
              class="form-input"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="PiC">
            <el-input
              v-model="mainForm.pic"
              class="form-input"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col
        :xs="24"
        :sm="24"
        :lg="20"
        :xl="18"
      >
        <el-table
          ref="documentTable"
          border
          class="document-table"
          :data="documents"
          highlight-current-row
          :max-height="gridSchema.maxHeight"
          size="mini"
          stripe
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
            label="Form Name"
            prop="name"
            width="200"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            label="Detail Form"
            width="150"
            show-overflow-tooltip
          >
            <template slot-scope="{ row }">
              <el-button
                class="btn-attachment"
                icon="el-icon-download"
                size="mini"
                type="primary"
                @click="downloadAttachment(row)"
              >
                Download
              </el-button>
            </template>
          </el-table-column>

          <el-table-column
            label="Action"
            width="100"
          >
            <template slot-scope="{ row }">
              <el-checkbox v-model="row.active"></el-checkbox>
            </template>
          </el-table-column>

          <el-table-column
            label="Remark"
            width="250"
          >
            <template slot-scope="{ row }">
              <el-input
                v-model="row.remark"
                size="mini"
                placeholder="Remark"
              ></el-input>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./contract-requisition.component.ts"></script>
<style lang="scss" scoped>
.compact .el-table .el-button {
  margin: 5px 0;
}
</style>
