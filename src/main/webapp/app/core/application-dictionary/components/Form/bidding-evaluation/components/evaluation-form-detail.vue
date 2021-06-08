<template>
    <div class="prequalification-form">
        <el-form ref="mainForm" v-loading="loadingAll" label-position="left" label-width="200px" size="mini">
            <el-row
                v-for="(criteria, index) in evaluationMethodCriteria"
                :key="criteria.id"
                :class="`criteria-${index}`"
                class="criteria-section"
            >
                <el-col :span="24">
                    <el-form-item
                        class="criteria-label"
                        label="Criteria"
                    >
                        {{ criteria.biddingCriteriaName }}
                    </el-form-item>
                    <el-row
                        v-for="(subCriteria, subIndex) in criteria.evalMethodSubCriteriaList"
                        :key="subCriteria.id"
                        :class="`sub-${subIndex}`"
                        class="sub-criteria-section"
                    >
                        <el-col :span="24">
                            <el-row>
                                <el-col :span="12">
                                    <div class="grid-content bg-purple">
                                        <el-form-item label="Sub Criteria">
                                            {{ subCriteria.biddingSubCriteriaName }}

                                        </el-form-item>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row
                                v-for="(biddingSubCriteria, subsubindex) in subCriteria.biddingSubCriteriaDTO"
                                :key="biddingSubCriteria.id"
                                :class="`sub-${subsubindex}`"
                                class="sub-sub-criteria-section"
                            >
                                <el-col style="text-align: right;padding-right: 40px;padding-bottom: 5px">
                                    <el-button v-if="biddingSubCriteria.attachmentName" icon="el-icon-view" size="mini"
                                               type="primary"
                                               @click="handlePreview(biddingSubCriteria)">
                                        {{ biddingSubCriteria.attachmentName }}
                                    </el-button>
                                </el-col>
                                <el-table
                                    :data="biddingSubCriteria.criteriaLineDTO" border
                                    class="question-list"
                                    highlight-current-row
                                    size="mini"
                                >
                                    <el-table-column
                                        label="No."
                                        width="50"
                                    >
                                        <template slot-scope="{ $index }">
                                            {{ $index + 1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        label="Question"
                                        min-width="320"
                                        prop="name"
                                        show-overflow-tooltip
                                    ></el-table-column>
                                    <el-table-column
                                        label="Requirement"
                                        width="150"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input
                                                v-model="row.requirement"
                                                :disabled="true"
                                                class="form-input"
                                                clearable
                                                size="mini"
                                            ></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        label="Answer"
                                        width="150"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input
                                                v-model="row.answer"
                                                :disabled="true"
                                                class="form-input"
                                                clearable
                                                size="mini"
                                            ></el-input>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        v-if="evaluationType"
                                        label="Evaluation"
                                        width="150"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input-number
                                                v-model="row.evaluation"
                                                :disabled="readOnly"
                                                :max="10"
                                                :min="0"
                                                @change="average"
                                                size="mini">
                                            </el-input-number>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        v-if="!evaluationType"
                                        label="Evaluation"
                                        width="150"
                                    >

                                        <template slot-scope="{ row }">
                                            <el-select v-model="row.evaluation" @change="average" :disabled="readOnly" placeholder="Select"  size="mini">
                                                <el-option
                                                    v-for="item in optionsEvaluation"
                                                    :key="item.value"
                                                    :label="item.label"
                                                    :value="item.value">
                                                </el-option>
                                            </el-select>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        label="notes"
                                        width="150"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input
                                                v-model="row.notes"
                                                :disabled="readOnly"
                                                class="form-input"
                                                size="mini"
                                            ></el-input>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>

            </el-row>
            <div style="margin-bottom: 20px;margin-top: 20px">
                <el-row>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">-</div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple-light">
                            <el-button size="mini"
                                       type="primary" @click="save()">
                                Save
                            </el-button>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple">
                            <el-form-item label="Average Score">
                                <el-input disabled="true" v-model="evaluationResultLine.score" :disabled="true" placeholder="Please input"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="grid-content bg-purple-light">
                            <template>
                                <el-select v-model="evaluationResultLine.status" :disabled="(readOnly,statusReadOnly)" placeholder="Select" size="mini">
                                    <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                        size="mini">
                                    </el-option>
                                </el-select>
                            </template>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </el-form>
    </div>
</template>
<script lang="ts" src="./evaluation-form-detail.component.ts"></script>
<style lang="scss">
.compact .prequalification-form {
    .el-table--mini {

        td,
        th {
            height: 35px;
        }
    }
}

</style>
<style lang="scss" scoped>
.prequalification-form {
    .criteria-section {

        &:not(.criteria-0),
        > .el-col > .sub-criteria-section:not(.sub-0) {
            margin-top: 10px;
        }
    }
}

</style>
