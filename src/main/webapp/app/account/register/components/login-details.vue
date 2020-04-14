<template>
  <el-form
    ref="login"
    label-position="left"
    label-width="128px"
    :model="login"
    :rules="rules"
  >
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item label="Username" prop="username" required>
          <el-input v-model="login.username" min="3" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Email" prop="email" required>
          <el-input v-model="login.email" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="columnSpacing">
      <el-col :span="6">
        <el-form-item label="Password" prop="password" required>
          <el-input v-model="login.password" type="password" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="Password Confirmation" prop="passwordConfirmation" required>
          <el-input v-model="login.passwordConfirmation" type="password" />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" src="./login-details.component.ts"></script>
