<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Vendor Scoring</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="vendorScoring"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="vendorscoring">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="criteria"
                        label="Criteria">
                      
                        
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="subcriteria"
                        label="SubCriteria">
                        
                        
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="persentage"
                        label="Percentage"/>

                    <el-table-column
                        min-width="100"
                        prop="pic"
                        label="PIC"/>

                    <!-- <el-table-column align="center" min-width="50">
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
                    </el-table-column> -->

                </el-table>
            </el-col>
        </el-row>



        <el-dialog
            width="50%"
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
                                        class="form-input"
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
                                        class="form-input"
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
                                    <el-input class="form-input" clearable v-model="vendorScoringCriteria.percentage" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="picName" required>
                                    <el-input class="form-input" clearable v-model="vendorScoringCriteria.picName" disabled />
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
