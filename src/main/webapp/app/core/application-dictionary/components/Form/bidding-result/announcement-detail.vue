<template>
  <div class="bidding-result-announcement-detail">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="data"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :span="8">
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
            <el-input
              v-model="data.totalScore"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="Contract Amount">
            <el-input
              v-model="data.contractAmount"
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
