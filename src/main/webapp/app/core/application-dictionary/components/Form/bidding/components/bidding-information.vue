<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Bidding Information</h4></el-divider>

        <el-form ref="biddingInformation" label-position="left" label-width="150px" size="mini" :model="biddingInformation" :rules="rules">

            <el-row :gutter="24">
                <el-col :span="12">
                    <el-form-item label="Title" prop="name" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.name" />
                    </el-form-item>
                    <el-form-item label="Bidding No" prop="biddingNo" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.biddingNo" />
                    </el-form-item>

                    <el-form-item label="Reference No" prop="referenceNo" required>

                        <el-input
                            clearable
                            v-model="biddingInformation.referenceNo">

                            <el-button
                                @click="searchReferenceNo"
                                v-loading.fullscreen.lock="fullscreenLoading"
                                slot="append"
                                icon="el-icon-search">
                                Search
                            </el-button>
                        </el-input>
                    </el-form-item>

                    <el-form-item label="Reference Type" prop="referenceTypeName" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.referenceTypeName" disabled />
                    </el-form-item>
                    <el-form-item label="Department" prop="costCenterId" required>
                        <el-select
                            style="width: 100%"
                            v-model="biddingInformation.costCenterId"
                            class="form-input"
                            clearable filterable
                            :placeholder="$t('register.form.select')">
                            <el-option
                                v-for="item in costCenterOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Currency" prop="currencyName" required >
                        <el-input class="form-input" clearable v-model="biddingInformation.currencyName" disabled />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Bidding Type" prop="biddingTypeId" required>
                        <el-select
                            style="width: 100%"
                            v-model="biddingInformation.biddingTypeId"
                            class="form-input"
                            clearable filterable
                            :placeholder="$t('register.form.select')">
                            <el-option
                                v-for="item in biddingTypeOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Vendor Selection" prop="vendorSelection" required>
                        <el-select
                            style="width: 100%"
                            v-model="biddingInformation.vendorSelection"
                            class="form-input"
                            clearable filterable
                            :placeholder="$t('register.form.select')">
                            <el-option
                                v-for="item in vendorSelectionOptions"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Ceiling Price" prop="ceilingPrice" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.ceilingPrice" v-inputmask="{'alias': 'currency'}" disabled />
                    </el-form-item>
                    <el-form-item label="Estimated Price" prop="estimatedPrice" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.estimatedPrice" v-inputmask="{'alias': 'currency'}" disabled />
                    </el-form-item>
                    <el-form-item label="PIC" prop="pic" required>
                        <el-select
                            style="width: 100%"
                            v-model="biddingInformation.pic"
                            class="form-input"
                            clearable filterable
                            :placeholder="$t('register.form.select')">
                            <el-option
                                v-for="item in picBiddingOptions"
                                :key="item.userId"
                                :label="item.userLogin"
                                :value="item.userId" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Event Type" prop="eventTypeId" required>
                        <el-select
                            style="width: 100%"
                            v-model="biddingInformation.eventTypeId"
                            class="form-input"
                            clearable filterable
                            :placeholder="$t('register.form.select')">
                            <el-option
                                v-for="item in eventTypeOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

        </el-form>

        <el-divider content-position="left"><h4>Requirement</h4></el-divider>
        <el-row>
            <el-col :span="24">

                <el-table
                    v-loading="processing"
                    ref="biddingInformationLine"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="biddingInformation.biddingInformationLine">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="productName"
                        label="Product"/>

                    <el-table-column
                        width="110"
                        prop="subItem"
                        label="Sub Item"
                    >
                        <template slot-scope="scope">
                            <el-link
                                type="primary"
                                size="mini"
                                plain
                                title="Add sub item"
                                :underline="false"
                                @click="displayAddSubItem(scope.row, scope.$index)"
                            >Add sub item</el-link>
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="50"
                        prop="quantity"
                        label="Qty"/>

                    <el-table-column
                        min-width="50"
                        prop="uomName"
                        label="UoM"/>

                    <el-table-column
                        min-width="80"
                        prop="unitPrice"
                        align="right"
                        label="Ceiling Price/Unit">
                        <template slot-scope="{ row }">
                            {{ row.unitPrice | formatCurrency }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="80"
                        prop="requisitionAmount"
                        align="right"
                        label="Total Ceiling Price">
                        <template slot-scope="{ row }">
                            {{ row.requisitionAmount | formatCurrency }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="70"
                        prop="deliveryDate"
                        label="Delivery Date">
                        <template slot-scope="{ row }">
                            <el-date-picker
                                style="width: 100%"
                                class="form-input"
                                size="mini"
                                clearable
                                v-model="row.deliveryDate"
                                format="dd/MM/yyyy"
                                value-format="yyyy-MM-dd"
                                type="date"
                                placeholder="Pick a day"
                            />
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="remark"
                        label="Remark"/>

                </el-table>

            </el-col>
        </el-row>


        <el-divider content-position="left"><h4>Project Information</h4></el-divider>
        <el-row>
            <el-col :span="18">

                <el-table
                    v-loading="processing"
                    ref="projectInformation"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="biddingInformation.projectInformation">

                    <el-table-column
                        min-width="20"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="information"
                        label="Information"/>

                    <el-table-column
                        min-width="80"
                        label="Attachment">
                        <template slot-scope="{ row }">
                            <el-button
                                class="btn-attachment"
                                icon="el-icon-download"
                                size="mini"
                                type="primary"
                                @click="downloadAttachment(row)">
                                    {{ row.attachment.fileName }}
                            </el-button>
                        </template>
                    </el-table-column>

                    <el-table-column align="center" min-width="20">
                        <template slot="header">
                            <el-button
                                size="mini"
                                icon="el-icon-plus"
                                type="primary"
                                @click="addProject"/>
                        </template>
                        <template slot-scope="row">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                type="danger"
                                @click="removeProject(row.$index)"/>
                        </template>
                    </el-table-column>

                </el-table>

            </el-col>
        </el-row>

        <el-dialog
            :width="dialogWidth"
            :close-on-click-modal="dialogCloseOnClick"
            :close-on-press-escape="dialogCloseOnClick"
            :show-close="dialogCloseOnClick"
            :visible.sync="dialogConfirmationVisible"
            :title="dialogTitle">

            <template>
                <div v-if="dialogContent == 1">
                    <el-form ref="projectInformation" label-position="left" label-width="150px" size="mini" :model="projectInformation">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Information" prop="information" required>
                                    <el-input class="form-input" clearable v-model="projectInformation.information" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Attachment" prop="attachment" required>
                                    <el-upload
                                        ref="upload"
                                        v-model="projectInformation.attachment"
                                        :action="action"
                                        :accept="accept"
                                        :limit="limit"
                                        :auto-upload="true"

                                        :before-upload="handleBeforeUpload"
                                        :on-preview="handlePreview"
                                        :on-exceed="handleExceed"
                                        :on-remove="handleRemove"
                                        :on-error="onUploadError"
                                        :on-success="onUploadSuccess">

                                        <el-button slot="trigger" type="primary" icon="el-icon-search">select file</el-button>
                                        <span style="margin-left: 10px;" class="el-upload__tip" slot="tip">files with a size less than 5Mb</span>
                                    </el-upload>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>

                <div v-else>

                    <div v-if="dialogContentSubItem == 1" class="app-container">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-table
                                    style="width: 100%; height: 100%"
                                    size="mini"
                                    :height="gridSchema.height"
                                    :max-height="gridSchema.maxHeight"
                                    :default-sort="gridSchema.defaultSort"
                                    :empty-text="gridSchema.emptyText"
                                    :data="gridDataProductSubItem">
                                    <el-table-column label="No" min-width="50">
                                        <template slot-scope="row">
                                            {{ row.$index+1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="biddingLineProductName" label="Product" min-width="200"/>
                                    <el-table-column label="Sub Item" min-width="200">
                                        <template slot-scope="{ row }">
                                            {{ row.productObj.name }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="totalAmount" align="right" label="Total Amount" min-width="100">
                                        <template slot-scope="{ row }">
                                            {{ row.totalAmount | formatCurrency }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column align="center" min-width="80">
                                        <template slot="header">
                                            <el-button
                                                size="mini"
                                                icon="el-icon-plus"
                                                type="primary"
                                                @click="addSubItem"/>
                                        </template>
                                        <template slot-scope="row">
                                            <el-button
                                                size="mini"
                                                icon="el-icon-edit"
                                                type="primary"
                                                @click="editSubItem(row.row)"/>
                                            <el-button
                                                size="mini"
                                                icon="el-icon-delete"
                                                type="danger"
                                                @click="removeSubItem(row.$index)"/>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                    </div>

                    <div v-if="dialogContentSubItem == 2" class="app-container">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form ref="formAddSubItem" :model="formAddSubItem" size="mini" label-width="80px">
                                    <el-form-item label="Product" >
                                        <el-input class="form-input" v-model="formAddSubItem.biddingLineProductName" clearable disabled />
                                    </el-form-item>
                                    <el-form-item label="Sub Item" prop="productId" required>
                                        <el-select
                                            style="width: 100%"
                                            v-model="formAddSubItem.productId"
                                            class="form-input"
                                            clearable filterable
                                            remote
                                            :remote-method="remoteMethod"
                                            :loading="loading"
                                            :placeholder="$t('register.form.select')">
                                            <el-option
                                                v-for="item in productOptions"
                                                :key="item.id"
                                                :label="item.name"
                                                :value="item.id" />
                                        </el-select>
                                    </el-form-item>

                                </el-form>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-table
                                    style="width: 100%; height: 100%"
                                    size="mini"
                                    :height="gridSchema.height"
                                    :max-height="gridSchema.maxHeight"
                                    :default-sort="gridSchema.defaultSort"
                                    :empty-text="gridSchema.emptyText"
                                    :data="gridDataProductSubSubItem">
                                    <el-table-column label="No" min-width="50">
                                        <template slot-scope="row">
                                            {{ row.$index + 1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="productId" label="Sub Item" min-width="200">
                                        <template slot-scope="{ row }">
                                            {{ row.productObj.name }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="quantity" label="Qty" min-width="70"/>
                                    <el-table-column prop="uomId" label="Uom" min-width="100">
                                        <template slot-scope="{ row }">
                                            {{ row.uomObj.name }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="price" label="Price" align="right" min-width="100">
                                        <template slot-scope="{ row }">
                                            {{ row.price | formatCurrency }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="amount" label="Amount" align="right" min-width="100">
                                        <template slot-scope="{ row }">
                                            {{ row.amount | formatCurrency }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column align="center" min-width="60">
                                        <template slot="header">
                                            <el-button
                                                size="mini"
                                                icon="el-icon-plus"
                                                type="primary"
                                                @click="addSubSubItem"/>
                                        </template>
                                        <template slot-scope="row">
                                            <el-button
                                                size="mini"
                                                icon="el-icon-delete"
                                                type="danger"
                                                @click="removeSubSubItem(row.$index)"/>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                    </div>

                </div>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        v-if="dialogContent == 1"
                        @click="saveProject">
                            Save
                    </el-button>

                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        v-if="(dialogContentSubItem == 1) || (dialogContentSubItem == 2)"
                        @click="saveSubItemProduct">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-arrow-left"
                        v-if="dialogContentSubItem == 2"
                        @click="backSubItem">
                            Back
                    </el-button>

                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        v-if="dialogContentSubItem != 2"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog
            width="50%"
            title="Add Sub sub Item"
            :close-on-click-modal="dialogCloseOnClick"
            :close-on-press-escape="dialogCloseOnClick"
            :show-close="dialogCloseOnClick"
            :visible.sync="dialogConfirmationVisibleFormSubSubItem">
            <template>

                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form ref="formAddSubSubItem" :model="formAddSubSubItem" size="mini" label-width="110px">

                            <el-form-item label="Sub Sub Item" prop="productId" required>
                                <el-select
                                    style="width: 100%"
                                    v-model="formAddSubSubItem.productId"
                                    class="form-input"
                                    clearable filterable
                                    remote
                                    :remote-method="remoteMethod"
                                    :loading="loading"
                                    :placeholder="$t('register.form.select')">
                                    <el-option
                                        v-for="item in productOptions"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Quantity" prop="quantity" required>
                                <el-input-number v-model="formAddSubSubItem.quantity" :min="1" clearable />
                            </el-form-item>
                            <el-form-item label="UoM" prop="uomId" required>
                                <el-select
                                    style="width: 100%"
                                    v-model="formAddSubSubItem.uomId"
                                    class="form-input"
                                    clearable filterable
                                    :placeholder="$t('register.form.select')">
                                    <el-option
                                        v-for="item in uomOptions"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Price" prop="price" required>
                                <el-input class="form-input" v-model="formAddSubSubItem.price" v-inputmask="{'alias': 'currency'}" clearable />
                            </el-form-item>

                        </el-form>
                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="saveSubSubItem">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="closeSubSubItem">
                            Close
                    </el-button>
                </div>

            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./bidding-information.component.ts"></script>
