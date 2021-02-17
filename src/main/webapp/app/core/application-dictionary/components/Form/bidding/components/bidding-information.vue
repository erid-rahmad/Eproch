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
                        <el-input class="form-input" clearable v-model="biddingInformation.ceilingPrice" disabled />
                    </el-form-item>
                    <el-form-item label="Estimated Price" prop="estimatedPrice" required>
                        <el-input class="form-input" clearable v-model="biddingInformation.estimatedPrice" disabled />
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
                        label="Ceiling Price/Unit"/>

                    <el-table-column
                        min-width="80"
                        prop="requisitionAmount"
                        label="Total Ceiling Price"/>

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
            <el-col :span="12">

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
                        min-width="30"
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
                        min-width="50"
                        prop="attachment.fileName"
                        label="Attachment"/>

                    <el-table-column align="center" min-width="50">
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
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            title="Add Project">

            <template>
                <div>
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

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="saveProject">
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

<script lang="ts" src="./bidding-information.component.ts"></script>
