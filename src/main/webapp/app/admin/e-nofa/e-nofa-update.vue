<template>
    <div class="app-container">
        <el-form
            ref="form"
            label-width="100px"
            size="mini">
            <el-form-item label="Vendor" prop="vendorId">
                <el-select
                    class="form-input"
                    filterable remote reserve-keyword
                    v-model="form.vendorId"
                    @change="setForm"
                    :remote-method="retrieveAllVendorRecords"
                    :loading="processing" >
                    <el-option
                        v-for="item in vendorOptions"
                        :key="item.key"
                        :label="item.value"
                        :value="item.key" />
                </el-select>
            </el-form-item>

            <el-form-item label="Start No." prop="startNo">
                <el-input
                    class="form-input"
                    clearable
                    v-model="form.startNo"
                    @change="setForm"/>
            </el-form-item>
            <el-form-item label="End No." prop="endNo">
                <el-input
                    class="form-input"
                    clearable
                    v-model="form.endNo"
                    @change="setForm"/>
            </el-form-item>
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
