<template>
    <div class="app-container">
        <el-divider content-position="left">
            <h4>More Information</h4>
        </el-divider>
            <el-form ref="biddingInformation" label-position="left" label-width="150px" size="mini">
            <el-row :gutter="24">
                <el-col :span="6">
                    <el-form-item label="Title" prop="title" required>
                        <span>{{moreInformation.name}}</span>
                    </el-form-item>
                    <el-form-item label="Bidding No" prop="documentNo" required>
                        <span>{{moreInformation.documentNo}}</span>
                    </el-form-item>
                    <el-form-item label="Currency" prop="vurrency" required>
                        <span>IDR</span>
                    </el-form-item>
                    <el-form-item label="Proposal Price" prop="">
                        <span>{{ moreInformation.totalpricesubmision | formatCurrency }}</span>
                    </el-form-item>
                    <el-form-item label="Remaining Time" prop="RemainingTime" required>
                        <span>3 days 8 hour 7 minut</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="Biding Type" prop="bidingType" required>
                        <span>{{moreInformation.biddingTypeName}}</span>
                    </el-form-item>
                    <el-form-item label="Vendor method" prop="vendorSelectionMethod" required>
                        <span>{{moreInformation.biddingTypeName}}</span>
                    </el-form-item>
                    <el-form-item label="Ceiling Price" prop="ceilingPrice" required>
                        <span>{{moreInformation.ceilingPrice | formatCurrency}}</span> 
                    </el-form-item>
                    <el-form-item label="Even Type" prop="evenType" required>
                        <span>{{moreInformation.eventTypeName}}</span>
                    </el-form-item>
                    <el-form-item label="Submision Deadline" prop="SubmisionDeadline" required>
                        <span>12/04/2021</span>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="Biding Status" prop="bidingStatus" required>
                        <span>{{moreInformation.documentStatus}}</span>
                    </el-form-item>
                    <el-form-item label="Vendor Selection" prop="vendorSelection" required>
                        <span>{{moreInformation.vendorSelection}}</span>
                    </el-form-item>
                    <el-form-item label="Join Bidding" prop="joinBidding">
                        <el-switch v-model="value1" active-text="Yes" inactive-text="No">
                        </el-switch>
                    </el-form-item>
                    <el-form-item label="PIC" prop="pic" required>
                        <span>{{moreInformation.adUserUserName}}</span>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-divider content-position="left">
            <h4>Requirement</h4>
        </el-divider>
        <el-row>
           
                <el-table ref="biddingInformationLine" highlight-current-row border stripe size="mini" style="width: 100%; height: 100%"
                    :data="moreInformation.biddingLineList">
                    <el-table-column min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="100" prop="productName" label="Product" />
                    <el-table-column min-width="60" sortable label="Product">
                        <template slot-scope="{ row, $index }">
                            <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="viewSubItem(row,$index)">
                                View
                            </el-button>
                        </template>
                    </el-table-column>
                    <el-table-column min-width="50" prop="quantity" label="Qty" />
                    <el-table-column min-width="50" prop="uomName" label="UoM" />
                    <el-table-column min-width="80"  align="right" label="Ceiling Price/Unit">
                    <template slot-scope="{ row }">
                        {{row.ceilingPrice | formatCurrency}}
                    </template>
               
                    </el-table-column>
                    <el-table-column min-width="80"  align="right" label="Total Ceiling Price">
                        <template slot-scope="{ row }">
                        {{row.totalCeilingPrice | formatCurrency}}
                    </template>
                    </el-table-column>
                    <el-table-column min-width="70" prop="deliveryDate" label="Delivery Date">
                    </el-table-column>
                    <el-table-column min-width="110" prop="" label="Price submision/unit">
                        <template slot-scope="{ row, $index }">
                            <el-input-number  v-inputmask="{'alias': 'currency'}" v-model="row.ceilingPrice1" :step="50000" clearable controls-position="right" 
                                @change="value => onQuantityOrderedChanged(row, $index, value)"></el-input-number>
                        </template>
                    </el-table-column>
                    <el-table-column min-width="70" prop="" label="Total Price Submission">
                        <template slot-scope="{ row }">
                            {{ row.totalpricesubmision | formatCurrency }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="50" prop="remark" label="Remark" />
                </el-table>           
        </el-row>
        <el-dialog title="Sub Item" :visible.sync="dialogTableVisible11" width="80%">
            <span>Produck PC</span>
            <el-table :data="SubItem.mbiddingSubItemLines">
                <el-table-column min-width="120" label="No">
                    <template slot-scope="row">
                        {{ row.$index+1 }}
                    </template>
                </el-table-column>
                <el-table-column width="150" property="biddingSubItem.product.name" label="Sub Item"></el-table-column>
                <el-table-column width="100" property="product.name" label="Sub Sub Item"></el-table-column>
                <el-table-column width="300" property="quantity" label="Qty"></el-table-column>
                <el-table-column width="300" property="uom.name" label="Uom"></el-table-column>
                <el-table-column width="300" property="price" label="Proposed Price"></el-table-column>
            </el-table>
        </el-dialog> 

        <el-dialog :width="dialogWidth" :close-on-click-modal="dialogCloseOnClick" :close-on-press-escape="dialogCloseOnClick" :show-close="dialogCloseOnClick"
            :visible.sync="dialogConfirmationVisible" :title="dialogTitle">

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
                                    <el-upload ref="upload" v-model="projectInformation.attachment" :action="action" :accept="accept" :limit="limit" :auto-upload="true"
                                        :before-upload="handleBeforeUpload" :on-preview="handlePreview" :on-exceed="handleExceed" :on-remove="handleRemove"
                                        :on-error="onUploadError" :on-success="onUploadSuccess">

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
                                <el-table style="width: 100%; height: 100%" size="mini" :height="gridSchema.height" :max-height="gridSchema.maxHeight"
                                    :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText" :data="gridDataProductSubItem">
                                    <el-table-column label="No" min-width="50">
                                        <template slot-scope="row">
                                            {{ row.$index+1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="biddingLineProductName" label="Product" min-width="200" />
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
                                            <el-button size="mini" icon="el-icon-plus" type="primary" />
                                        </template>
                                        <template slot-scope="row">
                                            <el-button size="mini" icon="el-icon-edit" type="primary" />
                                            <el-button size="mini" icon="el-icon-delete" type="danger" />
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
                                    <el-form-item label="Product">
                                        <el-input class="form-input" v-model="formAddSubItem.biddingLineProductName" clearable disabled />
                                    </el-form-item>
                                    <el-form-item label="Sub Item" prop="productId" required>
                                        <el-select style="width: 100%" v-model="formAddSubItem.productId" class="form-input" clearable filterable remote
                                            :remote-method="remoteMethod" :placeholder="$t('register.form.select')">
                                            <el-option v-for="item in productOptions" :key="item.id" :label="item.name" :value="item.id" />
                                        </el-select>
                                    </el-form-item>

                                </el-form>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-table style="width: 100%; height: 100%" size="mini" :height="gridSchema.height" :max-height="gridSchema.maxHeight"
                                    :default-sort="gridSchema.defaultSort" :empty-text="gridSchema.emptyText" :data="gridDataProductSubSubItem">
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
                                    <el-table-column prop="quantity" label="Qty" min-width="70" />
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
                                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="addSubSubItem" />
                                        </template>
                                        <template slot-scope="row">
                                            <el-button size="mini"  icon="el-icon-delete" type="danger" @click="removeSubSubItem(row.$index)" />
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                    </div>

                </div>

                <div slot="footer">
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" v-if="dialogContent == 1" @click="saveProject">
                        Save
                    </el-button>

                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary" v-if="(dialogContentSubItem == 1) || (dialogContentSubItem == 2)"
                        @click="saveSubItemProduct">
                        Save
                    </el-button>
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-arrow-left" v-if="dialogContentSubItem == 2" @click="backSubItem">
                        Back
                    </el-button>

                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-close" v-if="dialogContentSubItem != 2" @click="dialogConfirmationVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog width="50%" title="Add Sub sub Item" :close-on-click-modal="dialogCloseOnClick" :close-on-press-escape="dialogCloseOnClick" :show-close="dialogCloseOnClick"
            :visible.sync="dialogConfirmationVisibleFormSubSubItem">
            <template>

                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form ref="formAddSubSubItem" :model="formAddSubSubItem" size="mini" label-width="110px">

                            <el-form-item label="Sub Sub Item" prop="productId" required>
                                <el-select style="width: 100%" v-model="formAddSubSubItem.productId" class="form-input" clearable filterable remote :remote-method="remoteMethod"
                                    :placeholder="$t('register.form.select')">
                                    <el-option v-for="item in productOptions" :key="item.id" :label="item.name" :value="item.id" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Quantity" prop="quantity" required>
                                <el-input-number v-model="formAddSubSubItem.quantity" :min="1" clearable />
                            </el-form-item>
                            <el-form-item label="UoM" prop="uomId" required>
                                <el-select style="width: 100%" v-model="formAddSubSubItem.uomId" class="form-input" clearable filterable :placeholder="$t('register.form.select')">
                                    <el-option v-for="item in uomOptions" :key="item.id" :label="item.name" :value="item.id" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="Price" prop="price" required>
                                <el-input class="form-input" v-model="formAddSubSubItem.price" v-inputmask="{'alias': 'currency'}" clearable />
                            </el-form-item>

                        </el-form>
                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-check" type="primary">
                        Save
                    </el-button>
                    <el-button style="margin-left: 0px;" size="mini" icon="el-icon-close">
                        Close
                    </el-button>
                </div>

            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./price-proposal.component.ts"></script>
