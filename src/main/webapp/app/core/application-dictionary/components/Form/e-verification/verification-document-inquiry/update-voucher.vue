<template>
    <div class="app-container">
        <el-form
            ref="form"
            label-width="150px"
            size="mini">

            <el-row style="margin-top: 0px" class="filter">

                <el-col :span="24">
                    <el-form-item label="Verification No." prop="verificationNo">
                        <el-input class="form-input" v-model="voucher.verificationNo" clearable disabled />
                    </el-form-item>
                    <el-form-item label="Voucher No." prop="voucherNO">
                        <el-input class="form-input" v-model="voucher.voucherNO" @change="setVoucher" clearable />
                    </el-form-item>
                    <el-form-item label="Doc. Type" prop="docType">
                        <el-input class="form-input" v-model="voucher.docType" @change="setVoucher" clearable />
                    </el-form-item>
                    <el-form-item label="Company" prop="company">
                        <el-input class="form-input" v-model="voucher.company" @change="setVoucher" clearable />
                    </el-form-item>

                </el-col>

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
