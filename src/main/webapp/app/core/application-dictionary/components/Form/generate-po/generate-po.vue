<template>
    <div class="app-container">
        <el-row class="toolbar">
            <el-col :span="24">

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="primary"
                    icon="el-icon-check"
                    @click="generatePurchaseOrder">
                    Create PO
                </el-button>

            </el-col>
        </el-row>

        <el-row class="header" :gutter="24">
            <el-form ref="form" label-position="left" label-width="150px" size="mini">
                <el-col :span="8">
                    <el-form-item label="PR No" prop="prNo">
                        <el-input
                            placeholder="Please Input PR No"
                            clearable
                            v-model="pr.form.id">
                            <el-button
                                slot="append"
                                icon="el-icon-search"
                                @click="searchPurchaseRequisition"
                                v-loading.fullscreen.lock="fullscreenLoading"/>
                        </el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                    <el-form-item label="Warehouse" prop="warehouse">
                        <el-select class="form-input" clearable filterable v-model="pr.form.warehouseId" placeholder="Select" >
                            <el-option
                                v-for="item in selectWarehouse"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                    <el-form-item label="Term of Payment" prop="top">
                        <el-select class="form-input" clearable filterable v-model="pr.form.top" placeholder="Select" >
                            <el-option
                                v-for="item in selectTop"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                </el-col>

            </el-form>
        </el-row>

        <el-row class="header" :gutter="24">
            <el-form ref="form" label-position="left" label-width="150px" size="mini">
                <el-col :span="8">
                    <el-form-item label="Supplier" prop="supplier">
                        <el-select class="form-input" clearable filterable v-model="pr.form.supplierId" placeholder="Select" >
                            <el-option
                                v-for="item in selectVendor"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                    <el-form-item label="Date Order" prop="documentDate">
                        <el-date-picker
                            class="form-input"
                            clearable
                            v-model="pr.form.documentDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date"/>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                    <el-form-item label="Currency" prop="currency">
                        <el-select class="form-input" clearable filterable v-model="pr.form.currencyId" placeholder="Select" >
                            <el-option
                                v-for="item in selectCurrency"
                                :key="item.key"
                                :label="item.code"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                </el-col>

            </el-form>
        </el-row>

        <el-row class="main ">
            <el-col :span="24">

                <el-table
                    v-loading="processing"
                    ref="gridData"
                    highlight-current-row
                    border stripe fit
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="gridData"
                    @sort-change="changeOrder"
                    @selection-change="onSelectionChanged">

                    <el-table-column
                        align="center"
                        fixed
                        type="selection"
                        width="48"/>

                    <el-table-column
                        min-width="100"
                        sortable
                        prop="requisitionId"
                        label="PR No"/>
                    <el-table-column
                        min-width="200"
                        sortable
                        prop="productName"
                        label="Product"/>
                    <el-table-column
                        min-width="200"
                        sortable
                        prop="vendorName"
                        label="Supplier"/>
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="quantity"
                        label="PR Qty"/>
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="orderedQty"
                        label="Order Qty">
                        0
                    </el-table-column>
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="qtyBalance"
                        label="Qty Balance">
                        <template slot-scope="props">
                            <el-input
                                clearable
                                size="mini"
                                v-model="props.row.qtyBalance"/>
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="uomName"
                        label="UoM"/>

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

    </div>
</template>

<script lang="ts" src="./generate-po.component.ts">
</script>

<style lang="scss">
    .el-table__fixed, .el-table__fixed-right{
        box-shadow: none;
    }

    .header {
        color: #333;
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
