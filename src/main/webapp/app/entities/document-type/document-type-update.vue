<template>
    <div class="app-container">
        <div class="page-heading">
            <h2 v-text="$t('opusWebApp.documentType.home.createOrEditLabel')">
                Create or edit a DocumentType
            </h2>
        </div>
        <el-form
            ref="documentType"
            label-width="256px"
            :model="documentType"
            :rules="rules"
        >
            <el-form-item label="Document Type Name" prop="name" required>
                <el-col :span="6">
                    <el-input v-model="documentType.name" />
                </el-col>
            </el-form-item>
            <el-form-item label="Description" prop="description">
                <el-col :span="6">
                    <el-input v-model="documentType.description" />
                </el-col>
            </el-form-item>
            <el-form-item label="Has Expiration Date" prop="hasExpirationDate">
                <el-radio-group v-model="documentType.hasExpirationDate">
                    <el-radio :label="true">Yes</el-radio>
                    <el-radio :label="false">No</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="Mandatory for Sub-business Category" prop="mandatoryBusinessCategories">
                <el-radio-group v-model="documentType.mandatoryBusinessCategories">
                    <el-radio label="A">All</el-radio>
                    <el-radio label="S">Selected Only</el-radio>
                    <el-radio label="N">None</el-radio>
                </el-radio-group>
                <el-cascader-panel
                    ref="mandatoryBusinessCategories"
                    :class="{'hide': shouldHide(documentType.mandatoryBusinessCategories)}"
                    :v-model="documentType.mandatoryCategorySelections"
                    :props="businessCategoriesProps"
                />
            </el-form-item>
            <el-form-item label="Mandatory for Supplier Type" prop="vendorTypes">
                <el-checkbox-group v-model="documentType.vendorTypes" @change="handleMandatoryVendorType">
                    <el-checkbox label="C">Company</el-checkbox>
                    <el-checkbox label="P">Professional</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="Additional for Sub-business Category" prop="additionalBusinessCategories">
                <el-radio-group v-model="documentType.additionalBusinessCategories">
                    <el-radio label="A">All</el-radio>
                    <el-radio label="S">Selected Only</el-radio>
                    <el-radio label="N">None</el-radio>
                </el-radio-group>
                <el-cascader-panel
                    ref="additionalBusinessCategories"
                    :class="{'hide': shouldHide(documentType.additionalBusinessCategories)}"
                    :v-model="documentType.additionalCategorySelections"
                    :props="businessCategoriesProps"
                />
            </el-form-item>
            <el-form-item label="Active" prop="active">
                <el-radio-group v-model="documentType.active">
                    <el-radio :label="true">Yes</el-radio>
                    <el-radio :label="false">No</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :loading="isSaving" @click.prevent="save()">{{ $t('entity.action.save') }}</el-button>
                <el-button @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script lang="ts" src="./document-type-update.component.ts">
</script>

<style lang="scss" scoped>
    .el-cascader-panel.hide {
        display: none;
    }
</style>
