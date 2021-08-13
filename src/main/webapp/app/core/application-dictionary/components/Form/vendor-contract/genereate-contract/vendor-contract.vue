<template>
    <div
        v-loading="generating"
        class="app-container"
    >
        <el-row>
            <el-col
                :span="24"
                style="padding: 4px"
            >
                <el-button
                    class="button"
                    icon="el-icon-check"
                    size="mini"
                    type="primary"
                    @click="generatePurchaseOrder"
                >
                    Create PO
                </el-button>
            </el-col>
        </el-row>
        Generate PO
        <el-form
            ref="filterForm"
            label-position="left"
            label-width="150px"
            :model="filter"
            size="mini"
        >
            <el-row :gutter="rowGutter">
                <el-col
                    :md="8"
                    :sm="12"
                    :xs="24"
                >
                    <el-form-item label="Contract No">
                        <el-input
                            ref="contractNumber"
                            v-model="filter.contractNumber"
                            v-loading="loadingPr"
                            clearable
                            placeholder="Please Enter Contract No"
                            @change="retrievePurchaseRequisitionLines"
                            @clear="retrievePurchaseRequisitionLines()"
                        ></el-input>
                    </el-form-item>
                </el-col>

                <el-col
                    :md="8"
                    :sm="12"
                    :xs="24"
                >
                    <el-form-item label="Supplier">
                        <el-select
                            v-model="filter.vendorId"
                            class="form-input"
                            clearable
                            filterable
                            placeholder="Select"
                            @change="onVendorChange"
                        >
                            <el-option
                                v-for="item in vendorOptions"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col
                    :md="8"
                    :sm="12"
                    :xs="24"
                >
                    <el-button
                        icon="el-icon-search"
                        :loading="loadingPrLines"
                        size="mini"
                        type="primary"
                        @click="retrievePurchaseRequisitionLines(filter.requisitionNo, filter.vendorId)"
                    >
                        Search
                    </el-button>
                </el-col>
            </el-row>
        </el-form>
        <el-divider></el-divider>
        <el-table
            v-loading="loadingPrLines"
            ref="mainTable"
            border
            :data="gridData"
            :default-sort="gridSchema.defaultSort"
            :empty-text="gridSchema.emptyText"
            :height="gridSchema.height"
            highlight-current-row
            stripe
            size="mini"
            style="width: 100%"
            @sort-change="changeOrder"
            @selection-change="onSelectionChanged"
        >

            <el-table-column
                align="center"
                fixed
                type="selection"
                width="48"
            ></el-table-column>
            <el-table-column
                label="Contract No"
                min-width="100"
                prop="contractNo"
                sortable
            ></el-table-column>
            <el-table-column
                label="Product"
                min-width="200"
                prop="productName"
                show-overflow-tooltip
                sortable
            ></el-table-column>
            <el-table-column
                label="Supplier"
                min-width="200"
                prop="vendorName"
                show-overflow-tooltip
                sortable
            ></el-table-column>
            <el-table-column
                label="Contract Qty"
                min-width="150"
                prop="quantityMax"
                sortable
            ></el-table-column>
            <el-table-column
                label="Ordered Qty"
                min-width="152"
                prop="quantityOrdered"
                sortable
            >
                <template slot-scope="{ row, $index }">
                    <el-input-number
                        v-model="row.quantityOrdered"
                        clearable
                        controls-position="right"
                        :max="row.quantityMax"
                        :min="0"
                        size="mini"
                        @change="value => onQuantityOrderedChanged(row, $index, value)"
                    ></el-input-number>
                </template>
            </el-table-column>
            <el-table-column
                label="Qty Balance"
                min-width="150"
                prop="quantityBalance"
                sortable
            ></el-table-column>
            <el-table-column
                min-width="100"
                sortable
                prop="uomCode"
                label="UoM"
            ></el-table-column>
            <el-table-column
                min-width="150"
                sortable
                label="Unit Price"
            >
                <template slot-scope="{ row }">
                    {{ row.ceilingPrice | formatCurrency }}
                </template>
            </el-table-column>
            <el-table-column
                min-width="150"
                sortable
                label="Order Amount"
            >
                <template slot-scope="{ row }">
                    {{ row.orderAmount | formatCurrency }}
                </template>
            </el-table-column>
        </el-table>
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
                        v-model="form.paymentTermId"
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
                        v-model="form.warehouseId"
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
                        v-model="form.documentTypeId"
                        lookup-by-field="name"
                        :label-fields="['name']"
                        placeholder="Select document Type"
                        table-name="c_document_type"
                    ></ad-input-lookup>
                </el-form-item>
                <el-form-item
                    label="Date trx"

                >
                    <el-date-picker
                        v-model="form.dateTrx"
                        placeholder="Pick a day"
                        type="date">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="generatePA = false">Cancel</el-button>
                <el-button type="primary" @click="generatePo">Confirm</el-button>
                </span>
        </el-dialog>


        <!-- <el-pagination
          ref="pagination"
          background
          :current-page.sync="page"
          layout="sizes, prev, pager, next"
          mini
          :page-sizes="[10, 20, 50, 100]"
          :page-size="itemsPerPage"
          :total="queryCount"
          @size-change="changePageSize"
        > -->

    </div>
</template>

<script lang="ts" src="./vendor-contract.component.ts">
</script>

<style lang="scss">
.el-table__fixed, .el-table__fixed-right {
    box-shadow: none;
}
</style>

<style lang="scss" scoped>
.el-divider--horizontal {
    margin: 12px 0;
}

.form-input {
    width: 100%;
}

.main {
    padding: 0px;
}

.button {
    margin-bottom: 5px;
}
</style>
