<template>
  <div class="submission-form">
    <div v-if="page === 1">
      <el-divider content-position="left">
        <h4>Bidding Submission</h4>
      </el-divider>
      <el-form
        ref="biddingInformation"
        label-position="left"
        label-width="200px"
        size="mini"
      >
        <el-row :gutter="24">
          <el-col
            :xs="24"
            :sm="18"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Bidding No">
              <el-input
                v-model="mainForm.biddingNo"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Title">
              <el-input
                v-model="mainForm.biddingName"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="Bidding Type">
              <el-input
                v-model="mainForm.biddingType"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              v-if="!isVendor"
              label="Vendor"
            >
              <el-select
                v-model="mainForm.vendorId"
                class="form-input"
                clearable
                filterable
              >
                <el-option
                  v-for="item in vendorOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="18"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Submision Start Date">
              <el-date-picker
                v-model="mainForm.actualStartDate"
                disabled
                :format="dateDisplayFormat"
                size="mini"
                type="datetime"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="Submision End Date">
              <el-date-picker
                v-model="mainForm.actualEndDate"
                disabled
                :format="dateDisplayFormat"
                size="mini"
                type="datetime"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="Remaining Time">
              <span>{{ timeRemaining }}</span>
            </el-form-item>
            <el-form-item
              v-if="!isVendor"
              label="Date Submitted"
            >
              <el-date-picker
                v-model="mainForm.dateSubmit"
                disabled
                :format="dateDisplayFormat"
                size="mini"
                type="datetime"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" src="./submission-form.component.ts"></script>
