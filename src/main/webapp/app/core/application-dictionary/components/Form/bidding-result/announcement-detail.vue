<template>
  <div class="bidding-result-announcement-detail">
    <el-form
      ref="mainForm"
      disabled
      label-position="left"
      label-width="200px"
      :model="data"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Bidding No.">
            <el-input
              v-model="data.biddingNo"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding Title">
            <el-input
              v-model="data.biddingTitle"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding Type">
            <el-input
              v-model="data.biddingType"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Selected Winner">
            <el-input
              v-model="data.winner"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Total Score">
            <el-input-number
              v-model="data.totalScore"
              controls-position="right"
              :max="100"
              :min="0"
              size="mini"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="Contract Amount">
            <el-input
              v-model="data.contractAmount"
              v-inputmask="{'alias': 'currency'}"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Currency">
            <el-input
              v-model="data.currencyName"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./announcement-detail.component.ts"></script>
