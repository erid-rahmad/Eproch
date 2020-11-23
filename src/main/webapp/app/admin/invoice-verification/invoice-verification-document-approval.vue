<template>
    <div class="app-container">
        <el-form
            :model="dataVerificationAndLines"
            label-position="left"
            ref="form"
            label-width="120px"
            size="mini">
            <el-row style="margin-top: 0px" class="form">

                    <el-col :span="8">
                        <el-form-item label="Verification No." prop="verificationNo">
                            : {{ formHeader.verificationNo }}
                        </el-form-item>
                        <el-form-item label="Verification Date" prop="verificationDate">
                            : {{ formHeader.verificationDate }}
                        </el-form-item>
                        <el-form-item label="Currency" prop="currencyName">
                            : {{ formHeader.currencyName }}
                        </el-form-item>
                        <el-form-item label-width="0px">
                            <el-button
                                size="mini"
                                icon="el-icon-info"
                                type="success"
                                @click="displayTaxInfo()">
                                    Tax Info
                            </el-button>
                        </el-form-item>

                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Invoice No." prop="invoiceNo">
                            : {{ formHeader.invoiceNo }}
                        </el-form-item>
                        <el-form-item label="Invoice Date" prop="invoiceDate">
                            : {{ formHeader.invoiceDate }}
                        </el-form-item>
                        <el-form-item label="GL Date" prop="glDate">
                            <el-date-picker
                                style="width: 90%"
                                class="form-input date"
                                clearable
                                v-model="formHeader.glDate"
                                type="date"
                                format="yyyy/MM/dd"
                                value-format="yyyy-MM-dd"
                                placeholder="Pick a date"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Tax Invoice No." prop="taxInvoice">
                            : {{ formHeader.taxInvoice }}
                        </el-form-item>
                        <el-form-item label="Tax Invoice Date" prop="taxDate">
                            : {{ formHeader.taxDate }}
                        </el-form-item>
                        <el-form-item label="Due Date" prop="dueDate">
                            <el-date-picker
                                class="form-input date"
                                clearable
                                v-model="formHeader.dueDate"
                                type="date"
                                format="yyyy/MM/dd"
                                value-format="yyyy-MM-dd"
                                placeholder="Pick a date"/>
                        </el-form-item>
                    </el-col>

            </el-row>
            <el-row class="line" ref="tableWrapper">
                <el-col :span="24">

                    <el-table
                        v-loading="processing"
                        ref="gridData"
                        highlight-current-row
                        border stripe
                        size="mini"
                        style="width: 100%; height: 100%; padding-top: 0px;"
                        :height="gridSchema.height"
                        :empty-text="gridSchema.emptyText"
                        :data="gridData"
                        @sort-change="changeOrder">

                        <el-table-column
                            min-width="130"
                            prop="payStat"
                            label="Pay Stat">

                            <template slot-scope="props">
                                <el-select class="form-input" v-model="props.row.payStat" placeholder="Pay Stat" size="mini">
                                    <el-option
                                        v-for="item in payStat"
                                        :key="item.key"
                                        :label="item.value"
                                        :value="item.key" />
                                </el-select>
                            </template>

                        </el-table-column>
                        <el-table-column
                            min-width="130"
                            prop="poNo"
                            label="PO No."/>
                        <el-table-column
                            min-width="150"
                            prop="receiptNo"
                            label="Receipt No."/>
                        <el-table-column
                            min-width="256"
                            prop="description"
                            label="Item Description"/>
                        <el-table-column
                            min-width="128"
                            prop="cUOM"
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
                            label="Amount"/>

                        <el-table-column
                            min-width="128"
                            prop="taxAmount"
                            label="VAT In"/>

                    </el-table>


                </el-col>
            </el-row>
            <el-row style="margin-top: 0px" class="form">

                    <el-col :span="8">
                        <el-form-item label="Taxable Amount" prop="totalLines">
                            : {{ formHeader.totalLines }}
                        </el-form-item>
                        <el-form-item label="PPN" prop="taxAmount">
                            : {{ formHeader.taxAmount }}
                        </el-form-item>
                        <el-form-item label="Grand Total" prop="grandTotal">
                            : {{ formHeader.grandTotal }}
                        </el-form-item>

                    </el-col>
                    <el-col :span="16">
                        <el-form-item label="Notes" prop="note">
                            <el-input class="form-input" clearable v-model="formHeader.note"></el-input>
                        </el-form-item>
                    </el-col>

            </el-row>
        </el-form>

        <el-dialog
            margin="0px"
            padding="0px"
            width="60%"
            append-to-body
            :visible.sync="dialogTaxInfoVisible"
            :title="dialogTitle">

            <template>
                <el-row :gutter="16">
                    <el-col :span="24" :offset="0">
                        <tax-info
                            ref="taxInfo"
                            @get-form-tax-info="dataTaxInfo"
                        />
                    </el-col>
                </el-row>

                <div slot="footer">

                    <el-button
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="getTaxInfo">
                            Ok
                    </el-button>

                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./invoice-verification-document-approval.component.ts">
</script>

<style lang="scss">


</style>
