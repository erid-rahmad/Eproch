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

<!--            <el-button-->
<!--                v-if="submissionPage && isVendor"-->
<!--                :disabled="submitted"-->
<!--                size="mini"-->
<!--                type="primary"-->
<!--                @click="submitProposals"-->
<!--            >-->
<!--                <svg-icon name="guide"></svg-icon>-->
<!--                Submit-->
<!--            </el-button>-->

            <el-button
                v-if="!submissionPage "
                :loading="loading"
                size="mini"
                type="primary"
                @click="saveProposal"
            >
                <svg-icon name="icomoo/273-checkmark"></svg-icon>
                Save
            </el-button>

            <el-button
                v-if="!submissionPage "
                :loading="loading"
                size="mini"
                type="primary"
                @click="submitSaveProposal"
            >
                <svg-icon name="guide"></svg-icon>
                Submit
            </el-button>

            <el-divider
                v-if="!submissionPage"
                direction="vertical"
            ></el-divider>

            <el-button
                v-for="proposal in displayedProposals"
                :key="proposal.id"
                :disabled="!submission.id"
                size="mini"
                type="primary"
                @click="openProposalForm(proposal)"
            >
                {{ printEvaluation(proposal.evaluationMethodLineEvaluation) }} Proposal tes
            </el-button>

        </div>

        <div class="card">
            <submission-form
                v-if="submissionPage"
                ref="submissionForm"
                :submission.sync="submission"
                :scheduleFromGrid="scheduleFromGrid"
                @data-loaded="onSubmissionFormLoaded"
            ></submission-form>

            <component
                :is="proposalComponent"
                v-else
                ref="proposalForm"
                :data="selectedProposal"
                :disabled="submitted"
                :loading.sync="loading"
                :schedule="schedule"
                :submission-id="submission.id"
                :scheduleFromGrid="scheduleFromGrid"
            ></component>
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
            margin-left: 8px;
        }
    }
}
</style>
