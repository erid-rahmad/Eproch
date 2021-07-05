<template>
    <div class="app-container card-view vendor-contract">
        <div class="toolbar">
            <el-button
                v-if="mainPage"
                icon="el-icon-plus"
                size="mini"
                type="primary"
                @click="viewDetails()"
            ></el-button>
            <el-button
                v-if="mainPage"
                :disabled="selectedRow.documentStatus !== 'DRF'"
                icon="el-icon-delete"
                size="mini"
                title="Delete"
                type="danger"
                @click="onDeleteClicked"
            ></el-button>
            <el-button
                v-if="detailPage"
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="onCloseClicked"
            >
                Close
            </el-button>
            <el-button
                v-if="detailPage && isDraft && detailTabName === 'INF'"
                size="mini"
                type="primary"
                @click="onSaveClicked"
            >
                <svg-icon name="icomoo/273-checkmark"></svg-icon>
                Save
            </el-button>
            <el-button
                v-if="detailPage && isSubmit"

                size="mini"
                type="primary"
                @click="SubmitPA=true"
            >
                <svg-icon name="icomoo/273-checkmark"></svg-icon>
                Submit
            </el-button>
            <el-button
                v-if="detailPage && isAction"
                size="mini"
                type="primary"
                @click="ApprovePA=true"
                icon="el-icon-document-checked"
            >
                Approve
            </el-button>
            <el-button
                v-if="detailPage && isAction"
                size="mini"
                type="danger"
                @click="RejectPA=true"
                icon="el-icon-document-delete"
            >
                Reject
            </el-button>
        </div>

        <div class="card">
            <template v-if="mainPage">
                <el-table
                    ref="mainGrid"
                    v-loading="loading"
                    :data="gridData"
                    border
                    highlight-current-row
                    size="mini"
                    stripe
                    @current-change="onCurrentRowChanged"
                    @sort-change="changeOrder"
                >
                    <el-table-column
                        fixed
                        label="No"
                        width="50"
                    >
                        <template v-slot="row">
                            {{ row.$index + 1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Contract No"
                        min-width="120"
                        prop="documentNo"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Title"
                        min-width="200"
                        prop="name"
                        show-overflow-tooltip
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Type"
                        min-width="150"
                        prop="documentTypeName"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Price Confirmation"
                        min-width="200"
                        sortable
                    >
                        <template v-slot="{ row }">
                            <el-checkbox
                                v-model="row.forPriceConfirmation"
                                disabled
                            ></el-checkbox>
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Vendor Name"
                        prop="vendorName"
                        show-overflow-tooltip
                        sortable
                        width="200"
                    ></el-table-column>

                    <el-table-column
                        label="Start Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.startDate | formatDate(true) }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Expiration Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.expirationDate | formatDate(true) }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Modified Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.lastModifiedDate | formatDate }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Modified by"
                        min-width="150"
                        prop="lastModifiedBy"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        fixed="right"
                        label="Action"
                        width="180"
                    >
                        <template v-slot="{ row }">
                            <el-button
                                size="mini"
                                type="primary"
                                @click="viewDetails(row)"
                            >
                                <svg-icon name="link"></svg-icon>
                                View
                            </el-button>
                        </template>
                    </el-table-column>

                </el-table>
                <el-pagination
                    ref="pagination"
                    :current-page.sync="page"
                    :page-size="itemsPerPage"
                    :page-sizes="[10, 20, 50, 100]"
                    :total="queryCount"
                    background
                    layout="sizes, prev, pager, next"
                    small
                    @size-change="changePageSize"
                ></el-pagination>
            </template>

            <contract-detail
                v-else-if="detailPage"
                ref="detailPage"
                :data.sync="selectedRow"
                :tab.sync="detailTabName"
            ></contract-detail>
        </div>

        <el-dialog
            :visible.sync="terminationConfirmationVisible"
            title="Terminate Contract"
            width="30%"
        >
            <p>Are you sure you want to terminate the contract?</p>
            <div slot="footer">
                <el-button
                    icon="el-icon-close"
                    size="mini"
                    style="margin-left: 0px;"
                    @click="terminationConfirmationVisible = false"
                >
                    {{ $t('entity.action.cancel') }}
                </el-button>
                <el-button
                    size="mini"
                    style="margin-left: 0px;"
                    type="danger"
                    @click="terminateContract"
                >
                    <svg-icon name="icomoo/183-switch"></svg-icon>
                    Terminate
                </el-button>
            </div>
        </el-dialog>

        <!--        <el-dialog-->
        <!--            :title="$t('entity.delete.title')"-->
        <!--            :visible.sync="deleteConfirmationVisible"-->
        <!--            width="30%"-->
        <!--        >-->
        <!--            <template>-->
        <!--                <span>Are you sure to delete the selected record?</span>-->
        <!--                <div slot="footer">-->
        <!--                    <el-button-->
        <!--                        icon="el-icon-close"-->
        <!--                        size="mini"-->
        <!--                        style="margin-left: 0px;"-->
        <!--                        @click="deleteConfirmationVisible = false"-->
        <!--                    >-->
        <!--                        {{ $t('entity.action.cancel') }}-->
        <!--                    </el-button>-->
        <!--                    <el-button-->
        <!--                        icon="el-icon-delete"-->
        <!--                        size="mini"-->
        <!--                        style="margin-left: 0px;"-->
        <!--                        type="danger"-->
        <!--                        @click="deleteRecord"-->
        <!--                    >-->
        <!--                        {{ $t('entity.action.delete') }}-->
        <!--                    </el-button>-->
        <!--                </div>-->
        <!--            </template>-->
        <!--        </el-dialog>-->
        <el-dialog
            title="Submit Contract"
            width="30%"
            :visible.sync="SubmitPA"
        >
            <template>
                <span>Are you sure to Submit the selected record?</span>
                <div slot="footer">
                    <el-button
                        icon="el-icon-close"
                        size="mini"
                        style="margin-left: 0px;"
                        @click="SubmitPA = false"
                    >
                        Cancel
                    </el-button>
                    <el-button
                        icon="el-icon-check"
                        size="mini"
                        style="margin-left: 0px;"
                        type="primary"
                        @click="SubmitClicked"
                    >
                        Yes
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog
            title="Approve Contract"
            width="30%"
            :visible.sync="ApprovePA"
        >
            <template>
                <span>Are you sure to Approve the selected record?</span>
                <div slot="footer">
                    <el-button
                        icon="el-icon-close"
                        size="mini"
                        style="margin-left: 0px;"
                        @click="ApprovePA = false"
                    >
                        Cancel
                    </el-button>
                    <el-button
                        icon="el-icon-check"
                        size="mini"
                        style="margin-left: 0px;"
                        type="primary"
                        @click="ApproveClicked"
                    >
                        Yes
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog
            title="Reject Contract"
            width="30%"
            :visible.sync="RejectPA"
        >
            <template>
                <span>Are you sure to Reject the selected record?</span>
                <div slot="footer">
                    <el-button
                        icon="el-icon-close"
                        size="mini"
                        style="margin-left: 0px;"
                        @click="RejectPA = false"
                    >
                        Cancel
                    </el-button>
                    <el-button
                        icon="el-icon-check"
                        size="mini"
                        style="margin-left: 0px;"
                        type="danger"
                        @click="RejectClicked"
                    >
                        Yes
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./vendor-contract.component.ts"></script>
<style lang="scss">
.compact .vendor-contract {

    .el-table--mini {

        th,
        td {
            height: 35px;
        }
    }

    .toolbar {
        padding: 4px 16px 0;

        .el-button + .el-button {
            margin-left: 8px;
        }
    }
}
</style>
