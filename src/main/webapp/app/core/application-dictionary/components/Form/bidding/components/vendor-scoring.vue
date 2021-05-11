<template>
    <div class="vendor-scoring">
        <el-form ref="biddingInformation" :model="bidding" disabled label-position="left" label-width="150px"
                 size="mini">
            <el-row :gutter="24">
                <el-col :lg="12" :sm="24" :xl="8" :xs="24">
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
                    {{ vendorScoring.evaluationMethodName }}
                </el-form-item>
            </el-form>
            <el-row :gutter="24">
                <el-col :span="20">
                    <el-table ref="vendorScoringasd" :data="vendorScoring.vendorScoringLineDTOList" :default-sort="gridSchema.defaultSort"
                              :empty-text="gridSchema.emptyText" border
                              highlight-current-row size="mini" stripe style="width: 100%">
                        <el-table-column label="No" width="48">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Evaluation" min-width="128" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                <!-- {{ row.evaluation }} -->
                                {{ row.evaluationMethodLineEvaluation }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Evaluation Type" min-width="128" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluationMethodLineEvaluationType }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Weight" min-width="152">
                            <template slot-scope="{ row }">
                                {{ row.evaluationMethodLineWeight }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Passing Grade" min-width="200">
                            <template slot-scope="{ row }">
                                {{ row.evaluationMethodLinePassingGrade }}
                            </template>
                        </el-table-column>
                        <el-table-column align="center" label="Criteria" min-width="56">
                            <template slot-scope="{row}">
                                <div v-if="row.evaluationMethodLineEvaluation!=='P'">
                                    <el-button icon="el-icon-plus" size="mini" type="primary" @click="addScoring(row)">
                                        Criteria
                                    </el-button>
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
                            <el-option v-for="item in evaluationMethod" :key="item.id" :label="item.name"
                                       :value="item.id" @change="changeEvaluation">
                            </el-option>
                        </el-select>
                        <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary"
                                   @click="cekmainform">
                            Submit
                        </el-button>
                    </template>
                </el-form-item>
            </el-form>
            <el-row :gutter="24">
                <el-col :span="20">
                    <el-table ref="vendorScoring" :data="evaluationMethodLine" :default-sort="gridSchema.defaultSort"
                              :empty-text="gridSchema.emptyText" border
                              highlight-current-row size="mini" stripe style="width: 100%">
                        <el-table-column label="No" width="48">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Evaluation" min-width="128" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluation }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Evaluation Type" min-width="128" show-overflow-tooltip>
                            <template slot-scope="{ row }">
                                {{ row.evaluationType }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Weight" min-width="152">
                            <template slot-scope="{ row }">
                                {{ row.weight }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Passing Grade" min-width="200">
                            <template slot-scope="{ row }">
                                {{ row.passingGrade }}
                            </template>
                        </el-table-column>
                        <el-table-column align="center" label="Criteria" min-width="56">
                            <!--                             <template slot-scope="{row}">-->
                            <!--                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="addScoring(row)" >Add Criteria</el-button>-->
                            <!--                            </template> -->
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <el-dialog :visible.sync="criteriaPA" title=" Criteria" width="90%">
            <template>
                <prequalification-form :mainForm="mainForm" :pickrow="pickrow"
                                       @closecriteriaPA="closecriteriaPA"></prequalification-form>
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
