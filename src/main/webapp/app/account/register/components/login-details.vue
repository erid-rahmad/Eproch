<template>
  <div>
    <el-divider content-position="left"><h4>{{ $t('register.login.title') }}</h4></el-divider>
    <el-form
      ref="login"
      label-position="right"
      label-width="150px"
      size="mini"
      :model="login"
      :rules="rules"
    >
      <el-row :gutter="columnSpacing">
        
        <el-col :span="12">
          <el-form-item :label="$t('register.login.form.username')" prop="userLogin" required>
            <el-input v-model="login.userLogin" class="form-input" min="3" clearable />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item :label="$t('register.login.form.email')"  prop="email" required>
            <el-input v-model="login.email" class="form-input" clearable />
          </el-form-item>
        </el-col>

      </el-row>
      <el-row :gutter="columnSpacing">
        
        <el-col :span="12">
          <el-form-item :label="$t('register.login.form.password')"  prop="password" required>
            <el-input v-model="login.password" class="form-input" show-password clearable />
          </el-form-item>
        </el-col>

        <el-col :span="12">  
          <el-form-item :label="$t('register.login.form.passwordConfirm')"  prop="passwordConfirmation" required>
            <el-input v-model="login.passwordConfirmation" class="form-input" show-password clearable />
          </el-form-item>
        </el-col>
        
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./login-details.component.ts"></script>
<style lang="scss">
  .form-input {
    textarea {
      resize: none;
      border: none;
      border-radius: 0px;
      border-bottom: 1px solid #bfcbd9;
    }
  }
  
  /*
  .el-form-item {
    margin-bottom: 0px;
  }

  .el-form-item--mini.el-form-item {
    margin-bottom: 0px;
  }
  */
</style>