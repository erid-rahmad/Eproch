<template>
    <div>
        <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <!-- <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="validate">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button> -->
            </el-col>
        </el-row>
        <el-form ref="productCatalog" label-position="left" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left">
                <h4>Product Information</h4>
            </el-divider>
            <el-row :gutter="columnSpacing">
                <el-col :span="12">
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding Title" prop="name" required>
                                <h7>Biding tes</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding No" prop="name" required>
                                <h7>BP-123</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding type" prop="name" required>
                                <h7>promosi</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="name" required>
                                    <h7>angelina sondang</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Depertement" prop="name" required>
                                    <h7>EACCS</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Ceilling Price" prop="name" required>
                                    <h7>290.000.000.000</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-row>
                </el-col>
            </el-row>
            <el-divider content-position="left">
                <h4>Image Information</h4>
            </el-divider>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="No" width="60">
                    <template slot-scope="row">
                        {{ row.$index+1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="1" label="Vendor Name" width="150">
                </el-table-column>
                <el-table-column prop="2" label="Proposed Price" width="150">
                </el-table-column>
                <el-table-column prop="" label="Document Submision" width="180">
                    <el-table-column prop="3" label="" width="180">
                    </el-table-column>
                    <el-table-column prop="4" label="" width="180">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="5" label="Document Evaluation" width="180">
                    <template>
                        <el-checkbox v-model="checked">clear</el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column label="Tender Evaluation">
                    <el-table-column prop="1" label="Criteria1" width="80">
                    </el-table-column>
                    <el-table-column prop="2" label="Criteria1" width="80">
                    </el-table-column>
                    <el-table-column prop="3" label="Criteria1" width="80">
                    </el-table-column>
                    <el-table-column prop="4" label="Criteria1" width="80">
                    </el-table-column>
                    <el-table-column prop="5" label="Criteria1" width="80">
                    </el-table-column>
                    <el-table-column prop="6" label="Criteria1 t" width="80">
                    </el-table-column>
                </el-table-column>
            </el-table>
             <el-divider content-position="left">
                <h4></h4>
            </el-divider>

        </el-form>
           <el-col :span="24" class="tab-container">
            <el-col :span="20" class="tab-container">
                <el-col :span="15" class="tab-container">
                    <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" size="mini" :model="asd" :rules="asd">

                        <el-form-item label="note " prop="title">
                            <el-input prop="2" class="form-input"></el-input>
                        </el-form-item>
                    </el-form>
                    <!-- <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" :model="asd" :rules="asd">
                        <el-form-item label="Remark" prop="title">
                            <template>
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="viewSchedule(row)">
                                    Select Dockument
                                </el-button>
                            </template>
                        </el-form-item>
                    </el-form> -->
                </el-col>
            </el-col>
            <el-col :span="4" class="tab-container">
                <el-row>
                    <el-button type="success" plain>Approve</el-button>
                    <el-button type="danger" @click="back" plain>Cancle</el-button>
                </el-row>
            </el-col>
        </el-col>
    </div>
</template>

<script lang="ts" src="./product-information.component.ts"></script>

<style lang="scss">
    .form-input {
        textarea {
            resize: none;
            border: none;
            border-radius: 0px;
            border-bottom: 1px solid #bfcbd9;
        }
    }

    .el-form-item is-required el-form-item--mini {
        .el-form-item__content {
            margin-left: 0px;
        }
    }

    .cascader {
        width: 100%;
    }

</style>
