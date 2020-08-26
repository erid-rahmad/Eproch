<template>
    <el-form
        ref="document"
        label-position="right"
        label-width="128px"
        size="mini"
        :model="document"
        :rules="rules">
        <el-form-item 
            :label="$t('register.document.form.documentType')" 
            prop="typeId" 
            required>
            <el-select
                filterable
                :disabled="mandatory === true" 
                style="width:100%"
                v-model="document.typeId"
                :placeholder="$t('register.form.select')"
                @change="handleDocumentTypeChange">
                <el-option
                    v-for="item in documentTypes"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>

        <el-form-item 
            :label="$t('register.document.form.documentNo')" 
            prop="documentNo" 
            required>
            <el-input v-model="document.documentNo" />
        </el-form-item>

        <el-form-item 
            :label="$t('register.document.form.expirationDate')"
            prop="expirationDate" 
            :required="hasExpirationDate">
            <el-date-picker
                ref="documentExpirationDate"
                v-model="document.expirationDate"
                type="date"
                format="dd/MM/yyyy"
                value-format="yyyy-MM-dd"
                :placeholder="$t('register.form.datePicker')"/>
        </el-form-item>

        <el-form-item 
            :label="$t('register.document.form.file')"
            prop="file"
            >
            <el-upload
                ref="upload"
                v-model="document.file"
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
                <el-button slot="trigger" type="primary" icon="el-icon-search">Select file</el-button>
                <span style="margin-left: 10px;" class="el-upload__tip" slot="tip">files with a size less than 5Mb</span>
            </el-upload>

        </el-form-item>
    </el-form>
</template>

<script lang="ts" src="./supporting-documents-update.component.ts"></script>
