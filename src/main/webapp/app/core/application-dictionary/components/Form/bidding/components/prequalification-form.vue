<template>
    <div class="prequalification-form">
        <el-form ref="mainForm" label-position="left" label-width="200px"  size="mini">
            <!-- <el-row v-for="criteria in evaluationMethodCriteria" :key="criteria.id" class="criteria-section" :class="`criteria-${index}`"> -->
                 
            <el-row v-for="criteria in evaluationMethodCriteria" :key="criteria.id" >
                <el-col :span="24">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="Criteria">
                                <!-- <el-input v-model="criteria.biddingCriteriaName" ></el-input> -->
                                {{criteria.biddingCriteriaName}}
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <!-- <el-row v-for="(subCriteria, subIndex) in criteria.evalMethodSubCriteriaList" :key="subCriteria.id" class="sub-criteria-section" :class="`sub-${subIndex}`"> -->
                    <el-row v-for="(subCriteria) in criteria.evalMethodSubCriteriaList" :key="subCriteria.id">
                        <el-col :span="24">
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="Sub Criteria">
                                        <!-- <el-input v-model="subCriteria.biddingSubCriteriaName" ></el-input> -->
                                        {{subCriteria.biddingSubCriteriaName}}
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row v-for="(subCriteriaspace,subsubindex) in subCriteria.biddingSubCriteriaDTO" :key="subCriteriaspace.id" class="sub-sub-criteria-section"
                                :class="`sub-${subsubindex}`">
                                <el-table border class="question-list" :data="subCriteriaspace.criteriaLineDTO" highlight-current-row size="mini">
                                    <el-table-column label="No." width="50">
                                        <template slot-scope="{ $index }">
                                            {{ $index + 1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Question" min-width="320" show-overflow-tooltip sortable prop="name"></el-table-column>
                                    <el-table-column label="Requirement" width="150">
                                        <template slot-scope="{ row }">
                                            <!-- <el-select v-model="row.requirement" clearable size="mini">
                                            <el-option v-for="item in requirements" :key="item.code" :label="item.name" :value="item.code"></el-option>
                                            <el-input v-model="requirements.row" class="form-input" clearable></el-input>
                                        </el-select> -->
                                            <el-input v-model="row.requirement" class="form-input" clearable @change="testing"></el-input>
                                        </template>
                                    </el-table-column>

                                    <!-- <el-table-column label="Requirement" width="150">
                                        <template slot-scope="{ row }">
                                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="testing2(row)">help</el-button>                                            
                                        </template>
                                    </el-table-column> -->
                                </el-table>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
             <el-button size="mini" icon="el-icon-plus" type="primary" @click="setpushVendorScoringAnswer">PUSH</el-button>
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
