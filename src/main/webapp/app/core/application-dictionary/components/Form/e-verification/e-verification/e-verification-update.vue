<template>
    <div class="app-container verification-update">

        <el-row class="header">
            <el-col :span="24">

                <el-button
                    v-if="isDraft"
                    v-loading.fullscreen.lock="fullscreenLoading"
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="primary"
                    icon="el-icon-check"
                    @click="updateEVerification"
                />

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="danger"
                    icon="el-icon-close"
                    @click="closeEVerificationUpdate"
                />

                <el-button
                    v-if="isDraft"
                    class="button"
                    size="small"
                    type="primary"
                    icon="el-icon-plus"
                    @click="displayMatchPo(1)"
                >
                    Add
                </el-button>

                <el-button
                    v-if="isDraft"
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="primary"
                    icon="el-icon-plus"
                    @click="displayMatchPo(2)"
                >
                    Add by Receipt No.
                </el-button>

            </el-col>
        </el-row>

        <el-row class="filter" :gutter="24">
            <el-form
                ref="eVerificationUpdate"
                label-position="left"
                label-width="170px"
                :model="header"
                :rules="validationRules"
                size="mini"
            >
                <el-col :span="8">
                    <el-form-item label="Invoice No." prop="invoiceNo" required>
                        <el-input
                            v-model="header.invoiceNo"
                            :disabled="!isDraft"
                            class="form-input"
                            clearable
                        />
                    </el-form-item>
                    <el-form-item label="Invoice Date" prop="invoiceDate" required>
                        <el-date-picker
                            v-model="header.invoiceDate"
                            class="form-input"
                            clearable
                            :disabled="!isDraft"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="Currency" prop="currencyId" required>
                        <el-select
                            v-model="header.currencyId"
                            :disabled="!isDraft"
                            class="form-input"
                            clearable
                            filterable
                            placeholder="Currency"
                        >
                            <el-option
                                v-for="item in currencyOptions"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key"
                            />
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="!isDraft" label="Verification No" prop="verificationNo">
                        <el-input class="form-input" disabled clearable v-model="header.verificationNo"/>
                    </el-form-item>
                    <el-form-item v-if="!isDraft" label="Verification Date" prop="verificationDate">
                        <el-date-picker
                            class="form-input"
                            clearable disabled
                            v-model="header.verificationDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="Tax Invoice No." prop="taxInvoice" :required="header.taxable" >
                        <el-input
                            class="form-input"
                            :disabled="!isDraft"
                            v-model="header.taxInvoice"
                            v-cleave="{blocks: [3, 2, 8], delimiters: ['-', '.'], numericOnly: true}"
                            placeholder="___-__.________"
                            @change="checkVerification"
                        />
                    </el-form-item>
                    <el-form-item label="Tax Invoice Date" prop="taxDate" required>
                        <el-date-picker
                            class="form-input"
                            clearable
                            v-model="header.taxDate"
                            type="date"
                            :disabled="!isDraft"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="NPWP" prop="vendorName">
                        <el-input class="form-input" disabled clearable v-model="header.vendorName"/>
                    </el-form-item>
                    <el-form-item v-if="!isDraft" label="Status" prop="verificationStatus">
                        <el-input class="form-input" disabled clearable :value="formatDocumentStatus(header.verificationStatus)"/>
                    </el-form-item>
                    <el-form-item v-if="!isDraft" label="Status Date">
                        <el-date-picker
                            class="form-input"
                            clearable disabled
                            :value="dateStatus(header.verificationStatus)"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="Taxable Amount" prop="totalLines">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="header.totalLines"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                    <el-form-item label="PPN" prop="taxAmount">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="header.taxAmount"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                    <el-form-item label="Total Amount" prop="grandTotal">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="header.grandTotal"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                </el-col>
            </el-form>

        </el-row>

        <el-row class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-table
                    v-loading="processing"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="gridData"
                    :row-class-name="rowClassName"
                    @sort-change="changeOrder"
                >

                    <el-table-column
                        v-if="isDraft"
                        align="center"
                        fixed
                        width="55"
                    >
                        <template slot-scope="{ row, $index }">
                            <el-button
                                type="danger"
                                size="mini"
                                icon="el-icon-close"
                                plain
                                @click="removeRow(row, $index)"
                                :title="$t('entity.action.delete')"
                            />
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="120"
                        sortable
                        prop="poNo"
                        label="PO No."
                    />
                    <el-table-column
                        min-width="120"
                        sortable
                        prop="receiveNo"
                        label="Receipt No."
                    >
                        <template slot-scope="{ row }">
                            {{ row.receiptNo }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="120"
                        sortable
                        prop="deliveryNo"
                        label="Delivery No."
                    />
                    <el-table-column
                        min-width="250"
                        prop="mProductName"
                        label="Item Description"
                    />
                    <el-table-column
                        min-width="100"
                        prop="cUomName"
                        label="UoM"
                    />
                    <el-table-column
                        min-width="100"
                        sortable
                        prop="qty"
                        label="Qty"
                    />
                    <el-table-column
                        min-width="150"
                        sortable
                        prop="priceActual"
                        label="Unit Price"
                        align="right"
                    >
                        <template slot-scope="{ row }">
                            {{ row.priceActual | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="150"
                        sortable
                        prop="totalLines"
                        label="Taxable Amount"
                        align="right"
                    >
                        <template slot-scope="{ row }">
                            {{ row.totalLines | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="150"
                        sortable
                        prop="taxAmount"
                        label="PPN"
                        align="right"
                    >
                        <template slot-scope="{ row }">
                            {{ row.taxAmount | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="150"
                        prop="totalAmount"
                        label="Total Amount"
                        align="right"
                    >
                        <template slot-scope="{ row }">
                            {{ row.totalAmount | formatCurrency }}
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    ref="pagination"
                    background mini
                    layout="sizes, prev, pager, next"
                    :current-page.sync="page"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="itemsPerPage"
                    :total="queryCount"
                    @size-change="changePageSize"/>
            </el-col>
        </el-row>

        <el-dialog
            :width="modeFilterMatchPo.width"
            :visible.sync="dialogMatchPoVisible"
            :title="modeFilterMatchPo.titleModal"
            @opened="onMatchPoOpen"
        >

            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <match-po
                        ref="matchPo"
                        :mode-filter-match-po="modeFilterMatchPo"
                        @get-match-po="addAllLines"
                    />
                </el-col>
            </el-row>

            <div v-if="modeFilterMatchPo.mode === 1" slot="footer">
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-check"
                    type="primary"
                    @click="addSelectedLines"
                >
                    Add
                </el-button>
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-close"
                    @click="dialogMatchPoVisible = false"
                >
                    {{ $t('entity.action.cancel') }}
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./e-verification-update.component.ts">
</script>

<style lang="scss">
.compact .verification-update {
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

    .el-table .danger-row {
        background: oldlace;
    }
}

.button {
    margin-bottom: 5px;
}
</style>
