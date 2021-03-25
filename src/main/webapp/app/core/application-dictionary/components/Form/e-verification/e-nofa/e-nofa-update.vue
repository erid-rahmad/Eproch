<template>
    <div class="app-container">
        <el-form
            ref="eNofaForm"
            label-width="100px"
            label-position="left"
            size="mini"
            :model="form"
            :rules="rules"
        >
            <el-row>
                <el-col :span="24">
                    <el-form-item label="Address ID :" prop="vendorId">
                        <el-select
                            class="form-input"
                            clearable filterable remote reserve-keyword
                            v-model="form.vendorId"
                            :remote-method="retrieveAllVendorRecords"
                            :loading="processing" >
                            <el-option
                                v-for="item in vendorOptions"
                                :key="item.key"
                                :label="item.value+' - '+item.name"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="Start No. :" prop="startNo">
                        <el-input
                            v-model="form.startNo"
                            v-cleave="taxInvoicePattern"
                            class="form-input"
                            placeholder="___-__.________"
                        />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="End No. :" prop="endNo">
                        <el-input
                            v-model="form.endNo"
                            v-cleave="taxInvoicePattern"
                            class="form-input"
                            placeholder="___-__.________"
                        />
                    </el-form-item>
                </el-col>
            </el-row>
            <div style="float: right">
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-check"
                    type="primary"
                    v-loading.fullscreen.lock="fullscreenLoading"
                    @click="confirmSave">
                        Save
                </el-button>
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-close"
                    @click="closeDialog">
                        {{ $t('entity.action.cancel') }}
                </el-button>
            </div>
            <div class="clearfix"></div>
        </el-form>
    </div>
</template>

<script lang="ts" src="./e-nofa-update.component.ts">
</script>

<style lang="scss">
    .el-dialog__body{
        padding: 10px 20px;
    }
</style>
