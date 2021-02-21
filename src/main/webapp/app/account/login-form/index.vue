<template>
  <div class="login-container">
    <el-card>
      <el-row
        class="login-panel"
        :gutter="24"
      >
        <el-col
          class="panel-column"
          :span="14"
        >
          <img
            alt="User login"
            class="login-bg"
            src="@/assets/images/undraw_Login_re_4vu2.svg"
          />
        </el-col>
        <el-col
          class="panel-column"
          :span="10"
        >
          <el-form
            ref="loginForm"
            autocomplete="off"
            class="login-form"
            :model="loginForm"
            :rules="loginRules"
          >
            <div class="title-container">
              <h4 class="title">
                {{ $t('login.title') }}
              </h4>
            </div>

            <el-form-item prop="username">
              <el-input
                ref="username"
                v-model="loginForm.username"
                :placeholder="$t('global.form[\'username.label\']')"
                name="username"
                type="text"
                tabindex="1"
                autocomplete="off"
                clearable
                prefix-icon="el-icon-user"
              />
            </el-form-item>

            <el-tooltip
              v-model="capsTooltip"
              content="Caps lock is on"
              manual
              placement="right"
            >
              <el-form-item prop="password">
                <el-input
                  ref="password"
                  v-model="loginForm.password"
                  autocomplete="off"
                  clearable
                  name="password"
                  :placeholder="$t('login.form[\'password.placeholder\']')"
                  prefix-icon="el-icon-lock"
                  show-password
                  tabindex="2"
                  type="password"
                  @blur="capsTooltip = false"
                  @keyup.native="checkCapslock"
                  @keyup.enter.native="handleLogin"
                />
              </el-form-item>
            </el-tooltip>

            <el-checkbox 
              class="remember-me"
              v-model="loginForm.rememberMe"
              tabindex="3"
            >
              {{ $t('login.form.rememberme') }}
            </el-checkbox>

            <el-button
              v-loading="loading"
              class="login-button"
              icon="el-icon-user-solid"
              tabindex="4"
              type="primary"
              @click.native.prevent="handleLogin"
            >
              {{ $t('login.form.button') }}
            </el-button>

            <el-row
              class="alt-link-group"
              :gutter="24">
              <el-col
                :span="12"
                style="text-align: right;"
              >
                <router-link
                  class="forgot-password-link"
                  tabindex="5"
                  to="/account/reset/request"
                  v-text="$t('login.password.forgot')"
                >
                  Forgot password
                </router-link>
              </el-col>
              <el-col :span="12">
                <el-button
                  class="register-link"
                  size="mini"
                  type="text"
                  @click.native.prevent="showInvitationCodeDialog = true"
                >
                  Register
                </el-button>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
    </el-card>
    <el-dialog
      class="invitation-code-dialog"
      :visible.sync="showInvitationCodeDialog"
      title="Enter Invitation Code"
      width="30%"
    >
      <el-form :model="invitation">
        <el-form-item
          label="Invitation Code"
          prop="code"
          required
        >
          <el-input
            v-model="invitation.code"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showInvitationCodeDialog = false">Cancel</el-button>
        <el-button
          type="primary"
          @click="verifyInvitationCode"
        >
          Verify Code
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./index.component.ts"></script>

<style lang="scss">
.login-container {
  .el-card .el-card__body {
    height: 100%;
  }

  .invitation-code-dialog {
    .el-dialog__body {
      padding: 0 20px;
    }
  }
}
</style>

<style lang="scss" scoped>
.login-container {
  background: $loginBg;
  display: grid;
  height: 100%;
  width: 100%;
  overflow: hidden;

  .el-card {
    border: none;
    border-radius: 10px;
    height: 75%;
    width: 75%;
    margin: auto;

    .login-panel,
    .panel-column {
      height: 100%;
    }

    .panel-column {
      display: grid;

      &:last-child {
        border-left: 1px solid #d0d0de;
      }
    }

    .login-bg {
      margin: auto;
      width: 480px;
    }

    .login-form {
      position: relative;
      width: 320px;
      max-width: 100%;
      margin: auto;

      .alt-link-group .el-col:last-child {
        border-left: 1px solid #aeaeae;
      }

      .forgot-password-link {
        color: #1890ff;
        font-size: 14px;
        font-weight: 400;
        line-height: 1;
        padding: 5px 2px 5px 0;
      }

      .login-button {
        margin-bottom: 16px;
        width: 100%;
      }

      .register-link {
        font-size: 14px;
        padding-left: 0;
        padding-top: 4px;
      }

      .remember-me {
        margin-bottom: 16px;
      }
    }
  }

  .title-container {
    position: relative;

    .title {
      font-size: 24px;
      color: #333;
      margin: 0px auto 24px auto;
      text-align: center;
      font-weight: bold;
    }
  }
}
</style>
