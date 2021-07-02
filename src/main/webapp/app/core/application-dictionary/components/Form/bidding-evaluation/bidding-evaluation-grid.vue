<template>
    <div class="app-container card-view bidding-process">
        <el-row v-if="index===0" class="main">
            <el-col :span="24">
                <el-tabs class="card">

                        <el-col :span="24">
                            <el-table :data="bidding"
                                      :default-sort="{prop: 'date', order: 'descending'}"
                                      size="mini"
                                      v-loading="loading"
                                      border
                                      style="width: 100%">

                                <el-table-column label="Action" align="center" fixed="right" min-width="180">
                                    <template slot-scope="{ row }">
                                        <el-button  icon="el el-download-alt" :underline="false" size="mini" type="primary"
                                                   @click="evaluate(row)">
                                            Evaluate
                                        </el-button>
                                        <el-button  icon="el el-download-alt" :underline="false" size="mini" type="primary"
                                                   @click="result(row)">
                                            Result
                                        </el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column align="center" label="No" min-width="40">
                                    <template slot-scope="row">
                                        {{ row.$index + 1 }}
                                    </template>
                                </el-table-column>
                                <el-table-column align="center" label="Bidding No." prop="documentNo" sortable min-width="150">
                                </el-table-column>
                                <el-table-column align="center" label="Title" prop="name" show-overflow-tooltip sortable min-width="180">
                                </el-table-column>
                                <el-table-column align="center" label="Biding Type" prop="biddingTypeName" sortable min-width="180">
                                </el-table-column>
                                <el-table-column align="center" label="Depertement type" prop="costCenterName" sortable min-width="180">
                                </el-table-column>
                                <el-table-column align="center" label="Biding Schedule" prop="" sortable min-width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="button" size="mini" style="width: 100%" @click="viewBidding(row, 1)">
                                            <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
                                        </el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column align="center" label="Bidding Status" min-width="140" sortable>
                                    <template slot-scope="{ row }">
                                        {{ formatBiddingStatus(row.biddingStatus) }}
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>

                </el-tabs>
            </el-col>
        </el-row>
        <el-row v-if="index===1" ref="tableWrapper" class="main">
            <el-col :span="24" class="tab-container">
                <product-information :pickRow="pickRow" @close="close"/>
            </el-col>
        </el-row>
        <el-row v-if="index===2">
            <EvaluationResult @close="close" :pickRow="pickRow"></EvaluationResult>
        </el-row>
    </div>
</template>

<script lang="ts" src="./bidding-evaluation-grid.component.ts">
</script>

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
