<template>
  <div class="app-container">
    <h1 class="page-title">{{ $t('register.title') }}</h1>
    
    <el-steps align-center :active="active" finish-status="success">
      <el-step :title="$t('register.login.title')" />
      <el-step :title="$t('register.basic.basic.title')" />
      <el-step :title="$t('register.business.title')" />
      <el-step :title="$t('register.document.title')" />
      <el-step :title="$t('register.pic.title')" />
      <el-step :title="$t('register.payment.title')" />
      <el-step :title="$t('register.tax.header.title')" />
    </el-steps>
    <el-backtop target=".app-container" />
    <div :model="registration">
      <keep-alive>
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
          :businessCategory="registration.businessCategories"
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
        <payment-information
          class="step-panel"
          v-if="active === 5"
          :event-bus="eventBus"
          :payments="registration.payments"
        />
        <tax-information
          class="step-panel"
          v-if="active === 6"
          :event-bus="eventBus"
          :taxInformations="registration.taxInformations"
          :taxRates="registration.taxRates"
        />
        <summary-registration
          class="step-panel"
          v-if="active === 7"
          :event-bus="eventBus"
          :login="registration.loginDetails"
          :company="registration.companyProfile"
          :businessCategory="registration.businessCategories"
          :main-documents="registration.mainDocuments"
          :additional-documents="registration.additionalDocuments"
          :contacts="registration.contacts"
          :functionaries="registration.functionaries"
          :payments="registration.payments"
          :taxInformations="registration.taxInformations"
          :taxRates="registration.taxRates"
        />
      </keep-alive>
    </div>

    <p>
      <el-switch
          v-if="active === 7"
          v-model="submitAgree"
          required
          :active-text="$t('register.form.submit.info')">
      </el-switch>
    </p>

    <el-container class="steps-control-btn-group">
      
      <el-button
        round
        type="info"
        icon="el-icon-close"
        style="margin-bottom: 12px; align-text:left;"
        @click="back"
      >
        Back
      </el-button>
      <el-button
        round
        icon="el-icon-arrow-left"
        style="margin-bottom: 12px;"
        :disabled="active === 0"
        @click="previous"
      >
        Previous
      </el-button>
      
      <el-button
        round
        type="primary"
        style="margin-bottom: 12px;"
        :disabled="active === 7"
        v-if="active != 7"
        @click="next"
      >
        Next <i class="el-icon-arrow-right" />
      </el-button>

      <el-button
        round
        type="success"
        style="margin-bottom: 12px;"
        v-if="active === 7"
        :disabled="active != 7"
        :loading="loading"
        @click="submit"
      >
        Submit <i class="el-icon-arrow-right" />
      </el-button>

    </el-container>
    
    <div class="clearfix"></div>
  </div>
</template>
<script lang="ts" src="./steps-form.component.ts"></script>
<style lang="scss" scoped>
  .app-container {
    padding: 0px 50px 20px 50px;
  }
  .page-title {
    font-size: 2em;
    margin: 0px 20px 20px 20px;
  }
  .step-panel {
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
