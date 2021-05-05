<template>
    <div class="vendor-scoring">

        <el-divider content-position="left">
            <h4>Scoring Criteria</h4>
        </el-divider>
        <el-form inline size="mini">
            <el-form-item label="Evaluation Method">
                        <template>
                            <el-select v-model="value" filterable placeholder="Select">
                                <el-option v-for="item in evaluationMethod" :key="item.id" :label="item.name" :value="item.id">
                                </el-option>
                            </el-select>
                        </template>
            </el-form-item>
        </el-form>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table ref="vendorScoring" border :data="evaluationMethodLine" :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText" highlight-current-row
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

                    <!-- <el-table-column v-if="!readOnly" align="center" label="Criteria" min-width="56"> -->
                    <el-table-column align="center" label="Criteria" min-width="56">
                        <template slot-scope="{row}">
                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="addScoring(row)" />
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-dialog width="90%" :visible.sync="criteriaPA" title=" Criteria">

            <template>    
                <prequalification-form :pickrow="pickrow"></prequalification-form>
                <!-- <prequalification-form :read-only="readOnly" :pickrow="pickrow"></prequalification-form> -->
                   <div slot="footer">
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" @click="saveScoring">
                        Save
                    </el-button>
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-close" @click="closeCriteriaPA">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>          
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
