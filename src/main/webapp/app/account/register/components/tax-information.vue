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
            label-position="left"
            size="mini"
            label-width="256px"
        >
            <el-form-item :label="$t('register.tax.efaktur')">
                <el-checkbox
                    v-model="eInvoice"
                    @change="onEInvoiceChanged"
                />
            </el-form-item>
            <el-form-item :label="$t('register.tax.pkp')">
                <el-checkbox
                    v-model="taxableEmployers"
                    @change="onTaxableEmployersChanged"
                />
            </el-form-item>
        </el-form>

        <el-divider content-position="left">
            <h4>{{ $t('register.tax.body.title') }}</h4>
        </el-divider>
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
                    @click.native.prevent="showTaxList()">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>

            <el-table
                max-height="250"
                style="width: 100%"
                :data="taxes"
            >
                <el-table-column
                    fixed
                    min-width="128"
                    prop="name"
                    :label="$t('register.tax.taxCategoryName')"
                />
                <el-table-column
                    prop="description"
                    min-width="128"
                    :label="$t('register.tax.description')"
                />
                <el-table-column
                    min-width="128"
                    :label="$t('register.tax.isWithholding')"
                >
                    <template slot-scope="props">
                        <el-checkbox
                            v-model="props.row.isWithholding"
                            disabled
                        />
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
                            v-loading="applying"
                            @click="deleteTax(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        >
                            {{ $t('entity.action.delete') }}
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog
            :title="$t('register.form.tax[\'title.edit\']')"
            :visible.sync="editDialogVisible"
        >
            <tax-information-update
                ref="taxSelectionList"
                :event-bus="eventBus"
            />
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false" icon="el-icon-close">{{ $t('entity.action.cancel') }}</el-button>
                <el-button type="primary" @click="onTaxesApplied" icon="el-icon-check">{{ $t('entity.action.save') }}</el-button>
            </span>
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
