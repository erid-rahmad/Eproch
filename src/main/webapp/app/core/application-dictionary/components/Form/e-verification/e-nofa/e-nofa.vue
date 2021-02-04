<template>
    <div class="app-container">
                <el-row class="header">
                    <el-col :span="24">

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
                    <el-col :span="24">

                        <el-form ref="form" label-width="100px" size="mini" v-model="filter" @submit.native.prevent="verificationFilter">
                            <el-row>
                                <el-col :span="10">

                                    <el-form-item label="Address ID :" prop="vendor">
                                        <el-select
                                            class="form-input"
                                            clearable filterable remote reserve-keyword
                                            v-model="filter.vendor"
                                            :remote-method="retrieveAllVendorRecords"
                                            :loading="remoteProcessing" >
                                            <el-option
                                                v-for="item in vendorOptions"
                                                :key="item.key"
                                                :label="item.value+' - '+item.name"
                                                :value="item.key" />
                                        </el-select>
                                    </el-form-item>

                                </el-col>
                                <el-col :span="1">
                                    <el-form-item label-width="5px">
                                        <el-button
                                            size="medium"
                                            @click="verificationFilter"
                                            :loading="remoteProcessing"
                                            icon="el-icon-search"/>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </el-form>

                    </el-col>
                </el-row>

                <el-row class="main" ref="tableWrapper">
                    <el-col :span="24">
                        <el-table
                            v-loading="processing"
                            ref="gridData"
                            highlight-current-row
                            border stripe
                            size="mini"
                            style="width: 100%; height: 100%"
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
                                min-width="100"
                                sortable
                                prop="vendorCode"
                                label="Address ID"/>
                            <el-table-column
                                min-width="250"
                                sortable
                                prop="vendorName"
                                label="Name"/>

                            <el-table-column
                                min-width="100"
                                sortable
                                prop="startNo"
                                label="Start No">
                                <template slot-scope="{ row }">
                                    {{ row.startNo | facade('###-##.########') }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="100"
                                sortable
                                prop="endNo"
                                label="End No">
                                <template slot-scope="{ row }">
                                    {{ row.endNo | facade('###-##.########') }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="130"
                                sortable
                                prop="createdDate"
                                label="Created Date">
                            </el-table-column>

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
            width="40%"
            :visible.sync="dialogConfirmationVisible"
            :title="dialogTitle">

            <template>

                <el-row v-if="dialogType=='add'" :gutter="16">
                    <el-col :span="24" :offset="0">
                        <e-nofa-update
                            ref="addTaxInvoice"
                            @close-dialog="closetaxInvoiceUpdate"
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

                <div slot="footer" v-if="dialogType!='add'">
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

    .main {
        padding: 0px;
    }

    .header{
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }



</style>
