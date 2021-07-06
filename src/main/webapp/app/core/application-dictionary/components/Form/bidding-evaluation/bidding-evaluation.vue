<template>
    <div class="app-container card-view bidding-process">
        <div v-if="index===0" >
            <el-row class="toolbar">
                <el-col :span="24">
                    <el-button icon="el-icon-close" plain size="mini" type="danger" @click="close">
                        Back
                    </el-button>
                </el-col>
            </el-row>
            <div class="card">
            <el-form  ref="productCatalog" label-position="left" label-width="130px"
                     size="mini">
                <el-row> 
                    <el-col span="12">
                        <el-form-item label="Bidding No">
                            {{ pickRow.documentNo }}
                        </el-form-item>
                        <el-form-item label="Bidding Name">
                            {{ pickRow.name }}
                        </el-form-item>
                    </el-col>
                    <el-col span="12">
                        <el-form-item label="Event Type">
                            {{ pickRow.eventTypeName }}
                        </el-form-item>
                        <el-form-item label="Bidding Type">
                            {{ pickRow.biddingTypeName }}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-divider content-position="left">
                        <h4>Joined Vendor(s)</h4>
                    </el-divider>
                </el-row>
            </el-form>
            <el-table :data="biddingSubmission"
                      :default-sort="{prop: 'date', order: 'descending'}"
                      size="mini"
                      v-loading="loading"
                      border
                      style="width: 100%">
                <el-table-column label="No" min-width="30">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Vendor Name" min-width="180" prop="vendorName" sortable>
                </el-table-column>
                <el-table-column label="Date Submited" min-width="180" prop="dateSubmit" sortable>
                    <template slot-scope="{row}">
                        {{ row.dateSubmit | formatDate }}
                    </template>
                </el-table-column>
<!--                <el-table-column label="Evaluation Status" min-width="180" prop="biddingStatus" sortable>-->
<!--                </el-table-column>-->

                <el-table-column label="Summary" min-width="120" sortable>
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                   @click="retrieveEvaluateTable(row)">
                            Evaluate
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            </div>
        </div>
        <div v-if="index===1">
            <EvaluationForm :data="data" @close="close_"></EvaluationForm>
        </div>
    </div>
</template>

<script lang="ts" src="./bidding-evaluation.component.ts"></script>

<style lang="scss">
.compact .bidding-process {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    .joined-vendor-dialog .el-table.vendor-list {
        td {
            height: 35px;
        }
    }
}

.el-table__fixed,
.el-table__fixed-right {
    box-shadow: none;
}

.main {
    padding: 0px;

    .button {
        width: 100%;
    }
}

.toolbar {
    padding: 4px 16px;
}

.form-input {
    width: 100%;
}
</style>
