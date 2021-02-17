<template>
  <div class="app-container">

    <el-steps :space="10" simple :active="active" finish-status="success" >
      <el-step title="Bidding Information" />
      <el-step title="Bidding Schedule" />
      <el-step title="Vendor Invitation" />
      <el-step title="Vendor Scoring" />

    </el-steps>

    <el-backtop target=".app-container" />

    <div :model="bidding">
      <keep-alive>
        <bidding-information
          class="step-panel"
          v-if="active === 0"
          :biddingInformation="bidding.biddingInformation"/>

        <bidding-schedule
          class="step-panel"
          v-if="active === 1"
          />

        <vendor-invitation
          class="step-panel"
          v-if="active === 2"
          />

        <vendor-scoring
          class="step-panel"
          v-if="active === 3"
          />
      </keep-alive>
    </div>

    <el-container class="steps-control-btn-group">

      <el-button
        size="small"
        type="info"
        icon="el-icon-close"
        style="margin-bottom: 12px; align-text:left;"
        @click="back">
        Back
      </el-button>

      <el-button
        size="small"
        icon="el-icon-arrow-left"
        style="margin-bottom: 12px;"
        :disabled="active === 0"
        @click="previous">
        Prev
      </el-button>

      <el-button
        size="small"
        type="primary"
        style="margin-bottom: 12px;"
        :disabled="active === 4"
        v-if="active != 4"
        @click="next">
        Next <em class="el-icon-arrow-right"></em>
      </el-button>

      <el-button
        size="small"
        type="success"
        style="margin-bottom: 12px;"
        v-if="active === 4"
        :disabled="! agreementAccepted"
        v-loading.fullscreen.lock="fullscreenLoading"
        @click="submit">
        Submit <em class="el-icon-arrow-right"></em>
      </el-button>

    </el-container>

    <div class="clearfix"></div>
  </div>
</template>

<script lang="ts" src="./steps-form.component.ts"></script>

<style lang="scss" scoped>
    .el-steps--simple {
        padding: 10px 1%;
    }

    .step-panel {
        //padding: 24px;

        &.hide {
        display: none;
        }
    }

    .steps-control-btn-group {
        float: right;
        padding: 0 24px;
    }
</style>
