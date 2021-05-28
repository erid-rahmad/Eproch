<template>
    <div class="app-container">
        <el-row v-if="index===0" ref="tableWrapper" class="main">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <keep-alive>
                        <el-col :span="24">
                            <el-table :data="bidding" :default-sort="{prop: 'date', order: 'descending'}" size="mini"
                                      border
                                      style="width: 100%">
                                <el-table-column label="No" min-width="30">
                                    <template slot-scope="row">
                                        {{ row.$index + 1 }}
                                    </template>
                                </el-table-column>
                                <el-table-column label="Bidding Number" prop="documentNo" sortable width="180">
                                </el-table-column>
                                <el-table-column label="Title" prop="name" sortable width="180">
                                </el-table-column>
                                <el-table-column label="Biding Type" prop="biddingTypeName" sortable width="180">
                                </el-table-column>
                                <el-table-column label="Depertement type" prop="costCenterName" sortable width="180">
                                </el-table-column>
                                <el-table-column label="Biding Schedule" prop="" sortable width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="button" size="mini" style="width: 100%" @click="viewBidding(row, 1)">
                                            <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
                                        </el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column label="Biding Status" prop="documentStatus" sortable width="180">
                                </el-table-column>
                                <el-table-column label="Summary" min-width="120" sortable>
                                    <template slot-scope="{ row }">
                                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                                   @click="evaluate(row)">
                                            Evaluate
                                        </el-button>
                                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary"
                                                   @click="result(row)">
                                            Result
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </keep-alive>
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
        <el-dialog :visible.sync="dialogTableVisible" title="Shipping address">
            <el-table :data="gridData">
                <el-table-column label="no" property="no" width="150">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Vendor Name" property="name" width="200"></el-table-column>
                <el-table-column label="Address" property="address"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./bidding-evaluation-grid.component.ts">
</script>

<style lang="scss">
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
