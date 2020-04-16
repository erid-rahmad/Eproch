<template>
  <div class="app-container">
    <h1 class="page-title">{{ $t('register.title') }}</h1>
    <el-steps align-center :active="active">
      <el-step title="Login Details" />
      <el-step title="Basic Information" />
      <el-step title="Business Categories" />
      <el-step title="Supporting Documents" />
      <el-step title="PIC" />
      <el-step title="Payment Information" />
      <el-step title="Tax Information" />
    </el-steps>
    <el-backtop target=".app-container" />
    <div :model="registration">
      <login-details
        class="step-panel"
        v-if="active === 0"
        :event-bus="eventBus"
        :login="registration.loginDetails"
      />
      <company-profile
        class="step-panel"
        v-if="active === 1"
        :event-bus="eventBus"
        :company="registration.companyProfile"
      />
      <business-categories
        class="step-panel"
        v-if="active === 2"
        :event-bus="eventBus"
        :business-categories="registration.businessCategories"
      />
      <supporting-documents
        class="step-panel"
        v-if="active === 3"
        :event-bus="eventBus"
        :main-documents="registration.mainDocuments"
        :additional-documents="registration.additionalDocuments"
      />
      <person-in-charge
        class="step-panel"
        v-if="active === 4"
        :event-bus="eventBus"
        :contacts="registration.contacts"
        :functionaries="registration.functionaries"
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
        :disabled="active === 6"
        @click="next"
      >
        Next <i class="el-icon-arrow-right" />
      </el-button>
    </el-container>
    <div class="clearfix"></div>
  </div>
</template>
<script lang="ts" src="./steps-form.component.js"></script>
<style lang="scss" scoped>
  .app-container .page-title {
    font-size: 2em;
    margin: 24px 24px 48px;
  }
  .step-panel {
    margin-top: 24px;
    padding: 24px;

    &.hide {
      display: none;
    }
  }
  .steps-control-btn-group {
    float: right;
    padding: 0 24px;
  }
</style>
