<template>
  <div class="app-container">
    <h1 class="page-title">{{ $t('register.title') }}</h1>
    <el-steps align-center :active="active">
      <el-step title="Login Details" />
      <el-step title="Basic Information" />
      <el-step title="Supporting Documents" />
      <el-step title="PIC" />
      <el-step title="Payment Information" />
      <el-step title="Tax Information" />
    </el-steps>
    <el-backtop target=".app-container" />
    <div :model="registration">
      <login-details
        class="step-panel"
        :class="{'hide': active !== 0}"
        :event-bus="eventBus"
        :login="registration.loginDetails"
      />
      <company-profile
        class="step-panel"
        :class="{'hide': active !== 1}"
        :event-bus="eventBus"
        :company="registration.companyProfile"
      />
      <supporting-documents
        class="step-panel"
        :class="{'hide': active !== 2}"
        :event-bus="eventBus"
        :main-documents="registration.mainDocuments"
        :additional-documents="registration.additionalDocuments"
      />
    </div>
    <el-container class="steps-control-btn-group">
      <el-button
        round
        icon="el-icon-arrow-left"
        style="margin-top: 12px;"
        :disabled="active === 0"
        @click="previous"
      >
        Previous
      </el-button>
      <el-button
        round
        type="primary"
        style="margin-top: 12px;"
        :disabled="active === 2"
        @click="next"
      >
        Next <i class="el-icon-arrow-right" />
      </el-button>
    </el-container>
    <div class="clearfix"></div>
  </div>
</template>
<script lang="ts" src="./steps-form.component.js"></script>
<style scoped>
  .app-container .page-title {
    font-size: 2em;
    margin: 24px 24px 48px;
  }
  .step-panel {
    margin-top: 24px;
    padding: 24px;
  }
  .step-panel.hide {
    display: none;
  }
  .steps-control-btn-group {
    float: right;
    padding: 0 24px;
  }
</style>
