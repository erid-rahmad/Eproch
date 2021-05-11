<template>
    <div class="vendor-scoring">
        <el-form ref="biddingInformation" disabled label-position="left" label-width="150px" :model="bidding" size="mini">
            <el-row :gutter="24">
                <el-col :xs="24" :sm="24" :lg="12" :xl="8">
                    <el-form-item label="Title" prop="name" required>
                        <el-input v-model="bidding.name" class="form-input"></el-input>
                    </el-form-item>
                    <el-form-item label="Bidding No" prop="biddingNo">
                        <el-input v-model="bidding.documentNo" class="form-input"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-divider content-position="left">
            <h4>Scoring Criteria</h4>
        </el-divider>
        <div v-if="index">
            <el-form inline size="mini">
                <el-form-item label="Evaluation Method">
                    {{vendorScoring.evaluationMethodName}}
                </el-form-item>
            </el-form>
            <el-row :gutter="24">
                <el-col :span="20">
                    <el-table ref="vendorScoringasd" border :data="vendorScoring.vendorScoringLineDTOList" :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText"
                        highlight-current-row size="mini" stripe style="width: 100%">
                        <el-table-column width="48" label="No">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="128" label="Evaluation" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                <!-- {{ row.evaluation }} -->
                                {{ row.evaluationMethodLineEvaluation }}

                            </template>
                        </el-table-column>

                        <el-table-column min-width="128" label="Evaluation Type" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluationMethodLineEvaluationType}}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="152" label="Weight">
                            <template slot-scope="{ row }">
                                {{row.evaluationMethodLineWeight}}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="200" label="Passing Grade">
                            <template slot-scope="{ row }">
                                {{ row.evaluationMethodLinePassingGrade}}
                            </template>
                        </el-table-column>
                        <el-table-column align="center" label="Criteria" min-width="56">
                            <template slot-scope="{row}">
                                <div v-if="row.evaluationMethodLineEvaluation!=='Price'">
                                    
                                    <el-button size="mini" icon="el-icon-plus" type="primary" @click="addScoring(row)">Criteria</el-button>

                                </div>
                                

                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <div v-if="!index">
            <el-form inline size="mini">
                <el-form-item label="Evaluation Method">
                    <template>

                        <el-select v-model="value" filterable placeholder="Select">
                            <el-option v-for="item in evaluationMethod" :key="item.id" :label="item.name" :value="item.id" @change="changeEvaluation">
                            </el-option>
                        </el-select>
                        <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" @click="cekmainform">
                            Submit
                        </el-button>
                    </template>
                </el-form-item>
            </el-form>
            <el-row :gutter="24">
                <el-col :span="20">
                    <el-table ref="vendorScoring" border :data="evaluationMethodLine" :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText"
                        highlight-current-row size="mini" stripe style="width: 100%">
                        <el-table-column width="48" label="No">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="128" label="Evaluation" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluation }}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="128" label="Evaluation Type" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluationType}}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="152" label="Weight">
                            <template slot-scope="{ row }">
                                {{row.weight}}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="200" label="Passing Grade">
                            <template slot-scope="{ row }">
                                {{ row.passingGrade}}
                            </template>
                        </el-table-column>

                        <el-table-column align="center" label="Criteria" min-width="56">
                            <!-- <template slot-scope="{row}">
                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="addScoring(row)" >Add Criteria</el-button>
                            </template> -->
                        </el-table-column>
                    </el-table>
                </el-col>

            </el-row>

        </div>
        <el-dialog width="90%" :visible.sync="criteriaPA" title=" Criteria">
            <template>
                <prequalification-form :mainForm="mainForm" :pickrow="pickrow" @closecriteriaPA="closecriteriaPA"></prequalification-form>
            </template>
        </el-dialog>
        <!-- <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" @click="cekmainform">
            cek
        </el-button> -->
    </div>
</template>

<script lang="ts" src="./vendor-scoring.component.ts"></script>

<style lang="scss">
    .compact .vendor-scoring .el-table--mini {

        th,
        td {
            height: 35px;
        }
    }

</style>
