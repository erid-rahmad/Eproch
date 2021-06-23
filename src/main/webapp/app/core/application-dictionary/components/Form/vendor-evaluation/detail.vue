<template>
    <div class="vendor-evaluation-detail card-view">
        <el-form
            ref="mainForm"
            v-loading="loading"
            :model="evaluation"
            :rules="validationSchema"
            label-position="left"
            label-width="150px"
            size="mini"
        >
            <el-row :gutter="24">
                <el-col :span="8">
                    <el-form-item
                        label="Evaluation No."
                        prop="evaluationNo"
                    >
                        <el-input
                            v-model="evaluation.documentNo"
                            class="form-input"
                            clearable
                        ></el-input>
                    </el-form-item>
                    <el-form-item
                        label="Reviewer"
                        prop="reviewer"
                    >
                        <ad-input-lookup
                            v-model="evaluation.reviewerUserId"
                            :label-fields="['userLogin']"
                            :query="['employee.equals=true']"
                            placeholder="Select Reviewer"
                            table-name="ad_user"
                        ></ad-input-lookup>
                    </el-form-item>

                    <el-form-item
                        label="Vendor Name"
                        prop="vendorId"
                        v-if="!evaluation.vendorName"
                    >
                        <ad-input-lookup
                            v-model="evaluation.vendorId"
                            lookup-by-field="name"
                            placeholder="Select Vendor"
                            table-name="c_vendor"
                            @change="retriveContractFilter(evaluation.vendorId)"
                        ></ad-input-lookup>
                    </el-form-item>

                    <el-form-item
                        v-if="evaluation.vendorName"
                        label="Vendor Name"
                        prop="vendorId"
                    >
                        <el-input
                            v-model="evaluation.vendorName"
                            class="form-input"
                            disabled
                            clearable
                        ></el-input>
                    </el-form-item>

                    <el-form-item
                        label="Contract No."
                        prop="contractId"
                        v-if="!evaluation.contractNo"
                    >
                        <template>
                            <el-select v-model="evaluation.contractId"  @change="onContractIdChanged"  placeholder="Select">
                                <el-option
                                    v-for="item in ContractFilter"
                                    :key="item.id"
                                    :label="item.documentNo"
                                    :value="item.id">

                                </el-option>
                            </el-select>
                        </template>
                    </el-form-item>
                    <el-form-item
                        label="Contract No."
                        prop="contractId"
                        v-if="evaluation.contractNo"
                    >
                        <el-input
                            v-model="evaluation.contractNo"
                            class="form-input"
                            disabled
                            clearable
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item
                        label="Evaluation Type"
                        prop="evaluationType"
                    >
                        <el-input
                            v-model="evaluation.evaluationTypeName"
                            class="form-input"
                            disabled
                            clearable
                        ></el-input>
                    </el-form-item>
                    <el-form-item
                        label="Evaluation Period"
                        prop="evaluationPeriod"
                    >
                        <ad-input-list
                            v-model="evaluation.contractEvaluationPeriod"
                            disabled
                            placeholder="Select Evaluation Period"
                            reference-key="vendorEvaluationPeriod"
                        ></ad-input-list>
                    </el-form-item>
                    <el-form-item
                        label="Evaluation Date"
                        prop="evaluationDate"
                    >
                        <el-date-picker
                            v-model="evaluation.evaluationDate"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            class="form-input"
                            clearable
                            placeholder="Pick a date"
                            size="mini"
                            style="width: 100%"
                            type="date"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item
                        label="Total Score"
                        prop="score"
                    >
                        <el-input-number
                            v-model="evaluation.score"
                            :precision="2"
                            class="form-input"
                            clearable
                            controls-position="right"
                            disabled
                        ></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-table
            ref="lines"
            :data="lines"
            border
            class="evaluation-line-table"
            highlight-current-row
            size="mini"
            stripe
        >

            <el-table-column
                label="Question Category"
                min-width="80"
                prop="cQuestionCategoryName"
                show-overflow-tooltip
            ></el-table-column>

            <el-table-column
                label="Question"
                min-width="180"

                prop="question"
                show-overflow-tooltip
            ></el-table-column>

            <el-table-column
                label="Score %"
                min-width="250"

            >
                <template slot-scope="{ row }">
                    <el-slider v-model="row.score" :marks="marks" @change="onRateChanged"></el-slider>
                </template>
            </el-table-column>

            <el-table-column
                label="Remark"
                min-width="150"
            >
                <template slot-scope="{ row }">
                    <el-input
                        v-model="row.remark"
                        :maxlength="255"
                        class="remark"
                        clearable
                        size="mini"
                    ></el-input>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script lang="ts" src="./detail.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
    margin: 4px 0;
}
</style>
