<template>
    <el-form
        ref="businessCategoryForm"
        label-position="left"
        label-width="128px"
        :model="businessCategory"
        :rules="rules"
    >
        <el-form-item
            label="Business Categories"
            prop="values"
        >
            <el-cascader-panel
                ref="businessCategories"
                v-model="businessCategory.values"
                :value="businessCategory.values"
                :options="businessCategoryOptions"
                :props="{multiple: true}"
            />
        </el-form-item>
  </el-form>
</template>
<script lang="ts" src="./business-categories.component.ts"></script>
