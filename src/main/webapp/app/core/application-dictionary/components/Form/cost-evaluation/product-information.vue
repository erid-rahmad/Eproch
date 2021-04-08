<template>
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
        <el-form ref="productCatalog" label-position="left" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left">
                <h4>Product Information</h4>
            </el-divider>
            <el-row :gutter="columnSpacing">
                <el-col :span="12">
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Evaluation No" prop="name">
                                <h7>CE-00001</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding Title" prop="name">
                                <h7>Pengadaan Kendaraan Operasional</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding No" prop="name">
                                <h7>BN-00001</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="Biding Type" prop="name">
                                <h7>Tender Goods</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="Breakdown" prop="name">
                                <el-button type="success" v-on:click="isHidden = !isHidden">BreakDown</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="name">
                                    <h7>Admin Tender</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Depertement" prop="name">
                                    <h7>Marketing</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Reference" prop="name">
                                    <h7>PR-21030031</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Currency" prop="name">
                                    <h7>IDR</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Evaluation deadline" prop="name">
                                    <h7>03/01/2021 14:00</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-row>
                </el-col>
            </el-row>
            <el-divider content-position="left">
                <h4>Detail</h4>
            </el-divider>
            <el-table size="mini" :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="No" width="60">
                    <template slot-scope="row">
                        {{ row.$index+1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="1" label="Detail" width="150">
                    <el-table-column prop="1" label="Produck" width="150">
                    </el-table-column>
                    <el-table-column v-if="!isHidden" prop="22" label="Sub Item" width="180">
                    </el-table-column>
                    <el-table-column v-if="!isHidden" prop="33" label="Sub Sub item" width="180">
                    </el-table-column>
                    <el-table-column prop="2" label="Qty" width="150">
                    </el-table-column>
                    <el-table-column prop="3" label="Uom" width="180">
                    </el-table-column>
                    <el-table-column prop="4" label="Mandays" width="180">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Evaluation" width="150">
                    <el-table-column prop="5" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="6" label="subtotal" width="150">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Sistech Kharisma" width="150">
                    <el-table-column prop="7" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="8" label="subtotal" width="150">
                    </el-table-column>
                    <el-table-column prop="9" label="Gap" width="150">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Ingram Micro" width="150">
                    <el-table-column prop="10" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="11" label="subtotal" width="150">
                    </el-table-column>
                    <el-table-column prop="12" label="Gap" width="150">
                    </el-table-column>
                </el-table-column>
            </el-table>

        </el-form>
        <el-divider content-position="left">
            <h4></h4>
        </el-divider>

        <el-col :span="24" class="tab-container">
            <el-col :span="20" class="tab-container">
                <el-col :span="15" class="tab-container">
                    <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" size="mini" :model="asd" :rules="asd">

                        <el-form-item label="note " prop="title">
                            <el-input v-model="asd" :rows="3" type="textarea"></el-input>
                        </el-form-item>
                    </el-form>
                    <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" :model="asd" :rules="asd">
                        <el-form-item label="Remark" prop="title">
                            <template>
                                <el-button class="button" icon="el-icon-plus" size="mini" type="primary" @click="viewSchedule(row)">
                                    Select Dockument
                                </el-button>
                            </template>
                        </el-form-item>
                    </el-form>
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
