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
      :label="$t('register.tax.taxRateName')"
    />
    <el-table-column
      prop="transactionType"
      min-width="128"
      :label="$t('register.tax.transactionType')"
    />
    <el-table-column
      prop="rate"
      min-width="128"
      :label="$t('register.tax.rate')"
    />
  </el-table>
</template>

<script lang="ts" src="./tax-information-update.component.ts"></script>
