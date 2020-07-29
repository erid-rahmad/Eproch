<template>
    <div class="table-container">
        
        <el-table
            ref="tax"
            max-height="250"
            style="width: 100%"
            :data="tax"
        >
            <el-table-column
                prop="name"
                min-width="128"
                :label="$t('register.tax.taxRateName')"
            />
            <el-table-column
                prop="orderType"
                min-width="128"
                :label="$t('register.tax.transactionType')"
            />
            <el-table-column
                prop="rate"
                min-width="128"
                :label="$t('register.tax.rate')"
            />
            
            <el-table-column
                fixed="right"
                align="center"
                width="128"
                :label="$t('register.form.operation')">
                <template slot-scope="scope">
                    <el-button
                        @click="getTax(scope.row)"
                        type="primary"
                        icon="el-icon-check"
                        plain
                        :loading="loading"
                        :title="$t('entity.action.get')">
                        {{ $t('entity.action.get') }}
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script lang="ts" src="./tax-information-update.component.ts"></script>
