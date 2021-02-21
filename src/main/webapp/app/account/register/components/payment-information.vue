<template>
    <div>
        <el-divider content-position="left"><h4>{{ $t('register.payment.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.payment.description')"
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
                    size="mini"
                    @click.native.prevent="addPayment()">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>
            <!--<p v-if="hasErrors" :text="errors.payment" class="error">Your company must have at least a payment</p>-->
            <el-table
                ref="payments"
                max-height="250"
                style="width: 100%"
                size="mini"
                :data="payments"
            >
                <el-table-column
                    min-width="128"
                    :label="$t('register.payment.bank')"
                >
                    <template slot-scope="scope">
                        <span>{{printValueByParam(scope.row.bank)}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="branch"
                    min-width="128"
                    :label="$t('register.payment.branch')"
                />
                <el-table-column
                    min-width="128"
                    :label="$t('register.payment.currency')"
                >
                    <template slot-scope="scope">
                        <span>{{printValueByParam(scope.row.currency)}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="account"
                    min-width="128"
                    :label="$t('register.payment.account')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="accountName"
                    min-width="128"
                    :label="$t('register.payment.accountName')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="bban"
                    min-width="128"
                    :label="$t('register.payment.bban')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="iban"
                    min-width="128"
                    :label="$t('register.payment.iban')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="description"
                    min-width="128"
                    :label="$t('register.payment.description')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="supportingfile.name"
                    min-width="128"
                    :label="$t('register.payment.supportingfile')"
                    show-overflow-tooltip
                >
                    <template scope="props">
                        <el-link
                            target="_blank"
                            v-if="props.row.supportingfile"
                            v-bind:href="''+props.row.supportingfile.response.downloadUri+''"
                            :underline="false">
                            {{ props.row.supportingfile.response.attachment.fileName }}
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row, scope.$index)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog
            :title="$t('register.form.payment[\'title.edit\']')"
            :visible.sync="editDialogVisible"
            @closed="hideDialog">
            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <payment-information-update
                        ref="dialogBody"
                        :pays="pays"
                        :event-bus="eventBus"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="hideDialog" size="mini" icon="el-icon-close">{{ $t('entity.action.cancel') }}</el-button>
                <el-button :loading="loading" size="mini" type="primary" @click="saveDocument" icon="el-icon-check">{{ $t('entity.action.save') }}</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./payment-information.component.ts"></script>

<style lang="scss" scoped>
.error {
    background: none;
    color: #ff4949;
    font-size: 12px;
    line-height: 1;
}
</style>
