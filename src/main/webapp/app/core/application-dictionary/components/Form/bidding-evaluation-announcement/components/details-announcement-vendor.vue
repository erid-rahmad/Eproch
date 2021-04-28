<template>
    <div class="compact">
        <el-row>
            <el-row :gutter="columnSpacing">
                <el-col :span="24">
                    <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                        Back
                    </el-button>
                </el-col>
            </el-row>
        </el-row>
        <div class="body">
            <el-row :gutter="24" >
                <el-col :xs="24" :sm="12" :lg="8">
                    <el-form label-position="left" label-width="210px" size="mini">
                        <el-form-item label="Tanggal Mulai">
                            {{announcement.start}}
                        </el-form-item>
                        <el-form-item label="Tangaal Selesai">
                            {{announcement.end}}
                        </el-form-item>
                        <el-form-item label="Ringkasan Pengumuman">
                            {{announcement.announcement}}
                            <br>Nomor :081
                            <br>Klasifikasi :penting
                            <br>Perihal:Pengumuman Hasil Evaluasi Sampil I
                            <br>Nusu Dua 06 Desember-2019
                            <br><br>Kepada:
                            <br>Yth Pada Perserta Pengadaan Barang/Jasa
                            <br>PEngadaan A

                        </el-form-item>
                        <el-form-item label="Unduh Dokumen">
                            <el-button type="primary" plain size="mini" icon="el-icon-download" @click="back">{{announcement.doc}}</el-button>
                        </el-form-item>
                        <el-form-item label="Detail Evaluasi Sampul I">
                            <el-button type="primary" plain size="mini" icon="el-icon-view" @click="winerTableVisible=true" />
                        </el-form-item>
                    </el-form>
                </el-col>
            </el-row>
        </div>
        <el-dialog title="" :visible.sync="winerTableVisible">
            <template>
                <el-table ref="multipleTable" :data="winerTable" style="width: 100%" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column property="name" label="Vendor Name" min-width="120">
                    </el-table-column>
                    <el-table-column property="status" label="Status" show-overflow-tooltip>
                    </el-table-column>
                                        <el-table-column property="email" label="Email" show-overflow-tooltip>
                    </el-table-column>
                </el-table>
            </template>        
        </el-dialog>

    </div>
</template>
<script lang="ts" src="./details-announcement-vendor.component.ts"></script>
<style lang="scss">
    .compact 
    .details-announcement {
        .el-table--mini {

            td,
            th {
                height: 35px;
            }
        }
    }

</style>
<style lang="scss" scoped>
    .body {
        margin-top: 20px;
        margin-left: 20px;
    }

</style>
