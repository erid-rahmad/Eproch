<template>
    <div class="bidding-evaluation">
        <el-row>
            <el-col :span="24">
                <el-button icon="el-icon-close" plain size="mini" type="danger" @click="close">
                    Back
                </el-button>
            </el-col>
        </el-row>
        <div v-if="index" class="card-view">
            <el-form ref="productCatalog" label-position="left" label-width="130px"
                     size="mini">
                <el-form-item label="Bidding No">
                    {{ evaluation.biddingNo }}
                </el-form-item>
                <el-form-item label="Bidding Name">
                    {{ evaluation.biddingName }}
                </el-form-item>
                <el-form-item label="Bidding Type">
                    {{ evaluation.biddingTypeName }}
                </el-form-item>
                <el-divider content-position="left">
                    <h4>Evaluation</h4>
                </el-divider>
            </el-form>

            <el-table :data="biddingSubmission" :default-sort="{prop: 'date', order: 'descending'}"
                      size="mini"
                      border
                      style="width: 100%">
                <el-table-column label="Winer Recomendation" min-width="100">
                    <template slot-scope="row">
                        <el-checkbox v-model="row.checked"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="Vendor Name" min-width="180" prop="vendorName" sortable>
                </el-table-column>
                <el-table-column label="date Submited" min-width="180" prop="dateSubmit" sortable>
                    <template slot-scope="{row}">
                        {{ row.dateSubmit | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Status" min-width="180" prop="biddingStatus" sortable>
                </el-table-column>
                <el-table-column label="Total Score" min-width="180" prop="biddingId" sortable>
                </el-table-column>
                <el-table-column label="Rank" min-width="180" prop="adOrganizationId" sortable>
                </el-table-column>
                <el-table-column label="Summary" min-width="120" sortable>
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                   @click="index=false">
                            Detail Score
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <ResultDetail v-if="!index"></ResultDetail>
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
