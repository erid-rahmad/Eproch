<template>
    <div class="bidding-evaluation">
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
        <el-form ref="productCatalog" label-position="left" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left">
                <h4>Product Information</h4>
            </el-divider>
            <el-row :gutter="columnSpacing">
                <el-col :span="12">
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding Title" prop="name" required>
                                <h7>Penggadaan Kendaraan operasional</h7>
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
                                <h7>Tender Goods</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="name" required>
                                    <h7> Admin tender</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Depertement" prop="name" required>
                                    <h7>Marketing</h7>
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
            <el-table border :data="evaluationResult" highlight-current-row size="mini" stripe>
                <el-table-column label="No." width="50">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Vendor Name" prop="vendorName" show-overflow-tooltip min-width="200"></el-table-column>
                <el-table-column label="Proposed Price" width="200">
                    <template slot-scope="{ row }">
                        {{ row.proposedPrice | formatCurrency }}
                    </template>
                </el-table-column>
                <el-table-column class-name="document-submission" label="Document Submission" width="250">
                    <template slot-scope="{ row }">
                        <el-row v-for="item in row.attachments" :key="item.id">
                            <el-col class="border" :span="12">
                                {{ item.documentName }}
                            </el-col>
                            <el-col class="border" :span="12">
                                <el-button class="btn-attachment" icon="el-icon-download" size="mini" type="primary">
                                    Download
                                </el-button>
                            </el-col>
                        </el-row>
                    </template>
                </el-table-column>
                <el-table-column prop="" label="Document Received" width="150">
                    <el-col :span="24">
                        <el-col :span="24">
                            <h7> 22/12/2021</h7>
                        </el-col>
                        <el-col :span="24">
                            <h7> 22/12/2021</h7>
                        </el-col>
                    </el-col>

                </el-table-column>

                <el-table-column prop="" label="Document Evaluation" width="150">
                    <template>
                        <el-row>
                            <el-col :span="24">
                                <template>
                                    <el-checkbox v-model="checked" @click="download">check</el-checkbox>
                                </template>
                            </el-col>
                            <el-col :span="24">
                                <template>
                                    <el-checkbox v-model="checked" @click="checked = false">check</el-checkbox>
                                </template>
                            </el-col>

                        </el-row>
                    </template>
                </el-table-column>



                <el-table-column label="Evaluation">
                    <el-table-column label="Bidding Evaluation">
                        <el-table-column prop="5" label="quality" width="80">
                        </el-table-column>
                        <el-table-column prop="6" label="cost" width="80">
                        </el-table-column>
                        <el-table-column prop="7" label="delivery" width="80">
                        </el-table-column>
                        <el-table-column prop="8" label="safety" width="80">
                        </el-table-column>
                        <el-table-column prop="9" label="marale" width="80">
                        </el-table-column>
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
            <!-- <el-col :span="4" class="tab-container">
                <el-row>
                    <el-button type="success" plain>Approve</el-button>
                    <el-button type="danger" @click="back" plain>Cancle</el-button>
                </el-row>
            </el-col> -->
        </el-col>
    </div>
</template>

<script lang="ts" src="./product-information.component.ts"></script>

<style lang="scss">
    .bidding-evaluation-approval {
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
