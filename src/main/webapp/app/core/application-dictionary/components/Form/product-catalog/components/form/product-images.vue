<template>
    <div>

        <el-upload
            list-type="picture-card"
            v-model="productImages"
            :action="action"
            :accept="accept"
            :limit="limit"
            :auto-upload="true"

            :before-upload="handleBeforeUpload"
            :on-preview="handlePreview"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            :on-error="onUploadError"
            :on-success="onUploadSuccess">

            <i slot="default" class="el-icon-plus"/>

        </el-upload>

        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./product-images.component.ts">
</script>
