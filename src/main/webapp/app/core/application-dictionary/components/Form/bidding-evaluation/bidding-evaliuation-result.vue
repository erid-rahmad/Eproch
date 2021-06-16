<template>
    <div class="bidding-evaluation card-view">
        <el-row v-if="index" class="toolbar">
            <el-col :span="24">
                <el-button icon="el-icon-close" plain size="mini" type="danger" @click="close">
                    Back
                </el-button>
                <el-button icon="el-icon-arrow-right" plain size="mini" type="primary" @click="createTableNegotiation">
                    Submit
                </el-button>
            </el-col>



        </el-row>
        <div v-if="index" class="card">
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
            </el-form>

            <el-table :data="evaluationResult" align="center"
                      :default-sort="{prop: 'date', order: 'descending'}"
                      size="mini"
                      ref="multipleTable"
                      stripe @selection-change="onRecipientSelectionChanged"
                      border

                      v-loading="loading"
                      style="width: 100%">
                <el-table-column label="Summary"
                                 min-width="120"
                                 align="center"
                                 fixed="right"
                                 sortable>
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                   @click="detailScore(row)">
                            Detail Score
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Winer Recomendation" align="center"   min-width="100">
                    <el-table-column type="selection" width="100"></el-table-column>
                </el-table-column>
                <el-table-column label="Vendor Name" align="center" min-width="180" prop="vendorName" sortable>
                </el-table-column>
                <el-table-column label="Date Submited" align="center" min-width="180"  sortable>
                    <template slot-scope="{row}">
                        {{ row.submitDate | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Status" align="center" min-width="80" prop="status" sortable>
                </el-table-column>
                <el-table-column label="Score" align="center" min-width="80" prop="score" sortable>
                </el-table-column>
                <el-table-column label="Rank" align="center" min-width="80" prop="rank" sortable>
                </el-table-column>
                <el-table-column label="Evaluation Status" align="center" min-width="120" prop="evaluationStatus" sortable>
                    <template slot-scope="{row}">
                        {{getStatus(row.evaluationStatus)}}
                    </template>
                </el-table-column>

            </el-table>
        </div>
        <div>
            <ResultDetail :evaluationResultProp="evaluationResultProp" v-if="!index" @close="close_"></ResultDetail>
        </div>
    </div>
</template>

<script lang="ts" src="./bidding-evaliuation-result.component.ts"></script>

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
