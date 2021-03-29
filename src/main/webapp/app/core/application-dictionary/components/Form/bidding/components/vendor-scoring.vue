<template>
    <div class="vendor-scoring">

        <el-divider content-position="left"><h4>Scoring Criteria</h4></el-divider>
        <el-form inline size="mini">
            <el-form-item label="Evaluation Method">
                <el-select v-model="evaluationMethod">
                    <el-option value="Evaluation Tender Service">Evaluation Tender Service</el-option>
                    <el-option value="Evaluation Tender Goods">Evaluation Tender Goods</el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="vendorScoring"
                    highlight-current-row
                    border stripe
                    :fit="false"
                    size="mini"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="bidding.scoringCriteria">

                    <el-table-column
                        min-width="48"
                        label="No"
                    >
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="128"
                        label="Criteria"
                        show-overflow-tooltip
                    >
                        <template slot-scope="{ row }">
                            {{ row.criteria }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="128"
                        label="SubCriteria"
                        show-overflow-tooltip
                    >
                        <template slot-scope="{ row }">
                            {{ row.subCriteria }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="152"
                        label="Percentage"
                    >
                        <template slot-scope="{ row }">
                            <el-input-number
                                v-model="row.percentage"
                                controls-position="right"
                                :max="100"
                                :min="0"
                                size="mini"
                            ></el-input-number>
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="200"
                        label="PiC"
                    >
                        <template slot-scope="{ row }">
                            <el-input
                                v-model="row.picName"
                                clearable
                                size="mini"
                            ></el-input>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" min-width="56">
                        <template slot="header">
                            <el-button
                                size="mini"
                                icon="el-icon-plus"
                                type="primary"
                                @click="addScoring"/>
                        </template>
                        <template slot-scope="row">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                type="danger"
                                @click="removeScoring(row.$index)"/>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>



        <el-dialog
            width="30%"
            :visible.sync="dialogConfirmationVisible"
            title="Add Vendor Scoring Criteria">

            <template>
                <div>
                    <el-form ref="vendorScoringCriteria" label-position="left" label-width="150px" size="mini" :model="vendorScoringCriteria">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Criteria" prop="criteria" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="vendorScoringCriteria.criteria"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')"
                                        @change="getSubCriteria($event)">
                                        <el-option
                                            v-for="item in criteriaOptions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="SubCriteria" prop="subCriteria" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="vendorScoringCriteria.subCriteria"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')"
                                        @change="getPic($event)">
                                        <el-option
                                            v-for="item in subCriteriaOptions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Percentage" prop="percentage" required>
                                    <el-input-number
                                        v-model="vendorScoringCriteria.percentage"
                                        clearable
                                        controls-position="right"
                                        :max="100"
                                        :min="0"
                                    ></el-input-number>
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
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="saveScoring">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
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
