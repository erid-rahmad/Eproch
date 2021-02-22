<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Vendor Business Category</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="vendorInvitation"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="vendorInvitation.vendorBusinessCategory">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="businessClassificationName"
                        label="Business Classification"/>

                    <el-table-column
                        min-width="100"
                        prop="businessCategoryName"
                        label="Category"/>

                    <el-table-column
                        min-width="100"
                        prop="businessSubCategoryName"
                        label="Sub Category"/>

                    <el-table-column align="center" min-width="50">
                        <template slot="header">
                            <el-button
                                size="mini"
                                icon="el-icon-plus"
                                type="primary"
                                @click="addBusinessCategory"/>
                        </template>
                        <template slot-scope="row">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                type="danger"
                                @click="removeBusinessCategory(row.$index)"/>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-divider content-position="left"><h4>Vendor Suggestion</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="vendorInvitation"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    :height="gridSchema.height"
                    :max-height="gridSchema.maxHeight"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="vendorInvitation.vendorSuggestion">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        label="Vendor">
                        <template slot-scope="{ row }">
                            {{ row.vendorObj.vendorName }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        label="Sub Category">
                        <template slot-scope="{ row }">
                            {{ row.subCategoryObj.name }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="address"
                        label="Address"/>

                    <el-table-column align="center" min-width="50">
                        <template slot="header">
                            <el-button
                                size="mini"
                                icon="el-icon-plus"
                                type="primary"
                                :disabled="buttonDisable"
                                @click="addVendorSuggestion"/>
                        </template>
                        <template slot-scope="row">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                type="danger"
                                :disabled="buttonDisable"
                                @click="removeVendorSuggestion(row.$index)"/>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>

        <el-dialog
            width="85%"
            :visible.sync="dialogConfirmationVisibleBusinessCategory"
            title="Add Vendor Business Category">

            <template>
                <div>
                    <el-row :gutter="24">
                        <el-col :span="24">
                            <el-form
                                ref="businessCategoryForm"
                                :model="businessCategory"
                                :rules="rules">

                                <el-form-item
                                    prop="values">
                                    <el-cascader-panel
                                        ref="businessCategories"
                                        v-model="businessCategory.values"
                                        :value="businessCategory.values"
                                        :options="businessCategoryOptions"
                                        :props="{multiple: true}"
                                    />
                                </el-form-item>
                            </el-form>
                        </el-col>
                    </el-row>
                </div>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="saveBusinessCategory">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisibleBusinessCategory = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisibleVendorSuggestion"
            title="Add Vendor Suggestion">

            <template>
                <div>
                    <el-form ref="vendorSuggestion" label-position="left" label-width="150px" size="mini" :model="vendorSuggestion">
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="SubCategory" prop="subCategory" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="vendorSuggestion.subCategory"
                                        class="form-input"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')"
                                        @clear="clearSubCategory"
                                        @change="getVendor($event)">
                                        <el-option
                                            v-for="item in subCategoryOptions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Vendor" prop="vendor" required>
                                    <el-select
                                        style="width: 100%"
                                        v-model="vendorSuggestion.vendor"
                                        class="form-input"
                                        clearable filterable
                                        :placeholder="$t('register.form.select')"
                                        @clear="clearVendor"
                                        @change="getVendorDetail($event)">
                                        <el-option
                                            v-for="item in vendorOptions"
                                            :key="item.vendorId"
                                            :label="item.vendorName"
                                            :value="item.vendorId" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="24">
                            <el-col :span="24">
                                <el-form-item label="Address" prop="address" required>
                                    <el-input class="form-input" clearable v-model="vendorSuggestion.address" disabled />
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
                        @click="saveVendorSuggestion">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisibleVendorSuggestion = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./vendor-invitation.component.ts"></script>
