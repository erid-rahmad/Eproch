<template>
    <div class="vendor-evaluation-detail ">
        <el-row :gutter="24">

            <el-form
                label-position="left"
                label-width="220px"
                size="mini"
                disabled="true"
            >
                <el-col span="24" >
                <el-col class="card" span="11" >
                    <el-divider content-position="left">
                        <h4>Sourcing And Contracts</h4>
                    </el-divider>
                    <el-form-item label="Awarded Spending" prop="referenceNo">
                        <el-input
                            v-model="analis.awardedSpending"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="Awarded Savings" prop="referenceNo">
                        <el-input
                            v-model="analis.awardedSavings"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="Event Invited" prop="referenceNo">
                        <el-input
                            ref="Event Invited"
                            v-model="analis.eventInvited"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="% Participant" prop="referenceNo">
                        <el-input
                            ref="% Participant"
                            v-model="analis.participant"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="Active Contracts" prop="referenceNo">
                        <el-input
                            ref="Active Contracts"
                            v-model="analis.activeContracts"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                </el-col>
                <el-col class="card" span="11">
                    <el-divider content-position="left">
                        <h4>Procedure And Pay</h4>
                    </el-divider>
                    <el-form-item label="PO Spend" prop="referenceNo">
                        <el-input
                            ref="PO Spend"
                            v-model="analis.poSpend"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="PO Count" prop="referenceNo">
                        <el-input
                            ref="PO Count"
                            v-model="analis.poCount"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="Invoice Spend" prop="referenceNo">
                        <el-input
                            ref="Invoice Spend"
                            v-model="analis.invoiceSpend"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="Invoice Count" prop="referenceNo">
                        <el-input
                            ref="Invoice Count"
                            v-model="analis.invoiceCount"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="% Invoice Without Exception" prop="referenceNo">
                        <el-input
                            ref="% Invoice Without Exception"
                            v-model="analis.invoiceWithoutException"
                            clearable
                            placeholder="Please Enter Reference No">
                        </el-input>
                    </el-form-item>
                </el-col>
                </el-col>
                <el-col span="24">
                <el-col class="card" span="11">
                    <el-divider content-position="left">
                        <h4>Active Contract</h4>
                    </el-divider>
                    <el-table
                        v-loading="processing"
                        :data="analis.activeContractObj"
                        border
                        highlight-current-row
                        size="mini"
                        stripe
                    >

                        <el-table-column label="Project Name" min-width="150" prop="name" sortable>
                        </el-table-column>
                        <el-table-column label="Effective Date" min-width="140" prop="startDate" show-overflow-tooltip
                                         sortable>
                        </el-table-column>

                        <el-table-column label="Expired Date" min-width="130" prop="expirationDate" show-overflow-tooltip
                                         sortable>
                        </el-table-column>
                        <el-table-column label="Contract Amount" min-width="130" prop="price" show-overflow-tooltip
                                         sortable>
                        </el-table-column>



                    </el-table>

                </el-col>
                <el-col class="card" span="11">
                    <el-divider content-position="left">
                        <h4>Vendor Performance Project Analysis</h4>
                    </el-divider>
                    <el-table
                        v-loading="processing"
                        :data="analis.performanceProjectAnalysis"
                        border
                        highlight-current-row
                        size="mini"
                        stripe
                    >
                        <el-table-column label="Project Name" min-width="150" prop="name" sortable>
                        </el-table-column>
                        <el-table-column label="PIC" min-width="140" prop="pic" show-overflow-tooltip
                                         sortable>
                        </el-table-column>

                        <el-table-column label="Start Date" min-width="130" prop="startDate" show-overflow-tooltip
                                         sortable>
                        </el-table-column>
                        <el-table-column label="Total Score" min-width="130" prop="totalScore" show-overflow-tooltip
                                         sortable>
                        </el-table-column>
                        <el-table-column label="Project Count" min-width="130" show-overflow-tooltip
                                         sortable>
                                         1
                        </el-table-column>
                    </el-table>

                </el-col>
                </el-col>
            </el-form>
        </el-row>
    </div>
</template>
<script lang="ts" src="./vendor-analis.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
    margin: 4px 0;
}
</style>
