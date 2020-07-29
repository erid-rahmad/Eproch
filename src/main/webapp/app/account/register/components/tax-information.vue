<template>
    <div>
        <el-divider content-position="left"><h4>{{ $t('register.tax.header.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.tax.header.description')"
                :type="errors.type"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <el-form
            ref="taxInformations"
            label-position="right"
            size="mini"
            label-width="256px"
            :model="taxInformations"
            :rules="rules">
        
            <el-form-item
                :label="$t('register.tax.efaktur')"
                prop="efaktur"
                required>
                <el-radio-group v-model="taxInformations.efaktur" size="mini">
                    <el-radio label="true">Yes</el-radio>
                    <el-radio label="false">No</el-radio>
                </el-radio-group>
            </el-form-item>
            
            <el-form-item 
                :label="$t('register.tax.pkp')" 
                prop="pkp" 
                required>
                <el-radio-group v-model="taxInformations.pkp" size="mini">
                    <el-radio label="true">Yes</el-radio>
                    <el-radio label="false">No</el-radio>
                </el-radio-group>
            </el-form-item>
            
        </el-form>

        <el-divider content-position="left"><h4>{{ $t('register.tax.body.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.tax.body.description')"
                :type="errors.type"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click.native.prevent="addTax()">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>
            
            <el-table
                ref="taxRates"
                max-height="250"
                style="width: 100%"
                :data="taxRates">
                <el-table-column
                    fixed
                    min-width="128"
                    prop="name"
                    :label="$t('register.tax.taxRateName')"/>
                <el-table-column
                    prop="orderType"
                    min-width="128"
                    :label="$t('register.tax.transactionType')"/>
                <el-table-column
                    prop="rate"
                    min-width="128"
                    :label="$t('register.tax.rate')"/>
                
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128">
                    
                    <template slot-scope="scope">
                        
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')">
                            {{ $t('entity.action.delete') }}
                        </el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>
        
        <el-dialog
            :title="$t('register.form.tax[\'title.edit\']')"
            :visible.sync="editDialogVisible"
            @closed="hideDialog">
            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <tax-information-update
                        ref="dialogBody"
                        :event-bus="eventBus"/>
                </el-col>
            </el-row>
            
        </el-dialog>

    </div>
    
</template>
<script lang="ts" src="./tax-information.component.ts"></script>

<style lang="scss" scoped>
.error {
    background: none;
    color: #ff4949;
    font-size: 12px;
    line-height: 1;
}
</style>
