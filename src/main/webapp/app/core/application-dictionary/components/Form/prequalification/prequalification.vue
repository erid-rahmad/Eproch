<template>
    <div class="app-container card-view prequalification-process">
        <div class="toolbar">
            <el-button v-if="!index" icon="el-icon-close" size="mini" type="danger" @click="onFormClosed">
                Close
            </el-button>
            <el-button v-if="!index" :disabled="!dataChanged" size="mini" type="primary" @click="onFormSaved">
                <svg-icon name="icomoo/273-checkmark"></svg-icon>
                Save
            </el-button>
            <el-button v-if="!index" size="mini" type="primary" @click="onApprove" :disabled="selectedRow.documentStatus!='DRF'" icon="el-icon-document-checked">
                Approve
            </el-button>
            <el-button v-if="!index" icon="el-icon-document-delete" size="mini" type="danger" @click="onReject" :disabled="selectedRow.documentStatus!='DRF'">
                Reject
            </el-button>
            <el-button v-if="index" class="button" icon="el-icon-plus" size="mini" type="primary"
                       @click="onCreateClicked"></el-button>
        </div>
        <div v-if="index" class="card">
            <el-table
                ref="mainGrid"
                v-loading="processing"
                :data="gridData"
                :default-sort="gridSchema.defaultSort"
                :empty-text="gridSchema.emptyText"
                border
                highlight-current-row
                size="mini"
                stripe
                @current-change="onCurrentRowChanged"
                @sort-change="changeOrder"
                @selection-change="onSelectionChanged"
            >
                <el-table-column align="center" fixed type="selection" width="48"/>

                <el-table-column align="center" fixed="right" width="200">
                    <template slot-scope="{ row }">
                        <el-button :underline="false" icon="el-icon-search" size="mini" title="View" type="primary"
                                   @click="viewPreq(row)">
                            View
                        </el-button>
                    </template>
                </el-table-column>

                <el-table-column label="Prequalification No." min-width="150" prop="documentNo" sortable></el-table-column>

                <el-table-column label="Title" min-width="140" prop="name" show-overflow-tooltip
                                 sortable></el-table-column>

                <el-table-column label="Prequalification Event" min-width="130" prop="event" show-overflow-tooltip
                                 sortable></el-table-column>

                <el-table-column label="Prequalification Method" min-width="130" prop="method" show-overflow-tooltip
                                 sortable></el-table-column>
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
        </div>

        <step-form
            v-else
            ref="biddingForm"
            :data="selectedRow"
            :edit-mode.sync="editMode"
            :step-index="stepIndex"
            class="card"
            @change="onStepChanged"
        ></step-form>

        <document-action-confirm
            :action="selectedDocumentAction"
            :data="selectedRow"
            :visible.sync="showDocumentActionConfirm"
        ></document-action-confirm>

        <el-dialog :visible.sync="showRejectionDialog" title="Terminate Prequalification Confirmation" width="30%" :before-close="onCloseRejection">
            <p>Are you sure you want to reject this prequalification process?</p>
            <p>Please type the reason:</p>
            <el-input v-model="rejectionReason" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"></el-input>
            <div slot="footer">
                <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                        @click="onCloseRejection">
                    {{ $t('entity.action.cancel') }}
                </el-button>
                <el-button size="mini" style="margin-left: 0px;" type="danger" @click="confirmRejection">
                    <svg-icon name="icomoo/183-switch"></svg-icon>
                    Reject
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./prequalification.component.ts"></script>

<style lang="scss">
.compact .prequalification-process {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;
    /*
    .joined-vendor-dialog .el-table.vendor-list {
        td {
            height: 35px;
        }
    }
    */
}

.el-table__fixed,
.el-table__fixed-right {
    box-shadow: none;
}

.main {
    padding: 0px;

    .button {
        width: 100%;
    }
}

.toolbar {
    padding: 4px 16px;
}

.form-input {
    width: 100%;
}
</style>
