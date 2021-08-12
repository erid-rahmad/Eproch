<template>
    <div>
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
                :lg="8"
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
                :lg="8"
                :md="16"
                :sm="20"
                :xl="8"
                :xs="24">
                <el-form-item
                    label="Currency"
                    prop="currencyId"
                >
                    <ad-input-lookup
                        v-model="contract.currencyId"
                        lookup-by-field="code"
                        placeholder="Select currency"
                        table-name="c_currency"
                    ></ad-input-lookup>
                </el-form-item>
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
                    label="Contract Date"
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

            </el-col>

            <el-col>
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
                                    v-model="row.totalCeilingPrice"
                                    v-inputmask="{ alias: 'currency' }"
                                    clearable
                                    disabled
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
        <el-dialog
            :visible.sync="generatePA"
            title="Tips"
            width="30%">
            <el-form
                v-loading="loading"
                :label-position="formSettings.labelPosition"
                :label-width="formSettings.labelWidth"
                :rules="validationSchema"
                :size="formSettings.size"
            >
            <el-form-item
                label="payment Term"
                prop="paymentTermId"

            >
                <ad-input-lookup
                    v-model="contract.paymentTermId"
                    lookup-by-field="name"
                    :label-fields="['code']"
                    placeholder="Select paymentTerm"
                    table-name="c_payment_term"
                ></ad-input-lookup>
            </el-form-item>
            <el-form-item
                label="warehouse"
                prop="warehouseId"
            >
                <ad-input-lookup
                    v-model="contract.warehouseId"
                    lookup-by-field="name"
                    :label-fields="['name']"
                    placeholder="Select warehouse"
                    table-name="c_warehouse"
                ></ad-input-lookup>
            </el-form-item>
            <el-form-item
                label="document Type"
                prop="documentTypeId"
            >
                <ad-input-lookup
                    v-model="contract.documentTypeId"
                    lookup-by-field="name"
                    :label-fields="['name']"
                    placeholder="Select document Type"
                    table-name="c_document_type"
                ></ad-input-lookup>
            </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="generatePA = false">Cancel</el-button>
                <el-button type="primary" @click="generatePo">Confirm</el-button>
                </span>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./contract-info.component.ts"></script>
