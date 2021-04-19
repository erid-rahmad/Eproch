<template>
    <div class="vendor-scoring">

        <el-divider content-position="left">
            <h4>Scoring Criteria</h4>
        </el-divider>
        <el-form inline size="mini">
            <el-form-item label="Evaluation Method">
                <el-select v-model="evaluationMethod">
                    <el-option value="Evaluation Tender Service">Tender Service</el-option>
                    <el-option value="Evaluation Tender Goods">Tender Goods</el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table ref="vendorScoring" border :data="line" :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText" highlight-current-row
                    size="mini" stripe style="width: 100%">
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

                    <!-- <el-table-column v-if="!readOnly" align="center" label="Criteria" min-width="56"> -->
                    <el-table-column align="center" label="Criteria" min-width="56">
                        <template slot-scope="row">
                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="addScoring" />
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-dialog width="90%" :visible.sync="dialogConfirmationVisible" title=" Criteria">

            <template>
                <el-divider content-position="left">
                    <h4></h4>
                </el-divider>
                <prequalification-form :read-only="readOnly"></prequalification-form>
                   <div slot="footer">
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" @click="saveScoring">
                        Save
                    </el-button>
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-close" @click="dialogConfirmationVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>

            <!-- <template>
                <div>
                    <el-form ref="vendorScoringCriteria" label-position="left" label-width="150px" size="mini" :model="vendorScoringCriteria">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Criteria" prop="criteria" required>
                                    <el-select style="width: 100%" v-model="vendorScoringCriteria.criteria" clearable filterable :placeholder="$t('register.form.select')"
                                        @change="getSubCriteria($event)">
                                        <el-option v-for="item in criteriaOptions" :key="item.id" :label="item.name" :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="SubCriteria" prop="subCriteria" required>
                                    <el-select style="width: 100%" v-model="vendorScoringCriteria.subCriteria" clearable filterable :placeholder="$t('register.form.select')"
                                        @change="getPic($event)">
                                        <el-option v-for="item in subCriteriaOptions" :key="item.id" :label="item.name" :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Percentage" prop="percentage" required>
                                    <el-input-number v-model="vendorScoringCriteria.percentage" clearable controls-position="right" :max="100" :min="0"></el-input-number>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="picName" required>
                                    <el-input clearable v-model="vendorScoringCriteria.picName"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                </div>

                <div slot="footer">
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" @click="saveScoring">
                        Save
                    </el-button>
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-close" @click="dialogConfirmationVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template> -->
        </el-dialog>

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
