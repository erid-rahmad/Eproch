<template>
    <div class="app-container">
        <div>
            <el-divider content-position="left">
                <h4></h4>
            </el-divider>
            <el-form ref="biddingInformation" label-position="left" label-width="100px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="Title" prop="title" required>
                            <h7>BIDING mobil</h7>
                        </el-form-item>
                        <el-form-item label="Bidding No" prop="biddingNo" required>
                            <h7>BP-099</h7>
                        </el-form-item>
                        <el-form-item label="Biding Type" prop="vurrency" required>
                            <h7>Cicil</h7>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="PIC" prop="bidingType" required>
                            <h7>Zubaidah</h7>
                        </el-form-item>
                        <el-form-item label="Depertement" prop="vendorSelectionMethod" required>
                            <h7>Penyakit dalam</h7>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-divider content-position="left">
                <h4>Detail</h4>
            </el-divider>
        </div>
        <div>
        </div>
        <div>
            <el-row>
                <el-col :span="24">
                    <el-table :data="tableData">
                        <el-table-column min-width="30" label="No">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="4" label="Evaluation Even" sortable width="180">
                        </el-table-column>
                        <el-table-column prop="2" label="Criteria" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="3" label="Sub Criteria" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="4" label="Persentage" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="5" label="PIC" sortable width="200">
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-divider content-position="left">
                <h4>Suporting Dockument</h4>
            </el-divider>
        </div>
        <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" size="mini" :model="asd" :rules="asd">
            <el-row :gutter="24">
                <el-col :span="12">
                    <el-form-item label="PIC" prop="title" required>
                        <h7>Wiro sableng</h7>
                    </el-form-item>
                    <el-form-item label="Dockument" prop="biddingNo" required>
                        <h7>jejakpetualang.com</h7>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div>
            <el-divider content-position="left">
                <h4>Biding Evaluation Result</h4>
            </el-divider>
        </div>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="date" label="No" width="60">
                <template slot-scope="row">
                    {{ row.$index+1 }}
                </template>
            </el-table-column>
            <el-table-column prop="1" label="Vendor Name" width="140">
            </el-table-column>
            <el-table-column prop="2" label="Proposed Price" width="140">
            </el-table-column>
            <el-table-column prop="3" label="Price Ranking" width="140">
            </el-table-column>
            <el-table-column prop="4" label="Dockument Submission" width="100">
                <el-table-column prop="4" label="" width="120">
                </el-table-column>
                <el-table-column prop="2" label="" width="100">
                </el-table-column>
            </el-table-column>
            <el-table-column label="Scoring Criteria 1 Name">
                <el-table-column prop="4" label="Criteria 1" width="120">
                    <el-table-column prop="4" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="4" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="2" label="Criteria 2" width="100">
                    <el-table-column prop="4" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="4" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="3" label=" Criteria 3" width="300">
                    <el-table-column prop="4" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="4" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="4" label="Criteria 4" width="100">
                    <el-table-column prop="4" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="4" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="5" label="Criteria 5" width="100">
                    <el-table-column prop="4" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="4" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
            </el-table-column>
            <el-table-column prop="4" label="Total Score">
            </el-table-column>
            <el-table-column prop="4" label="Score Ranking">
            </el-table-column>
        </el-table>
        <div>
            <el-divider content-position="left">
                <h4>Select Vendor to the next phase</h4>
            </el-divider>
        </div>
        <template>
            <el-table ref="multipleTable" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="1" label="Vendor" width="120">
                </el-table-column>
                <el-table-column prop="2" label="Total Score" width="120">
                </el-table-column>
            </el-table>
            <div style="margin-top: 20px">
                <!-- <el-button @click="toggleSelection([tableData[1], tableData[2]])">Toggle selection status of second and third rows</el-button>
                <el-button @click="toggleSelection()">Clear selection</el-button> -->
            </div>
        </template>
        <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" size="mini" :model="asd" :rules="asd">
            <el-form-item label="Remark" prop="title" >
                <el-input prop="2" class="form-input" ></el-input>
            </el-form-item>
        </el-form>
        <!-- <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane v-for="(item, index) in tabTitleOptions" :key="item.value" :label="item.name" :name="item.value">
                        <keep-alive>                            
                        </keep-alive>
                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </el-row> -->
        <el-row v-if="!index" class="main" ref="tableWrapper">
            <el-col :span="24" class="tab-container">
                <product-information :setRowProductCatalog="setRow" @closeProductInformation="closeProductInformation" />
            </el-col>
        </el-row>
        <el-row v-if="index" class="header">
        </el-row>
        <el-dialog title="Shipping address" :visible.sync="dialogTableVisible">
            <el-table :data="gridData">
                <el-table-column property="no" label="no" width="150"><template slot-scope="row">
                        {{ row.$index+1 }}
                    </template></el-table-column>
                <el-table-column property="name" label="Name" width="200"></el-table-column>
                <el-table-column property="address" label="Address"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./product-catalog.component.ts">
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
