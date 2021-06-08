<template>
    <div class="app-container card-view bidding-announcement">
        <div class="toolbar">
            <el-col :span="24">
                <el-button icon="el-icon-close" plain size="mini" type="danger" @click="backtomain">
                    Back
                </el-button>
                <el-button
                    v-loading.fullscreen.lock="fullscreenLoading"
                    size="mini"
                    style="margin-left: 0px"
                    type="primary"
                    @click="saveDraft"
                >
                    Save Draft
                </el-button>
                <el-button
                    v-loading.fullscreen.lock="fullscreenLoading"
                    size="mini"
                    style="margin-left: 0px"
                    type="primary"
                    @click="recipientListVisible = true"
                >
                    Publish <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>
        </div>
        <div class="card" style="margin-top: 30px">
            <div>

                <div class="header">
                    <h2>{{ data.No }}</h2>
                    <p>{{ data.Name }}</p>
                </div>
                <el-form
                    ref="mainForm"
                    v-loading="loading"
                    :model="formData"
                    :rules="validationSchema"
                    label-position="left"
                    label-width="150px"
                    size="mini"
                >
                    <el-row :gutter="24">
                        <el-col :lg="12" :sm="12" :xl="12" :xs="12">
                            <el-form-item label="Announcement Date">
                                <template>
                                    <div class="block">
                                        <el-date-picker
                                            v-model="formData.publishDate"
                                            placeholder="Pick a day"
                                            type="date">
                                        </el-date-picker>
                                    </div>
                                </template>
                                <!--                <el-input v-model="formData.name"></el-input>-->
                            </el-form-item>
                        </el-col>
                        <el-col :lg="12" :sm="12" :xl="12" :xs="12">
                            <el-form-item label="Announcement Number">
                                <el-input v-model="formData.documentNo"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="24">
                        <el-col :lg="18" :sm="24" :xl="16" :xs="24">
                            <el-form-item label="Description" prop="description">
                                <html-editor v-loading="descriptionLoading" v-model="formData.description" size="mini"></html-editor>
                            </el-form-item>
                            <el-form-item style="margin-top: .5rem">
                                <el-button v-if="!hasAttachment" size="mini" type="primary"
                                           @click="attachmentFormVisible = true">
                                    <svg-icon name="icomoo/206-attachment"></svg-icon>
                                    Attachment
                                </el-button>
                                <el-button v-if="hasAttachment" icon="el-icon-view" size="mini" type="primary"
                                           @click="handlePreview">
                                    {{ formData.attachmentName }}
                                </el-button>
                                <el-button v-if="hasAttachment" icon="el-icon-close" size="mini" type="primary"
                                           @click="cancelAttachment"></el-button>
                                <el-button icon="el-icon-view" size="mini" type="primary"
                                           @click="emailPreviewVisible = true">
                                    Preview
                                </el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <template>
                    <el-table
                        v-loading="biddingEvalResultLoading"
                        :data="biddingEvalResult"
                        border
                        highlight-current-row
                        size="mini"
                        stripe
                        style="width: 100%; height: 100%"
                    >
                        <el-table-column label="No" min-width="30">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Vendor Name" min-width="120" prop="vendorName"/>
                        <el-table-column label="Evaluasi Status" min-width="100" prop="status"/>
                    </el-table>
                </template>
            </div>
            <div>
                <el-dialog :visible.sync="dialogTableVisible11" title="">
                    <template>
                        <el-table ref="multipleTable" :data="biddingEvalResult" style="width: 100%"
                                  @selection-change="handleSelectionChange">
                            <el-table-column type="selection" width="55"></el-table-column>
                            <el-table-column label="Vendor Name" min-width="120"
                                             property="vendorName"></el-table-column>
                            <el-table-column label="Status" property="status" show-overflow-tooltip></el-table-column>
                        </el-table>
                    </template>
                    <template>
                        <div slot="footer">
                            <el-button size="mini" type="primary" @click="dialogTableVisible11 = false">Sent</el-button>
                        </div>
                    </template>
                </el-dialog>
            </div>
            <el-dialog :visible.sync="viewemail" title="View">
                <template>
                    <div>
                        <br/>Nomor :081<br/>Klasifikasi :penting<br/>Perihal:Pengumuman Hasil Evaluasi Sampil I <br/>Nusu
                        Dua 06 Desember-2019<br/><br/>Kepada:<br/>Yth
                        Pada Perserta Pengadaan Barang/Jasa<br/>PEngadaan A
                        <el-table ref="multipleTable" :data="winerTable" style="width: 100%">
                            <el-table-column label="Vendor Name" min-width="120" property="name"></el-table-column>
                            <el-table-column label="Status" property="status" show-overflow-tooltip></el-table-column>
                            <el-table-column label="Email" property="email" show-overflow-tooltip></el-table-column>
                        </el-table>
                    </div>
                </template>
            </el-dialog>
            <el-dialog :show-close="false" :visible.sync="attachmentFormVisible" title="Add Attachment">
                <el-upload
                    ref="docUpload"
                    :accept="accept"
                    :action="action"
                    :before-upload="handleBeforeUpload"
                    :headers="uploadHeaders"
                    :limit="limit"
                    :on-error="onUploadError"
                    :on-exceed="handleExceed"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-success="onUploadSuccess"
                    auto-upload
                >
                    <el-button slot="trigger" icon="el-icon-search" type="primary">
                        Select File
                    </el-button>
                    <span slot="tip" class="el-upload__tip" style="margin-left: 10px">
                        Files with a size less than 5Mb
                    </span>
                </el-upload>

                <div slot="footer">
                    <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                               @click="attachmentFormVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                    <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary"
                               @click="saveAttachment">
                        {{ $t('entity.action.save') }}
                    </el-button>
                </div>
            </el-dialog>
            <el-dialog
                v-loading="publishing"
                :close-on-click-modal="!publishing"
                :close-on-press-escape="!publishing"
                :visible.sync="recipientListVisible"
                title="Publish Email"
                @open="retrieveEmailList"
            >
                <el-table v-loading="loadingEmailList" :data="emailList" border size="mini" stripe
                          @selection-change="onRecipientSelectionChanged">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column label="Name" min-width="150" property="name"
                                     show-overflow-tooltip></el-table-column>
                    <el-table-column label="Position" min-width="150" property="position"
                                     show-overflow-tooltip></el-table-column>
                    <el-table-column label="Email" min-width="150" property="email"
                                     show-overflow-tooltip></el-table-column>
                </el-table>
                <template>
                    <div slot="footer">
                        <el-button :loading="publishing" size="mini" type="primary" @click="publish">Send</el-button>
                    </div>
                </template>
            </el-dialog>

        </div>
    </div>
</template>

<script lang="ts" src="./evaluation-announcement-email-grid.component.ts"></script>

<style lang="scss">
.header {
    text-align: center;
    font-size: 15px;
    padding: 14px;
}

.compact .bidding-process {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    .joined-vendor-dialog .el-table.vendor-list {
        td {
            height: 35px;
        }
    }
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
