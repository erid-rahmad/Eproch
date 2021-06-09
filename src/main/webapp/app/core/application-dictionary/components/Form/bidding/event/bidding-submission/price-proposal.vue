<template>
    <div class="price-proposal">
        <h3 style="margin-top: 0">Price Proposal aa</h3>

        <el-form
            ref="mainForm"
            v-loading="loading"
            :model="mainForm"
            :rules="validationRulesHeader"
            label-position="left"
            label-width="200px"
            size="mini"
        >
            <el-row :gutter="24">
                <el-col
                    :lg="12"
                    :sm="18"
                    :xl="8"
                    :xs="24"
                >
                    <el-form-item label="Title">
                        <el-input
                            v-model="mainForm.biddingName"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Bidding No">
                        <el-input
                            v-model="mainForm.biddingNo"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Currency">
                        <el-input
                            v-model="mainForm.currencyName"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Proposed Price">
                        <el-input
                            v-model="mainForm.proposedPrice"
                            v-inputmask="{'alias': 'currency'}"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Remaining Time">
                        <span>{{ timeRemaining }}</span>
                    </el-form-item>
                </el-col>
                <el-col
                    :lg="12"
                    :sm="18"
                    :xl="8"
                    :xs="24"
                >
                    <el-form-item label="Bidding Type">
                        <el-input
                            v-model="mainForm.biddingTypeName"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Vendor Selection Method">
                        <el-select
                            v-model="mainForm.vendorSelection"
                            class="form-input"
                            disabled
                        >
                            <el-option
                                v-for="item in vendorSelectionOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Ceiling Price">
                        <el-input
                            v-model="mainForm.ceilingPrice"
                            v-inputmask="{'alias': 'currency'}"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Event Type">
                        <el-input
                            v-model="mainForm.eventTypeName"
                            disabled
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="Submision Deadline">
                        <el-date-picker
                            v-model="schedule.actualEndDate"
                            :format="dateDisplayFormat"
                            disabled
                            size="mini"
                            type="datetime"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col
                    :lg="12"
                    :sm="18"
                    :xl="8"
                    :xs="24"
                >
                    <el-form-item label="Biding Status">
                        <el-select
                            v-model="mainForm.biddingStatus"
                            disabled
                        >
                            <el-option
                                v-for="item in biddingStatuses"
                                :key="item.id"
                                :label="item.name"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-row :gutter="24">
                        <el-col :xs="24" :sm="24" :lg="18" :xl="16">
                            <el-form-item label="Description" prop="description">
                                <html-editor v-model="formData.description" size="mini"></html-editor>
                            </el-form-item>
                            <el-form-item style="margin-top: .5rem">
                                <el-button v-if="!mainForm.attachmentName" size="mini" type="primary" @click="attachmentFormVisible = true">
                                    <svg-icon name="icomoo/206-attachment"></svg-icon> Attachment
                                </el-button>
                                <el-button v-if="mainForm.attachmentName" icon="el-icon-view" size="mini" type="primary" @click="handlePreview">
                                    {{ mainForm.attachmentName }}
                                </el-button>
                                <el-button v-if="mainForm.attachmentName" icon="el-icon-close" size="mini" type="primary" @click="cancelAttachment"></el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item
                        v-if="!isVendor"
                        label="Vendor"
                    >
                        <el-input
                            v-model="mainForm.vendorName"
                            disabled
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <el-table
            ref="proposalPriceLines"
            v-loading="loadingLines"
            :data="mainForm.proposalPriceLines"
            :default-sort="gridSchema.defaultSort"
            :empty-text="gridSchema.emptyText"
            border
            class="proposal-price-table"
            highlight-current-row
            size="mini"
            stripe
            style="width: 100%"
        >
            <el-table-column
                fixed
                label="No"
                min-width="50"
            >
                <template slot-scope="{ $index }">
                    {{ $index + 1 }}
                </template>
            </el-table-column>

            <el-table-column label="Requirement">
                <el-table-column
                    label="Product"
                    min-width="200"
                    prop="productName"
                    show-overflow-tooltip
                ></el-table-column>

                <el-table-column
                    label="Sub Item"
                    width="110"
                >
                    <template slot-scope="{ row, $index }">
                        <el-badge :is-dot="!!row.subItem">
                            <el-button
                                :disabled="!row.subItem"
                                class="share-button"
                                size="mini"
                                title="Add sub item"
                                type="primary"
                                @click="editSubItem(row, $index)"
                            >
                                Sub-item
                            </el-button>
                        </el-badge>
                    </template>
                </el-table-column>

                <el-table-column
                    label="Quantity"
                    min-width="150"
                    prop="quantity"
                ></el-table-column>

                <el-table-column
                    label="UoM"
                    min-width="50"
                    prop="uomName"
                ></el-table-column>

                <el-table-column
                    align="right"
                    label="Ceiling Price/Unit"
                    min-width="150"
                >
                    <template slot-scope="{ row }">
                        {{ row.ceilingPrice | formatCurrency }}
                    </template>
                </el-table-column>

                <el-table-column
                    align="right"
                    label="Total Ceiling Price"
                    min-width="150"
                >
                    <template slot-scope="{ row }">
                        {{ row.totalCeilingPrice | formatCurrency }}
                    </template>
                </el-table-column>

                <el-table-column
                    label="Delivery Date"
                    min-width="150"
                >
                    <template slot-scope="{ row }">
                        {{ row.reqDeliveryDate | formatDate(true) }}
                    </template>
                </el-table-column>
            </el-table-column>

            <el-table-column label="Submission">
                <el-table-column
                    align="right"
                    label="Price Submission/Unit"
                    min-width="150"
                >
                    <template slot-scope="{ row, $index }">
                        <el-tooltip
                            v-if="!row.subItem"
                            :content="row.proposedPriceMessage"
                            :disabled="!row.proposedPriceError"
                        >
                            <el-input
                                v-model="row.proposedPrice"
                                v-inputmask="{ alias: 'currency' }"
                                :class="{ 'is-error': row.proposedPriceError }"
                                :disabled="disabled"
                                size="mini"
                                @change="value => onProposedPriceChange(row, $index, value)"
                            ></el-input>
                        </el-tooltip>
                        <span v-else>{{ row.proposedPrice | formatCurrency }}</span>
                    </template>
                </el-table-column>

                <el-table-column
                    align="right"
                    label="Total Price Submission"
                    min-width="150"
                >
                    <template slot-scope="{ row }">
                        {{ row.totalPriceSubmission | formatCurrency }}
                    </template>
                </el-table-column>

                <el-table-column
                    label="Delivery Date"
                    min-width="150"
                >
                    <template slot-scope="{ row, $index }">
                        <el-tooltip
                            :content="row.deliveryDateMessage"
                            :disabled="!row.deliveryDateError"
                        >
                            <el-date-picker
                                :ref="`deliveryDate${$index}`"
                                v-model="row.deliveryDate"
                                :class="{ 'is-error': row.deliveryDateError }"
                                :disabled="disabled"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                class="form-input"
                                clearable
                                placeholder="Pick a date"
                                size="mini"
                                style="width: 100%"
                                type="date"
                            ></el-date-picker>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table-column>

            <el-table-column
                label="Remark"
                min-width="200"
                prop="remark"
            ></el-table-column>
            <el-table-column
                width="100"
                label="Dockument"
                align="center"
                >
                <template scope="{row}">
                    <el-checkbox label="" name="type"></el-checkbox>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :show-close="false" :visible.sync="subItemEditorVisible" title="Edit Sub Item"
                   @closed="onSubItemClosed">
            <subitem-editor
                ref="subitemEditor"
                :item-detail="selectedItem"
                :item-index="selectedItemIndex"
                :read-only="disabled"
                @error="onSubItemError"
                @saved="onSubItemSaved"
            >
            </subitem-editor>
            <div slot="footer">
                <el-button :disabled="savingSubitem" icon="el-icon-close" size="mini" style="margin-left: 0px;"
                           @click="closeSubitemEditor">
                    {{ $t('entity.action.cancel') }}
                </el-button>
                <el-button
                    v-if="!disabled"
                    :loading="savingSubitem"
                    icon="el-icon-check"
                    size="mini"
                    style="margin-left: 0px;"
                    type="primary"
                    @click="saveSubitemEditor"
                >
                    {{ $t('entity.action.save') }}
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :show-close="false" title="Add Attachment" :visible.sync="attachmentFormVisible">
            <el-upload
                ref="docUpload"
                :accept="accept"
                :action="action"
                auto-upload
                :headers="uploadHeaders"
                :limit="limit"
                :before-upload="handleBeforeUpload"
                :on-error="onUploadError"
                :on-exceed="handleExceed"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="onUploadSuccess"
            >
                <el-button icon="el-icon-search" slot="trigger" type="primary">
                    Select File
                </el-button>
                <span class="el-upload__tip" style="margin-left: 10px" slot="tip">
          Files with a size less than 5Mb
        </span>
            </el-upload>

            <div slot="footer">
                <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;" @click="attachmentFormVisible = false">
                    {{ $t('entity.action.cancel') }}
                </el-button>
                <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary" @click="saveAttachment">
                    {{ $t('entity.action.save') }}
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./price-proposal.component.ts"></script>
