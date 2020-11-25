<template>
    <div class="app-container">
                <el-row class="header">
                    <el-col :span="24">

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            @click="showDialogConfirmation('detail')"
                            icon="el-icon-check"/>

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-search"
                            @click.native.prevent="verificationFilter"/>

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            @click="showDialogConfirmation('add')"
                            icon="el-icon-plus"/>

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="danger"
                            @click="showDialogConfirmation('remove')"
                            icon="el-icon-delete"/>

                        <el-button
                            class="button"
                            size="small"
                            type="primary"
                            @click="showDialogConfirmation('export')"
                            icon="el-icon-download">
                            Export
                        </el-button>

                    </el-col>
                </el-row>

                <el-row class="filter">
                    <el-form ref="form"  label-width="170px" size="mini">
                        <el-col :span="8">

                            <el-form-item label="Vendor" prop="vendor">
                                <el-select class="form-input" clearable filterable v-model="filter.vendor" >
                                    <el-option
                                        v-for="item in vendorOptions"
                                        :key="item.key"
                                        :label="item.value"
                                        :value="item.key" />
                                </el-select>
                            </el-form-item>

                        </el-col>
                    </el-form>

                </el-row>

                <el-row class="main grid-view" ref="tableWrapper">
                    <el-col :span="24">
                        <el-table
                            v-loading="processing"
                            ref="gridData"
                            highlight-current-row
                            border stripe
                            size="mini"
                            style="width: 100%: height: 100%"
                            :height="gridSchema.height"
                            :max-height="gridSchema.maxHeight"
                            :default-sort="gridSchema.defaultSort"
                            :empty-text="gridSchema.emptyText"
                            :data="gridData"
                            @row-click="singleSelection"
                            @sort-change="changeOrder">

                            <el-table-column
                                align="center"
                                fixed
                                width="35">
                                <template slot-scope="scope">
                                    <el-radio class="radio" v-model="radioSelection" :label="scope.$index">&nbsp;</el-radio>
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="130"
                                prop="vendorCode"
                                label="Address ID"/>
                            <el-table-column
                                min-width="130"
                                prop="vendorName"
                                label="Name"/>
                            <el-table-column
                                min-width="100"
                                prop="startNo"
                                label="Start No"/>

                            <el-table-column
                                min-width="128"
                                prop="endNo"
                                label="End No"/>
                            <el-table-column
                                min-width="128"
                                prop="createdDate"
                                label="Created Date"/>

                        </el-table>
                        <el-pagination
                            ref="pagination"
                            background
                            layout="sizes, prev, pager, next"
                            mini
                            :current-page.sync="page"
                            :page-sizes="[10, 20, 50, 100]"
                            :page-size="itemsPerPage"
                            :total="queryCount"
                            @size-change="changePageSize"/>
                    </el-col>
                </el-row>

            <!-- =========================================================================== -->

        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            :title="dialogTitle">

            <template>

                <el-row v-if="dialogType=='add'" :gutter="16">
                    <el-col :span="24" :offset="0">
                        <e-nofa-update
                            ref="addTaxInvoice"
                            @get-form="dataFormInputFaktur"
                        />
                    </el-col>
                </el-row>

                <el-row v-if="dialogType=='export'" :gutter="16">
                    <el-col :span="24" :offset="0">

                    </el-col>
                </el-row>

                <el-row v-else :gutter="16">
                    <el-col :span="24" :offset="0">
                        {{ dialogMessage }}
                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        @click="dialogButtonAction(dialogType)"
                        :icon="dialogButtonIcon"
                        :type="dialogButtonType">
                            {{ dialogButton }}
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./e-nofa.component.ts">
</script>

<style lang="scss">
    .el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
    }

    .header {
        color: #333;
    }

    .filter {
        .form-input {
            width: 100%;
        }
    }

    .main {
        padding: 0px;
    }

    .button {
        margin-bottom: 5px;
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
</style>
