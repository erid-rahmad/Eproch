<template>
  <div class="app-container bidding-form">

    <el-steps
      :active="active"
      finish-status="success"
      simple
      :space="10"
    >
      <el-step title="Bidding Information" />
      <el-step title="Bidding Schedule" />
      <el-step title="Vendor Invitation" />
      <el-step title="Vendor Scoring" />
    </el-steps>

    <keep-alive>
      <bidding-information
        v-if="active === 0"
        ref="step-0"
        class="step-panel"
        :edit-mode="editMode"
        :data="bidding"
        @saved="goToNextStep"
      ></bidding-information>

      <bidding-schedule
        v-if="active === 1"
        ref="step-1"
        class="step-panel"
        :edit-mode="editMode"
        :data="bidding"
        @saved="goToNextStep"
      ></bidding-schedule>

      <vendor-invitation
        ref="step-2"
        class="step-panel"
        v-if="active === 2"
        :edit-mode="editMode"
        :data="bidding"
        @saved="goToNextStep"
      ></vendor-invitation>

      <vendor-scoring
        ref="step-3"
        class="step-panel"
        v-if="active === 3"
        :edit-mode="editMode"
        :data="bidding"
        @saved="goToNextStep"
      ></vendor-scoring>
    </keep-alive>

    <el-container class="steps-control-btn-group">

      <el-button
        size="mini"
        type="info"
        icon="el-icon-close"
        style="margin-bottom: 12px; align-text:left;"
        @click="back"
      >
        Back
      </el-button>

      <el-button
        size="mini"
        icon="el-icon-arrow-left"
        style="margin-bottom: 12px;"
        :disabled="active === 0"
        @click="previous"
      >
        Previous
      </el-button>

      <el-button
        size="mini"
        type="primary"
        style="margin-bottom: 12px;"
        :disabled="active === 4"
        v-if="active != 4"
        @click="next"
      >
        Next <em class="el-icon-arrow-right"></em>
      </el-button>

      <el-button
        size="mini"
        type="success"
        style="margin-bottom: 12px;"
        v-if="active === 4"
        :disabled="! agreementAccepted"
        v-loading.fullscreen.lock="fullscreenLoading"
        @click="submit"
      >
        Submit <em class="el-icon-arrow-right"></em>
      </el-button>

    </el-container>

    <div class="clearfix"></div>
  </div>
</template>

<script lang="ts" src="./steps-form.component.ts"></script>

<style lang="scss">
.bidding-form {
  .el-steps--simple {
    .el-step.is-simple:not(:last-of-type) .el-step__title {
      max-width: 70%;
    }
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
}
</style>
