<template>
  <el-popover
    v-model="visible"
    title="Update Password"
    trigger="click"
    width="320"
  >
    <el-form
      ref="form"
      label-position="left"
      label-width="128px"
      size="mini"
      :model="account"
      :rules="validationSchema"
    >
      <el-row>
        <el-col :span="24">  
          <el-form-item :label="$t('global.form[\'newpassword.label\']')" prop="newPassword" required>
            <el-input v-model="account.newPassword" class="form-input" clearable show-password />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <div style="text-align: right">
          <el-button size="mini" type="text" @click="visible = false">Cancel</el-button>
          <el-button type="primary" size="mini" @click="onSave">Save</el-button>
        </div>
      </el-row>
    </el-form>
    <el-button
      slot="reference"
      :disabled="disabled"
      size="mini"
      type="primary"
    >
      Change Password
    </el-button>
  </el-popover>
</template>
<script lang="ts" src="./password-editor.component.ts"></script>
