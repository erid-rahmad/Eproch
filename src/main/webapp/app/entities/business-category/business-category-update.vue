<template>
    <div class="app-container">
        <div class="page-heading">
            <h2 v-text="$t('opusWebApp.businessCategory.home.createOrEditLabel')">
                Create or edit a Business Category
            </h2>
        </div>
        <el-form
            ref="businessCategory"
            label-width="256px"
            :model="businessCategory"
            :rules="rules"
        >
            <el-form-item :label="$t('opusWebApp.businessCategory.name')" prop="name" required>
                <el-col :span="6">
                    <el-input v-model="businessCategory.name" />
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.businessCategory.description')" prop="description">
                <el-col :span="6">
                    <el-input v-model="businessCategory.description" />
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.businessCategory.parentCategory')" prop="parentCategory">
                <el-select
                    filterable
                    v-model="businessCategory.parentCategoryId"
                    placeholder="Select Parent Category"
                >
                    <el-option
                        v-for="businessCategory in businessCategories"
                        :key="businessCategory.id"
                        :label="businessCategory.name"
                        :value="businessCategory.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :loading="isSaving" @click.prevent="save()">{{ $t('entity.action.save') }}</el-button>
                <el-button @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script lang="ts" src="./business-category-update.component.ts">
</script>
