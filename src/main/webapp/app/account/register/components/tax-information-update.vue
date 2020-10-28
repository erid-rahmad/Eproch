<template>
  <el-table
    v-loading="loading"
    :data="taxes"
    max-height="250"
    stripe
    style="width: 100%"
    @selection-change="onSelectionChanged"
  >
    <el-table-column
      type="selection"
      width="55"
    />
    <el-table-column
      prop="name"
      min-width="128"
      :label="$t('register.tax.taxCategoryName')"
    />
    <el-table-column
      prop="description"
      min-width="128"
      :label="$t('register.tax.description')"
    />
    <el-table-column
      min-width="128"
      :label="$t('register.tax.isWithholding')"
    >
        <template slot-scope="props">
            <el-checkbox
                v-model="props.row.isWithholding"
                disabled
            />
        </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" src="./tax-information-update.component.ts"></script>
