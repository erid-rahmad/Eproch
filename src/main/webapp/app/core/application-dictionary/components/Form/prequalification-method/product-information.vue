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
                <h4>Prequalification</h4>
            </el-divider>
            <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini" :model="biddingInformation" :rules="rules">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="Prequalification Method" prop="">
                            <el-input v-model="dummy.aa" class="form-input" clearable :disabled="editMode"></el-input>
                        </el-form-item>
                        <el-form-item label="Evaluation Type" prop="title">
                            <template>
                                <el-select v-model="value.evaluationtype" placeholder="Pass Fail">
                                    <el-option v-for="item in evaluationtype" :key="item.value" :label="item.label" :value="item.value">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-form-item>
                        <el-form-item label="Search Criteria" prop="">
                            <el-input v-model="dummy.aaa" class="form-input" clearable :disabled="editMode"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Active" prop="">
                            <el-select v-model="value.Active" placeholder="Yes">
                                <el-option v-for="item in active" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Type" prop="title">
                            <template>
                                <el-select v-model="value.type">
                                    <el-option v-for="item in type" :key="item.value" :label="item.label" :value="item.value">
                                    </el-option>
                                </el-select>
                            </template>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-divider content-position="left">
                <h4>Prequalification Criteria</h4>
            </el-divider>
            <el-col>
                <el-col :span="24">
                    <el-collapse v-model="activeNames" @change="handleChange">
                        <el-collapse-item title="Pengelolaan K3L" name="1">
                            <el-col :span="20" style="margin-left: 20px;">
                                <el-collapse-item title="Faktor Utama" name="4">
                                    <el-form-item label="Option Type">
                                        <template>
                                            <el-select v-model="value.optiontype1">
                                                <el-option v-for="item in optiontype" :key="item.value" :label="item.label" :value="item.value">
                                                </el-option>
                                            </el-select>
                                        </template>
                                    </el-form-item>
                                    <!-- <el-table :data="tableData" size="mini" style="width: 100%">
                                        <el-table-column min-width="30" label="No">
                                            <template slot-scope="row">
                                                {{ row.$index+1 }}
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="1" label="Question" min-width="200">
                                        </el-table-column>
                                        <el-table-column min-width="30" label="Bidding Schedule">
                                            <template slot="header">
                                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="addNewRow"></el-button>
                                            </template>
                                            <template slot-scope="row">
                                                <el-button size="mini" icon="el-icon-delete" type="danger" @click="removeBusinessCategory(row.$index)"></el-button>
                                            </template>
                                        </el-table-column>
                                    </el-table> -->
                                    <template>
                                        <el-table :data="tableDataquestion" size="mini">
                                            <el-table-column min-width="20" label="No">
                                                <template slot-scope="row">
                                                    {{ row.$index+1 }}
                                                </template>
                                            </el-table-column>
                                            <el-table-column prop="question" label="Name" size="small" min-width="300">
                                                <template slot-scope="scope">
                                                    <el-input size="small" style="text-align:center" v-model="scope.row.question" :disabled='isEdit'></el-input>
                                                </template>
                                            </el-table-column>
                                            <el-table-column fixed="right" label="Operations" min-width="50">
                                                <template slot="header">
                                                    <el-button size="mini" icon="el-icon-plus" type="primary" @click="addRow"></el-button>
                                                    <el-button size="mini" icon="el-icon-edit" type="primary" @click="isEdit=!isEdit"></el-button>  
                                                    <!-- <el-button size="small" @click="saveAll">Save All</el-button> -->

                                                </template>
                                                <template slot-scope="row">
                                                    <!-- <el-button @click.native.prevent="saveRow($index, row)" type="text" size="small">
                                                        Save
                                                    </el-button> -->
                                                                                                
                                                    <!-- <el-button size="mini" icon="el-icon-edit" type="primary"  @click.native.prevent="saveRow($index, row)"></el-button> -->
                                                    <el-button size="mini" icon="el-icon-delete" type="danger" @click.native.prevent="deleteRow($index, row)"></el-button>

                                                </template>

                                            </el-table-column>
                                        </el-table>
                                    </template>
                                </el-collapse-item>
                                <el-collapse-item title="Faktor Pendukung" name="5">
                                    <el-form-item label="Option Type">
                                        <template>
                                            <el-select v-model="value.optiontype2">
                                                <el-option v-for="item in optiontype" :key="item.value" :label="item.label" :value="item.value">
                                                </el-option>
                                            </el-select>
                                        </template>
                                    </el-form-item>
                                    <el-table :data="tableData" size="mini" style="width: 100%">
                                        <el-table-column min-width="30" label="No">
                                            <template slot-scope="row">
                                                {{ row.$index+1 }}
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="1" label="Question" min-width="200">
                                        </el-table-column>
                                        <el-table-column min-width="30" label="Bidding Schedule">
                                            <template slot="header">
                                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="addNewRow"></el-button>
                                            </template>
                                            <template slot-scope="row">
                                                <el-button size="mini" icon="el-icon-delete" type="danger" @click="removeBusinessCategory(row.$index)"></el-button>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-collapse-item>
                            </el-col>
                        </el-collapse-item>
                        <el-collapse-item title="Organisasi Dan management " name="2">
                            <div>Operation feedback: enable the users to clearly perceive their operations by style updates and interactive effects;</div>
                            <div>Visual feedback: reflect current state by updating or rearranging elements of the page.</div>
                        </el-collapse-item>
                        <el-collapse-item title="Pengelolaan Sumber Daya " name="3">
                            <div>Simplify the process: keep operating process simple and intuitive;</div>
                            <div>Definite and clear: enunciate your intentions clearly so that the users can quickly understand and make decisions;</div>
                            <div>Easy to identify: the interface should be straightforward, which helps the users to identify and frees them from memorizing and recalling.</div>
                        </el-collapse-item>
                    </el-collapse>

                </el-col>
            </el-col>

            <!-- <template>
                <el-table size="mini" ref="multipleTable" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column property="1" label="Prequalification Criteria" min-width="120">
                    </el-table-column>
                    <el-table-column property="2" min-width="120" label="Sub Criteria">
                        <template slot-scope="{ row }">
                            <el-button type="text" @click="dialogTableVisible = true">view</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <template>
                <el-dialog title="Shipping address" :visible.sync="dialogTableVisible">
                    <el-table size="mini" :data="tableData">
                        <el-table-column property="1" label="Subcriteria" min-width="150"></el-table-column>
                        <el-table-column property="2" label="Option Type" min-width="200">
                            <el-select v-model="value" placeholder="Inpit Text">
                                <el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value">
                                </el-option>
                            </el-select>
                        </el-table-column>
                        <el-table-column property="3" label="Detail">
                            <el-button type="text" @click="dialogTableVisible2 = true">view</el-button>
                        </el-table-column>
                    </el-table>
                </el-dialog>
            </template> -->
            <template>
                <el-dialog title="Shipping address" :visible.sync="dialogTableVisible2">
                    <el-table size="mini" :data="tableData">
                        <el-table-column property="1" label="Question" min-width="150"></el-table-column>
                    </el-table>
                </el-dialog>
            </template>
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
