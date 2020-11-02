<template>
    <el-form
        ref="pay"
        label-position="right"
        size="mini"
        label-width="128px"
        :model="pay"
        :rules="rules"
    >
        <el-form-item
            :label="$t('register.payment.bank')"
            prop="bank"
            required>
            <el-select
                filterable
                style="width:100%"
                clearable
                v-model="pay.bank"
                :placeholder="$t('register.form.select')">
                <el-option
                    v-for="item in banks"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id + '_' + item.name"
                />
            </el-select>
        </el-form-item>
        <el-form-item
            :label="$t('register.payment.branch')"
            prop="branch"
            required>
            <el-input clearable v-model="pay.branch" />
        </el-form-item>

        <el-form-item
            :label="$t('register.payment.currency')"
            prop="currency"
            required>
            <el-select
                filterable
                style="width:100%"
                clearable
                v-model="pay.currency"
                :placeholder="$t('register.form.select')">
                <el-option
                    v-for="item in currencies"
                    :key="item.id"
                    :label="item.code"
                    :value="item.id + '_' + item.code"
                />
            </el-select>
        </el-form-item>

        <el-form-item 
            :label="$t('register.payment.account')" 
            prop="account" 
            required>
            <el-input clearable v-model="pay.account" />
        </el-form-item>
        <el-form-item 
            :label="$t('register.payment.supportingfile')"
            prop="supportingfile"
            required>
            
            <el-upload
                ref="upload"
                v-model="pay.supportingfile"
                :action="action"
                :accept="accept"
                :file-list="fileList"
                :limit="limit"
                :before-upload="handleBeforeUpload"
                :on-change="onUploadChange"
                :on-exceed="handleExceed"
                :on-remove="handleRemove"
                :on-error="onUploadError"
                :on-success="onUploadSuccess">
                <el-button slot="trigger" type="primary" icon="el-icon-search">select file</el-button>
                <span style="margin-left: 10px;" class="el-upload__tip" slot="tip">files with a size less than 500kb</span>
            </el-upload>

        </el-form-item>
        
    </el-form>
</template>

<script lang="ts" src="./payment-information-update.component.ts"></script>
