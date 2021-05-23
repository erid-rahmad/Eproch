<template>
    <div class="prequalification-form">
        <el-form ref="mainForm" label-position="left" label-width="200px" size="mini">
            <el-row
                v-for="(criteria, index) in evaluationMethodCriteria"
                :key="criteria.id"
                class="criteria-section"
                :class="`criteria-${index}`"
            >
                <el-col :span="24">
                    <el-form-item
                        class="criteria-label"
                        label="Criteria"
                    >
                        {{criteria.biddingCriteriaName}}
                    </el-form-item>
                    <el-row
                        v-for="(subCriteria, subIndex) in criteria.evalMethodSubCriteriaList"
                        :key="subCriteria.id"
                        class="sub-criteria-section"
                        :class="`sub-${subIndex}`"
                    >
                        <el-col :span="24">
                            <el-form-item label="Sub Criteria">
                                {{subCriteria.biddingSubCriteriaName}}
                            </el-form-item>
                            <el-row
                                v-for="(biddingSubCriteria, subsubindex) in subCriteria.biddingSubCriteriaDTO"
                                :key="biddingSubCriteria.id"
                                class="sub-sub-criteria-section"
                                :class="`sub-${subsubindex}`"
                            >
                                <el-table
                                    border class="question-list"
                                    :data="biddingSubCriteria.criteriaLineDTO"
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
                                        show-overflow-tooltip
                                        prop="name"
                                    ></el-table-column>
                                    <el-table-column
                                        label="Requirement"
                                        width="150"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input
                                                v-model="row.requirement"
                                                class="form-input"
                                                clearable
                                                :disabled="readOnly"
                                                size="mini"
                                                @change="onRequirementChanged"
                                            ></el-input>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>

    </div>
</template>
<script lang="ts" src="./prequalification-form.component.ts"></script>
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
            >.el-col>.sub-criteria-section:not(.sub-0) {
                margin-top: 10px;
            }
        }
    }

</style>
