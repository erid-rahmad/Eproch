<template>
    <div class="app-container verification">

        <el-row class="header">
            <el-col :span="24">

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="danger"
                    icon="el-icon-close"
                    @click="closeDetailVerification">
                    Close
                </el-button>

            </el-col>
        </el-row>

        <el-row class="filter">
            <el-form ref="form"  label-width="170px" size="mini">
                <el-col :span="8">
                    <el-form-item label="Invoice No. :" prop="invoiceNo">
                        {{ detailVerification.invoiceNo }}
                    </el-form-item>
                    <el-form-item label="Invoice Date :" prop="invoiceDate">
                        {{ detailVerification.invoiceDate }}
                    </el-form-item>
                    <el-form-item label="Currency :" prop="currencyName">
                        {{ detailVerification.currencyName }}
                    </el-form-item>

                </el-col>
                <el-col :span="8">
                    <el-form-item label="Tax Invoice No. :" prop="taxInvoice">
                        {{ detailVerification.taxInvoice | facade('###-##.########') }}
                    </el-form-item>
                    <el-form-item label="Tax Invoice Date :" prop="taxDate">
                        {{ detailVerification.taxDate }}
                    </el-form-item>
                    <el-form-item label="NPWP :" prop="vendorName">
                        {{ detailVerification.vendorName }}
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="Taxable Amount :" prop="totalLines" align="right">
                        {{ detailVerification.totalLines | formatCurrency }}
                    </el-form-item>
                    <el-form-item label="PPN :" prop="taxAmount" align="right">
                        {{ detailVerification.taxAmount | formatCurrency }}
                    </el-form-item>
                    <el-form-item label="Total Amount :" prop="grandTotal" align="right">
                        {{ detailVerification.grandTotal | formatCurrency }}
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
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="gridData"
                    :row-class-name="rowClassName"
                    @row-click="singleSelection"
                    @sort-change="changeOrder"
                >

                    <el-table-column
                        align="center"
                        class-name="no-ellipsis"
                        fixed
                        width="36"
                    >
                        <template slot="header"></template>
                        <template slot-scope="{ $index }">
                            <el-radio class="radio" v-model="radioSelection" :label="$index">&nbsp;</el-radio>
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="130"
                        sortable
                        prop="poNo"
                        label="PO No."/>
                    <el-table-column
                        min-width="130"
                        sortable
                        prop="receiptNo"
                        label="Receipt No."/>
                    <el-table-column
                        min-width="130"
                        sortable
                        prop="deliveryNo"
                        label="Delivery No."/>
                    <el-table-column
                        min-width="250"
                        sortable
                        prop="mProductName"
                        label="Description"/>
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="cUomName"
                        label="UoM"/>
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
                        min-width="150"
                        sortable
                        label="Total Amount"
                        align="right">
                        <template slot-scope="{ row }">
                            {{ row.totalAmount | formatCurrency }}
                        </template>
                    </el-table-column>

                </el-table>
                <el-pagination
                    ref="pagination"
                    background
                    layout="sizes, prev, pager, next"
                    small
                    :current-page.sync="page"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="itemsPerPage"
                    :total="queryCount"
                    @size-change="changePageSize"/>

            </el-col>
        </el-row>



    </div>
</template>

<script lang="ts" src="./detail-verification-document.component.ts"></script>

<style lang="scss">
.compact .verification{
    padding: 0px;
}

.el-table .cell.no-ellipsis {
    text-overflow: inherit;
}

.main {
    padding: 0px;

    .el-table .el-table__body,
    .el-table--striped .el-table__body {

        .danger-row td,
        tr.el-table__row--striped.danger-row td {
            background: #ffc1c1;
        }

        .warning-row td,
        tr.el-table__row--striped.warning-row td {
            background: #fff2cd;
        }
    }
}
</style>
