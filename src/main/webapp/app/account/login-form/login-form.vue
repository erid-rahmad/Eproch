<template>
  <el-dialog
    :visible="visible"
    :append-to-body="true"
    @opened="onLoginFormOpened"
    @close="onHide"
    width="40%"
    center
  >
    <div class="login-container">
      <el-row :gutter="16">
        <el-col :span="24" :offset="0">
          <el-form
            ref="loginForm"
            :model="loginForm"
            class="login-form"
            autocomplete="off"
            label-position="left"
          >
            <div class="title-container">
              <h3 class="title">
                {{ $t('login.title') }}
              </h3>
            </div>
            <hr />

            <el-form-item prop="username" required>
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

            <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
              <el-form-item prop="password" required>
                <el-input
                  :key="passwordType"
                  ref="password"
                  v-model="loginForm.password"
                  :type="passwordType"
                  :placeholder="$t('global.form[\'password.label\']')"
                  name="password"
                  tabindex="2"
                  autocomplete="off"
                  @keyup.native="checkCapslock"
                  @blur="capsTooltip = false"
                  @keyup.enter.native="handleLogin"
                  clearable
                  show-password
                  prefix-icon="el-icon-lock"
                />
              </el-form-item>
            </el-tooltip>

            <el-checkbox 
              v-model="loginForm.rememberMe"
              tabindex="3">
              {{ $t('login.form.rememberme') }}
            </el-checkbox>

            <el-button
              :loading="loading"
              type="primary"
              style="width:100%; margin-bottom:0px;"
              tabindex="4"
              icon="el-icon-user-solid"
              @click.native.prevent="handleLogin"
            >{{ $t('login.form.button') }}</el-button>
          </el-form>
          <hr />
          <el-alert type="info" :closable="false">
            <router-link
              slot="title"
              to="/account/reset/request"
              v-text="$t('login.password.forgot')"
              tabindex="5"
            >Did you forget your password?</router-link>
          </el-alert>
          <el-alert type="info" :closable="false">
            <p slot="title">
              <span
                v-text="$t('global.messages.info.register.noaccount')"
              >You don't have an account yet?</span>
              <router-link
                to="/register"
                v-text="$t('global.messages.info.register.link')"
                tabindex="6"
              >Register a new account</router-link>
            </p>
          </el-alert>
        </el-col>
      </el-row>
    </div>
  </el-dialog>
</template>

<script lang="ts" src="./login-form.component.ts">
</script>

<style lang="scss">
// References: https://www.zhangxinxu.com/wordpress/2018/01/css-caret-color-first-line/
@supports (-webkit-mask: none) and (not (cater-color: $loginCursorColor)) {
  .login-container .el-input {
    input {
      color: #5d5d5d;
    }
    input::first-line {
      color: #5d5d5d;
    }
  }
}

.login-container {
  .el-input {
    display: inline-block;
    height: 4px;
    width: 100%;

    input {
      background-color: #F4F6F7;
      padding: 12px 55px 12px 30px;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $loginBg inset !important;
        -webkit-text-fill-color: #5d5d5d !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 5px;
    margin-bottom: 17px;
  }

  .el-button{
    padding: 10px 10px;
  }
}
</style>

<style lang="scss" scoped>
.login-container {
  height: 100%;
  width: 100%;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 320px;
    max-width: 100%;
    //padding: 64px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #5d5d5d;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $darkGray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #5d5d5d;
      //margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    .set-language {
      color: #5d5d5d;
      position: absolute;
      top: 3px;
      font-size: 18px;
      right: 0px;
      cursor: pointer;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $darkGray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
