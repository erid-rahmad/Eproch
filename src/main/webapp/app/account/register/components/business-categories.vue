<template>
    <div>
        <el-divider content-position="left"><h4>{{ $t('register.business.title') }}</h4></el-divider>
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

    </div>
</template>
<script lang="ts" src="./business-categories.component.ts"></script>
