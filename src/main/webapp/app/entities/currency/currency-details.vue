<template>

    <div class="app-container">
        <div class="page-heading">
            <h2>
                <span v-text="$t('opusWebApp.currency.detail.title')">Currency</span> {{currency.id}}
            </h2>
        </div>

        <el-form
            ref="currency"
            label-width="100px"
            :model="currency"
        >

            <el-form-item :label="$t('opusWebApp.currency.code')" prop="code" required>
                <el-col :span="6">
                    {{currency.code}}
                </el-col>
            </el-form-item>

            <el-form-item :label="$t('opusWebApp.currency.symbol')" prop="symbol" required>
                <el-col :span="6">
                    {{currency.symbol}}
                </el-col>
            </el-form-item>

            <el-form-item :label="$t('opusWebApp.currency.name')" prop="name" required>
                <el-col :span="6">
                    {{currency.name}}
                </el-col>
            </el-form-item>

            <el-form-item>
                
                <!--<router-link v-if="currency.id" :to="{name: 'CurrencyUpdate', params: {currencyId: currency.id}}">
                    <el-button type="primary">{{ $t('entity.action.edit') }}</el-button>
                </router-link>-->
                <el-button icon="el-icon-edit" @click="edit(currency)" type="primary">{{ $t('entity.action.edit') }}</el-button>
                <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
                
            </el-form-item>
        </el-form>
    </div>

</template>

<script lang="ts" src="./currency-details.component.ts">
</script>
