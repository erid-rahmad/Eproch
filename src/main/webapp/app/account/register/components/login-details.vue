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
<script lang="ts">
import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'

const LoginProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    login: {
      type: Object,
      default: () => {}
    }
  }
})

@Component
export default class LoginDetails extends LoginProps {
  data() {
    return {
      columnSpacing: 32,
      rules: {
        username: {
          min: 3,
          pattern: '^[_.@A-Za-z0-9-]*$'
        },
        email: {
          type: 'email'
        },
        password: {
          min: 4,
          max: 100
        }
      }
    }
  }

  mounted() {
    this.eventBus.$on('validate-form', this.validate)
  }

  validate(formIndex) {
    if (formIndex === 0) {
      (this.$refs.login as ElForm).validate((passed, errors) => {
        this.eventBus.$emit('step-validated', { passed, errors })
      })
    }
  }
}
</script>
