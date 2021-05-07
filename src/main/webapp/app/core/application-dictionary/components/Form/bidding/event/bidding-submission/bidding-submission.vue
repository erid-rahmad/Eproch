<template>
  <div class="app-container card-view bidding-submission">
    <div class="toolbar">
      <el-button
        v-if="!submissionPage"
        icon="el-icon-back"
        size="mini"
        type="danger"
        @click="closeProposalPage"
      >
        Back
      </el-button>

      <el-divider
        v-if="!submissionPage"
        direction="vertical"
      ></el-divider>

      <el-button
        v-if="showAdministrationProposal && submissionPage"
        size="mini"
        type="primary"
        @click="openAdministrationProposal"
      >
        Administration Proposal
      </el-button>
      <el-button
        v-if="showTechnicalProposal && submissionPage"
        size="mini"
        type="primary"
        @click="openTechnicalProposal"
      >
        Technical Proposal
      </el-button>
      <el-button
        v-if="showPriceProposal && submissionPage"
        size="mini"
        type="primary"
        @click="openPriceProposal"
      >
        Price Proposal
      </el-button>
    </div>

    <div class="card">
      <submission-form
        v-if="submissionPage"
        @data-loaded="onSubmissionFormLoaded"
      ></submission-form>

      <administration-proposal
        v-else-if="administrationProposalPage"
      ></administration-proposal>

      <price-proposal
        v-else-if="priceProposalPage"
      ></price-proposal>

      <technical-proposal
        v-else-if="technicalProposalPage"
      ></technical-proposal>
    </div>
  </div>
</template>
<script lang="ts" src="./bidding-submission.component.ts"></script>
<style lang="scss">
.compact .bidding-submission {
  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;

    .el-button + .el-button {
      margin-left: 0;
    }
  }
}
</style>