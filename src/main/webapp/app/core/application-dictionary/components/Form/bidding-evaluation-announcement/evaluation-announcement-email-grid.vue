<template>
    <div class="app-container">
        <div v-if="pages===1">
            <el-col :span="24">
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="backtomain">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="dialogTableVisible11 = true">
                    Sent <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>

            <div class="header">
                <h2>BN-00001</h2>
                <p>Pengadaan Kendaraan Operasional</p>
            </div>





            <el-button class="button" icon="el-icon-plus" size="mini" type="primary" @click="pages=2"></el-button>

            <template>
                <el-table v-loading="processing" ref="biddingSchedule" highlight-current-row border stripe size="mini" style="width: 100%; height: 100%" :data="tableData1">

                    <el-table-column min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column min-width="120" prop="name" label="Vendor Name" />
                    <el-table-column min-width="100" prop="status" label="Evaluasi Status" />
                    <el-table-column min-width="60" sortable label="Action">
                        <template slot-scope="{ row }">
                            <el-button class="button" icon="el el-download-alt" size="mini" type="primary" @click="viewemail=true">
                                View
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
        </div>
        <div v-if="pages===2">
            <AddAnnouncementForm @back="back"></AddAnnouncementForm>
        </div>
        <div>
            <el-dialog title="" :visible.sync="dialogTableVisible11">
                <template>
                    <el-table ref="multipleTable" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="55">
                        </el-table-column>
                        <el-table-column property="name" label="Vendor Name" min-width="120">
                        </el-table-column>
                        <el-table-column property="status" label="Status" show-overflow-tooltip>
                        </el-table-column>
                    </el-table>
                </template>
                <template>
                    <div slot="footer">
                        <el-button type="primary" size="mini" @click="dialogTableVisible11  = false">Sent</el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
        <el-dialog title="View" :visible.sync="viewemail">
            <template>
                <div>
                    <br>Nomor :081<br>Klasifikasi :penting<br>Perihal:Pengumuman Hasil Evaluasi Sampil I <br>Nusu Dua 06 Desember-2019<br><br>Kepada:<br>Yth Pada Perserta Pengadaan
                    Barang/Jasa<br>PEngadaan A
                    <el-table ref="multipleTable" :data="winerTable" style="width: 100%" >   
                        <el-table-column property="name" label="Vendor Name" min-width="120">
                        </el-table-column>
                        <el-table-column property="status" label="Status" show-overflow-tooltip>
                        </el-table-column>
                        <el-table-column property="email" label="Email" show-overflow-tooltip>
                        </el-table-column>
                    </el-table>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./evaluation-announcement-email-grid.component.ts">
</script>

<style lang="scss">
    .header {
        text-align: center;
        font-size: 15px;
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

    .header {
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }

    .steps-control-btn-group {
        float: right;
        padding: 0 24px;
    }

    .ProseMirror {
        >*+* {
            margin-top: 0.75em;
        }

        ul,
        ol {
            padding: 0 1rem;
        }

        h1,
        h2,
        h3,
        h4,
        h5,
        h6 {
            line-height: 1.1;
        }

        code {
            background-color: rgba(#616161, 0.1);
            color: #616161;
        }

        pre {
            background: #0D0D0D;
            color: #FFF;
            font-family: 'JetBrainsMono', monospace;
            padding: 0.75rem 1rem;
            border-radius: 0.5rem;

            code {
                color: inherit;
                padding: 0;
                background: none;
                font-size: 0.8rem;
            }
        }

        img {
            max-width: 100%;
            height: auto;
        }

        blockquote {
            padding-left: 1rem;
            border-left: 2px solid rgba(#0D0D0D, 0.1);
        }

        hr {
            border: none;
            border-top: 2px solid rgba(#0D0D0D, 0.1);
            margin: 2rem 0;
        }
    }

</style>
