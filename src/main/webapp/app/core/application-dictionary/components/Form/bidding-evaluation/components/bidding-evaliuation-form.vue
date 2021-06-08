<template>
    <div v-if="" class="bidding-evaluation card-view">
        <el-row  class="toolbar">
            <el-col :span="24">
                <el-button  size="mini"
                            style="margin-left: 10px"
                            type="danger"
                            @click="close">
                    Close
                </el-button>
                <el-button  size="mini"
                            v-if="button===0"
                            style="margin-left: 10px"
                            type="primary"
                            @click="submitEvaluation">
                    Submit
                </el-button>
                <el-button  size="mini"
                            v-if="button===1"
                            style="margin-left: 10px"
                            type="primary"
                            @click="approveEvaluation">
                    Approve
                </el-button>
                <el-button  size="mini"
                            v-if="button===1"
                            style="margin-left: 10px"
                            type="primary"
                            @click="rejectEvaluation">
                    Reject
                </el-button>
                <el-button  size="mini"
                            v-if="button===2"
                            style="margin-left: 10px"
                            type="danger"
                            disabled="true"
                            >
                    Rejected
                </el-button>
                <el-button  size="mini"
                            v-if="button===3"
                            style="margin-left: 10px"
                            type="primary"
                            disabled="true"
                           >
                    Approved
                </el-button>
                <el-button v-for="MethodLine in VendorScoringLine"
                           size="mini" style="margin-left: 10px"
                           type="primary"
                           @click="setEvaluation(MethodLine)">
                    {{ changeCode(MethodLine.evaluationMethodLineName) }}
                </el-button>
            </el-col>
        </el-row>
        <div class="card">
            <el-form  v-if="FormMenu===0 || FormMenu===2|| FormMenu===3" ref="productCatalog" label-position="left" label-width="130px"
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
            <div v-if="FormMenu===1" >
                <EvaluationTeamDetailPrice ref="evaluationFormDetail" :evaluationFormProp="evaluationFormProp" :readOnly="readOnly" ></EvaluationTeamDetailPrice>
            </div>
            <div v-if="FormMenu===2">
                <el-divider content-position="left">
                    <h4>Evaluation</h4>
                </el-divider>
                <EvaluationFormDetail :evaluationFormProp="evaluationFormProp" :readOnly="readOnly" :SelectVendorScoringLine="SelectVendorScoringLine" ></EvaluationFormDetail>
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
