<template>
    <div class="bidding-evaluation">
        <div v-if="index===0">
            <el-row>
                <el-col :span="24">
                    <el-button icon="el-icon-close" plain size="mini" type="danger" @click="close">
                        Back
                    </el-button>
                </el-col>
            </el-row>
            <el-form ref="productCatalog" label-position="left" label-width="130px"
                     size="mini">
                <el-form-item label="Bidding No">
                    {{ pickRow.documentNo }}
                </el-form-item>
                <el-form-item label="Bidding Name">
                    {{ pickRow.name }}
                </el-form-item>
                <el-form-item label="Bidding Type">
                    {{ pickRow.biddingTypeName }}
                </el-form-item>
                <el-form-item label="Event Type">
                    {{ pickRow.eventTypeName }}
                </el-form-item>
                <el-divider content-position="left">
                    <h4>Vendor Join</h4>
                </el-divider>
            </el-form>
            <el-table :data="biddingSubmission" :default-sort="{prop: 'date', order: 'descending'}" size="mini"
                      border
                      style="width: 100%">
                <el-table-column label="No" min-width="30">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Vendor Name" min-width="180" prop="vendorName" sortable>
                </el-table-column>
                <el-table-column label="date Submited" min-width="180" prop="dateSubmit" sortable>
                    <template slot-scope="{row}">
                        {{ row.dateSubmit | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Evaluation Status" min-width="180" prop="biddingStatus" sortable>
                </el-table-column>
                <el-table-column label="Summary" min-width="120" sortable>
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                   @click="evaluate(row)">
                            Evaluate
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div v-if="index===1">
            <EvaluationForm :data="data" @close="close"></EvaluationForm>
        </div>
    </div>
</template>

<script lang="ts" src="./bidding-evaluation.component.ts"></script>

<style lang="scss">
.bidding-evaluation {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    td.document-submission .cell {
        padding: 0;

        .el-row .el-col.border {
            border-bottom: none !important;
            border-left: none !important;
            padding: 4px 10px;

            &:last-child {
                border-right: none !important;
            }
        }

        .el-row:first-child .el-col.border {
            border-top: none !important;
        }
    }

    .el-divider--horizontal {
        margin-top: 32px;
        margin-bottom: 16px;
    }

    .form-wrapper {
        .el-scrollbar__wrap {
            overflow-x: hidden;
            padding: 15px;
        }
    }


    .toolbar {
        padding: 4px;
    }

    .vendor-scoring tbody td {
        height: 35px;
    }
}

.el-tabs__header {
    margin: 0px;
}

.el-table__fixed {
    box-shadow: none;
}

.main {
    padding: 0px;
}

.form-input {
    width: 100%;
}

</style>
