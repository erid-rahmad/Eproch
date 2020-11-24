<template>
    <div class="app-container verification-update">

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
                    <el-form-item label="Invoice No." prop="invoiceNo">
                        <el-input class="form-input" clearable v-model="detailVerification.invoiceNo" disabled/>
                    </el-form-item>
                    <el-form-item label="Invoice Date" prop="invoiceDate">
                        <el-date-picker
                            class="form-input"
                            clearable disabled
                            v-model="detailVerification.invoiceDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="Currency" prop="currencyName">
                        <el-select class="form-input" clearable disabled filterable v-model="detailVerification.currencyName" placeholder="Currency" >
                            <el-option
                                v-for="item in currencyOptions"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>

                </el-col>
                <el-col :span="8">
                    <el-form-item label="Tax Invoice No." prop="taxInvoice">
                        <el-input
                            class="form-input"
                            clearable disabled
                            v-model="detailVerification.taxInvoice"
                            v-inputmask
                            data-inputmask="'mask': '99.999.999.9-999.999'"
                            placeholder="__.___.___._-___.___"
                            />
                    </el-form-item>
                    <el-form-item label="Tax Invoice Date" prop="taxDate">
                        <el-date-picker
                            class="form-input"
                            clearable disabled
                            v-model="detailVerification.taxDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="NPWP" prop="vendorName">
                        <el-input class="form-input" disabled clearable v-model="detailVerification.vendorName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="Taxable Amount" prop="totalLines">
                        <el-input class="form-input" disabled clearable v-model="detailVerification.totalLines"></el-input>
                    </el-form-item>
                    <el-form-item label="PPN" prop="taxAmount">
                        <el-input class="form-input" disabled clearable v-model="detailVerification.taxAmount"></el-input>
                    </el-form-item>
                    <el-form-item label="Total Amount" prop="grandTotal">
                        <el-input class="form-input" disabled clearable v-model="detailVerification.grandTotal"></el-input>
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
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="gridData"
                    @sort-change="changeOrder">

                    <el-table-column
                        min-width="130"
                        prop="poNo"
                        label="PO No."/>
                    <el-table-column
                        min-width="150"
                        prop="receiptNo"
                        label="Receipt No."/>
                    <el-table-column
                        min-width="128"
                        prop="deliveryNo"
                        label="Delivery No."/>
                    <el-table-column
                        min-width="256"
                        prop="description"
                        label="Description"/>
                    <el-table-column
                        min-width="128"
                        prop="uomName"
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
                        label="Total Amount">
                        {{ totalAmount }}
                    </el-table-column>

                </el-table>

            </el-col>
        </el-row>



    </div>
</template>

<script lang="ts" src="./detail-verification-document.component.ts">
</script>

<style lang="scss">
    .compact .verification-update{
        padding: 0px;
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
