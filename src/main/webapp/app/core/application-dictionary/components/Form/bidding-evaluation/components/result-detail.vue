<template>
    <div class="bidding-evaluation">
        <div class="card-view">
            <el-form ref="productCatalog" label-position="left" label-width="130px"
                     size="mini">
                <el-form-item label="Bidding No">
                    {{ evaluationResultProp.biddingNo }}
                </el-form-item>
                <el-form-item label="Bidding Name">
                    {{ evaluationResultProp.biddingName }}
                </el-form-item>
                <el-form-item label="Bidding Type">
                    {{ evaluationResultProp.biddingTypeName }}
                </el-form-item>
                <el-form-item label="Vendor Name">
                    {{ evaluationResultProp.vendorName }}
                </el-form-item>
                <el-form-item label="TotalScore">
                    {{ evaluationResultProp.total }}
                </el-form-item>
                <el-divider content-position="left">
                    <h4>Evaluation</h4>
                </el-divider>
            </el-form>

            <el-table :data="evaluationResultLine" :default-sort="{prop: 'date', order: 'descending'}" size="mini" style="width: 100%">
                <el-table-column label="No" min-width="30">
                        <template slot-scope="row">
                            {{ row.$index + 1 }}
                        </template>
                </el-table-column>
                <el-table-column label="Proposal" prop="evaluationMethodLineName" sortable min-width="180">
                </el-table-column>
                <el-table-column label="Evaluation" prop="status" sortable min-width="180">
                </el-table-column>
                <el-table-column label="Total Score" prop="score" sortable min-width="180">
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script lang="ts" src="./result-detail.component.ts"></script>

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
