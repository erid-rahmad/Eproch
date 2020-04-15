<template>
    <div class="app-container">
        <div class="page-heading">
            <h2 v-text="$t('opusWebApp.currency.home.createOrEditLabel')">
                Create or edit a Currency
            </h2>
        </div>
        <el-form
            ref="currency"
            label-width="100px"
            :model="currency"
            :rules="rules"
        >
            <el-form-item :label="$t('opusWebApp.currency.code')" prop="code" required>
                <el-col :span="6">
                    <el-input v-model="currency.code" required/>
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.currency.symbol')" prop="symbol" required>
                <el-col :span="6">
                    <el-input v-model="currency.symbol" required />
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.currency.name')" prop="name" required>
                <el-col :span="6">
                    <el-input v-model="currency.name" required />
                </el-col>
            </el-form-item>
            <el-form-item>
                <el-button icon="el-icon-edit-outline" type="primary" :loading="isSaving" @click.prevent="save()">{{ $t('entity.action.save') }}</el-button>
                <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" src="./currency-update.component.ts">
</script>
