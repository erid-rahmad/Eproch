<template>
    <div class="vendor-evaluation-detail ">
        <el-row :gutter="10"><!-- search section -->
            <el-col :span="24">
                <div class="card">
                    <el-form
                        label-position="left"
                        label-width="220px"
                        size="mini"
                    >
                        <el-form-item label="Period">
                            <el-col :span="8">
                                <el-select
                                    v-model="period"
                                    @change="refreshContent()"
                                    clearable
                                    filterable
                                    placeholder="Period"
                                    width="280"
                                >
                                    
                                    <el-option
                                        v-for="item in periodSelections"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    ></el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="10"><!-- form section -->
            <el-col :span="12">
                <div class="card">
                    <el-divider content-position="left">
                        <h4>Sourcing And Contracts</h4>
                    </el-divider>
                    <el-form
                        label-position="left"
                        label-width="220px"
                        size="mini"
                    >
                        <el-form-item label="Awarded Spending" prop="referenceNo">
                            <el-input
                                v-model="analis.awardedSpending"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Awarded Savings" prop="referenceNo">
                            <el-input
                                v-model="analis.awardedSavings"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Event Invited" prop="referenceNo">
                            <el-input
                                ref="Event Invited"
                                v-model="analis.eventInvited"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="% Participant" prop="referenceNo">
                            <el-input
                                ref="% Participant"
                                v-model="analis.participant"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Active Contracts" prop="referenceNo">
                            <el-input
                                ref="Active Contracts"
                                v-model="analis.activeContracts"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                    </el-form>
                </div>
                
            </el-col>
            <el-col :span="12">
                <div class="card">
                    <el-divider content-position="left">
                        <h4>Procedure And Pay</h4>
                    </el-divider>
                    <el-form
                        label-position="left"
                        label-width="220px"
                        size="mini"
                    >
                        <el-form-item label="PO Spend" prop="referenceNo">
                            <el-input
                                ref="PO Spend"
                                v-model="analis.poSpend"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="PO Count" prop="referenceNo">
                            <el-input
                                ref="PO Count"
                                v-model="analis.poCount"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Invoice Spend" prop="referenceNo">
                            <el-input
                                ref="Invoice Spend"
                                v-model="analis.invoiceSpend"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="Invoice Count" prop="referenceNo">
                            <el-input
                                ref="Invoice Count"
                                v-model="analis.invoiceCount"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="% Invoice Without Exception" prop="referenceNo">
                            <el-input
                                ref="% Invoice Without Exception"
                                v-model="analis.invoiceWithoutException"
                                clearable
                                disabled
                                placeholder="Please Enter Reference No">
                            </el-input>
                        </el-form-item>
                    </el-form>
                </div>
                
            </el-col>
        </el-row>
        <el-row :gutter="10"><!-- table section -->
            <el-col :span="12">
                <div class="card table-data">
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
                        <el-table-column 
                            label="Project Name" 
                            min-width="250" 
                            prop="name" 
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="Effective Date" 
                            min-width="140" 
                            prop="startDate" 
                            show-overflow-tooltip
                            sortable>
                        </el-table-column>
                        <el-table-column 
                            label="Expired Date" 
                            min-width="130" 
                            prop="expirationDate" 
                            show-overflow-tooltip
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="Contract Amount" 
                            min-width="130" 
                            prop="price" 
                            show-overflow-tooltip
                            sortable
                        >
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="card table-data">
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
                        <el-table-column 
                            label="Project Name" 
                            min-width="250" 
                            prop="name" 
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="PIC" 
                            min-width="140" 
                            prop="pic" 
                            show-overflow-tooltip
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="Start Date" 
                            min-width="130" 
                            prop="startDate" 
                            show-overflow-tooltip
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="Total Score" 
                            min-width="130" 
                            prop="totalScore" 
                            show-overflow-tooltip
                            sortable
                        >
                        </el-table-column>
                        <el-table-column 
                            label="Project Count" 
                            min-width="130" 
                            show-overflow-tooltip
                            sortable
                        >
                            1
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="10">
            <el-col :span="24">
                <div class="card">
                    <!-- <div id="echarts" style="height:400px;"></div> -->
                    <canvas :id="chartId" width="100%" height="50"></canvas>
                </div>
                
            </el-col>
        </el-row>
    </div>
</template>
<script lang="ts" src="./vendor-analis.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
    margin: 4px 0;
}

.table-data {
   height: 400px;
}

</style>
