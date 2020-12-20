<template>
    <div class="app-container">
        <el-form
            ref="eNofaForm"
            label-width="100px"
            size="mini"
            :model="form"
            :rules="rules">
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
                            class="form-input"
                            clearable
                            placeholder="___-__.________"
                            v-model="form.startNo"
                            v-inputmask="{'mask': '999-99.99999999'}"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="End No. :" prop="endNo">
                        <el-input
                            class="form-input"
                            clearable
                            placeholder="___-__.________"
                            v-model="form.endNo"
                            v-inputmask="{'mask': '999-99.99999999'}"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row type="flex" justify="end">
                <el-col :span="11">
                    <el-form-item>
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
                    </el-form-item>
                </el-col>
            </el-row>

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
