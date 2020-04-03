<template>
    <div class="app-container">
        <el-row :gutter="16" align="center">
            <el-col :span="18">
                <h1 v-text="$t('activate.title')">Activation</h1>
                <div class="alert alert-success" v-if="success">
                    <span v-html="$t('activate.messages.success')"><strong>Your user account has been activated.</strong> Please </span>
                    <el-link href="/login" v-text="$t('global.messages.info.authenticated.link')">sign in</el-link>.
                </div>
                <div class="alert alert-danger" v-if="error" v-html="$t('activate.messages.error')">
                    <strong>Your user could not be activated.</strong> Please use the registration form to sign up.
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" src="./activate.component.ts">
</script>
