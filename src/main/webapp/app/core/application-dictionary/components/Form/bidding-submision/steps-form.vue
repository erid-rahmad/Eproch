<template>
  <div class="app-container">

    <el-steps :space="10" simple :active="active" finish-status="success" >
      <el-step title="Project Information" />
      <el-step title="More Information" />      
      <el-step title="Event Schedule" />
      <el-step title="Vendor Scoring" />
    </el-steps>

    <el-backtop target=".app-container" />

    <div :model="bidding">
      <bidding-schedule
          class="step-panel"
          v-if="active === 0"
          :ItemDetail="ItemDetail"
          :biddingrow="biddingrow"
          :biddingInformation="bidding.biddingInformation"
          :biddingSchedule="bidding.biddingSchedule"/>

        <item-detail
          class="step-panel"
          v-if="active === 1"
          :biddingrow="biddingrow"
          :ItemDetail="ItemDetail"
          :biddingInformation="bidding.biddingInformation"/> 

        <vendor-invitation
          class="step-panel"
          v-if="active === 2"
          :biddingInformation="bidding.biddingInformation"
          :vendorInvitation="bidding.vendorInvitation"/>

        <vendor-scoring
          class="step-panel"
          v-if="active === 3"
          :biddingInformation="bidding.biddingInformation"
          :vendorScoring="bidding.vendorScoring"/>
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
        Previous
      </el-button>

      <el-button
        size="small"
        type="primary"
        style="margin-bottom: 12px;"
        :disabled="active === 3"
        v-if="active != 3"
        @click="next">
        Next <em class="el-icon-arrow-right"></em>
      </el-button>

      <el-button
        size="small"
        type="success"
        style="margin-bottom: 12px;"
        v-if="active === 3"
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
