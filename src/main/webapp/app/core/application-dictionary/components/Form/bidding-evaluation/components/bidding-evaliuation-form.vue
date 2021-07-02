<template>
    <div class="bidding-evaluation card-view">

        <el-row class="toolbar">
            <el-col :span="24">
                <el-button size="mini"
                           style="margin-left: 10px"
                           type="danger"
                           @click="close">
                    Close
                </el-button>
                <el-button v-if="button===0"
                           size="mini"
                           style="margin-left: 10px"
                           type="primary"
                           @click="dialogSubmitEvaluation=true">
                    Submit
                </el-button>
                <el-dialog
                    :visible.sync="dialogSubmitEvaluation"
                    title="Tips"
                    width="30%">
                    <span>Submit Evaluation ?</span>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogSubmitEvaluation = false">Cancel</el-button>
                    <el-button type="primary" @click="submitEvaluation">Confirm</el-button>
                </span>
                </el-dialog>
                <el-button v-if="button===1"
                           size="mini"
                           style="margin-left: 10px"
                           type="primary"
                           @click="dialogApproveEvaluation=true">
                    Approve
                </el-button>
                <el-dialog
                    :visible.sync="dialogApproveEvaluation"
                    title="Tips"
                    width="30%">
                    <span>Approve Evaluation ?</span>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogApproveEvaluation=false">Cancel</el-button>
                    <el-button type="primary" @click="approveEvaluation">Confirm</el-button>
                </span>
                </el-dialog>
                <el-button v-if="button===1"
                           size="mini"
                           style="margin-left: 10px"
                           type="primary"
                           @click="dialogRejectEvaluation=true">
                    Reject
                </el-button>
                <template>
                    <el-dialog
                        :visible.sync="dialogRejectEvaluation"
                        title="Tips"
                        width="30%">
                        <span>Reject Evaluation ?</span>
                        <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogRejectEvaluation=false">Cancel</el-button>
                    <el-button type="primary" @click="rejectEvaluation">Confirm</el-button>
                </span>
                    </el-dialog>
                </template>

                <el-button v-if="button===2"
                           disabled="true"
                           size="mini"
                           style="margin-left: 10px"
                           type="danger"
                >
                    Rejected
                </el-button>
                <el-button v-if="button===3"
                           disabled="true"
                           size="mini"
                           style="margin-left: 10px"
                           type="primary"
                >
                    Approved
                </el-button>
                <el-button v-for="MethodLine in VendorScoringLine"
                           size="mini" style="margin-left: 10px" :key="MethodLine.id"
                           type="primary"
                           @click="setEvaluation(MethodLine)">
                    {{ changeCode(MethodLine.evaluationMethodLineName) }}
                </el-button>
            </el-col>
        </el-row>
        <div class="card">
            <header v-if="title" v-bind:style="{'padding-bottom':FormMenu===1?'20px':'0px'}">
                <h3 align="center">{{changeCode(title)}} Proposal</h3>
            </header>
            <el-form v-if="FormMenu===0 || FormMenu===2|| FormMenu===3" ref="productCatalog" label-position="left"
                     label-width="130px"
                     size="mini">
                <el-form-item label="Bidding No">
                    {{ evaluation.biddingNo }}
                </el-form-item>
                <el-form-item label="Bidding Name">
                    {{ evaluation.biddingName }}
                </el-form-item>
                <el-form-item label="Bidding Type">
                    {{ evaluation.biddingTypeName }}
                </el-form-item>
                <el-form-item label="Event Type">
                    {{ evaluation.eventTypeName }}
                </el-form-item>
                <el-form-item label="Vendor Name">
                    {{ evaluation.vendorName }}
                </el-form-item>
            </el-form>
            <div v-if="FormMenu===1">
                <EvaluationTeamDetailPrice ref="evaluationFormDetail" :evaluationFormProp="evaluationFormProp"
                                           :readOnly="readOnly" ></EvaluationTeamDetailPrice>
            </div>
            <div v-if="FormMenu===2">
                <h4 align="center">Evaluation</h4>
                <EvaluationFormDetail :SelectVendorScoringLine="SelectVendorScoringLine" :evaluationFormProp="evaluationFormProp"
                                      :readOnly="readOnly" :title="title" ></EvaluationFormDetail>
            </div>

        </div>
    </div>
</template>

<script lang="ts" src="./bidding-evaliuation-form.component.ts"></script>

<style lang="scss">
.bidding-evaluation {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    td.document-submission .cell {
        padding: 0;

        .el-row .el-col.border {
            border-bottom: none !important;
            border-left: none !important;
            padding: 4px 10px;

            &:last-child {
                border-right: none !important;
            }
        }

        .el-row:first-child .el-col.border {
            border-top: none !important;
        }
    }

    .el-divider--horizontal {
        margin-top: 32px;
        margin-bottom: 16px;
    }

    .form-wrapper {
        .el-scrollbar__wrap {
            overflow-x: hidden;
            padding: 15px;
        }
    }


    .toolbar {
        padding: 4px;
    }

    .vendor-scoring tbody td {
        height: 35px;
    }
}

.el-tabs__header {
    margin: 0px;
}

.el-table__fixed {
    box-shadow: none;
}

.main {
    padding: 0px;
}

.form-input {
    width: 100%;
}

</style>
