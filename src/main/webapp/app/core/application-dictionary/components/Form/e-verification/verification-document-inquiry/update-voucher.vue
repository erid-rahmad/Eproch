<template>
    <div class="app-container">
        <el-form
            ref="form"
            label-width="150px"
            size="mini"
            :model="voucher"
            :rules="rules">

            <el-row style="margin-top: 0px" class="filter">

                <el-col :span="24">
                    <el-form-item label="Verification No." prop="verificationNo">
                        <el-input class="form-input" v-model="getVerification.verificationNo" disabled />
                    </el-form-item>
                    <el-form-item label="Voucher No." prop="invoiceAp">
                        <el-input
                            class="form-input"
                            v-model="voucher.invoiceAp"
                            clearable :disabled="fillVoucherNo" />
                    </el-form-item>
                    <el-form-item label="Doc. Type" prop="docType">
                        <el-input
                            class="form-input"
                            v-model="voucher.docType"
                            clearable :disabled="fillVoucherNo" />
                    </el-form-item>
                    <el-form-item label="Company" prop="adOrganizationCode">
                        <el-input class="form-input" v-model="getVerification.adOrganizationCode" disabled />
                    </el-form-item>
                </el-col>

            </el-row>
            <el-row type="flex" justify="end">
                <el-form-item>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        v-loading.fullscreen.lock="fullscreenLoading"
                        :disabled="fillVoucherNo"
                        @click="confirmUpdate">
                            Save
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="closeDialog">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </el-form-item>
            </el-row>

        </el-form>

    </div>
</template>

<script lang="ts" src="./update-voucher.component.ts">
</script>

<style lang="scss">
    .el-dialog__body{
        padding: 10px 20px;
    }
</style>
