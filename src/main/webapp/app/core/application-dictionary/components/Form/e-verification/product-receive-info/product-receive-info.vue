<template>
    <div class="app-container">
                <el-row class="header">
                    <el-col :span="24">

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-search"
                            @click.native.prevent="verificationFilter"/>

                        <el-button
                            class="button"
                            size="small"
                            type="primary"
                            icon="el-icon-download">
                            Export
                        </el-button>

                    </el-col>
                </el-row>

                <el-row class="filter">
                    <el-form ref="form"  label-width="170px" size="mini">
                        <el-col :span="8">

                            <el-form-item label="Receive No." prop="receiveNo">
                                <el-input class="form-input" clearable v-model="filter.receiveNo"/>
                            </el-form-item>
                            <el-form-item label="Receive Date - From" prop="receiveDateFrom">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.receiveDateFrom"
                                    type="date"
                                    :format="dateDisplayFormat"
                                    :value-format="dateValueFormat"
                                    placeholder="Pick a date"/>
                            </el-form-item>

                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="PO No." prop="poNo">
                                <el-input class="form-input" clearable v-model="filter.poNo"/>
                            </el-form-item>

                            <el-form-item label="Receive Date - To" prop="receiveDateTo">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.receiveDateTo"
                                    type="date"
                                    :format="dateDisplayFormat"
                                    :value-format="dateValueFormat"
                                    placeholder="Pick a date"/>
                            </el-form-item>

                        </el-col>
                        <el-col :span="8">

                            <el-form-item v-if="isVendor!=null" label="Delivery No." prop="deliveryNo">
                                <el-input class="form-input" clearable v-model="filter.deliveryNo"/>
                            </el-form-item>

                            <el-form-item v-else label="Vendor" prop="vendor">
                                <el-select class="form-input" clearable filterable v-model="filter.vendor" >
                                    <el-option
                                        v-for="item in vendorOptions"
                                        :key="item.key"
                                        :label="item.value"
                                        :value="item.key" />
                                </el-select>
                            </el-form-item>

                            <el-form-item label="Status" prop="productReceiveStatus">
                                <el-select class="form-input" clearable filterable v-model="filter.productReceiveStatus" placeholder="Select" >
                                    <el-option
                                        v-for="item in statusOptions"
                                        :key="item.key"
                                        :label="item.value"
                                        :value="item.key" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-form>

                </el-row>

                <el-row class="main" ref="tableWrapper">
                    <el-col :span="24">
                        <el-table
                            v-loading="processing"
                            ref="gridData"
                            highlight-current-row
                            border stripe
                            size="mini"
                            style="width: 100%; height: 100%"
                            :height="gridSchema.height"
                            :max-height="gridSchema.maxHeight"
                            :default-sort="gridSchema.defaultSort"
                            :empty-text="gridSchema.emptyText"
                            :data="gridData"
                            @row-click="singleSelection"
                            @sort-change="changeOrder">

                            <el-table-column
                                align="center"
                                fixed
                                width="35">
                                <template slot-scope="scope">
                                    <el-radio class="radio" v-model="radioSelection" :label="scope.$index">&nbsp;</el-radio>
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="250"
                                sortable
                                v-if="isVendor==null"
                                prop="cVendorId"
                                label="Vendor">
                                <template slot-scope="{ row }">
                                    {{ row.cVendorShortName }}
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="120"
                                sortable
                                prop="poNo"
                                label="PO No."/>
                            <el-table-column
                                min-width="120"
                                sortable
                                prop="receiptNo"
                                label="Receive No."/>
                            <el-table-column
                                min-width="120"
                                sortable
                                prop="receiptDate"
                                label="Receive Date"/>
                            <el-table-column
                                min-width="120"
                                sortable
                                prop="deliveryNo"
                                label="Delivery No."/>
                            <el-table-column
                                min-width="250"
                                sortable
                                prop="mProductName"
                                label="Item Description"/>
                            <el-table-column
                                min-width="100"
                                sortable
                                prop="cUomId"
                                label="UoM">
                                <template slot-scope="{ row }">
                                    {{ row.cUomName }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="100"
                                sortable
                                prop="qty"
                                label="Qty"/>

                            <el-table-column
                                min-width="150"
                                sortable
                                prop="priceActual"
                                label="Unit Price"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.priceActual | formatCurrency }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="150"
                                sortable
                                prop="totalLines"
                                label="Taxable Amount"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.totalLines | formatCurrency }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="150"
                                sortable
                                prop="taxAmount"
                                label="PPN"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.taxAmount | formatCurrency }}
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="120"
                                sortable
                                prop="mMatchType"
                                label="Status">
                                <template slot-scope="{ row }">
                                    {{ formatDocumentStatus(row.mMatchType) }}
                                </template>
                            </el-table-column>

                        </el-table>
                        <el-pagination
                            ref="pagination"
                            background
                            layout="sizes, prev, pager, next"
                            mini
                            :current-page.sync="page"
                            :page-sizes="[10, 20, 50, 100]"
                            :page-size="itemsPerPage"
                            :total="queryCount"
                            @size-change="changePageSize"/>
                    </el-col>
                </el-row>

            <!-- =========================================================================== -->



        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            title="Update Voucher Match No.">

            <template>

                <el-row :gutter="16">
                    <el-col :span="24" :offset="0">

                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./product-receive-info.component.ts">
</script>

<style lang="scss">
    .el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
    }

    .header {
        color: #333;
    }

    .filter {
        .form-input {
            width: 100%;
        }
    }

    .main {
        padding: 0px;
    }

    .button {
        margin-bottom: 5px;
    }
</style>
