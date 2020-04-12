<template>
    <el-form
        ref="document"
        label-position="left"
        label-width="128px"
        :model="document"
        :rules="rules"
    >
        <el-form-item label="Document Type" prop="docType" required>
            <el-select
                filterable
                v-model="document.docType"
                placeholder="Select Document Type"
                @change="handleDocumentTypeChange"
            >
                <el-option
                    v-for="item in documentTypes"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="Document No." prop="docNo" required>
            <el-input v-model="document.docNo" />
        </el-form-item>
        <el-form-item label="Expiration Date" prop="expirationDate" :required="hasExpirationDate">
            <el-date-picker
                ref="documentExpirationDate"
                v-model="document.expirationDate"
                type="date"
                format="dd/MM/yyyy"
                placeholder="Pick a day"
            />
        </el-form-item>
    </el-form>
</template>

<script lang="ts" src="./supporting-documents-form.component.ts"></script>
