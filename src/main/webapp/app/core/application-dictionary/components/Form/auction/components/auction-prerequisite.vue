<template>
  <div class="auction-item">
    <el-form
      disabled
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="auction"
      :size="formSettings.size"
    >
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Auction No.">
            <el-input
              v-model="auction.documentNo"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="gutterSize">
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Title">
            <el-input
              v-model="auction.name"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-form
      v-loading="loading"
      ref="mainForm"
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="prerequisite"
      :size="formSettings.size"
    >
      <html-editor v-model="prerequisite.description" size="mini"></html-editor>
    </el-form>
  </div>
</template>
<script lang="ts" src="./auction-prerequisite.component.ts"></script>
