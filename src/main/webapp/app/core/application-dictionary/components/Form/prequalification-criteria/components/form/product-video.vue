<template>
    <div>

        <el-upload
            ref="upload"
            v-model="productVideos.video"
            :action="action"
            :accept="accept"
            :limit="limit"
            :auto-upload="true"

            :file-list="fileList"
            :before-upload="handleBeforeUpload"
            :on-change="onUploadChange"
            :on-preview="handlePreview"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            :on-error="onUploadError"
            :on-success="onUploadSuccess">

            <el-button slot="trigger" type="primary" icon="el-icon-search">select file</el-button>
            <span style="margin-left: 10px;" class="el-upload__tip" slot="tip">files with a size less than 5Mb</span>
        </el-upload>

    </div>
</template>

<script lang="ts" src="./product-video.component.ts"></script>
