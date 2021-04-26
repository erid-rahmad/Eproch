<template>
    <div class="add-announcement">
        <el-row :gutter="columnSpacing">
            <el-col :span="24">
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="dialogTableVisible11 = true">
                    Sent <em class="el-icon-arrow-right"></em>
                </el-button>
            </el-col>
        </el-row>
        <el-divider content-position="left">
            <h4>Text Pengumuman</h4>
        </el-divider>
        <el-row :gutter="24">
            <el-col :xs="24" :sm="12" :lg="17">
                <el-form ref="form" :model="sizeForm" label-width="120px" size="mini">
                    <el-form-item label="Kode Tender">
                        <el-input v-model="sizeForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="Nama Tender">
                        <el-input v-model="sizeForm.q"></el-input>
                    </el-form-item>
                    <el-form-item label="Deskripsi">
                        <tiptap :editor="editor" @email="emailFromChild=$event "></tiptap>

                    </el-form-item>
                    <el-form-item size="large">

                        <el-button type="primary" size="mini" @click="onSubmit">Chose File</el-button>
                        <el-button type="primary" size="mini" @click="dialogTableVisible = true">View</el-button>
                    </el-form-item>
                </el-form>

                <el-divider content-position="left">
                    <h4>Data Rekanan</h4>
                </el-divider>
                <el-col :span="20">
                    <el-table v-loading="processing" ref="biddingSchedule" highlight-current-row border stripe size="mini" style="width: 100%; height: 100%" :data="gridData1">

                        <el-table-column min-width="30" label="No">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="100" prop="documentNo" label="Kode" />
                        <el-table-column min-width="100" prop="name" label="Nama Vendor" />
                    </el-table>
                </el-col>
            </el-col>

        </el-row>

        <template>
            <div>
                <el-dialog title="View" :visible.sync="dialogTableVisible">
                    <template>
                        <div>
                            <p>
                                <br>Kepada Bapak/Ibu Pimpinan
                                <br>PT Sistech
                                <br><span th:text="${BIDING_DATA.vendorSelection}"></span>
                                <br>Hal: Undangan Bidding
                                <br>Dengan hormat</p>
                            <p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut
                                . Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut
                                . Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan
                                sampaikan ke email eproc.berca.co.id </p>
                            <p>Hormat Kami
                                <br>Berca.co.id</p>
                        </div>
                    </template>
                </el-dialog>

            </div>
        </template>
        <template>
            <div>
                <el-dialog title="" :visible.sync="dialogTableVisible11">
                    <template>
                        <el-table ref="multipleTable" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
                            <el-table-column type="selection" width="55">
                            </el-table-column>
                            <el-table-column label="Date" width="120">
                                <template slot-scope="scope">{{ scope.row.date }}</template>
                            </el-table-column>
                            <el-table-column property="name" label="Name" width="120">
                            </el-table-column>
                            <el-table-column property="address" label="Address" show-overflow-tooltip>
                            </el-table-column>
                        </el-table>
                        <div style="margin-top: 20px">
                            <el-button @click="toggleSelection([tableData[1], tableData[2]])">Toggle selection status of second and third rows</el-button>
                            <el-button @click="toggleSelection()">Clear selection</el-button>
                        </div>
                    </template>
                    <template>
                        <div slot="footer">
                            <el-button type="primary" size="mini" @click="dialogTableVisible11  = false">Sent</el-button>
                        </div>
                    </template>
                </el-dialog>
            </div>
        </template>
    </div>
</template>
<script lang="ts" src="./add-announcement.component.ts"></script>
<style lang="scss">
    .compact .add-announcement {
        .el-table--mini {

            td,
            th {
                height: 35px;
            }
        }
    }

</style>
<style lang="scss" scoped>
    .add-announcement {
        .criteria-section {

            &:not(.criteria-0),
            >.el-col>.sub-criteria-section:not(.sub-0) {
                margin-top: 10px;
            }
        }
    }

</style>
<style lang="scss">
    /* Basic editor styles */
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
