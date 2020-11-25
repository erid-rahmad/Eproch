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
                            style="margin-left: 0px;"
                            size="small"
                            type="danger"
                            icon="el-icon-close"/>

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

                <el-row class="main grid-view" ref="tableWrapper">
                    <el-col :span="24">
                        <el-table
                            v-loading="processing"
                            ref="gridData"
                            highlight-current-row
                            border stripe
                            size="mini"
                            style="width: 100%: height: 100%"
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
                                min-width="130"
                                v-if="isVendor==null"
                                prop="cvendorName"
                                label="Vendor"/>

                            <el-table-column
                                min-width="130"
                                prop="poNo"
                                label="PO No."/>
                            <el-table-column
                                min-width="130"
                                prop="receiptNo"
                                label="Receive No."/>
                            <el-table-column
                                min-width="100"
                                prop="receiptDate"
                                label="Receive Date"/>

                            <el-table-column
                                min-width="128"
                                prop="deliveryNo"
                                label="Delivery No."/>
                            <el-table-column
                                min-width="128"
                                prop="description"
                                label="Description"/>
                            <el-table-column
                                min-width="128"
                                prop="cuomName"
                                label="UoM"/>
                            <el-table-column
                                min-width="128"
                                prop="qty"
                                label="Qty"/>
                            <el-table-column
                                min-width="128"
                                prop="priceActual"
                                label="Unit Price"/>

                            <el-table-column
                                min-width="128"
                                prop="totalLines"
                                label="Taxable Amount"/>
                            <el-table-column
                                min-width="128"
                                prop="taxAmount"
                                label="PPN"/>
                            <el-table-column
                                min-width="128"
                                prop="mMatchType"
                                label="Status"/>

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

    .grid-view {
        .el-table {
            .is-error .el-input__inner {
                border-color: #ff4949;
            }

            label.el-checkbox {
                margin: 4px 0;
            }

            .switch, .checkbox, .selectRemote, .select, .input, .numeric, .date{
                width: 100%;
            }
        }

        .el-pagination {
            background: #fff;
            bottom: 0;
            left: 0;
            right: 0;
            z-index: 5;

            .el-input--mini .el-input__inner {
                height: 22px;
            }
        }
    }
</style>
