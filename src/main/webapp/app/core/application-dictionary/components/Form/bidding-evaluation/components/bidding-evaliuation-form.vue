<template>
    <div v-if="" class="bidding-evaluation">
        <el-row  class="toolbar">
            <el-col :span="24">
                <el-button  size="mini" style="margin-left: 10px"
                            type="danger" @click="close">
                    Close
                </el-button>
                <el-button v-for="MethodLine in VendorScoringLine" size="mini" style="margin-left: 10px"
                           type="primary" @click="setEvaluation(MethodLine)">
                    {{ changeCode(MethodLine.evaluationMethodLineName) }}
                </el-button>
            </el-col>
        </el-row>
        <div class="card-view">
            <el-form  v-if="FormMenu===0 || FormMenu===2" ref="productCatalog" label-position="left" label-width="130px"
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
                <EvaluationTeamDetailPrice :evaluationFormProp="evaluationFormProp" :SelectVendorScoringLine="SelectVendorScoringLine" :vendorId="vendorId"></EvaluationTeamDetailPrice>
            </div>
            <div v-if="FormMenu===2">
                <el-divider content-position="left">
                    <h4>Evaluation</h4>
                </el-divider>
                <EvaluationFormDetail :evaluationFormProp="evaluationFormProp"  :SelectVendorScoringLine="SelectVendorScoringLine" :vendorId="vendorId"></EvaluationFormDetail>
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
