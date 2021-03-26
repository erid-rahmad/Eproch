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

    <bidding-information
      v-if="active === 0"
      ref="step-0"
      class="step-panel"
      :edit-mode="editMode"
      :data="bidding"
      @saved="goToNextStep"
      @error="showSaveDialog = false"
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

    <el-container class="steps-control-btn-group">

      <el-button
        icon="el-icon-close"
        size="small"
        style="margin-bottom: 12px; align-text:left;"
        @click="close"
      >
        Close
      </el-button>

      <el-button
        icon="el-icon-arrow-left"
        size="small"
        style="margin-bottom: 12px;"
        :disabled="active === 0"
        @click="previous"
      >
        Previous
      </el-button>

      <el-button
        size="small"
        style="margin-bottom: 12px;"
        type="primary"
        @click="next"
      >
        {{ nextButton }} <em class="el-icon-arrow-right"></em>
      </el-button>

    </el-container>

    <div class="clearfix"></div>
    <el-dialog
      width="30%"
      :visible.sync="showSaveDialog"
      title="Confirm Save"
    >
      <span>Do you want to save the document?</span>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          style="margin-left: 0px;"
          @click="showSaveDialog = false"
        >
          No
        </el-button>
        <el-button
          style="margin-left: 0px;"
          size="mini"
          icon="el-icon-check"
          type="primary"
          @click="saveStep"
        >
          {{ $t('entity.action.save') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./steps-form.component.ts"></script>

<style lang="scss">
.bidding-form {
  .el-steps--simple {
    background: #1890ff;

    .el-step__head {
      &.is-process {
        border-color: #fff;
        color: #fff;
      }

      &.is-success,
      &.is-wait {
        border-color: #f4f4f4;
        color: #f4f4f4;
      }
    }

    .el-step__title {
      &.is-process {
        color: #fff;
      }

      &.is-success,
      &.is-wait {
        color: #f4f4f4;
      }
    }

    .el-step.is-simple {
      .el-step__arrow::before,
      .el-step__arrow::after {
        background: #fff;
      }

      &:not(:last-of-type) .el-step__title {
        max-width: 70%;
      }
    }
  }

  .step-panel {
    //padding: 24px;

    &.hide {
    display: none;
    }
  }

  .steps-control-btn-group {
    padding: 16px 24px;
  }
}
</style>
