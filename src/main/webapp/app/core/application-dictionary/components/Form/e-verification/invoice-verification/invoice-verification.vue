<template>
    <div class="app-container invoice-verification">
        <el-row class="main">
            <el-col :span="24">
                <el-row style="height: 100%; display: grid;">
                    <el-form
                        ref="form"
                        label-position="top"
                        :model="filterForm"
                        size="mini"
                        style="margin: 200px auto 0px; width: 256px"
                        @submit.native.prevent="searchInvoiceVerification"
                    >
                        <el-form-item prop="verificationNo" label="Verification No." required>
                            <el-input
                                v-model="filterForm.verificationNo"
                                placeholder="Please Input Verification No."
                                clearable
                            >
                                <el-button
                                    v-loading.fullscreen.lock="fullscreenLoading"
                                    slot="append"
                                    icon="el-icon-search"
                                    @click="searchInvoiceVerification"
                                >
                                    Search
                                </el-button>
                            </el-input>
                        </el-form-item>
                    </el-form>
                </el-row>
            </el-col>
        </el-row>

            <!-- =========================================================================== -->

        <el-dialog
            margin="0px"
            padding="0px"
            width="90%"
            :visible.sync="dialogInvoiceVerificationVisible"
            :title="dialogTitle">

            <template>
                <el-row :gutter="16">
                    <el-col :span="24" :offset="0">
                        <invoice-verification-document-approval
                            ref="invoiceVerificationDocumentApproval"
                            :data-verification-and-lines="eVerification"
                        />
                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        size="mini"
                        icon="el-icon-check"
                        type="primary"
                        @click="actionSubmit('APV')"
                    >
                            Approve
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-warning-outline"
                        type="danger"
                        @click="actionSubmit('RJC')"
                    >
                            Reject
                    </el-button>
                    <el-button
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogInvoiceVerificationVisible = false"
                    >
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./invoice-verification.component.ts">
</script>

<style lang="scss">

.el-dialog__body{
    padding: 10px 20px;
}

.form {

    .form-input {
        width: 100%;
    }
}

.el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
}

.grid-view {
    .el-table {
        .is-error .el-input__inner {
            border-color: #ff4949;
        }

        label.el-checkbox {
            margin: 4px 0;
        }

        .switch, .checkbox, .selectRemote, .select, .input, .numeric, .date{
            width: 100%;
        }
    }

    .el-pagination {
        background: #fff;
        bottom: 0;
        left: 0;
        right: 0;
        z-index: 5;

        .el-input--mini .el-input__inner {
            height: 22px;
        }
    }
}

.invoice-verification {
    .el-form--label-top .el-form-item__label {
        padding: 0;
    }
}
</style>
