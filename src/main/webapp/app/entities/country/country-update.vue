<template>
    <div class="app-container">
        <div class="page-heading">
            <h2 v-text="textMap[status]">
                Create or edit a Country
            </h2>
        </div>
            <el-form
                ref="country"
                label-width="100px"
                :model="country"
                :rules="rules"
            >
                <el-form-item :label="$t('opusWebApp.country.name')" prop="name" required>
                    <el-col :span="6">
                        <el-input v-model="country.name" required />
                    </el-col>
                </el-form-item>
                <el-form-item :label="$t('opusWebApp.country.code')" prop="code" required>
                    <el-col :span="6">
                        <el-input v-model="country.code" required />
                    </el-col>
                </el-form-item>
                <el-form-item 
                    :label="$t('opusWebApp.country.currency')" 
                    prop="currency">
                    <el-select
                        filterable
                        v-model="country.currencyId"
                        placeholder="Select Parent Currency"
                    >
                        <el-option
                            v-for="currencyOption in currencies"
                            :key="currencyOption.id"
                            :label="currencyOption.name"
                            :value="currencyOption.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-edit-outline" type="primary" :loading="isSaving" @click.prevent="save()">{{ $t('entity.action.save') }}</el-button>
                    <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
                </el-form-item>
            </el-form>

        
    </div>
</template>

<script lang="ts" src="./country-update.component.ts">
</script>
