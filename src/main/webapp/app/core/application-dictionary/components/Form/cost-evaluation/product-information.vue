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
                                <h7>BP-123</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding Title" prop="name">
                                <h7>Biding Tes</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding No" prop="name">
                                <h7>BP-1122</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="Biding Type" prop="name">
                                <h7>gratis</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="Breakdown" prop="name">
                                                           <el-dropdown>
                                <span class="el-dropdown-link">
                                    Breakdown<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>Yes</el-dropdown-item>
                                    <el-dropdown-item>No</el-dropdown-item>                         
                                </el-dropdown-menu>
                            </el-dropdown>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="PIC" prop="name">
                                    <h7>jubaidah</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Depertement" prop="name">
                                    <h7>majumaju</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Reference" prop="name">
                                    <h7>Mr.smith</h7>
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
                                    <h7>21/12/2021 12:99</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-row>
                </el-col>
            </el-row>
            <el-divider content-position="left">
                <h4>Detail</h4>
            </el-divider>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="date" label="No" width="60">
                    <template slot-scope="row">
                        {{ row.$index+1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="1" label="Detail" width="150">
                    <el-table-column prop="1" label="Produck" width="150">
                    </el-table-column>
                    <el-table-column prop="2" label="Qty" width="150">
                    </el-table-column>
                    <el-table-column prop="3" label="Uom" width="180">
                    </el-table-column>
                    <el-table-column prop="4" label="Mandays" width="180">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Evaluation" width="150">
                    <el-table-column prop="1" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="2" label="subtotal" width="150">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Vendor A" width="150">
                    <el-table-column prop="1" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="2" label="subtotal" width="150">
                    </el-table-column>
                    <el-table-column prop="3" label="Gap" width="150">
                    </el-table-column>
                </el-table-column>
                <el-table-column prop="1" label="Vendor B" width="150">
                    <el-table-column prop="1" label="price" width="150">
                    </el-table-column>
                    <el-table-column prop="2" label="subtotal" width="150">
                    </el-table-column>
                    <el-table-column prop="2" label="Gap" width="150">
                    </el-table-column>
                </el-table-column>
            </el-table>

        </el-form>
        <el-divider content-position="left">
            <h4></h4>
        </el-divider>
        <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" :model="asd" :rules="asd">
            <el-form-item label="Note" prop="title">
                <el-input prop="2" class="form-input"></el-input>
            </el-form-item>
        </el-form>
        <el-form ref="biddingInfasdsadormation" label-position="left" label-width="100px" :model="asd" :rules="asd">
            <el-form-item label="Remark" prop="title">
               <template >
                                <el-button
                                    class="button"
                                    icon="el-icon-search"
                                    size="mini"
                                    type="primary"
                                    @click="viewSchedule(row)">
                                    Select Dockument
                                </el-button>
                            </template>
            </el-form-item>
        </el-form>
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
