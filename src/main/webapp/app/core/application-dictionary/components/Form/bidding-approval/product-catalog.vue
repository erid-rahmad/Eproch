<template>
    <div class="app-container">
        <div>
             <el-row :gutter="columnSpacing">
            <el-col :span="24">               
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="validate">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>
        </el-row>
            <el-divider content-position="left">              
            </el-divider>
            <el-form ref="biddingInformation" label-position="left" label-width="100px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="Title" prop="title" required>
                            <h7>Pengandaan kendaraan operasional</h7>
                        </el-form-item>
                        <el-form-item label="Bidding No" prop="biddingNo" required>
                            <h7>BN-0001</h7>
                        </el-form-item>
                        <el-form-item label="Biding Type" prop="vurrency" required>
                            <h7>Tender Good</h7>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="PIC" prop="bidingType" required>
                            <h7>admin tender</h7>
                        </el-form-item>
                        <el-form-item label="Depertement" prop="vendorSelectionMethod" required>
                            <h7>standar</h7>
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
                    
                    <el-table  v-loading="processing"  highlight-current-row border stripe size="mini" style="width: 100%; height: 100%" :data="tableData">
                        <el-table-column label="No" sortable width="60">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="2" label="Evaluation Even" sortable width="180">
                        </el-table-column>
                        <el-table-column prop="3" label="Criteria" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="4" label="Sub Criteria" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="5" label="Persentage" sortable width="200">
                        </el-table-column>
                        <el-table-column prop="6" label="PIC" sortable width="200">
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
                        <h7>Admin Tender</h7>
                    </el-form-item>
                    <el-form-item label="Dockument" prop="biddingNo" required>
                          <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-download" size="mini" type="primary" @click="downloadAttachment(row)">
                                 proposal_projeck.pdf 
                                
                            </el-button>
                        </template>
                       
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div>
            <el-divider content-position="left">
                <h4>Biding Evaluation Result</h4>
            </el-divider>
        </div>
        <el-table :data="tableData1" v-loading="processing"  highlight-current-row border stripe size="mini" >
            <el-table-column prop="date" label="No" width="60">
                <template slot-scope="row">
                    {{ row.$index+1 }}
                </template>
            </el-table-column>
            <el-table-column prop="1" label="Vendor Name" width="140">
            </el-table-column>
            <el-table-column prop="2" label="Proposed Price" width="140">
            </el-table-column>
            <el-table-column prop="3" label="Price Ranking" width="120">
            </el-table-column>
            <el-table-column prop="" label="Dockument Submission" width="">
                <el-table-column prop="4" label="" width="150">
                </el-table-column>
                <el-table-column prop="5" label="" width="150">
                    <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-download" size="mini" type="primary" @click="downloadAttachment(row)">
                                 time line.pdf                                 
                            </el-button>
                        </template>
                </el-table-column>                
            </el-table-column>
            <el-table-column label="Scoring Criteria 1 Name">
                <el-table-column prop="4" label="Quantity" width="120">
                    <el-table-column prop="6" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="7" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="2" label="Cost" width="100">
                    <el-table-column prop="8" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="9" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="3" label="Delivery" width="300">
                    <el-table-column prop="10" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="11" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="4" label="Safety" width="100">
                    <el-table-column prop="12" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="13" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="4" label="Morale" width="100">
                    <el-table-column prop="12" label="PIC 1" width="120">
                    </el-table-column>
                    <el-table-column prop="13" label="PIC 2" width="120">
                    </el-table-column>
                </el-table-column>     
            </el-table-column>
            <el-table-column prop="14" label="Total Score" width="120">
            </el-table-column>
            <el-table-column prop="15" label="Score Ranking" width="120">
            </el-table-column>
        </el-table>
        <div>
            <el-divider content-position="left">
                <h4>Select Vendor to the next phase</h4>
            </el-divider>
        </div>
        <template>
            <el-table ref="multipleTable" :data="tableData1" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="1" label="Vendor" width="120">
                </el-table-column>
                <el-table-column prop="14" label="Total Score" width="120">
                </el-table-column>
            </el-table>
            
        </template>
        <el-col :span="24" class="tab-container">
            <el-col :span="20" class="tab-container">
                <el-col :span="15" class="tab-container">
                    <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" size="mini" :model="asd" :rules="asd">

                        <el-form-item label="Remark" prop="title">
                            <el-input prop="2" class="form-input"></el-input>
                        </el-form-item>
                    </el-form>
                </el-col>
            </el-col>
            <el-col :span="4" class="tab-container">
                <!-- <el-row>
                    <el-button type="success" plain>Approve</el-button>
                    <el-button type="danger" plain>Cancle</el-button>
                </el-row> -->
            </el-col>
        </el-col>
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
