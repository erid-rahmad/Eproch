<template>
    <el-form
        ref="contractInfoForm"
        v-loading="loading"
        :disabled="readOnly"
        :label-position="formSettings.labelPosition"
        :label-width="formSettings.labelWidth"
        :model="contract"
        :rules="validationSchema"
        :size="formSettings.size"
        class="contract-info"
    >
        <el-row :gutter="gutterSize">
            <el-col
                :lg="12"
                :md="16"
                :sm="20"
                :xl="8"
                :xs="24"
            >
                <el-form-item label="Contract No.">
                    <el-input
                        ref="documentNo"
                        v-model="contract.documentNo"
                        :disabled="editMode && !!contract.documentNo"
                        clearable
                    ></el-input>
                </el-form-item>
                <el-form-item
                    label="Title"
                    prop="name"
                >
                    <el-input
                        v-model="contract.name"
                        clearable
                    ></el-input>
                </el-form-item>
                <el-form-item label="Purpose">
                    <el-radio-group v-model="contract.purpose">
                        <el-radio label="P">Project</el-radio>
                        <el-radio label="N">Non Project</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="">
                    <el-checkbox v-model="contract.forPriceConfirmation">
                        For Price Confirmation
                    </el-checkbox>
                </el-form-item>
                <el-form-item
                    label="PiC"
                    prop="picUserId"
                >
                    <ad-input-lookup
                        v-model="contract.picUserId"
                        :label-fields="['userLogin']"
                        :query="['employee.equals=true']"
                        placeholder="Select PiC"
                        table-name="ad_user"
                    ></ad-input-lookup>
                </el-form-item>
                <el-form-item
                    label="Department"
                    prop="costCenterId"
                >
                    <ad-input-lookup
                        v-model="contract.costCenterId"
                        placeholder="Select Department"
                        table-name="c_cost_center"
                    ></ad-input-lookup>
                </el-form-item>


            </el-col>
            <el-col
                :lg="12"
                :md="16"
                :sm="20"
                :xl="8"
                :xs="24">
                <el-form-item
                    label="Vendor Name"
                    prop="vendorId"
                >
                    <ad-input-lookup
                        v-model="contract.vendorId"
                        lookup-by-field="name"
                        placeholder="Select Vendor"
                        table-name="c_vendor"
                    ></ad-input-lookup>
                </el-form-item>
                <el-form-item
                    label="Start Date"
                    prop="startDate"
                >
                    <el-date-picker
                        v-model="contract.startDate"
                        :format="dateDisplayFormat"
                        :value-format="dateValueFormat"
                        clearable
                        type="date"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="Expiration Date">
                    <el-date-picker
                        v-model="contract.expirationDate"
                        :format="dateDisplayFormat"
                        :value-format="dateValueFormat"
                        clearable
                        type="date"
                    ></el-date-picker>
                </el-form-item>
                <el-form-item label="Evaluation Type">
                    <ad-input-lookup
                        v-model="contract.vendorEvaluationId"
                        placeholder="Select Evaluation Type"
                        table-name="c_vendor_evaluation"
                    ></ad-input-lookup>
                </el-form-item>
                <el-form-item label="Evaluation Period">
                    <ad-input-list
                        v-model="contract.evaluationPeriod"
                        placeholder="Select Evaluation Period"
                        reference-key="vendorEvaluationPeriod"
                    ></ad-input-list>
                </el-form-item>
                <el-form-item label="Total Price">
                    <el-input
                        v-model="contract.price"
                        clearable
                        disabled
                        v-inputmask="{ alias: 'currency' }"
                    ></el-input>
                </el-form-item>
            </el-col>
            <el-col
            >
                <el-divider content-position="left">
                    <h4>Requirement</h4>
                </el-divider>

                <template>
                    <el-table
                        v-loading="loading"
                        :data="contractLine"
                        border
                        highlight-current-row
                        size="mini"
                        stripe
                        style="width: 100%"

                    >
                        <el-table-column label="Product" min-width="180" size="mini">
                            <template slot-scope="{row}">
                                <ad-input-lookup
                                    v-model="row.productId"
                                    :label-fields="['name']"
                                    :query="['active.equals=true']"
                                    placeholder="Select Product"
                                    size="mini"
                                    table-name="c_product"
                                ></ad-input-lookup>
                            </template>
                        </el-table-column>
<!--                        <el-table-column label="Sub Product" min-width="80" size="mini">-->
<!--                            <template slot-scope="{row}">-->
<!--                            </template>-->
<!--                        </el-table-column>-->
                        <el-table-column label="Quantity" min-width="80" size="mini">
                            <template slot-scope="{row}">
                                <template>
                                    <el-input-number v-model="row.quantity" :max="999999" :min="1" size="mini"
                                                     @change="average"></el-input-number>
                                </template>
                            </template>
                        </el-table-column>
                        <el-table-column label="UOM" min-width="60" size="mini">
                            <template slot-scope="{row}">
                                <ad-input-lookup
                                    v-model="row.uomId"
                                    :label-fields="['code']"
                                    :query="['active.equals=true']"
                                    placeholder="Select Uom"
                                    size="mini"
                                    table-name="c_unit_of_measure"
                                ></ad-input-lookup>
                            </template>
                        </el-table-column>
                        <el-table-column label="Cost Center" min-width="60" size="mini">
                            <template slot-scope="{row}">
                                <ad-input-lookup
                                    v-model="row.costCenterId"
                                    :label-fields="['code']"
                                    :query="['active.equals=true']"
                                    placeholder="Select CC"
                                    size="mini"
                                    table-name="c_cost_center"
                                ></ad-input-lookup>
                            </template>
                        </el-table-column>
                        <el-table-column label="Ceiling Price" min-width="120" size="mini">
                            <template slot-scope="{row}">
                                <el-input
                                    v-model="row.ceilingPrice"
                                    v-inputmask="{ alias: 'currency' }"
                                    clearable
                                    size="mini"
                                    @change="average"
                                ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="Total Ceiling Price" min-width="120" size="mini">
                            <template slot-scope="{row}">
                                <el-input
                                    disabled
                                    v-model="row.totalCeilingPrice"
                                    v-inputmask="{ alias: 'currency' }"
                                    clearable
                                    size="mini"
                                ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="Delivery Date" min-width="120" size="mini">
                            <template slot-scope="{row}">
                                <el-date-picker
                                    v-model="row.deliveryDate"
                                    placeholder="Pick a day"
                                    type="date">
                                </el-date-picker>
                            </template>
                        </el-table-column>
                        <el-table-column label="Remark" min-width="100" size="mini">
                            <template slot-scope="{row}">
                                <el-input
                                    v-model="row.remark"
                                    clearable
                                    size="mini"
                                ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column min-width="50" size="mini">
                            <template slot="header">
                                <el-button
                                    icon="el-icon-plus"
                                    size="mini"
                                    type="primary"
                                    @click="addLine"
                                ></el-button>
                            </template>
                            <template slot-scope="row">
                                <el-button
                                    icon="el-icon-delete"
                                    size="mini"
                                    type="primary"
                                    @click="deleteRow(row)"
                                ></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
    </el-form>
</template>
<script lang="ts" src="./contract-info.component.ts"></script>
