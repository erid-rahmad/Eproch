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
        <el-form ref="productCatalog" label-position="right" label-width="130px" size="mini" :model="productCatalog" :rules="rules">
            <el-divider content-position="left">
                <h4>Product Information</h4>
            </el-divider>
            <el-row :gutter="columnSpacing">
                <el-col :span="12">
                    <el-row :gutter="columnSpacing">
                        <el-col :span="24">
                            <el-form-item label="Biding No" prop="name" required>
                                <h7>BP-123</h7>
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
                            <el-form-item label="Biding No" prop="name" required>
                                <h7>BP-123</h7>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <el-col :span="12">
                    <el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Biding No" prop="name" required>
                                    <h7>BP-123</h7>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="Biding No" prop="name" required>
                                    <h7>BP-123</h7>
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
                <el-table-column prop="3" label="Document Submision" width="180">
                </el-table-column>
                <el-table-column prop="4" label="Document Evaluation" width="180">
                    <template>
                        <el-checkbox v-model="checked">Option</el-checkbox>
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
